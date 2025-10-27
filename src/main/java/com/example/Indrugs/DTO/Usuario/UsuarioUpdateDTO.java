package com.example.Indrugs.DTO.Usuario;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UsuarioUpdateDTO {

    private  Long idUsuario;

    private String nombre;

    private String direccion;

    private String telefono;

    private String correo;

    private Long rol;

    private String estado;

}
