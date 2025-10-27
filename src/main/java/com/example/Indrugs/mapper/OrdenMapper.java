package com.example.Indrugs.mapper;

import com.example.Indrugs.DTO.OrdenDTO;
import com.example.Indrugs.entities.Medicamentos;
import com.example.Indrugs.entities.Orden;
import com.example.Indrugs.entities.Usuario;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OrdenMapper {



    public static OrdenDTO toDTO(Orden orden) {
        if (orden == null) {
            return null;
        }

        OrdenDTO dto = new OrdenDTO();
        dto.setIdOrden(orden.getIdOrden());

        // Mapear paciente
        if (orden.getPaciente() != null) {
            dto.setPaciente(orden.getPaciente().getIdUsuario());
            dto.setPacienteNombre(orden.getPaciente().getNombre());
        }

        dto.setEpsOrden(orden.getEpsOrden());
        dto.setFechaEntrega(orden.getFechaEntrega());
        dto.setDireccionOrden(orden.getDireccionOrden());
        dto.setTelefonoOrden(orden.getTelefonoOrden());
        dto.setCantidad(orden.getCantidad());
        dto.setEstadoOrden(orden.getEstadoOrden());
//        dto.setFormulaMedica(orden.getFormulaMedica());

        // Mapear medicamentos
        if (orden.getMedicamentos() != null && !orden.getMedicamentos().isEmpty()) {
            dto.setMedicamentos(
                    orden.getMedicamentos().stream()
                            .map(Medicamentos::getNombreMedicamento)
                            .collect(Collectors.toList())
            );
            // Tomar el primer medicamento como nombre principal
            Medicamentos primerMedicamento = orden.getMedicamentos().get(0);
            dto.setNombreMedicamento(primerMedicamento.getNombreMedicamento());
            dto.setIdMedicamento(primerMedicamento.getIdMedicamento());

        } else {
            dto.setMedicamentos(new ArrayList<>());
        }

        return dto;
//        if (orden == null) {
//            return null;
//        }
//
//        OrdenDTO dto = new OrdenDTO();
//        dto.setIdOrden(orden.getIdOrden());
//
//
//        if (orden.getPaciente() != null) {
//            dto.setPacienteNombre(orden.getPaciente().getNombre());
//        }
//
//        dto.setEpsOrden(orden.getEpsOrden());
//        dto.setFechaEntrega(orden.getFechaEntrega());
//        dto.setDireccionOrden(orden.getDireccionOrden());
//        dto.setTelefonoOrden(orden.getTelefonoOrden());
//        dto.setCantidad(orden.getCantidad());
//        dto.setEstadoOrden(orden.getEstadoOrden());
//
//        if (orden.getMedicamentos() != null && !orden.getMedicamentos().isEmpty()) {
//            dto.setMedicamentos(
//                    orden.getMedicamentos().stream()
//                            .map(Medicamentos::getNombreMedicamento)
//                            .collect(Collectors.toList())
//            );
//        } else {
//            dto.setMedicamentos(new ArrayList<>());
//        }
//
//        return dto;
    }


    public static List<OrdenDTO> toDTOList(List<Orden> ordenes) {
        if (ordenes == null) {
            return new ArrayList<>();
        }

        return ordenes.stream()
                .map(OrdenMapper::toDTO)
                .collect(Collectors.toList());
    }


    public static Orden toEntity(OrdenDTO ordenDTO) {
        if (ordenDTO == null) {
            return null;
        }

        Orden orden = new Orden();
        orden.setIdOrden(ordenDTO.getIdOrden());
        orden.setEpsOrden(ordenDTO.getEpsOrden());
        orden.setFechaEntrega(ordenDTO.getFechaEntrega());
        orden.setDireccionOrden(ordenDTO.getDireccionOrden());
        orden.setTelefonoOrden(ordenDTO.getTelefonoOrden());
        orden.setCantidad(ordenDTO.getCantidad());
        orden.setEstadoOrden(ordenDTO.getEstadoOrden());
//        orden.setFormulaMedica(ordenDTO.getFormulaMedica());


        if (ordenDTO.getIdMedicamento() != null) {
            Medicamentos medicamento = new Medicamentos();
            medicamento.setIdMedicamento(ordenDTO.getIdMedicamento());
            List<Medicamentos> medicamentosList = new ArrayList<>();
            medicamentosList.add(medicamento);
            orden.setMedicamentos(medicamentosList);
        }


        return orden;
    }


    public static void updateEntityFromDTO(Orden orden, OrdenDTO ordenDTO) {
        if (orden == null || ordenDTO == null) {
            return;
        }

        if (ordenDTO.getEpsOrden() != null) {
            orden.setEpsOrden(ordenDTO.getEpsOrden());
        }
        if (ordenDTO.getFechaEntrega() != null) {
            orden.setFechaEntrega(ordenDTO.getFechaEntrega());
        }
        if (ordenDTO.getDireccionOrden() != null) {
            orden.setDireccionOrden(ordenDTO.getDireccionOrden());
        }
        if (ordenDTO.getTelefonoOrden() != null) {
            orden.setTelefonoOrden(ordenDTO.getTelefonoOrden());
        }
        if (ordenDTO.getCantidad() != null) {
            orden.setCantidad(ordenDTO.getCantidad());
        }
        if (ordenDTO.getEstadoOrden() != null) {
            orden.setEstadoOrden(ordenDTO.getEstadoOrden());
        }
    }
}