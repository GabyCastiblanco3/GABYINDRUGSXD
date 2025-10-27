package com.example.Indrugs.repositorios;

import com.example.Indrugs.entities.Control;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ControlRepository extends JpaRepository<Control, Long> {

    List<Control> findByUsuario_IdUsuario(Long idUsuario);

}