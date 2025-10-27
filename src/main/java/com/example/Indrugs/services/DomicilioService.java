package com.example.Indrugs.services;
import com.example.Indrugs.DTO.DomicilioDTO;
import java.util.List;
import java.util.Map;

public interface DomicilioService {
    List<DomicilioDTO> read();
    void crear(DomicilioDTO domicilioDTO);
    void actualizar(Long idDomicilio);
    List<DomicilioDTO> findByEstadoDomicilio(String estadoDomicilio);
    long countDomicilioActivo();
    List<DomicilioDTO> ObtenerDomiciliosRecientes();
    Map<String,Object> ObtenerResumen();
}


