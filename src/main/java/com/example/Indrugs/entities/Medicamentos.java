package com.example.Indrugs.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "medicamentos")
public class Medicamentos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_MEDICAMENTOS")
    private Long idMedicamento;

    @Column(name = "NOMBRE_MEDICAMENTOS")
    private String nombreMedicamento;

    @Column(name = "DESCRIPCION_MEDICAMENTOS")
    private String descripcionMedicamento;

    // Nuevo campo para imagen
    @Column(name = "IMAGEN_MEDICAMENTO")
    private String imagenMedicamento;
}
