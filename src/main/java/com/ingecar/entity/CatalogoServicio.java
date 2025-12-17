package com.ingecar.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "catalogos_servicios")
public class CatalogoServicio {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_servicio")
    private Integer idServicio;
    
    @Column(name = "nombre_servicio", nullable = false, length = 100)
    private String nombreServicio;
    
    @Column(columnDefinition = "TEXT")
    private String descripcion;
    
    @Column(name = "precio_referencial")
    private Double precioReferencial;
    
    @Column(name = "tiempo_estimado_horas")
    private Integer tiempoEstimadoHoras;
    
    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private Estado estado;
    
    public enum Estado {
        activo, inactivo
    }
    
    public CatalogoServicio() {
        this.estado = Estado.activo;
    }
    
    public Integer getIdServicio() { 
        return idServicio; }
    public void setIdServicio(Integer idServicio) { 
        this.idServicio = idServicio; 
    }

    public String getNombreServicio() { 
        return nombreServicio; 
    }

    public void setNombreServicio(String nombreServicio) { 
        this.nombreServicio = nombreServicio; 
    }

    public String getDescripcion() { 
        return descripcion; 
    }

    public void setDescripcion(String descripcion) { 
        this.descripcion = descripcion; 
    }

    public Double getPrecioReferencial() { 
        return precioReferencial; 
    }

    public void setPrecioReferencial(Double precioReferencial) { 
        this.precioReferencial = precioReferencial; 
    }

    public Integer getTiempoEstimadoHoras() { 
        return tiempoEstimadoHoras; 
    }

    public void setTiempoEstimadoHoras(Integer tiempoEstimadoHoras) { 
        this.tiempoEstimadoHoras = tiempoEstimadoHoras; 
    }
    

    public Estado getEstado() { 
        return estado; 
    }

    public void setEstado(Estado estado) { 
        this.estado = estado; 
    }
}