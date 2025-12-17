package com.ingecar.entity;
import jakarta.persistence.*;
@Entity
@Table(name = "detalle_servicios")
public class DetalleServicio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle") 
    private Integer idDetalle;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "id_orden", nullable = false) 
    private OrdenServicio orden;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "id_servicio", nullable = false) 
    private CatalogoServicio servicio;

    private Integer cantidad;
    @Column(name = "precio_unitario") 
    private Double precioUnitario;

    @Column private Double subtotal;
    public DetalleServicio() {

    }
    public DetalleServicio(OrdenServicio orden, CatalogoServicio servicio, Integer cantidad) {
        this.orden = orden; this.servicio = servicio; this.cantidad = cantidad;
        this.precioUnitario = servicio.getPrecioReferencial(); calcularSubtotal();
    }

    public Integer getIdDetalle() { 
        return idDetalle; 
    }

    public void setIdDetalle(Integer idDetalle) { 
        this.idDetalle = idDetalle; 
    }

    public OrdenServicio getOrden() { 
        return orden; 
    }

    public void setOrden(OrdenServicio orden) { 
        this.orden = orden; 
    }

    public CatalogoServicio getServicio() { 
        return servicio; 
    }

    public void setServicio(CatalogoServicio servicio) { 
        this.servicio = servicio; 
    }

    public Integer getCantidad() { 
        return cantidad; 
    }

    public void setCantidad(Integer cantidad) { 
        this.cantidad = cantidad; calcularSubtotal(); 
    }

    public Double getPrecioUnitario() { 
        return precioUnitario; 
    }

    public void setPrecioUnitario(Double precioUnitario) { 
        this.precioUnitario = precioUnitario; calcularSubtotal(); 
    }

    public Double getSubtotal() { 
        return subtotal; 
    }

    public void setSubtotal(Double subtotal) { 
        this.subtotal = subtotal; 
    }

    private void calcularSubtotal() { 
        if (this.precioUnitario != null && this.cantidad != null) this.subtotal = this.precioUnitario * this.cantidad; 
    }
}