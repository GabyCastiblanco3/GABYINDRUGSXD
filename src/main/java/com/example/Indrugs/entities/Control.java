package com.example.Indrugs.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@Table(name = "control")
public class Control {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CONTROL")
    private Long idControl;

    @ManyToOne
    @JoinColumn(name = "ID_MEDICAMENTOS")
    private Medicamentos idMedicamento;

    @ManyToOne
    @JoinColumn(name = "ID_USUARIO")
    private Usuario usuario;

    @Column(name = "CANTIDAD_MEDIC")
    private String cantidadMedic;

    @Column(name = "PROBLEMA_SALUD")
    private String problemaSalud;

    @Column(name = "FRECUENCIA_MEDIC")
    private String frecuenciaMedic;

    @Column(name = "FECHA_INICIO_TRATAMIENTO")
    private LocalDateTime fechaInicioTratamiento;

    @Column(name = "FECHA_FIN_TRATAMIENTO")
    private LocalDateTime fechaFinTratamiento;

    @Column(name = "ALARMA_CONTROL")
    private LocalTime alarmaControl;
}

