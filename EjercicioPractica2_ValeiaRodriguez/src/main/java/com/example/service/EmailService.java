package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void enviarCorreoBienvenida(String destinatario, String nombreUsuario) {
        try {
            SimpleMailMessage mensaje = new SimpleMailMessage();
            mensaje.setTo(destinatario);
            mensaje.setSubject("Bienvenido/a al sistema!");
            mensaje.setText("Hola " + nombreUsuario + ",\n\n"
                    + "Tu cuenta ha sido creada exitosamente.\n"
                    + "Ya puedes acceder al sistema.\n\n"
                    + "Saludos,\nEl equipo del sistema.");
            mailSender.send(mensaje);
        } catch (Exception e) {
            System.err.println("Error al enviar correo a " + destinatario + ": " + e.getMessage());
        }
    }
}
