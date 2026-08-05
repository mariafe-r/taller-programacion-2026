package com.umb.taller.badsmell;

public class EmailService {
    public void sendEmail(String email, String message) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("El correo es obligatorio");
        }
        if (message == null || message.trim().isEmpty()) {
            throw new IllegalArgumentException("El mensaje es obligatorio");
        }

        System.out.println("Enviando correo a: " + email);
        System.out.println("Mensaje: " + message);
    }
}
