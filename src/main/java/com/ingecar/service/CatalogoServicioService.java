package com.ingecar.service;

import com.ingecar.entity.CatalogoServicio;
import com.ingecar.repository.CatalogoServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CatalogoServicioService {
    
    @Autowired
    private CatalogoServicioRepository catalogoServicioRepository;
    
    public List<CatalogoServicio> listarTodos() {
        return catalogoServicioRepository.findAll();
    }
    
    public List<CatalogoServicio> listarActivos() {
        return catalogoServicioRepository.findByEstado(CatalogoServicio.Estado.activo);
    }
    
    public CatalogoServicio obtenerPorId(Integer id) {
        return catalogoServicioRepository.findById(id).orElse(null);
    }
    
    public CatalogoServicio guardar(CatalogoServicio servicio) {
        return catalogoServicioRepository.save(servicio);
    }
    
    public void eliminar(Integer id) {
        catalogoServicioRepository.deleteById(id);
    }
}