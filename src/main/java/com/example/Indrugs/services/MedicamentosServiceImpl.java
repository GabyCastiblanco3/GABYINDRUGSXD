package com.example.Indrugs.services;

import com.example.Indrugs.DTO.MedicamentoDTO;
import com.example.Indrugs.entities.Medicamentos;
import com.example.Indrugs.mapper.MedicamentosMap;
import com.example.Indrugs.repositorios.MedicamentoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
public class MedicamentosServiceImpl implements MedicamentosService{

    MedicamentoRepository medicRepository;

    public MedicamentosServiceImpl(MedicamentoRepository medicRepository){
        this.medicRepository = medicRepository;
    }

    @Override
    public List<MedicamentoDTO> readAdmin() {
        List<Medicamentos> medic = medicRepository.findAll();
        return medic.stream()
                .map(MedicamentosMap ::mapToDtoAdmin)
                .collect(Collectors.toList());
    }

    @Override
    public List<MedicamentoDTO> mostarEnPaciente() {
        List<Medicamentos> medic = medicRepository.findAll();
        return medic.stream()
                .map(MedicamentosMap::mapToPaciente)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void crear(MedicamentoDTO MedDto) {
        if(medicRepository.existsByNombreMedicamento(MedDto.getNombreMedicamento())){
            throw new RuntimeException("Ya existe este medicamento");
        }
        Medicamentos medic = MedicamentosMap.mapToEntitie(MedDto);
        medicRepository.save(medic);
    }

    @Override
    public void actualizar(Long idMedicamento, MedicamentoDTO MedDto) {
        Medicamentos medic = medicRepository.findById(idMedicamento)
                .orElseThrow(() -> new RuntimeException("Medicamento no encontrado"));
        MedicamentosMap.mapUpdate(medic, MedDto);
        medicRepository.save(medic);
    }

    @Override
    public List<MedicamentoDTO> findByNombre(String nombreMedicamento) {
        List<Medicamentos> medic = medicRepository.findByNombreMedicamentoContainingIgnoreCase(nombreMedicamento);
        return medic.stream()
                .map(MedicamentosMap ::mapToPaciente)
                .collect(Collectors.toList());
    }

    @Override
    public MedicamentoDTO buscarPorIdMedicamento(Long idMedicamento) {
        Medicamentos medicamento = medicRepository.findById(idMedicamento)
                .orElseThrow(() -> new RuntimeException("Medicamento no encontrado"));
        return MedicamentosMap.mapToPaciente(medicamento);

    }
}
