package com.ingecar.service;
import com.ingecar.entity.Cliente;
import com.ingecar.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
@Service
public class ClienteService {
    @Autowired private ClienteRepository clienteRepository;
    public List<Cliente> listarTodos() { 
        return clienteRepository.findAll(); 
    }

    public Cliente obtenerPorId(Integer id) { 
        return clienteRepository.findById(id).orElse(null); 
    }

    public Cliente obtenerPorDni(String dni) { 
        Optional<Cliente> cliente = clienteRepository.findByDni(dni); 
        return cliente.orElse(null); 
    }
    public List<Cliente> buscarPorNombre(String nombre) { 
        return clienteRepository.findByNombreCompletoContaining(nombre); 
    }

    public Cliente guardar(Cliente cliente) { 
        return clienteRepository.save(cliente); 
    }

    public void eliminar(Integer id) { 
        clienteRepository.deleteById(id); 
    }

    public boolean existeDni(String dni) { 
        return clienteRepository.findByDni(dni).isPresent(); 
    }

    public long contarClientes() { 
        return clienteRepository.countTotalClientes(); 
    }

    public List<Cliente> listarActivos() { 
        return clienteRepository.findClientesActivos(); 
    }
}