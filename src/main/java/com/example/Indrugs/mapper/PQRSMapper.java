package com.example.Indrugs.mapper;

import com.example.Indrugs.DTO.PQRSDTO;
import com.example.Indrugs.entities.PQRS;
import com.example.Indrugs.entities.Usuario;

public class PQRSMapper {

    // Convierte de DTO a entidad
    public static PQRS toEntity(PQRSDTO dto, Usuario usuario) {
        PQRS pqrs = new PQRS();
        pqrs.setIdPqrs(dto.getIdPqrs());
        pqrs.setUsuario(usuario);
        pqrs.setTipoPqrs(dto.getTipoPqrs());
        pqrs.setMotivo(dto.getMotivo());
        pqrs.setFechaPqrs(dto.getFechaPqrs());
        return pqrs;
    }

    // Convierte de entidad a DTO
    public static PQRSDTO toDTO(PQRS pqrs) {
        PQRSDTO dto = new PQRSDTO();
        dto.setIdPqrs(pqrs.getIdPqrs());
        dto.setUsuarioid(pqrs.getUsuario().getIdUsuario()); 
        dto.setTipoPqrs(pqrs.getTipoPqrs());
        dto.setMotivo(pqrs.getMotivo());
        dto.setFechaPqrs(pqrs.getFechaPqrs());
        return dto;
    }
}
