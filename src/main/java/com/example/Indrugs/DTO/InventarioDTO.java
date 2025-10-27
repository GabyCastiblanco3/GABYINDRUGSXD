package com.example.Indrugs.DTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InventarioDTO {

    private Long idInventario;

    private String nombreMedicamento;

    private LocalDateTime fechaEntrada;

    private LocalDateTime fechaSalida;

    private Integer stock;

    private LocalDate vencimiento;

    private String estadoMed;

}
