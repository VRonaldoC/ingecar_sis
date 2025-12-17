package com.ingecar.service;

import com.ingecar.entity.OrdenServicio;
import com.ingecar.repository.OrdenServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrdenService {
    @Autowired private OrdenServicioRepository ordenRepository;
    @Autowired private DetalleServicioService detalleServicioService;
    
    public List<OrdenServicio> listarTodas() { 
        return ordenRepository.findAll(); 
    }
    
    public OrdenServicio obtenerPorId(Integer id) { 
        return ordenRepository.findById(id).orElse(null); 
    }
    
    public List<OrdenServicio> listarPorTecnico(Integer idTecnico) { 
        return ordenRepository.findByTecnicoAsignadoIdUsuario(idTecnico); 
    }
    
    public List<OrdenServicio> listarPorEstado(OrdenServicio.EstadoOrden estado) { 
        return ordenRepository.findByEstado(estado); 
    }
    
    public List<OrdenServicio> listarPorCliente(Integer idCliente) { 
        return ordenRepository.findByClienteId(idCliente); 
    }
    
    public List<OrdenServicio> listarPorVehiculo(Integer idVehiculo) { 
        return ordenRepository.findByVehiculoIdVehiculo(idVehiculo); 
    }
    
    public OrdenServicio guardar(OrdenServicio orden) { 
        return ordenRepository.save(orden); 
    }
    
    public void actualizarEstado(Integer idOrden, OrdenServicio.EstadoOrden estado) {
        OrdenServicio orden = obtenerPorId(idOrden);
        if (orden != null) {
            orden.setEstado(estado);
            if (estado == OrdenServicio.EstadoOrden.entregado) {
                orden.setFechaSalidaEstimada(LocalDateTime.now());
            }
            ordenRepository.save(orden);
        }
    }
    
    public void actualizarDiagnostico(Integer idOrden, String diagnostico) {
        OrdenServicio orden = obtenerPorId(idOrden);
        if (orden != null) { 
            orden.setDiagnostico(diagnostico); 
            ordenRepository.save(orden); 
        }
    }
    
    public void asignarTecnico(Integer idOrden, Integer idTecnico, UsuarioService usuarioService) {
        OrdenServicio orden = obtenerPorId(idOrden);
        if (orden != null) { 
            orden.setTecnicoAsignado(usuarioService.obtenerPorId(idTecnico)); 
            ordenRepository.save(orden); 
        }
    }
    
    public long contarPorEstado(OrdenServicio.EstadoOrden estado) { 
        return ordenRepository.countByEstado(estado); 
    }
    
    public List<OrdenServicio> buscarPorFechas(LocalDateTime fechaInicio, LocalDateTime fechaFin) { 
        return ordenRepository.findByFechaIngresoBetween(fechaInicio, fechaFin); 
    }
    
    @Transactional
    public void actualizarCostoTotal(Integer idOrden) {
        OrdenServicio orden = obtenerPorId(idOrden);
        if (orden != null) {
            Double total = detalleServicioService.calcularTotalOrden(idOrden);
            orden.setCostoTotal(total != null ? total : 0.0);
            ordenRepository.save(orden);
        }
    }

    @Transactional
    public void eliminar(Integer id) {
        detalleServicioService.eliminarPorOrden(id);
        ordenRepository.deleteById(id);
    }

}