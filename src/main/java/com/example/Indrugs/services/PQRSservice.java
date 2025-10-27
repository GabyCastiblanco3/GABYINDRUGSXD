package com.example.Indrugs.services;

import com.example.Indrugs.DTO.PQRSDTO;

import java.util.List;

public interface PQRSservice {
    void crear (PQRSDTO pqrsdto,Long idUsuario);
            // accesibilidad (pubic, private,),lo que se devuelve (list,void,dto),nombre del metodo(crear,actualizar),parametro()

    List<PQRSDTO> listarTodo(); // opcional: para listar PQRS enviados

    PQRSDTO obtenerPorId(Long id);

    void eliminar(Long id);
}
