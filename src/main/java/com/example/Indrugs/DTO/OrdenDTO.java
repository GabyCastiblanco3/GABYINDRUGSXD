package com.example.Indrugs.DTO;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class OrdenDTO {

    private Long idOrden;               // ID de la orden
    private String pacienteNombre;      // Nombre del paciente
    private String epsOrden;            // EPS del paciente
    private Long paciente;              // ID del paciente
    private String direccionOrden;      // Dirección de entrega
    private String telefonoOrden;       // Teléfono de contacto
    private Integer cantidad;           // Cantidad de medicamento
    private Long idMedicamento;         // ID del medicamento
    private String nombreMedicamento;   // Nombre del medicamento
    private List<String> medicamentos;  // Lista de nombres de medicamentos (opcional)
    private String estadoOrden;         // Estado de la orden (ACTIVO, ENTREGADO, etc.)
    private String fotoFormula;         // Nombre del archivo de la fórmula médica

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime fechaEntrega; // Fecha y hora de entrega
}
