package com.ingecar.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Integer idUsuario;
    
    @Column(name = "nombre_usuario", unique = true, nullable = false, length = 50)
    private String nombreUsuario;
    
    @Column(name = "password", nullable = false, length = 255)
    private String password;
    
    @Column(name = "nombre_completo", nullable = false, length = 150)
    private String nombreCompleto;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Rol rol;
    
    @Column(length = 100)
    private String email;
    
    @Column(length = 15)
    private String telefono;
    
    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private Estado estado;
    
    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;
    
    public enum Rol { administrador, tecnico, recepcionista }
    public enum Estado { activo, inactivo }
    
    public Usuario() {
        this.fechaCreacion = LocalDateTime.now();
        this.estado = Estado.activo;
    }
    
    public Integer getIdUsuario() { 
        return idUsuario; 
    }

    public void setIdUsuario(Integer idUsuario) { 
        this.idUsuario = idUsuario; 
    }

    public String getNombreUsuario() { 
        return nombreUsuario; 
    }
    
    public void setNombreUsuario(String nombreUsuario) { 
        this.nombreUsuario = nombreUsuario; 
    }
    
    public String getPassword() { 
        return password; 
    }

    public void setPassword(String password) { 
        this.password = password; 
    }

    public String getNombreCompleto() { 
        return nombreCompleto; 
    }

    public void setNombreCompleto(String nombreCompleto) { 
        this.nombreCompleto = nombreCompleto; 
    }

    public Rol getRol() { 
        return rol; 
    }

    public void setRol(Rol rol) { 
        this.rol = rol; 
    }

    public String getEmail() { 
        return email; 
    }

    public void setEmail(String email) { 
        this.email = email; 
    }

    public String getTelefono() { 
        return telefono; 
    }

    public void setTelefono(String telefono) { 
        this.telefono = telefono; 
    }

    public Estado getEstado() { 
        return estado; 
    }

    public void setEstado(Estado estado) { 
        this.estado = estado; 
    }

    public LocalDateTime getFechaCreacion() { 
        return fechaCreacion; 
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) { 
        this.fechaCreacion = fechaCreacion; 
    }
    
    public boolean isActivo() { 
        return this.estado == Estado.activo; 
    }

    public boolean isAdministrador() { 
        return this.rol == Rol.administrador; 
    }

    public boolean isTecnico() { 
        return this.rol == Rol.tecnico; 
    }

    public boolean isRecepcionista() { 
        return this.rol == Rol.recepcionista; 
    }
}