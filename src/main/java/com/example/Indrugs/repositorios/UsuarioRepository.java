package com.example.Indrugs.repositorios;

import com.example.Indrugs.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    List<Usuario> findByRol_idRol(Long idRol);
    List<Usuario> findByEstado(String estado);
    boolean existsByCorreo(String correo);
    boolean existsByNumDoc(String numDoc);
    Optional<Usuario> findByCorreo(String correo);
    long countByRol_nombreRol(String nombreRol);
    long countByEstado(String estado);
    List<Usuario> findTop5ByOrderByIdUsuarioDesc();
    List<Usuario> findByRol_nombreRolAndEstado(String nombreRol, String estado);
    List<Usuario> findByRol_nombreRol(String nombreRol);
    Optional<Usuario> findByIdUsuario(Long idUsuario);
}
