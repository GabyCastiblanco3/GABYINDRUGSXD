package com.example.Indrugs.services;

import com.example.Indrugs.DTO.PQRSDTO;
import com.example.Indrugs.entities.PQRS;
import com.example.Indrugs.entities.Usuario;
import com.example.Indrugs.mapper.PQRSMapper;
import com.example.Indrugs.repositorios.PQRSRepository;
import com.example.Indrugs.repositorios.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PQRSserviceImpl implements PQRSservice {

    private final PQRSRepository pqrsRepository;
    private final UsuarioRepository usuarioRepository;

    @Autowired
    public PQRSserviceImpl(PQRSRepository pqrsRepository, UsuarioRepository usuarioRepository) {
        this.pqrsRepository = pqrsRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public void crear(PQRSDTO dto, Long idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));


        if (dto.getTipoPqrs() == null || dto.getTipoPqrs().isEmpty()) {
            throw new IllegalArgumentException("El tipo de solicitud es obligatorio.");
        }
        if (dto.getMotivo() == null || dto.getMotivo().isEmpty()) {
            throw new IllegalArgumentException("El motivo es obligatorio.");
        }

        if (dto.getFechaPqrs() == null) {
            dto.setFechaPqrs(LocalDateTime.now());
        }

        PQRS pqrs = PQRSMapper.toEntity(dto, usuario);
        pqrsRepository.save(pqrs);
    }

    @Override
    public List<PQRSDTO> listarTodo() {
        return pqrsRepository.findAll()
                .stream()
                .map(PQRSMapper::toDTO)
                .toList();
    }

    @Override
    public PQRSDTO obtenerPorId(Long id) {
        return pqrsRepository.findById(id)
                .map(PQRSMapper::toDTO)
                .orElse(null);
    }

    @Override
    public void eliminar(Long id) {
        pqrsRepository.deleteById(id);
    }
}
