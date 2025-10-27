package com.example.Indrugs.mapper;
import com.example.Indrugs.DTO.DomicilioDTO;
import com.example.Indrugs.entities.Domicilio;
import com.example.Indrugs.entities.Orden;
import com.example.Indrugs.entities.Vehiculo;

public class DomicilioMapper {
    public static DomicilioDTO entityToDto(Domicilio domicilio) {
        DomicilioDTO dto = new DomicilioDTO();
        dto.setIdDomicilio(domicilio.getIdDomicilio());
        dto.setUbicacionDomicilio(domicilio.getUbicacionDomicilio());
        dto.setEstadoDomicilio(domicilio.getEstadoDomicilio());
        dto.setFechaEntregaDomicilio(domicilio.getFechaEntregaDomicilio());

        if (domicilio.getVehiculo() != null) {
            dto.setIdVehiculo(domicilio.getVehiculo().getIdVehiculo());
        }
        if (domicilio.getOrden() != null) {
            dto.setIdOrden(domicilio.getOrden().getIdOrden());
            dto.setTelefonoOrden(domicilio.getOrden().getTelefonoOrden());
        }
        return dto;
    }

    public static Domicilio toEntity(DomicilioDTO dto, Vehiculo vehiculo, Orden orden) {
        Domicilio domicilio = new Domicilio();
        domicilio.setIdDomicilio(dto.getIdDomicilio());
        domicilio.setUbicacionDomicilio(dto.getUbicacionDomicilio());
        domicilio.setEstadoDomicilio(dto.getEstadoDomicilio());
        domicilio.setFechaEntregaDomicilio(dto.getFechaEntregaDomicilio());
//        domicilio.setFormulaMedica(dto.getFormulaMedica());
//        domicilio.setEpsDomicilio(dto.getEpsDomicilio());
//        domicilio.setTelefonoDomicilio(dto.getTelefonoDomicilio());
        domicilio.setVehiculo(vehiculo);
        domicilio.setOrden(orden);
        return domicilio;
    }

    public static void updateEntity(DomicilioDTO dto, Domicilio domicilio, Vehiculo vehiculo, Orden orden) {
        domicilio.setUbicacionDomicilio(dto.getUbicacionDomicilio());
        domicilio.setEstadoDomicilio(dto.getEstadoDomicilio());
        domicilio.setFechaEntregaDomicilio(dto.getFechaEntregaDomicilio());
//        domicilio.setFormulaMedica(dto.getFormulaMedica());
//        domicilio.setEpsDomicilio(dto.getEpsDomicilio());
//        domicilio.setTelefonoDomicilio(dto.getTelefonoDomicilio());
        domicilio.setVehiculo(vehiculo);
        domicilio.setOrden(orden);
    }
}
