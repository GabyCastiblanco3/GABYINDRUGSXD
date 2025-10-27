package com.example.Indrugs.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
public class ControlDTO {

    private Long idControl;
    private Long idMedicamento;
    private String nombreMedicamento;
    private Long idUsuario;
    private String nombreUsuario;
    private String cantidadMedic;
    private String problemaSalud;
    private String frecuenciaMedic;
    private LocalDateTime fechaInicioTratamiento;
    private LocalDateTime fechaFinTratamiento;
    private LocalTime alarmaControl;


    }


