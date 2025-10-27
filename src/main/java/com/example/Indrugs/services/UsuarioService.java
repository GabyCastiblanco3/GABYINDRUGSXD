package com.example.Indrugs.services;


import com.example.Indrugs.DTO.Usuario.UsuarioCreateDTO;
import com.example.Indrugs.DTO.Usuario.UsuarioDTO;
import com.example.Indrugs.DTO.Usuario.UsuarioUpdateDTO;
import com.example.Indrugs.entities.Usuario;

import java.util.List;
import java.util.Map;

public interface UsuarioService  {

    List<UsuarioDTO> read();
    void crear(UsuarioCreateDTO userCreate);
    void actualizar(Long idUsuario, UsuarioUpdateDTO userUpdate);
    void eliminar(Long idUsuario);
    List<UsuarioDTO>findByRol(Long idRol);
    List<UsuarioDTO> findByStatus(String estado);
    Usuario autenticar(String correo, String password);
    boolean existsByCorreo(String correo);
    boolean existsByNumDoc(String numDoc);
    long contarUsuariosPorRol(String nombreRol);
    long contarUsuariosActivos();
    Map<String, Long> obtenerResumenUsuarios();
    List<UsuarioDTO> obtenerUsuariosRecientes();
    List<UsuarioDTO> findByRolNombreAndEstado(String nombreRol, String estado);
    List<UsuarioDTO> findByRolNombre(String nombreRol);
    UsuarioDTO findById(Long idUsuario);
}
