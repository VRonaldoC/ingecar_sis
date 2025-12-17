package com.ingecar.controller;

import com.ingecar.entity.*;
import com.ingecar.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.stream.Collectors;

import java.util.List;

@Controller
@RequestMapping("/ordenes")
public class OrderController {
    
    @Autowired private CatalogoServicioService catalogoServicioService;
    @Autowired private VehiculoService vehiculoService;
    @Autowired private UsuarioService usuarioService;
    @Autowired private OrdenService ordenService;
    @Autowired private DetalleServicioService detalleServicioService;
    @Autowired private ClienteService clienteService;
    
    private void verificarAutenticacion(HttpSession session) {
        if (session.getAttribute("usuario") == null) throw new RuntimeException("Usuario no autenticado");
    }
    
    @GetMapping 
    public String listarOrdenes(Model model, HttpSession session) {
        verificarAutenticacion(session);
        model.addAttribute("ordenes", ordenService.listarTodas());
        return "ordenes/listar";
    }
    
    @GetMapping("/nueva/{placa}") 
    public String nuevaOrden(@PathVariable String placa, Model model, HttpSession session) {
        verificarAutenticacion(session);
        Vehiculo vehiculo = vehiculoService.obtenerPorPlaca(placa);
        if (vehiculo == null) return "redirect:/clientes";
        if (vehiculo.getCliente() == null) return "redirect:/vehiculos/editar/" + vehiculo.getIdVehiculo() + "?error=SinCliente";
        
        OrdenServicio orden = new OrdenServicio();
        orden.setVehiculo(vehiculo);
        orden.setCliente(vehiculo.getCliente());
        
        Usuario usuarioSesion = (Usuario) session.getAttribute("usuario");
        if (usuarioSesion != null && usuarioSesion.isRecepcionista()) {
            orden.setUsuarioRecepcion(usuarioSesion);
        }
        
        model.addAttribute("orden", orden);
        model.addAttribute("tecnicos", usuarioService.listarTecnicosActivos());
        model.addAttribute("servicios", catalogoServicioService.listarActivos());
        model.addAttribute("recepcionistas", usuarioService.listarRecepcionistasActivos());
        return "ordenes/form";
    }
    
    @PostMapping("/guardar") 
    public String guardarOrden(@ModelAttribute OrdenServicio orden, 
                              @RequestParam(required = false) List<Integer> serviciosSeleccionados,
                              HttpSession session) {
        verificarAutenticacion(session);
        
        if (orden.getTecnicoAsignado() != null && orden.getTecnicoAsignado().getIdUsuario() != null) {
            if (orden.getTecnicoAsignado().getIdUsuario() == 0) {
                orden.setTecnicoAsignado(null);
            } else {
                Usuario tecnico = usuarioService.obtenerPorId(orden.getTecnicoAsignado().getIdUsuario());
                orden.setTecnicoAsignado(tecnico);
            }
        }
        
        if (orden.getUsuarioRecepcion() != null && orden.getUsuarioRecepcion().getIdUsuario() != null) {
            if (orden.getUsuarioRecepcion().getIdUsuario() == 0) {
                orden.setUsuarioRecepcion(null);
            } else {
                Usuario recepcionista = usuarioService.obtenerPorId(orden.getUsuarioRecepcion().getIdUsuario());
                orden.setUsuarioRecepcion(recepcionista);
            }
        }
        
        orden = ordenService.guardar(orden);
        
        if (serviciosSeleccionados != null && !serviciosSeleccionados.isEmpty()) {
            for (Integer idServicio : serviciosSeleccionados) {
                CatalogoServicio servicio = catalogoServicioService.obtenerPorId(idServicio);
                if (servicio != null) {
                    DetalleServicio detalle = new DetalleServicio(orden, servicio, 1);
                    detalleServicioService.guardar(detalle);
                }
            }
            ordenService.actualizarCostoTotal(orden.getIdOrden());
        }
        
        return "redirect:/ordenes/id/" + orden.getIdOrden();
    }
    
