package com.ingecar.repository;
import com.ingecar.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    Optional<Cliente> findByDni(String dni);
    List<Cliente> findByNombreCompletoContaining(String nombre);
    List<Cliente> findByTelefonoContaining(String telefono);
    @Query("SELECT COUNT(c) FROM Cliente c") Long countTotalClientes();
    @Query("SELECT c FROM Cliente c WHERE c.estado = 'activo'") List<Cliente> findClientesActivos();
    @Query("SELECT c FROM Cliente c WHERE EXISTS (SELECT 1 FROM Vehiculo v WHERE v.cliente = c)") List<Cliente> findClientesConVehiculos();
}