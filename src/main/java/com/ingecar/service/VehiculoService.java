package com.ingecar.service;
import com.ingecar.entity.Vehiculo;
import com.ingecar.repository.VehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
@Service
public class VehiculoService {
    @Autowired private VehiculoRepository vehiculoRepository;
    public List<Vehiculo> listarTodos() { 
        return vehiculoRepository.findAll(); 
    }

    public Vehiculo obtenerPorId(Integer id) { 
        return vehiculoRepository.findById(id).orElse(null); 
    }

    public Vehiculo obtenerPorPlaca(String placa) { 
        Optional<Vehiculo> vehiculo = vehiculoRepository.findByPlaca(placa); 
        return vehiculo.orElse(null); 
    }
    public List<Vehiculo> listarPorCliente(Integer idCliente) { 
        return vehiculoRepository.findByClienteIdCliente(idCliente); 
    }

    public Vehiculo guardar(Vehiculo vehiculo) { 
        return vehiculoRepository.save(vehiculo); 
    }

    public void eliminar(Integer id) { 
        vehiculoRepository.deleteById(id); 
    }

    public boolean existePlaca(String placa) { 
        return vehiculoRepository.findByPlaca(placa).isPresent(); 
    }

    public long contarPorCliente(Integer idCliente) { 
        return vehiculoRepository.countByClienteId(idCliente); 
    }
}