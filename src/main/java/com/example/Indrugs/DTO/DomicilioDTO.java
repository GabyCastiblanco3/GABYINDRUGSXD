package com.example.Indrugs.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
    @Setter
    public class DomicilioDTO {
        private Long idDomicilio;
        private String ubicacionDomicilio;
        private String estadoDomicilio;
        private LocalDateTime fechaEntregaDomicilio;
        private Long idVehiculo;
        private Long idOrden;
        private String telefonoOrden;
        private String formulaMedica;
}

