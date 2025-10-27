package com.example.Indrugs.services;

import com.example.Indrugs.DTO.InventarioDTO;

import java.util.List;

public interface InventarioService {
    List<InventarioDTO> read();
    void crear(InventarioDTO inventarioDTO);
    void actualizar(Long idInventario, InventarioDTO inventarioDTO);
    Long totalUnidadesEnStock();
    InventarioDTO buscarPorId(Long idInventario);
    List<InventarioDTO> findByEstado(String estadoMed);
}
