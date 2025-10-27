package com.example.Indrugs.services;

import com.example.Indrugs.DTO.ControlDTO;
import com.example.Indrugs.entities.Control;
import com.example.Indrugs.mapper.ControlMapper;
import com.example.Indrugs.repositorios.ControlRepository;
import com.example.Indrugs.services.ControlService;
import com.example.Indrugs.repositorios.MedicamentoRepository;
import com.example.Indrugs.repositorios.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ControlServiceImpl implements ControlService {

    private final ControlRepository controlRepository;
    private final MedicamentoRepository medicamentoRepository;
    private final UsuarioRepository usuarioRepository;

    @Autowired
    public ControlServiceImpl(ControlRepository controlRepository, MedicamentoRepository medicamentoRepository, UsuarioRepository usuarioRepository) {
        this.controlRepository = controlRepository;
        this.medicamentoRepository = medicamentoRepository;
        this.usuarioRepository = usuarioRepository;

    }

    @Override
    public List<ControlDTO> obtenerTodosLosControlesporUsuario(Long idUsuario) {
        return controlRepository.findByUsuario_IdUsuario(idUsuario).stream()
                .map(ControlMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void guardarControl(ControlDTO controlDTO) {
        Control control = ControlMapper.mapToEntity(controlDTO);
        controlRepository.save(control);
    }

    @Override
    public void actualizar(Long idControl, ControlDTO controlDTO) {
        Optional<Control> optionalControl = controlRepository.findById(idControl);
        if (optionalControl.isPresent()) {
            Control control = optionalControl.get();
            // Actualiza los campos necesarios
            control.setIdMedicamento(controlDTO.getIdMedicamento() != null ? medicamentoRepository.findById(controlDTO.getIdMedicamento()).orElse(null) : null);
            control.setUsuario(controlDTO.getIdUsuario() != null ? usuarioRepository.findById(controlDTO.getIdUsuario()).orElse(null) : null);
            control.setCantidadMedic(controlDTO.getCantidadMedic());
            control.setProblemaSalud(controlDTO.getProblemaSalud());
            control.setFrecuenciaMedic(controlDTO.getFrecuenciaMedic());
            control.setFechaInicioTratamiento(controlDTO.getFechaInicioTratamiento());
            control.setFechaFinTratamiento(controlDTO.getFechaFinTratamiento());
            control.setAlarmaControl(controlDTO.getAlarmaControl());
            controlRepository.save(control);
        } else {
            throw new RuntimeException("Control no encontrado");
        }
    }

    @Override
    public ControlDTO findById(Long idControl) {
        return controlRepository.findById(idControl)
                .map(ControlMapper::mapToDto)
                .orElseThrow(() -> new RuntimeException("Control no encontrado"));
    }
}
