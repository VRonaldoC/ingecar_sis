package com.ingecar.repository;
import com.ingecar.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Usuario findByNombreUsuario(String nombreUsuario);
    Usuario findByNombreUsuarioAndPassword(String nombreUsuario, String password);
    List<Usuario> findByRolAndEstado(Usuario.Rol rol, Usuario.Estado estado);
    
    @Query("SELECT u FROM Usuario u WHERE u.rol = 'tecnico' AND u.estado = 'activo'")
    List<Usuario> findTecnicosActivos();
    
    @Query("SELECT u FROM Usuario u WHERE u.rol = 'recepcionista' AND u.estado = 'activo'")
    List<Usuario> findRecepcionistasActivos();
}