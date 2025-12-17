package com.ingecar.service;

import com.ingecar.entity.DetalleServicio;
import com.ingecar.repository.DetalleServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetalleServicioService {
    @Autowired private DetalleServicioRepository detalleServicioRepository;
    
    public List<DetalleServicio> listarPorOrden(Integer idOrden) { 
        return detalleServicioRepository.findByOrdenIdOrden(idOrden); 
    }
    
    public DetalleServicio guardar(DetalleServicio detalle) { 
        return detalleServicioRepository.save(detalle);
    }
    
    public void eliminar(Integer id) { 
        detalleServicioRepository.deleteById(id); 
    }
    
    public Double calcularTotalOrden(Integer idOrden) {
        List<DetalleServicio> detalles = listarPorOrden(idOrden);
        return detalles.stream()
            .mapToDouble(d -> d.getSubtotal() != null ? d.getSubtotal() : 0.0)
            .sum();
    }

    public void eliminarPorOrden(Integer ordenId) {
                List<DetalleServicio> detalles = detalleServicioRepository.findByOrdenIdOrden(ordenId);
        detalleServicioRepository.deleteAll(detalles);
    }
}