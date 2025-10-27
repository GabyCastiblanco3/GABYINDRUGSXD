package com.example.Indrugs.DTO.Usuario;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioCreateDTO {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String nombre;

    @NotBlank(message = "El tipo de documento es obligatorio")
    private String tipoDoc;

    @NotBlank(message = "El número de documento es obligatorio")
    //pattern regrex permite que la lo que se va a recibir sea dentro de los valores que estan ahi
    @Pattern(regexp = "\\d{8,10}", message = "El documento debe tener entre 8 y 10 dígitos")
    private String numDoc;

    @NotBlank(message = "La dirección es obligatoria")
    @Size(max = 200, message = "La dirección no puede exceder los 200 caracteres")
    private String direccion;

    @NotBlank(message = "El teléfono es obligatorio")
    @Pattern(regexp = "\\d{7,10}", message = "El teléfono debe tener entre 7 y 10 dígitos")
    private String telefono;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "Debe ser un correo válido")
    private String correo;

    @NotNull(message = "El rol es obligatorio")
    @Min(value = 1, message = "Debe seleccionar un rol válido")
    private Long rol;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    private String password;
}
