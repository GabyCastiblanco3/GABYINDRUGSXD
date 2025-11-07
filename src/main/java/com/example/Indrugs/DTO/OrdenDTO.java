package com.example.Indrugs.DTO;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;
@Getter
@Setter
public class OrdenDTO {

    private Long idOrden;
    private String pacienteNombre;
    private String epsOrden;
    private Long paciente;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime fechaEntrega;
    private String direccionOrden;
    private String telefonoOrden;
    private Integer cantidad;
    private Long idMedicamento;
    //    private String formulaMedica;
    private String nombreMedicamento;
    private List<String> medicamentos;
    private String estadoOrden;


}