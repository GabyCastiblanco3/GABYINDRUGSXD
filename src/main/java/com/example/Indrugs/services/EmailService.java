package com.example.Indrugs.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void enviarCorreoRegistro(String destinatario, String nombre) {
        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setFrom("indrugs3@gmail.com");
        mensaje.setTo(destinatario);
        mensaje.setSubject("¡Bienvenido a Indrugs!");
        mensaje.setText("Hola " + nombre + ",\n\nTu registro fue exitoso.\n\n¡Gracias por unirte!");
        mailSender.send(mensaje);
    }
}
