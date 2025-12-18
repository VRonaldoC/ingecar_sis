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

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired 
    private UsuarioService usuarioService;
    
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
            redirectAttributes.addFlashAttribute("error", "El nombre de usuario ya existe");
            return "redirect:/usuarios/nuevo";
        }
        usuarioService.guardar(usuario);
        redirectAttributes.addFlashAttribute("success", "Usuario guardado correctamente");
        return "redirect:/usuarios";
    }
    
    @GetMapping("/editar/{id}") 
    public String editarUsuario(@PathVariable Integer id, Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        if (!esAdministrador(session, redirectAttributes)) {
            return redirigirAPaginaAnterior();
        }
        Usuario usuario = usuarioService.obtenerPorId(id);
        if (usuario == null) {
            redirectAttributes.addFlashAttribute("error", "Usuario no encontrado");
            return "redirect:/usuarios";
        }
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
        redirectAttributes.addFlashAttribute("success", "Usuario eliminado correctamente");
        return "redirect:/usuarios";
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