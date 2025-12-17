package com.ingecar.entity;
import jakarta.persistence.*;
import java.time.LocalDateTime;
@Entity
@Table(name = "ordenes_servicio")
public class OrdenServicio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_orden") 
    private Integer idOrden;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "id_vehiculo", nullable = false) 
    private Vehiculo vehiculo;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "id_cliente", nullable = false) 
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "id_usuario_recepcion") 
    private Usuario usuarioRecepcion;

    @Column(name = "fecha_ingreso") 
    private LocalDateTime fechaIngreso;

    @Column(name = "fecha_salida_estimada") 
    private LocalDateTime fechaSalidaEstimada;

    @Column(columnDefinition = "TEXT") 
    private String diagnostico;

    @Column(name = "costo_total") 
    private Double costoTotal;

    @Enumerated(EnumType.STRING) @Column(length = 20) 
    private EstadoOrden estado;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "id_tecnico_asignado") 
    private Usuario tecnicoAsignado;

    public enum EstadoOrden { pendiente, en_proceso, completado, entregado }
    
    public OrdenServicio() { 
        this.fechaIngreso = LocalDateTime.now(); this.estado = EstadoOrden.pendiente; 
    }
    
    public OrdenServicio(Vehiculo vehiculo) { 
        this(); 
        this.vehiculo = vehiculo; 
        if (vehiculo != null && vehiculo.getCliente() != null) {
            this.cliente = vehiculo.getCliente();
        }
    }
    public Integer getIdOrden() { 
        return idOrden; }
    public void setIdOrden(Integer idOrden) { 
        this.idOrden = idOrden; 
    }

    public Vehiculo getVehiculo() { 
        return vehiculo; 
    }

    public void setVehiculo(Vehiculo vehiculo) { 
        this.vehiculo = vehiculo; 
    }

    public Cliente getCliente() { 
        return cliente; 
    }

    public void setCliente(Cliente cliente) { 
        this.cliente = cliente; 
    }

    public Usuario getUsuarioRecepcion() { 
        return usuarioRecepcion; 
    }

    public void setUsuarioRecepcion(Usuario usuarioRecepcion) { 
        this.usuarioRecepcion = usuarioRecepcion; 
    }

    public LocalDateTime getFechaIngreso() { 
        return fechaIngreso; 
    }

    public void setFechaIngreso(LocalDateTime fechaIngreso) { 
        this.fechaIngreso = fechaIngreso; 
    }

    public LocalDateTime getFechaSalidaEstimada() { 
        return fechaSalidaEstimada; 
    }

    public void setFechaSalidaEstimada(LocalDateTime fechaSalidaEstimada) { 
        this.fechaSalidaEstimada = fechaSalidaEstimada; 
    }

    public String getDiagnostico() { 
        return diagnostico; 
    }

    public void setDiagnostico(String diagnostico) { 
        this.diagnostico = diagnostico;
    
    }
    public Double getCostoTotal() { 
        return costoTotal; 
    }

    public void setCostoTotal(Double costoTotal) { 
        this.costoTotal = costoTotal; 
    }

    public EstadoOrden getEstado() { 
        return estado; 
    }

    public void setEstado(EstadoOrden estado) { 
        this.estado = estado; 
    }

    public Usuario getTecnicoAsignado() { 
        return tecnicoAsignado; 
    }

    public void setTecnicoAsignado(Usuario tecnicoAsignado) { 
        this.tecnicoAsignado = tecnicoAsignado; 
    }

    public boolean isPendiente() { 
        return this.estado == EstadoOrden.pendiente; 
    }

    public boolean isEnProceso() { 
        return this.estado == EstadoOrden.en_proceso; 
    }

    public boolean isCompletado() { 
        return this.estado == EstadoOrden.completado; 
    }

    public boolean isEntregado() { 
        return this.estado == EstadoOrden.entregado; 
    }
}