    @GetMapping("/detalle/dni/{dni}")
    public String detalleOrdenPorDni(@PathVariable String dni, Model model, HttpSession session) {
        verificarAutenticacion(session);
        
        Cliente cliente = clienteService.obtenerPorDni(dni);
        if (cliente == null) {
            return "redirect:/ordenes?error=No existe cliente con DNI: " + dni;
        }
        
        List<OrdenServicio> ordenesCliente = ordenService.listarPorCliente(cliente.getIdCliente());
        
        if (ordenesCliente.isEmpty()) {
            return "redirect:/ordenes?error=El cliente " + cliente.getNombreCompleto() + " no tiene órdenes";
        }
        
        OrdenServicio orden = ordenesCliente.get(ordenesCliente.size() - 1);
        
        model.addAttribute("orden", orden);
        model.addAttribute("detalles", detalleServicioService.listarPorOrden(orden.getIdOrden()));
        model.addAttribute("todasOrdenesCliente", ordenesCliente);
        model.addAttribute("cliente", cliente);
        
        return "ordenes/detalle";
    }
    
    @GetMapping("/detalle/id/{id}")
    public String detalleOrdenPorId(@PathVariable Integer id, Model model, HttpSession session) {
        verificarAutenticacion(session);
        OrdenServicio orden = ordenService.obtenerPorId(id);
        if (orden == null) return "redirect:/ordenes?error=No existe orden #" + id;
        
        if (orden.getCliente() != null) {
            List<OrdenServicio> ordenesCliente = ordenService.listarPorCliente(orden.getCliente().getIdCliente());
            model.addAttribute("todasOrdenesCliente", ordenesCliente);
        }
        
        model.addAttribute("orden", orden);
        model.addAttribute("detalles", detalleServicioService.listarPorOrden(id));
        return "ordenes/detalle";
    }
    
    @GetMapping("/id/{id}")
    public String detalleOrdenPorId2(@PathVariable Integer id, Model model, HttpSession session) {
        return detalleOrdenPorId(id, model, session);
    }
    
    @GetMapping("/actualizar-estado/{id}/{estado}") 
    public String actualizarEstado(@PathVariable Integer id,
                                   @PathVariable OrdenServicio.EstadoOrden estado, 
                                   HttpSession session) {
        verificarAutenticacion(session);
        ordenService.actualizarEstado(id, estado);
        return "redirect:/ordenes/id/" + id;
    }
    
    @GetMapping("/tecnico") 
    public String ordenesTecnico(Model model, HttpSession session) {
        verificarAutenticacion(session);
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null || !usuario.isTecnico()) return "redirect:/auth/login";
        List<OrdenServicio> ordenesAsignadas = ordenService.listarPorTecnico(usuario.getIdUsuario());
        model.addAttribute("ordenes", ordenesAsignadas);
        return "ordenes/tecnico";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditarOrden(@PathVariable Integer id, Model model, HttpSession session) {
        verificarAutenticacion(session);
        
        OrdenServicio orden = ordenService.obtenerPorId(id);
        if (orden == null) {
            return "redirect:/ordenes?error=Orden no encontrada";
        }
        
        model.addAttribute("orden", orden);
        model.addAttribute("tecnicos", usuarioService.listarTecnicosActivos());
        model.addAttribute("servicios", catalogoServicioService.listarActivos());
        model.addAttribute("recepcionistas", usuarioService.listarRecepcionistasActivos());
        
        // Si quieres mostrar los servicios ya seleccionados
        List<DetalleServicio> detalles = detalleServicioService.listarPorOrden(id);
        List<Integer> serviciosSeleccionados = detalles.stream()
            .map(d -> d.getServicio().getIdServicio())
            .collect(Collectors.toList());
        
        model.addAttribute("serviciosSeleccionados", serviciosSeleccionados);
        
        return "ordenes/form"; // Mismo template que para nueva orden
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarOrden(@PathVariable Integer id, HttpSession session, RedirectAttributes redirectAttributes) {
        verificarAutenticacion(session);
        
        try {
            ordenService.eliminar(id);
            redirectAttributes.addFlashAttribute("mensaje", "Orden eliminada exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar la orden: " + e.getMessage());
        }
        
        return "redirect:/ordenes";
    }
}