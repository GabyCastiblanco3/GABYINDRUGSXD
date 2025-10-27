package com.example.Indrugs.DTO.Usuario;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UsuarioDTO {

        private Long idUsuario;

        private String nombre;

        private String tipoDoc;

        private String numDoc;

        private String direccion;

        private String estado;

        private String telefono;

        private String correo;

        private String nombreRol;
}
