package com.ingecar.controller;

import com.ingecar.entity.DetalleServicio;
import com.ingecar.entity.OrdenServicio;
import com.ingecar.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/consulta")
public class ConsultaController {
    
    @Autowired
    private OrdenService ordenService;
    
    @Autowired
    private VehiculoService vehiculoService;
    
    @Autowired
    private DetalleServicioService detalleServicioService;
    
    @GetMapping
    public String consultaIndex() { 
        return "consulta/estado"; 
    }
    
    @GetMapping("/estado")
    public String estadoVehiculo() { 
        return "consulta/estado"; 
    }
    
    @PostMapping("/buscar")
    public String buscarPorPlaca(@RequestParam String placa, Model model) {
        String placaNormalizada = placa.trim().toUpperCase();
        var vehiculo = vehiculoService.obtenerPorPlaca(placaNormalizada);
        
        if (vehiculo == null) {
            model.addAttribute("error", "No se encontró vehículo con placa: " + placaNormalizada);
            return "consulta/estado";
        }
        
        var ordenes = ordenService.listarPorVehiculo(vehiculo.getIdVehiculo());
        
        if (ordenes.isEmpty()) {
            model.addAttribute("error", "El vehículo no tiene órdenes de servicio");
            model.addAttribute("vehiculo", vehiculo);
            return "consulta/estado";
        }
        
        OrdenServicio ultimaOrden = ordenes.get(ordenes.size() - 1);
        List<DetalleServicio> detallesServicio = detalleServicioService.listarPorOrden(ultimaOrden.getIdOrden());
        
        model.addAttribute("orden", ultimaOrden);
        model.addAttribute("vehiculo", vehiculo);
        model.addAttribute("detallesServicio", detallesServicio);
        
        return "consulta/resultado";
    }
}