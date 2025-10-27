package com.example.Indrugs.services;

import com.example.Indrugs.DTO.Usuario.UsuarioCreateDTO;
import com.example.Indrugs.DTO.Usuario.UsuarioDTO;
import com.example.Indrugs.DTO.Usuario.UsuarioUpdateDTO;
import com.example.Indrugs.entities.Rol;
import com.example.Indrugs.entities.Usuario;
import com.example.Indrugs.mapper.UsuarioMapper;
import com.example.Indrugs.repositorios.RolRepository;
import com.example.Indrugs.repositorios.UsuarioRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UsuarioServiceImpl implements UsuarioService{

    RolRepository rolRepository;
    UsuarioRepository usuarioRepository;
    BCryptPasswordEncoder passwordEncoder;

    public UsuarioServiceImpl(RolRepository rolRepository, UsuarioRepository usuarioRepository, BCryptPasswordEncoder passwordEncoder ){
        this.rolRepository = rolRepository;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<UsuarioDTO> read() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios.stream()
                .map(UsuarioMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void crear(UsuarioCreateDTO userCreate) {

        if(usuarioRepository.existsByCorreo(userCreate.getCorreo())){
                throw new RuntimeException("Ya existe un usuario con ese correo");}
        if (usuarioRepository.existsByNumDoc(userCreate.getNumDoc())) {
            throw new RuntimeException("Ya existe un usuario con ese número de documento");
        }
        Usuario usuario = UsuarioMapper.mapNewToEntitie(userCreate);
        String passwordEncriptada = passwordEncoder.encode(userCreate.getPassword());
        usuario.setPassword(passwordEncriptada);
        Rol rol = rolRepository.findById(userCreate.getRol())
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        usuario.setRol(rol);
        usuario.setEstado("ACTIVO");
        usuarioRepository.save(usuario);
    }

    @Override
    public void actualizar(Long idUsuario, UsuarioUpdateDTO userUpdate) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        UsuarioMapper.mapUpdateTo(usuario, userUpdate);
        usuarioRepository.save(usuario);
    }

    @Override
    public void eliminar(Long idUsuario) {
        usuarioRepository.deleteById(idUsuario);
    }

    @Override
    public List<UsuarioDTO> findByRol(Long idRol) {
        List<Usuario> usuarios = usuarioRepository.findByRol_idRol(idRol);
        return usuarios.stream()
                .map(UsuarioMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<UsuarioDTO> findByStatus(String estado) {
        List<Usuario> usuarios = usuarioRepository.findByEstado(estado);
        return usuarios.stream()
                .map(UsuarioMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Usuario autenticar(String correo, String password) {
        Usuario usuario = usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new RuntimeException("Usuario no registrado"));

        if (!passwordEncoder.matches(password, usuario.getPassword())) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        return usuario;
    }

    public UsuarioDTO findById(Long idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return UsuarioMapper.mapToDto(usuario);
    }

    @Override
    public boolean existsByCorreo(String correo) {
        return usuarioRepository.existsByCorreo(correo);
    }

    @Override
    public boolean existsByNumDoc(String numDoc) {
        return usuarioRepository.existsByNumDoc(numDoc);
    }

    @Override
    public long contarUsuariosPorRol(String nombreRol) {
        return usuarioRepository.countByRol_nombreRol(nombreRol);
    }

    @Override
    public long contarUsuariosActivos() {
        return usuarioRepository.countByEstado("ACTIVO");
    }

    @Override
    public Map<String, Long> obtenerResumenUsuarios() {
        Map<String, Long> resumen = new HashMap<>();
        resumen.put("pacientes", contarUsuariosPorRol("Paciente"));
        resumen.put("domiciliarios", contarUsuariosPorRol("Domiciliario"));
        resumen.put("administradores", contarUsuariosPorRol("Administrador"));
        resumen.put("ACTIVOS", contarUsuariosActivos());
        return resumen;
    }
//
    @Override
    public List<UsuarioDTO> obtenerUsuariosRecientes() {
        List<Usuario> usuarios = usuarioRepository.findTop5ByOrderByIdUsuarioDesc();
        return usuarios.stream()
                .map(UsuarioMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<UsuarioDTO> findByRolNombre(String nombreRol) {
        List<Usuario> usuarios = usuarioRepository.findByRol_nombreRol(nombreRol);
        return usuarios.stream()
                .map(UsuarioMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<UsuarioDTO> findByRolNombreAndEstado(String nombreRol, String estado) {
        List<Usuario> usuarios = usuarioRepository.findByRol_nombreRolAndEstado(nombreRol, estado);
        return usuarios.stream()
                .map(UsuarioMapper::mapToDto)
                .collect(Collectors.toList());
    }


}
