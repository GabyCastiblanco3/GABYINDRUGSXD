package com.example.Indrugs.services;

import com.example.Indrugs.DTO.MedicamentoDTO;

import java.util.List;

public interface MedicamentosService {

    List<MedicamentoDTO> readAdmin();
    List <MedicamentoDTO> mostarEnPaciente();
    void crear(MedicamentoDTO MedDto);
    void actualizar(Long idMedicamento, MedicamentoDTO MedDto);
    List <MedicamentoDTO> findByNombre(String nombreMedicamento);
    MedicamentoDTO buscarPorIdMedicamento(Long idMedicamento);

}
