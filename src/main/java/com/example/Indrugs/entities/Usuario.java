package com.example.Indrugs.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
@Table(name="usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_USUARIOS")
    private Long idUsuario;

    @Column(name = "NOMBRE_USUARIOS")
    private String nombre;

    @Column(name = "TIPODOC_USUARIOS")
    private String tipoDoc;

    @Column(name = "NUMDOC_USUARIOS")
    private String numDoc;

    @Column(name = "DIRECCION_USUARIOS")
    private String direccion;

    @Column(name = "ESTADO_USUARIO")
    private String estado;

    @Column(name = "TELEFONO_USUARIOS")
    private String telefono;

    @Column(name = "CORREO_USUARIOS")
    private String correo;

    @Column(name = "CONTRASEÑA_USUARIOS")
    private String password;

    //aca es para que un usuario tenga un rol, conexion de Rol entidad y usuario
    @ManyToOne
    @JoinColumn(name = "ID_ROLES_USUARIOS")
    private Rol rol;
}

