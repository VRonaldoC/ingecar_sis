package com.ingecar.controller;

import com.ingecar.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UtilController {
    
    @Autowired private OrdenService ordenService;
    
    @GetMapping("/recalcular-costos")
    public String recalcularTodosCostos() {
        var ordenes = ordenService.listarTodas();
        for (var orden : ordenes) {
            ordenService.actualizarCostoTotal(orden.getIdOrden());
        }
        return "redirect:/ordenes";
    }
}