package com.example.Indrugs.mapper;

import com.example.Indrugs.DTO.InventarioDTO;
import com.example.Indrugs.entities.Inventario;
import com.example.Indrugs.entities.Medicamentos;

public class InventarioMapper {

    public static InventarioDTO entiteToDto(Inventario inventario){

        InventarioDTO dto = new InventarioDTO();
        dto.setIdInventario(inventario.getIdInventario());
        dto.setNombreMedicamento(inventario.getIdMedicamento().getNombreMedicamento());
        dto.setFechaEntrada(inventario.getFechaEntrada());
        dto.setFechaSalida(inventario.getFechaSalida());
        dto.setStock(inventario.getStock());
        dto.setVencimiento(inventario.getVencimiento());
        dto.setEstadoMed(inventario.getEstadoMed());

        return dto;
    }

    public static Inventario Toentitie(InventarioDTO dto, Medicamentos medicamentos){

        Inventario inv = new Inventario();
        inv.setIdInventario(dto.getIdInventario());
        inv.setIdMedicamento(medicamentos);
        inv.setFechaEntrada(dto.getFechaEntrada());
        inv.setFechaSalida(dto.getFechaSalida());
        inv.setStock(dto.getStock());
        inv.setVencimiento(dto.getVencimiento());
        inv.setEstadoMed(dto.getEstadoMed());

        return inv;
    }

    public static void ToUpdate(InventarioDTO dto, Inventario inv){

        inv.setFechaSalida(dto.getFechaSalida());
        inv.setStock(dto.getStock());
        inv.setVencimiento(dto.getVencimiento());
        inv.setEstadoMed(dto.getEstadoMed());
    }
}
