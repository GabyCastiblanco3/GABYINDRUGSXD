package com.example.Indrugs.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "pqrs")
public class PQRS {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PQRS")
    private Long idPqrs;

    @ManyToOne
    @JoinColumn(name = "ID_USUARIOS")
    private Usuario usuario;

    @Column(name = "TIPO_SOLICITUD")
    private String tipoPqrs;

    @Column(name = "MOTIVO_PQRS")
    private String motivo;

    @Column(name = "FECHA_PQRS")
    private LocalDateTime fechaPqrs;
}

