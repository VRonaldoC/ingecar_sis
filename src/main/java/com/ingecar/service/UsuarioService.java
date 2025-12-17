package com.ingecar.service;
import com.ingecar.entity.Usuario;
import com.ingecar.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UsuarioService {
    @Autowired private UsuarioRepository usuarioRepository;
    
    public Usuario autenticar(String nombreUsuario, String contraseña) {
        Usuario usuario = obtenerPorNombreUsuario(nombreUsuario);
        if (usuario != null && usuario.getContraseña().equals(contraseña)) {
            return usuario;
        }
        return null;
    }
    
    public Usuario obtenerPorId(Integer id) {
        return usuarioRepository.findById(id).orElse(null);
    }
    
    public Usuario obtenerPorNombreUsuario(String nombreUsuario) {
        return usuarioRepository.findByNombreUsuario(nombreUsuario);
    }
    
    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }
    
    public List<Usuario> listarTecnicosActivos() {
        return usuarioRepository.findByRolAndEstado(Usuario.Rol.tecnico, Usuario.Estado.activo);
    }
    
    public List<Usuario> listarRecepcionistasActivos() {
        return usuarioRepository.findByRolAndEstado(Usuario.Rol.recepcionista, Usuario.Estado.activo);
    }
    
    public Usuario guardar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }
    
    public void eliminar(Integer id) {
        usuarioRepository.deleteById(id);
    }
    
    public boolean existeNombreUsuario(String nombreUsuario) {
        return usuarioRepository.findByNombreUsuario(nombreUsuario) != null;
    }
}