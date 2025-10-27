package com.example.Indrugs.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "vehiculo")
public class Vehiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // autoincremental
    @Column(name = "ID_VEHICULO")
    private Long idVehiculo;

    @ManyToOne
    @JoinColumn(name = "PROPIETARIO_USUARIOS")
    private Usuario idPropietario;

    @Column(name = "TIPO_VEHICULO")
    private String tipoVehiculo;

    @Column(name = "PLACA_VEHICULO")
    private String placaVehiculo;

    @Column(name = "MARCA_VEHICULO")
    private String marcaVehiculo;

    @Column(name = "COLOR_VEHICULO")
    private String colorVehiculo;

    @Column(name = "ESTADO_VEHICULO")
    private String estadoVehiculo;
}