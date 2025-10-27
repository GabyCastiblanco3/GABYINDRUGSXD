package com.example.Indrugs.mapper;

import com.example.Indrugs.DTO.VehiculoDTO;
import com.example.Indrugs.entities.Usuario;
import com.example.Indrugs.entities.Vehiculo;

public class VehiculoMapper {

    // Mapear entidad a DTO
    public static VehiculoDTO mapToDto(Vehiculo vehiculo) {
        if (vehiculo == null) {
            return null;
        }
        VehiculoDTO dto = new VehiculoDTO();
        if (vehiculo.getIdPropietario() != null) {
            dto.setIdUsuario(vehiculo.getIdPropietario().getIdUsuario());
            dto.setNombreUsuario(vehiculo.getIdPropietario().getNombre());
        }
        dto.setIdVehiculo(vehiculo.getIdVehiculo());
        dto.setTipoVehiculo(vehiculo.getTipoVehiculo());
        dto.setPlacaVehiculo(vehiculo.getPlacaVehiculo());
        dto.setMarcaVehiculo(vehiculo.getMarcaVehiculo());
        dto.setColorVehiculo(vehiculo.getColorVehiculo());
        dto.setEstadoVehiculo(vehiculo.getEstadoVehiculo());
        return dto;
    }

    // Mapear DTO a entidad
    public static Vehiculo mapToEntity(VehiculoDTO dto) {
        if (dto == null) {
            return null;
        }
        Vehiculo vehiculo = new Vehiculo();
        Usuario IdPropietario = new Usuario();
        IdPropietario.setIdUsuario(dto.getIdUsuario());

        vehiculo.setIdPropietario(IdPropietario);
        vehiculo.setIdVehiculo(dto.getIdVehiculo());
        vehiculo.setTipoVehiculo(dto.getTipoVehiculo());
        vehiculo.setPlacaVehiculo(dto.getPlacaVehiculo());
        vehiculo.setMarcaVehiculo(dto.getMarcaVehiculo());
        vehiculo.setColorVehiculo(dto.getColorVehiculo());
        vehiculo.setEstadoVehiculo(dto.getEstadoVehiculo());
        return vehiculo;
    }
}
