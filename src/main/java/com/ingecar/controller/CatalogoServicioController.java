package com.ingecar.controller;

import com.ingecar.entity.CatalogoServicio;
import com.ingecar.entity.Usuario;
import com.ingecar.service.CatalogoServicioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/catalogos")
public class CatalogoServicioController {
    
    @Autowired
    private CatalogoServicioService catalogoServicioService;
    
    private void verificarAutenticacion(HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) {
            throw new RuntimeException("Usuario no autenticado");
        }
    }
    
    @GetMapping
    public String listarServicios(Model model, HttpSession session) {
        verificarAutenticacion(session);
        
        List<CatalogoServicio> servicios = catalogoServicioService.listarTodos();
        model.addAttribute("servicios", servicios);
        return "catalogos/listar";
    }
    
    @GetMapping("/nuevo")
    public String nuevoServicio(Model model, HttpSession session) {
        verificarAutenticacion(session);
        
        model.addAttribute("servicio", new CatalogoServicio());
        return "catalogos/form";
    }
    
    @PostMapping("/guardar")
    public String guardarServicio(@ModelAttribute CatalogoServicio servicio,
                                 HttpSession session) {
        verificarAutenticacion(session);
        
        catalogoServicioService.guardar(servicio);
        return "redirect:/catalogos";
    }
    
    @GetMapping("/editar/{id}")
    public String editarServicio(@PathVariable Integer id, Model model, HttpSession session) {
        verificarAutenticacion(session);
        
        CatalogoServicio servicio = catalogoServicioService.obtenerPorId(id);
        if (servicio == null) {
            return "redirect:/catalogos";
        }
        
        model.addAttribute("servicio", servicio);
        return "catalogos/form";
    }
    
    @GetMapping("/eliminar/{id}")
    public String eliminarServicio(@PathVariable Integer id, HttpSession session) {
        verificarAutenticacion(session);
        
        catalogoServicioService.eliminar(id);
        return "redirect:/catalogos";
    }
}