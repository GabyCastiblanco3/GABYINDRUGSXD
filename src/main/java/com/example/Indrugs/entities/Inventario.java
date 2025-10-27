package com.example.Indrugs.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "inventario")
public class Inventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_INVENTARIO")
    private Long idInventario;

    @ManyToOne
    @JoinColumn(name = "ID_MEDICAMENTOS")
    private Medicamentos idMedicamento;

    @Column(name = "FECHA_ENTRADA_INVENTARIO")
    private LocalDateTime fechaEntrada;

    @Column(name = "FECHA_SALIDA_INVENTARIO")
    private LocalDateTime fechaSalida;

    @Column(name = "STOCK_INVENTARIO")
    private Integer stock;

    @Column(name = "VENCIMIENTOMED_INVENTARIO")
    private LocalDate vencimiento;

    @Column(name = "ESTADOMED_INVENTARIO")
    private String estadoMed;

}
