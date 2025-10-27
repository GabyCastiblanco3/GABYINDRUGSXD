package com.example.Indrugs.repositorios;

import com.example.Indrugs.entities.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InventarioRepository extends JpaRepository<Inventario, Long> {
    List<Inventario> findByEstadoMed(String estadoMed);
    @Query("SELECT SUM(i.stock) FROM Inventario i")
    Long contarUnidadesEnStock();
    boolean existsByIdMedicamento_IdMedicamento(Long idMedicamento);
}
