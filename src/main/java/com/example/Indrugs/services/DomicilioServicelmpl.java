package com.example.Indrugs.services;

import com.example.Indrugs.DTO.DomicilioDTO;
import com.example.Indrugs.entities.Control;
import com.example.Indrugs.entities.Domicilio;
import com.example.Indrugs.entities.Inventario;
import com.example.Indrugs.mapper.ControlMapper;
import com.example.Indrugs.mapper.DomicilioMapper;
import com.example.Indrugs.mapper.InventarioMapper;
import com.example.Indrugs.repositorios.DomicilioRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

    @Service
public class DomicilioServicelmpl implements DomicilioService{
    private final DomicilioRepository domicilioRepository;
    public DomicilioServicelmpl(DomicilioRepository domicilioRepository){
        this.domicilioRepository=domicilioRepository;
    }
    @Override
    public List<DomicilioDTO> read() {
        List<Domicilio> domicilio = domicilioRepository.findAll();
        return domicilio.stream()
                .map(DomicilioMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void crear(DomicilioDTO domicilioDTO) {
        Domicilio domicilio = DomicilioMapper.toEntity(domicilioDTO, null, null);
        domicilioRepository.save(domicilio);

    }

    @Override
    public void actualizar(Long idDomicilio) {
        Domicilio domicilio = domicilioRepository.findByIdDomicilio(idDomicilio)
                .orElseThrow(() -> new RuntimeException("Domicilio no encontrado con id: " + idDomicilio));
            domicilio.setEstadoDomicilio("ENTREGADO");
            domicilioRepository.save(domicilio);

    }

    @Override
    public List<DomicilioDTO> findByEstadoDomicilio(String estadoDomicilio) {
        List<Domicilio> domicilio = domicilioRepository.findByEstadoDomicilio(estadoDomicilio);
        return domicilio.stream()
                .map(DomicilioMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public long countDomicilioActivo() {
        return domicilioRepository.countByEstadoDomicilio("EN ESPERA");
    }

    @Override
    public List<DomicilioDTO> ObtenerDomiciliosRecientes() {
        List<Domicilio> domicilio = domicilioRepository.findTop3ByOrderByIdDomicilioDesc();
        return domicilio.stream()
                .map(DomicilioMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Map<String, Object> ObtenerResumen() {
        Map<String, Object> dashboard = new HashMap<>();


        long domiciliosActivos = domicilioRepository.countByEstadoDomicilio("EN ESPERA");
        dashboard.put("totalDomiciliosActivos", domiciliosActivos);


        List<Domicilio> top3 = domicilioRepository.findTop3ByOrderByIdDomicilioDesc();
        dashboard.put("domiciliosRecientes", top3);

        return dashboard;
    }
}
