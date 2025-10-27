package com.example.Indrugs.mapper;

import com.example.Indrugs.DTO.MedicamentoDTO;
import com.example.Indrugs.entities.Medicamentos;

public class MedicamentosMap {

    public static MedicamentoDTO mapToDtoAdmin(Medicamentos medicamentos){

        MedicamentoDTO dto = new MedicamentoDTO();
        dto.setIdMedicamento(medicamentos.getIdMedicamento());
        dto.setNombreMedicamento(medicamentos.getNombreMedicamento());
        dto.setDescripcionMedicamento(medicamentos.getDescripcionMedicamento());

        return dto;
    }

    public static MedicamentoDTO mapToPaciente(Medicamentos medicamentos){

        MedicamentoDTO dto = new MedicamentoDTO();
        dto.setIdMedicamento(medicamentos.getIdMedicamento());
        dto.setImagenMedicamento(medicamentos.getImagenMedicamento());
        dto.setNombreMedicamento(medicamentos.getNombreMedicamento());
        dto.setDescripcionMedicamento(medicamentos.getDescripcionMedicamento());

        return dto;
    }

    public static Medicamentos mapToEntitie(MedicamentoDTO medicamentoDTO){

        Medicamentos medic = new Medicamentos();
        medic.setNombreMedicamento(medicamentoDTO.getNombreMedicamento());
        medic.setDescripcionMedicamento(medicamentoDTO.getDescripcionMedicamento());
        medic.setImagenMedicamento(medicamentoDTO.getImagenMedicamento());

        return medic;
    }

    public static void mapUpdate(Medicamentos medic, MedicamentoDTO medicamentoDTO){

        medic.setNombreMedicamento(medicamentoDTO.getNombreMedicamento());
        medic.setDescripcionMedicamento(medicamentoDTO.getDescripcionMedicamento());
        medic.setImagenMedicamento(medicamentoDTO.getImagenMedicamento());
    }
}
