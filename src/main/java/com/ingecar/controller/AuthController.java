package com.ingecar.controller;

import com.ingecar.entity.Usuario;
import com.ingecar.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank; // AÑADE ESTA IMPORTACIÓN
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/auth")
public class AuthController {
    
    @Autowired
    private UsuarioService usuarioService;
    
    @GetMapping("/login")
    public String loginForm(Model model) {
        if (!model.containsAttribute("loginRequest")) {
            model.addAttribute("loginRequest", new LoginRequest());
        }
        return "auth/login";
    }
    
    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("loginRequest") LoginRequest loginRequest,
                       BindingResult result,
                       HttpSession session,
                       Model model,
                       RedirectAttributes redirectAttributes) {
        
        if (result.hasErrors()) {
            return "auth/login";
        }
        
        Usuario usuario = usuarioService.autenticar(loginRequest.getNombreUsuario(), 
                                                   loginRequest.getContraseña());
        
        if (usuario != null && usuario.getEstado() == Usuario.Estado.activo) {
            session.setAttribute("usuario", usuario);
            session.setAttribute("rol", usuario.getRol().name());
            return "redirect:/"; // Cambiado de "/" a "/dashboard"
        } else {
            redirectAttributes.addFlashAttribute("error", 
                "Credenciales incorrectas o usuario inactivo");
            redirectAttributes.addFlashAttribute("loginRequest", loginRequest);
            return "redirect:/auth/login";
        }
    }
    
    @GetMapping("/logout")
    public String logout(HttpSession session, RedirectAttributes redirectAttributes) {
        session.invalidate();
        redirectAttributes.addFlashAttribute("success", "Sesión cerrada correctamente");
        return "redirect:/auth/login";
    }
    
    // Clase interna para validación de login
    public static class LoginRequest {
        @NotBlank(message = "Usuario es requerido")
        private String nombreUsuario;
        
        @NotBlank(message = "Contraseña es requerida")
        private String contraseña;
        
        // Getters y Setters
        public String getNombreUsuario() { return nombreUsuario; }
        public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }
        public String getContraseña() { return contraseña; }
        public void setContraseña(String contraseña) { this.contraseña = contraseña; }
    }
}