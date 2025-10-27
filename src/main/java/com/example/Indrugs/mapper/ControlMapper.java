package com.example.Indrugs.mapper;

import com.example.Indrugs.DTO.ControlDTO;
import com.example.Indrugs.entities.Control;
import com.example.Indrugs.entities.Medicamentos;
import com.example.Indrugs.entities.Usuario;

public class ControlMapper {

    // Mapear entidad a DTO
    public static ControlDTO mapToDto(Control control) {
        if (control == null) {
            return null;
        }
        ControlDTO dto = new ControlDTO();
        dto.setIdControl(control.getIdControl());

        // Mapear solo el ID de Medicamento y Usuario
        if (control.getUsuario() != null) {
            dto.setIdUsuario(control.getUsuario().getIdUsuario());
            dto.setNombreUsuario(control.getUsuario().getNombre());
        }

        if (control.getIdMedicamento() != null) {
            dto.setIdMedicamento(control.getIdMedicamento().getIdMedicamento());
            dto.setNombreMedicamento(control.getIdMedicamento().getNombreMedicamento());
        }

        dto.setCantidadMedic(control.getCantidadMedic());
        dto.setProblemaSalud(control.getProblemaSalud());
        dto.setFrecuenciaMedic(control.getFrecuenciaMedic());
        dto.setFechaInicioTratamiento(control.getFechaInicioTratamiento());
        dto.setFechaFinTratamiento(control.getFechaFinTratamiento());
        dto.setAlarmaControl(control.getAlarmaControl());

        return dto;
    }

    // Mapear DTO a entidad
    public static Control mapToEntity(ControlDTO dto) {
        if (dto == null) {
            return null;
        }
        Control control = new Control();
        control.setIdControl(dto.getIdControl());

        // Aquí se pueden manejar las relaciones con Medicamentos y Usuario
        // Asegúrate de que las entidades `Medicamentos` y `Usuario` existan antes de asignarlas.
        Medicamentos medicamento = new Medicamentos();
        medicamento.setIdMedicamento(dto.getIdMedicamento());
        control.setIdMedicamento(medicamento);

        Usuario usuario = new Usuario();
        usuario.setIdUsuario(dto.getIdUsuario());
        control.setUsuario(usuario);

        control.setCantidadMedic(dto.getCantidadMedic());
        control.setProblemaSalud(dto.getProblemaSalud());
        control.setFrecuenciaMedic(dto.getFrecuenciaMedic());
        control.setFechaInicioTratamiento(dto.getFechaInicioTratamiento());
        control.setFechaFinTratamiento(dto.getFechaFinTratamiento());
        control.setAlarmaControl(dto.getAlarmaControl());

        return control;
    }
}
