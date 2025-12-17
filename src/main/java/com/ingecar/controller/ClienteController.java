package com.ingecar.controller;

import com.ingecar.entity.Cliente;
import com.ingecar.entity.Vehiculo;
import com.ingecar.service.ClienteService;
import com.ingecar.service.VehiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;

@Controller
@RequestMapping("/clientes")
public class ClienteController {
    @Autowired private ClienteService clienteService;
    @Autowired private VehiculoService vehiculoService;
    
    @GetMapping 
    public ModelAndView listarClientes() {
        ModelAndView mav = new ModelAndView("clientes/listar.html");
        mav.addObject("clientes", clienteService.listarTodos());
        return mav;
    }
    
    @GetMapping("/nuevo") 
    public ModelAndView nuevoCliente() {
        ModelAndView mav = new ModelAndView("clientes/form.html");
        mav.addObject("cliente", new Cliente());
        return mav;
    }
    
    @PostMapping("/guardar") 
    public String guardar(@ModelAttribute Cliente cliente, Model model) {
        Cliente clienteExistente = clienteService.obtenerPorDni(cliente.getDni());
        
        if (clienteExistente != null && 
            !clienteExistente.getIdCliente().equals(cliente.getIdCliente())) {
            model.addAttribute("error", "El DNI " + cliente.getDni() + " ya está registrado");
            model.addAttribute("cliente", cliente);
            return "clientes/form.html";
        }
        
        clienteService.guardar(cliente);
        return "redirect:/clientes";
    }
    
    @GetMapping("/editar/{id}") 
    public ModelAndView editar(@PathVariable Integer id) {
        Cliente cliente = clienteService.obtenerPorId(id);
        ModelAndView mav = new ModelAndView("clientes/form.html");
        mav.addObject("cliente", cliente);
        return mav;
    }
    
    @GetMapping("/eliminar/{id}") 
    public String eliminar(@PathVariable Integer id) {
        clienteService.eliminar(id);
        return "redirect:/clientes";
    }
    
    @GetMapping("/detalle/{dni}") 
    public ModelAndView detallePorDni(@PathVariable String dni) {
        ModelAndView mav = new ModelAndView("clientes/detalle.html");
        Cliente cliente = clienteService.obtenerPorDni(dni);
        if (cliente == null) {
            mav.addObject("error", "No existe cliente con DNI: " + dni);
            mav.setViewName("clientes/listar.html");
            return mav;
        }
        List<Vehiculo> vehiculos = vehiculoService.listarPorCliente(cliente.getIdCliente());
        mav.addObject("cliente", cliente);
        mav.addObject("vehiculos", vehiculos);
        return mav;
    }
}