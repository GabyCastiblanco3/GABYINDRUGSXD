package com.example.Indrugs.Configuraciones;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
public class SeguridadConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        // El  12 son las veces que se hace el hash, o sea la fuerza de la seguridad
        return new BCryptPasswordEncoder(12);
    }


}

