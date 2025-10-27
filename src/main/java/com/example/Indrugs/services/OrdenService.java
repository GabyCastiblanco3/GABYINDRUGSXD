package com.example.Indrugs.services;

import com.example.Indrugs.DTO.OrdenDTO;
import com.example.Indrugs.entities.Orden;

import java.util.List;
import java.util.Map;

public interface OrdenService {
    List<OrdenDTO> listarOrdenes();
    List<OrdenDTO> listarOrdenesP(Long idUsuario);
    void marcarComoEntregada(Long idOrden);
    void crear (OrdenDTO ordenDTO,Long idUsuario,Long idMedicameto);
    void eliminar (Long idOrden);
    long countOrdenActivo();
    List<OrdenDTO> ObtenerOrdenesRecientes();
    Map<String,Object> ObtenerResumenOrden();
    void crearDomicilioConOrden(Orden orden);

}
