package com.example.Indrugs.repositorios;

import com.example.Indrugs.entities.Medicamentos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MedicamentoRepository extends JpaRepository<Medicamentos, Long> {
    Optional<Medicamentos> findByNombreMedicamento(String nombreMedicamento);
    List<Medicamentos> findByNombreMedicamentoContainingIgnoreCase(String nombreMedicamento);
    boolean existsByNombreMedicamento(String nombreMedicamento);
    long countByNombreMedicamento(String nombreMedicamento);
}
