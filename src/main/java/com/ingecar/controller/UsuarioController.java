package com.ingecar.controller;
import com.ingecar.entity.Usuario;
import com.ingecar.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired private UsuarioService usuarioService;
    
    @GetMapping 
    public String listarUsuarios(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        if (!esAdministrador(session, redirectAttributes)) {
            return redirigirAPaginaAnterior();
        }
        model.addAttribute("usuarios", usuarioService.listarTodos());
        model.addAttribute("roles", Usuario.Rol.values());
        model.addAttribute("estados", Usuario.Estado.values());
        return "usuarios/listar";
    }
    
    @GetMapping("/nuevo") 
    public String nuevoUsuario(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        if (!esAdministrador(session, redirectAttributes)) {
            return redirigirAPaginaAnterior();
        }
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("roles", Usuario.Rol.values());
        return "usuarios/form";
    }
    
    @PostMapping("/guardar") 
    public String guardarUsuario(@ModelAttribute Usuario usuario, HttpSession session, RedirectAttributes redirectAttributes) {
        if (!esAdministrador(session, redirectAttributes)) {
            return redirigirAPaginaAnterior();
        }
        if (usuario.getIdUsuario() == null && usuarioService.existeNombreUsuario(usuario.getNombreUsuario())) {
            throw new RuntimeException("El nombre de usuario ya existe");
        }
        usuarioService.guardar(usuario);
        return "redirect:/usuarios";
    }
    
    @GetMapping("/editar/{id}") 
    public String editarUsuario(@PathVariable Integer id, Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        if (!esAdministrador(session, redirectAttributes)) {
            return redirigirAPaginaAnterior();
        }
        Usuario usuario = usuarioService.obtenerPorId(id);
        if (usuario == null) return "redirect:/usuarios";
        model.addAttribute("usuario", usuario);
        model.addAttribute("roles", Usuario.Rol.values());
        return "usuarios/form";
    }
    
    @GetMapping("/eliminar/{id}") 
    public String eliminarUsuario(@PathVariable Integer id, HttpSession session, RedirectAttributes redirectAttributes) {
        if (!esAdministrador(session, redirectAttributes)) {
            return redirigirAPaginaAnterior();
        }
        Usuario usuarioSesion = (Usuario) session.getAttribute("usuario");
        if (usuarioSesion.getIdUsuario().equals(id)) {
            redirectAttributes.addFlashAttribute("error", "No puede eliminar su propio usuario");
            return "redirect:/usuarios";
        }
        usuarioService.eliminar(id);
        return "redirect:/usuarios";
    }
    
    @GetMapping("/cambiar-password") 
    public String cambiarPasswordForm(Model model, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) return "redirect:/auth/login";
        model.addAttribute("usuario", usuario);
        return "usuarios/cambiar-password";
    }
    
    @PostMapping("/cambiar-password") 
    public String cambiarPassword(@RequestParam String passwordActual,
                                 @RequestParam String passwordNueva, @RequestParam String passwordConfirmar,
                                 HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) return "redirect:/auth/login";
        if (!usuario.getContraseña().equals(passwordActual)) {
            model.addAttribute("error", "Contraseña actual incorrecta");
            return "usuarios/cambiar-password";
        }
        if (!passwordNueva.equals(passwordConfirmar)) {
            model.addAttribute("error", "Las nuevas contraseñas no coinciden");
            return "usuarios/cambiar-password";
        }
        usuario.setContraseña(passwordNueva);
        usuarioService.guardar(usuario);
        model.addAttribute("success", "Contraseña actualizada correctamente");
        return "usuarios/cambiar-password";
    }
    
    private boolean esAdministrador(HttpSession session, RedirectAttributes redirectAttributes) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null || !usuario.isAdministrador()) {
            redirectAttributes.addFlashAttribute("error", 
                "Acceso denegado. Solo los administradores pueden acceder a la sección de Usuarios.");
            return false;
        }
        return true;
    }
    
    private String redirigirAPaginaAnterior() {
        HttpServletRequest request = 
            ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String referer = request.getHeader("Referer");
        
        if (referer != null && !referer.isEmpty()) {
            return "redirect:" + referer;
        } else {
            return "redirect:/";
        }
    }
}