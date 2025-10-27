package com.example.Indrugs.services;

import com.example.Indrugs.DTO.OrdenDTO;
import com.example.Indrugs.entities.Domicilio;
import com.example.Indrugs.entities.Medicamentos;
import com.example.Indrugs.entities.Orden;
import com.example.Indrugs.entities.Usuario;
import com.example.Indrugs.mapper.OrdenMapper;
import com.example.Indrugs.repositorios.DomicilioRepository;
import com.example.Indrugs.repositorios.MedicamentoRepository;
import com.example.Indrugs.repositorios.OrdenRepository;
import com.example.Indrugs.repositorios.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrdenServiceImpl implements OrdenService {

    private final OrdenRepository ordenRepository;
    private final MedicamentoRepository medicamentoRepository;
    private final UsuarioRepository usuarioRepository;
    private final DomicilioRepository domicilioRepository;

    public OrdenServiceImpl(OrdenRepository ordenRepository, MedicamentoRepository medicamentoRepository, UsuarioRepository usuarioRepository, DomicilioRepository domicilioRepository) {
        this.ordenRepository = ordenRepository;
        this.medicamentoRepository = medicamentoRepository;
        this.usuarioRepository = usuarioRepository;
        this.domicilioRepository = domicilioRepository;

    }

    @Override
    public List<OrdenDTO> listarOrdenes() {
        List<Orden> ordenes = ordenRepository.findAll();
        return OrdenMapper.toDTOList(ordenes);
    }

    @Override
    public List<OrdenDTO> listarOrdenesP(Long idUsuario) {
        List<Orden> ordenes = ordenRepository.findByPaciente_IdUsuario(idUsuario);
        return ordenes.stream()
                .map(OrdenMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void marcarComoEntregada(Long idOrden) {
        Orden orden = ordenRepository.findById(idOrden)
                .orElseThrow(() -> new RuntimeException("Orden no encontrada con ID: " + idOrden));

        orden.setEstadoOrden("Entregada");
        ordenRepository.save(orden);
    }


    @Override
    public void crear(OrdenDTO ordenDTO,Long idUsuario, Long idMedicamento)    {

            Orden orden = OrdenMapper.toEntity(ordenDTO);
        Usuario usuario = usuarioRepository.findByIdUsuario(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        orden.setPaciente(usuario);
        Medicamentos medicamento = medicamentoRepository.findById(idMedicamento)
                .orElseThrow(() -> new RuntimeException("Medicamento no encontrado"));
        orden.setMedicamentos(List.of(medicamento));
        ordenRepository.save(orden);

        crearDomicilioConOrden(orden);

    }

    @Override
    public void crearDomicilioConOrden(Orden orden) {
        Domicilio domicilio = new Domicilio();
        domicilio.setOrden(orden);
        domicilio.setUbicacionDomicilio(orden.getDireccionOrden());
        domicilio.setEstadoDomicilio("EN ESPERA");
        domicilio.setFechaEntregaDomicilio(orden.getFechaEntrega());

        domicilioRepository.save(domicilio);
    }


    @Override
    public void eliminar(Long idOrden) {
        ordenRepository.deleteById(idOrden);
    }

    @Override
    public long countOrdenActivo() {
        return ordenRepository.countByEstadoOrden("ACTIVO");
    }

    @Override
    public List<OrdenDTO> ObtenerOrdenesRecientes() {
        List<Orden> domicilio = ordenRepository.findTop4ByOrderByIdOrdenDesc();
        return domicilio.stream()
                .map(OrdenMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Map<String, Object> ObtenerResumenOrden() {

        Map<String, Object> dashboard = new HashMap<>();


        long ordenesActivos = ordenRepository.countByEstadoOrden("ACTIVO");
        dashboard.put("totalOrdenesActivos", ordenesActivos);


        List<Orden> top4Orden = ordenRepository.findTop4ByOrderByIdOrdenDesc();
        dashboard.put("ordenesRecientes", top4Orden);

        return dashboard;
    }



    public OrdenDTO obtenerOrdenPorId(Long id) {
        Orden orden = ordenRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Orden no encontrada con ID: " + id));
        return OrdenMapper.toDTO(orden);
    }


}