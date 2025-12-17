package com.ingecar.repository;
import com.ingecar.entity.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;
public interface VehiculoRepository extends JpaRepository<Vehiculo, Integer> {
    Optional<Vehiculo> findByPlaca(String placa);
    List<Vehiculo> findByClienteIdCliente(Integer idCliente);
    List<Vehiculo> findByMarcaContaining(String marca);
    List<Vehiculo> findByModeloContaining(String modelo);
    @Query("SELECT COUNT(v) FROM Vehiculo v WHERE v.cliente.idCliente = :idCliente") Long countByClienteId(@Param("idCliente") Integer idCliente);
    @Query("SELECT DISTINCT v FROM Vehiculo v JOIN v.ordenes o WHERE o.estado = 'pendiente'") List<Vehiculo> findVehiculosConOrdenesPendientes();
}