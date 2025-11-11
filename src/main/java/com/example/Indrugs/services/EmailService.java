package com.example.Indrugs.services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    /**
     * Enviar correo genérico con contenido HTML (mensaje predefinido)
     *
     * @param destinatario correo del destinatario
     * @throws MessagingException excepción si falla la creación del mensaje
     */
    public void enviarCorreo(String destinatario) throws MessagingException {
        MimeMessage mensaje = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mensaje, true); // true permite contenido HTML

        // Configuración del correo
        helper.setTo(destinatario);
        helper.setSubject("INDRUGS MEDICA");
        helper.setFrom("indrugsmedica@gmail.com");

        // Contenido HTML
        String contenidoHtml = "<html>" +
                "<body style='font-family: Arial, sans-serif;'>" +
                "<h2 style='color: #00796b;'>¡Gracias por usar INDRUGS MEDICAL!</h2>" +
                "<p>Hola, acabas de ingresar un nuevo control en nuestra plataforma.</p>" +
                "<table style='border-collapse: collapse; width: 100%;'>" +
                "<tr style='background-color: #f2f2f2;'>" +
                "<th style='padding: 10px; text-align: left; border: 1px solid #ddd;'>Fecha Inicio</th>" +
                "<th style='padding: 10px; text-align: left; border: 1px solid #ddd;'>Fecha Fin</th>" +
                "<th style='padding: 10px; text-align: left; border: 1px solid #ddd;'>Problema de Salud</th>" +
                "</tr>" +
                "<tr>" +
                "<td style='padding: 10px; border: 1px solid #ddd;'>15 Nov 2025</td>" +
                "<td style='padding: 10px; border: 1px solid #ddd;'>15 Dic 2025</td>" +
                "<td style='padding: 10px; border: 1px solid #ddd;'>Dolor en las articulaciones</td>" +
                "</tr>" +
                "</table>" +
                "<p style='margin-top: 20px;'>Gracias por elegirnos. ¡Estamos aquí para ayudarte!</p>" +
                "</body>" +
                "</html>";

        helper.setText(contenidoHtml, true);

        try {
            mailSender.send(mensaje);
        } catch (MailException e) {
            e.printStackTrace();
        }
    }

    /**
     * Enviar correo de registro personalizado
     */
    public void enviarCorreoRegistro(
            @NotBlank(message = "El correo es obligatorio")
            @Email(message = "Debe ser un correo válido") String correo,
            @NotBlank(message = "El nombre es obligatorio")
            @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres") String nombre) {
        try {
            MimeMessage mensaje = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mensaje, true);

            helper.setTo(correo);
            helper.setSubject("Bienvenido a INDRUGS MEDICA");
            helper.setFrom("indrugsmedica@gmail.com");

            String contenidoHtml = "<html>" +
                    "<body style='font-family: Arial, sans-serif;'>" +
                    "<h2 style='color: #00796b;'>¡Bienvenido, " + nombre + "!</h2>" +
                    "<p>Tu registro en INDRUGS MEDICA se ha completado exitosamente.</p>" +
                    "<p>Ahora puedes acceder a nuestra plataforma y empezar a gestionar tus pedidos de manera segura.</p>" +
                    "<p style='margin-top: 20px;'>Gracias por confiar en nosotros.</p>" +
                    "</body>" +
                    "</html>";

            helper.setText(contenidoHtml, true);

            mailSender.send(mensaje);

        } catch (MessagingException | MailException e) {
            e.printStackTrace();
        }
    }

    /**
     * 🔥 Nuevo método genérico: permite enviar cualquier correo HTML con asunto y contenido personalizados.
     * Útil para notificaciones de eliminación de orden o mensajes especiales.
     */
    public void enviarCorreo(String destinatario, String asunto, String contenidoHtml) throws MessagingException {
        MimeMessage mensaje = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mensaje, true, "UTF-8");

        helper.setTo(destinatario);
        helper.setSubject(asunto);
        helper.setFrom("indrugsmedica@gmail.com");
        helper.setText(contenidoHtml, true);

        try {
            mailSender.send(mensaje);
        } catch (MailException e) {
            e.printStackTrace();
        }
    }
}
