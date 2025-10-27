package com.example.Indrugs.services;

import com.example.Indrugs.DTO.ControlDTO;
import java.util.List;

public interface ControlService {
    List<ControlDTO> obtenerTodosLosControlesporUsuario(Long idUsuario);
    void guardarControl(ControlDTO controlDTO);
    void actualizar(Long idControl, ControlDTO controlDTO);
    ControlDTO findById(Long idControl);
}
