package com.ingecar.repository;
import com.ingecar.entity.DetalleServicio;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface DetalleServicioRepository extends JpaRepository<DetalleServicio, Integer> {
    List<DetalleServicio> findByOrdenIdOrden(Integer idOrden);
    void deleteByOrdenIdOrden(Integer idOrden);
}