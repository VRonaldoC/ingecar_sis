package com.ingecar.controller;
import com.ingecar.entity.Cliente;
import com.ingecar.entity.Usuario;
import com.ingecar.entity.Vehiculo;
import com.ingecar.service.ClienteService;
import com.ingecar.service.VehiculoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
@Controller
@RequestMapping("/vehiculos")
public class VehiculoController {
    @Autowired private VehiculoService vehiculoService;
    @Autowired private ClienteService clienteService;
    private void verificarAutenticacion(HttpSession session) {
        if (session.getAttribute("usuario") == null) throw new RuntimeException("Usuario no autenticado");
    }
    @GetMapping("/nuevo/{dni}") public String nuevoVehiculo(@PathVariable String dni, Model model, HttpSession session) {
        verificarAutenticacion(session);
        Cliente cliente = clienteService.obtenerPorDni(dni);
        if (cliente == null) return "redirect:/clientes";
        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setCliente(cliente);
        model.addAttribute("vehiculo", vehiculo);
        return "vehiculos/form";
    }
    @PostMapping("/guardar") public String guardarVehiculo(@ModelAttribute Vehiculo vehiculo, HttpSession session) {
        verificarAutenticacion(session);
        vehiculoService.guardar(vehiculo);
        Cliente cliente = clienteService.obtenerPorId(vehiculo.getCliente().getIdCliente());
        if (cliente != null && cliente.getDni() != null) return "redirect:/clientes/detalle/" + cliente.getDni();
        return "redirect:/clientes";
    }
    @GetMapping("/editar/{id}") public String editarVehiculo(@PathVariable Integer id, Model model, HttpSession session) {
        verificarAutenticacion(session);
        Vehiculo vehiculo = vehiculoService.obtenerPorId(id);
        if (vehiculo == null) return "redirect:/clientes";
        model.addAttribute("vehiculo", vehiculo);
        return "vehiculos/form";
    }
    @GetMapping("/eliminar/{id}") public String eliminarVehiculo(@PathVariable Integer id, HttpSession session) {
        verificarAutenticacion(session);
        Vehiculo vehiculo = vehiculoService.obtenerPorId(id);
        if (vehiculo == null) return "redirect:/clientes";
        String dni = vehiculo.getCliente().getDni();
        vehiculoService.eliminar(id);
        return "redirect:/clientes/detalle/" + dni;
    }
    @GetMapping("/detalle/{id}") public String detalleVehiculo(@PathVariable Integer id, Model model, HttpSession session) {
        verificarAutenticacion(session);
        Vehiculo vehiculo = vehiculoService.obtenerPorId(id);
        if (vehiculo == null) return "redirect:/clientes";
        model.addAttribute("vehiculo", vehiculo);
        return "vehiculos/detalle";
    }
}