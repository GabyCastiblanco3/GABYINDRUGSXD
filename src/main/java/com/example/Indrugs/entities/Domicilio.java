package com.example.Indrugs.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "domicilio")
public class Domicilio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_DOMICILIO")
    private Long idDomicilio;

    @Column(name = "UBICACION_DOMICILIO")
    private String ubicacionDomicilio;

    @Column(name = "ESTADO_DOMICILIO")
    private String estadoDomicilio;

    @Column(name = "FECHA_ENTREGA_DOMICILIO")
    private LocalDateTime fechaEntregaDomicilio;

    @ManyToOne
    @JoinColumn(name = "ID_VEHICULO")
    private Vehiculo vehiculo;

    @ManyToOne
    @JoinColumn(name = "ID_ORDENES")
    private Orden orden;

}
