package com.ingecar.entity;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
@Entity
@Table(name = "vehiculos")
public class Vehiculo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_vehiculo") 
    private Integer idVehiculo;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "id_cliente") 
    private Cliente cliente;

    @Column(nullable = false, unique = true, length = 20) 
    private String placa;

    @Column(nullable = false, length = 100) 
    private String marca;

    @Column(nullable = false, length = 100) 
    private String modelo;

    @Column(name = "año") 
    private Integer anio;

    @Column(length = 50) 
    private String color;

    private Integer kilometraje;

    @Column(name = "fecha_registro") 
    private LocalDateTime fechaRegistro;

    @OneToMany(mappedBy = "vehiculo", cascade = CascadeType.ALL, fetch = FetchType.LAZY) 
    private List<OrdenServicio> ordenes;

    public Vehiculo() { 
        this.fechaRegistro = LocalDateTime.now(); 
    }

    public Integer getIdVehiculo() { 
        return idVehiculo; 
    }

    public void setIdVehiculo(Integer idVehiculo) { 
        this.idVehiculo = idVehiculo; 
    }

    public Cliente getCliente() { 
        return cliente; 
    }

    public void setCliente(Cliente cliente) { 
        this.cliente = cliente; 
    }

    public String getPlaca() { 
        return placa;
    }
    public void setPlaca(String placa) { 
        this.placa = placa; 
    }

    public String getMarca() { 
        return marca; 
    }

    public void setMarca(String marca) { 
        this.marca = marca; 
    }
    public String getModelo() { 
        return modelo; 
    }

    public void setModelo(String modelo) { 
        this.modelo = modelo; 
    }

    public Integer getAnio() { 
        return anio; 
    }

    public void setAnio(Integer anio) { 
        this.anio = anio; 
    }

    public String getColor() { 
        return color; 
    }

    public void setColor(String color) { 
        this.color = color;
    }
    
    public Integer getKilometraje() { 
        return kilometraje; 
    }
    public void setKilometraje(Integer kilometraje) { 
        this.kilometraje = kilometraje; 
    }

    public LocalDateTime getFechaRegistro() { 
        return fechaRegistro; 
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) { 
        this.fechaRegistro = fechaRegistro; 
    }

    public List<OrdenServicio> getOrdenes() { 
        return ordenes; 
    }

    
    public void setOrdenes(List<OrdenServicio> ordenes) { 
        this.ordenes = ordenes; 
    }
}