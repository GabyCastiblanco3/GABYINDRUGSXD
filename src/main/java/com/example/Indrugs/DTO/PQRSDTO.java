package com.example.Indrugs.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PQRSDTO {

    private Long idPqrs;

    private Long usuarioid;

    private String tipoPqrs;

    private String motivo;

    private LocalDateTime fechaPqrs;
}
