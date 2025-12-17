package com.ingecar.entity;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
@Entity
@Table(name = "clientes")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente") 
    private Integer idCliente;

    @Column(nullable = false, unique = true, length = 20) 
    private String dni;

    @Column(name = "nombre_completo", nullable = false, length = 255) 
    private String nombreCompleto;

    @Column(length = 20) 
    private String telefono;

    @Column(length = 255) 
    private String email;

    @Column(length = 255) 
    private String direccion;

    @Column(name = "fecha_registro") 
    private LocalDateTime fechaRegistro;

    @Enumerated(EnumType.STRING) @Column(length = 10) 
    private Estado estado;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, fetch = FetchType.LAZY) 
    private List<Vehiculo> vehiculos;
    
    public enum Estado { 
        activo, inactivo 
    
    }
    public Cliente() { 
        this.fechaRegistro = LocalDateTime.now(); this.estado = Estado.activo; 
    }

    public Integer getIdCliente() { 
        return idCliente; 
    }
    public void setIdCliente(Integer idCliente) { 
        this.idCliente = idCliente; 
    }

    public String getDni() { 
        return dni;
    }

    public void setDni(String dni) { 
        this.dni = dni; 
    }

    public String getNombreCompleto() { 
        return nombreCompleto; 
    }

    public void setNombreCompleto(String nombreCompleto) { 
        this.nombreCompleto = nombreCompleto; 
    }

    public String getTelefono() { 
        return telefono; 
    }

    public void setTelefono(String telefono) { 
        this.telefono = telefono; 
    }

    public String getEmail() { 
        return email; 
    }
    public void setEmail(String email) { 
        this.email = email; 
    }
    public String getDireccion() { 
        return direccion; 
    }

    public void setDireccion(String direccion) { 
        this.direccion = direccion; 
    }

    public LocalDateTime getFechaRegistro() { 
        return fechaRegistro; 
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) { 
        this.fechaRegistro = fechaRegistro; 
    }

    public Estado getEstado() { 
        return estado; 
    }

    public void setEstado(Estado estado) { 
        this.estado = estado; 
    }

    public List<Vehiculo> getVehiculos() { 
        return vehiculos; 
    }

    public void setVehiculos(List<Vehiculo> vehiculos) { 
        this.vehiculos = vehiculos; 
    }
}