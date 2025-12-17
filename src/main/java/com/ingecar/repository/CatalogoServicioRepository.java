package com.ingecar.repository;

import com.ingecar.entity.CatalogoServicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CatalogoServicioRepository extends JpaRepository<CatalogoServicio, Integer> {
    List<CatalogoServicio> findByEstado(CatalogoServicio.Estado estado);
}