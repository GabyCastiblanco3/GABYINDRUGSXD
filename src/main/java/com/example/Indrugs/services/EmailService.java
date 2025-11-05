package com.example.Indrugs.services;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void enviarCorreo(String destinatario) {
        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setTo(destinatario);
        mensaje.setSubject("INDRUGS MEDICA");
        mensaje.setText("Hola, acabas de ingresar un nuevo control a Indrugs Medica");
        mensaje.setFrom("indrugsmedica@gmail.com");

        mailSender.send(mensaje);
    }


    public void enviarCorreoRegistro(@NotBlank(message = "El correo es obligatorio") @Email(message = "Debe ser un correo válido") String correo, @NotBlank(message = "El nombre es obligatorio") @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres") String nombre) {
    }
}

