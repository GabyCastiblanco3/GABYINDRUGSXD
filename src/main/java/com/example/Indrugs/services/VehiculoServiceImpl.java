package com.example.Indrugs.services;

import com.example.Indrugs.DTO.VehiculoDTO;
import com.example.Indrugs.entities.Vehiculo;
import com.example.Indrugs.mapper.VehiculoMapper;
import com.example.Indrugs.repositorios.VehiculoRepository;
import com.example.Indrugs.repositorios.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VehiculoServiceImpl implements VehiculoService {

    private final VehiculoRepository vehiculoRepository;
    private final UsuarioRepository usuarioRepository;

    public VehiculoServiceImpl(VehiculoRepository vehiculoRepository, UsuarioRepository usuarioRepository) {
        this.vehiculoRepository = vehiculoRepository;
        this.usuarioRepository = usuarioRepository;

    }

    @Override
    public List<VehiculoDTO> read(Long IdUsuario) {

        return vehiculoRepository.findByIdPropietario_IdUsuario(IdUsuario).stream()
                .map(VehiculoMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void crear(VehiculoDTO vehiculoDTO) {
        Vehiculo vehiculo = VehiculoMapper.mapToEntity(vehiculoDTO);
        vehiculoRepository.save(vehiculo);
    }

    @Override
    public void actualizar(Long idVehiculo, VehiculoDTO vehiculoDTO) {
        Vehiculo vehiculo = vehiculoRepository.findById(idVehiculo)
                .orElseThrow(() -> new RuntimeException("Vehículo no encontrado"));
        // Actualizar campos usando el mapper y el repositorio de usuarioç

        VehiculoMapper.mapToEntity(vehiculoDTO);
        vehiculoRepository.save(vehiculo);

    }

    @Override
    public void eliminar(Long idVehiculo) {
        vehiculoRepository.deleteById(idVehiculo);
    }

    @Override
    public VehiculoDTO findById(Long idVehiculo) {
        Vehiculo vehiculo = vehiculoRepository.findById(idVehiculo)
                .orElseThrow(() -> new RuntimeException("Vehículo no encontrado"));
        return VehiculoMapper.mapToDto(vehiculo);
    }
}
