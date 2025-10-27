package com.example.Indrugs.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "ordenes")
public class Orden {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ORDENES")
    private Long idOrden;

    @ManyToOne
    @JoinColumn(name = "USUARIOS_PACIENTE")
    private Usuario paciente;

    @Column(name = "CANTIDADMED_ORDEN")
    private Integer cantidad;

    @Column(name = "DIRECCION_ORDEN")
    private String direccionOrden;

    @Column(name = "EPS_ORDEN")
    private String epsOrden;

    @Column(name = "TELEFONO_ORDEN")
    private String telefonoOrden;

    @Column(name = "FECHA_ENTREGA")
    private LocalDateTime fechaEntrega;

    @Column(name = "ESTADO_ORDEN")
    private String estadoOrden;

    @Column(name ="FORMULA_MEDICA")
    private String formulaMedica;

    @OneToOne(mappedBy = "orden", cascade = CascadeType.ALL, orphanRemoval = true)
    private Domicilio domicilio;

    @ManyToMany
    @JoinTable(
            name = "ordenes_has_medicamentos",
            joinColumns = @JoinColumn(name = "ID_ORDENES"),
            inverseJoinColumns = @JoinColumn(name = "ID_MEDICAMENTOS")
    )
    private List<Medicamentos> medicamentos;
}
