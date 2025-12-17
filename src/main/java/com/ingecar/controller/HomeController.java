package com.ingecar.controller;

import com.ingecar.entity.Usuario;
import com.ingecar.service.ClienteService;
import com.ingecar.service.OrdenService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    
    @Autowired
    private ClienteService clienteService;
    
    @Autowired
    private OrdenService ordenService;
    
    @GetMapping("/")
    public String home(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        
        if (usuario == null) {
            return "redirect:/auth/login";
        }
        
        model.addAttribute("totalClientes", clienteService.contarClientes());
        model.addAttribute("ordenesPendientes", ordenService.contarPorEstado(com.ingecar.entity.OrdenServicio.EstadoOrden.pendiente));
        model.addAttribute("ordenesEnProceso", ordenService.contarPorEstado(com.ingecar.entity.OrdenServicio.EstadoOrden.en_proceso));
        model.addAttribute("ordenesCompletadas", ordenService.contarPorEstado(com.ingecar.entity.OrdenServicio.EstadoOrden.completado));
        model.addAttribute("ordenesEntregadas", ordenService.contarPorEstado(com.ingecar.entity.OrdenServicio.EstadoOrden.entregado));
        
        model.addAttribute("usuario", usuario);
        return "index";
    }
}