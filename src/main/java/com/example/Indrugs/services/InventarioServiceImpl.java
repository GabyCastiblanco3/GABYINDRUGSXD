package com.example.Indrugs.services;

import com.example.Indrugs.DTO.InventarioDTO;
import com.example.Indrugs.entities.Inventario;
import com.example.Indrugs.entities.Medicamentos;
import com.example.Indrugs.mapper.InventarioMapper;
import com.example.Indrugs.repositorios.InventarioRepository;
import com.example.Indrugs.repositorios.MedicamentoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InventarioServiceImpl implements InventarioService{

    private  InventarioRepository inventarioRepository;
    private MedicamentoRepository medicamentoRepository;

    public InventarioServiceImpl(InventarioRepository inventarioRepository, MedicamentoRepository medicamentoRepository){
        this.inventarioRepository = inventarioRepository;
        this.medicamentoRepository = medicamentoRepository;
    }

    @Override
    public List<InventarioDTO> read() {
        List<Inventario> inventarios = inventarioRepository.findAll();
        return inventarios.stream()
                .map(InventarioMapper::entiteToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void crear(InventarioDTO inventarioDTO) {

        Medicamentos medicamentos = medicamentoRepository.findByNombreMedicamento(inventarioDTO.getNombreMedicamento())
                .orElseThrow(() -> new RuntimeException("Medicamento no encontrado con el nombre" + inventarioDTO.getNombreMedicamento()));
        boolean existeEnInventario = inventarioRepository.existsByIdMedicamento_IdMedicamento(medicamentos.getIdMedicamento());
        if (existeEnInventario) {
            throw new RuntimeException("El medicamento ya se encuentra registrado en el inventario.");
        }

        Inventario inventario = InventarioMapper.Toentitie(inventarioDTO, medicamentos);
        inventarioRepository.save(inventario);
    }

    @Override
    public void actualizar(Long idInventario, InventarioDTO inventarioDTO) {
        Inventario inventario = inventarioRepository.findById(idInventario)
                .orElseThrow(()-> new RuntimeException("Inventario no encontrado"));
        InventarioMapper.ToUpdate(inventarioDTO, inventario);
        inventarioRepository.save(inventario);
    }

    @Override
    public Long totalUnidadesEnStock() {
        Long total = inventarioRepository.contarUnidadesEnStock();
        return total != null ? total : 0L;
    }

    @Override
    public InventarioDTO buscarPorId(Long idInventario) {
        Inventario inventario = inventarioRepository.findById(idInventario)
                .orElseThrow(() -> new RuntimeException("Inventario no encontrado con ID: " + idInventario));
        return InventarioMapper.entiteToDto(inventario);
    }

    @Override
    public List<InventarioDTO> findByEstado(String estadoMed) {
        List<Inventario> inventarios = inventarioRepository.findByEstadoMed(estadoMed);
        return inventarios.stream()
                .map(InventarioMapper::entiteToDto)
                .collect(Collectors.toList());
    }
}
