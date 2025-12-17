package com.ingecar.repository;
import com.ingecar.entity.OrdenServicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;
import java.util.List;
public interface OrdenServicioRepository extends JpaRepository<OrdenServicio, Integer> {
    List<OrdenServicio> findByTecnicoAsignadoIdUsuario(Integer idTecnico);
    List<OrdenServicio> findByEstado(OrdenServicio.EstadoOrden estado);
    List<OrdenServicio> findByVehiculoIdVehiculo(Integer idVehiculo);
    @Query("SELECT o FROM OrdenServicio o WHERE o.vehiculo.cliente.idCliente = :idCliente") 
    List<OrdenServicio> findByClienteId(@Param("idCliente") Integer idCliente);
    @Query("SELECT o FROM OrdenServicio o WHERE o.tecnicoAsignado.idUsuario = :idTecnico AND o.estado = 'pendiente'") 
    List<OrdenServicio> findPendientesByTecnico(@Param("idTecnico") Integer idTecnico);
    @Query("SELECT o FROM OrdenServicio o WHERE o.tecnicoAsignado.idUsuario = :idTecnico AND o.estado = 'en_proceso'") 
    List<OrdenServicio> findEnProcesoByTecnico(@Param("idTecnico") Integer idTecnico);
    @Query("SELECT o FROM OrdenServicio o WHERE o.fechaIngreso BETWEEN :fechaInicio AND :fechaFin") 
    List<OrdenServicio> findByFechaIngresoBetween(@Param("fechaInicio") LocalDateTime fechaInicio, @Param("fechaFin") LocalDateTime fechaFin);
    @Query("SELECT COUNT(o) FROM OrdenServicio o WHERE o.estado = :estado") 
    Long countByEstado(@Param("estado") OrdenServicio.EstadoOrden estado);
}