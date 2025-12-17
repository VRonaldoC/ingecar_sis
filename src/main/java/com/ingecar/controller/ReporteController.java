package com.ingecar.controller;
import com.ingecar.entity.OrdenServicio;
import com.ingecar.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
@Controller
@RequestMapping("/reportes")
public class ReporteController {
    @Autowired private OrdenService ordenService;
    @Autowired private ClienteService clienteService;
    private void verificarAutenticacion(HttpSession session) {
        if (session.getAttribute("usuario") == null) throw new RuntimeException("Usuario no autenticado");
    }
    @GetMapping
    public String reportes(Model model, HttpSession session) {
        verificarAutenticacion(session);
        model.addAttribute("totalClientes", clienteService.contarClientes());
        model.addAttribute("ordenesPendientes", ordenService.contarPorEstado(OrdenServicio.EstadoOrden.pendiente));
        model.addAttribute("ordenesEnProceso", ordenService.contarPorEstado(OrdenServicio.EstadoOrden.en_proceso));
        model.addAttribute("ordenesCompletadas", ordenService.contarPorEstado(OrdenServicio.EstadoOrden.completado));
        model.addAttribute("ordenesEntregadas", ordenService.contarPorEstado(OrdenServicio.EstadoOrden.entregado));
        return "reportes/index";
    }
    @GetMapping("/servicios")
    public String reporteServicios(
            @RequestParam(required = false) String fechaInicio,
            @RequestParam(required = false) String fechaFin,
            Model model, HttpSession session) {
        verificarAutenticacion(session);
        List<OrdenServicio> todasOrdenes = ordenService.listarTodas();
        List<OrdenServicio> ordenesFiltradas;
        if (fechaInicio != null && !fechaInicio.isEmpty() && 
            fechaFin != null && !fechaFin.isEmpty()) {
            LocalDate inicio = LocalDate.parse(fechaInicio);
            LocalDate fin = LocalDate.parse(fechaFin);
            ordenesFiltradas = todasOrdenes.stream()
                .filter(orden -> {
                    LocalDate fechaOrden = orden.getFechaIngreso().toLocalDate();
                    return (fechaOrden.isEqual(inicio) || fechaOrden.isAfter(inicio)) &&
                           (fechaOrden.isEqual(fin) || fechaOrden.isBefore(fin.plusDays(1)));
                })
                .toList();
            model.addAttribute("fechaInicio", fechaInicio);
            model.addAttribute("fechaFin", fechaFin);
            model.addAttribute("mostrandoFiltrado", true);
        } else {
            ordenesFiltradas = todasOrdenes;
            model.addAttribute("mostrandoFiltrado", false);
        }
        model.addAttribute("ordenes", ordenesFiltradas);
        return "reportes/servicios";
    }
}