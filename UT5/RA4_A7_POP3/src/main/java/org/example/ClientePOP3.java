package org.example;

import org.apache.commons.net.pop3.POP3Client;
import org.apache.commons.net.pop3.POP3MessageInfo;
import java.io.BufferedReader;

/*
 * author: Pedro Martínez Herrero
 * @since: 13/01/2026
 * @until: 15/01/2026
 * Actividad Extra: Cliente POP3
 */

public class ClientePOP3 {

    public static void main(String[] args) {

        String servidor = "pop.gmail.com";
        int puerto = 995; // SSL
        String usuario = "1813227@alu.murciaeduca.es";
        String password = "mhpaee1245?";

        POP3Client cliente = new POP3Client();

        try {
            // Conexión segura
            cliente.setDefaultTimeout(10000);
            cliente.connect(servidor, puerto);

            if (!cliente.login(usuario, password)) {
                System.out.println("Error de autenticación.");
                cliente.disconnect();
                return;
            }

            System.out.println("Conectado al servidor POP3");

            // Información del buzón
            POP3MessageInfo estado = cliente.status();
            System.out.println("Mensajes en el buzón: " + estado.number);
            System.out.println("Tamaño total: " + estado.size + " bytes");

            if (estado.number > 0) {
                System.out.println("\nLeyendo el primer mensaje...\n");

                // retrieveMessage devuelve un Reader, lo envolvemos en BufferedReader
                BufferedReader reader = new BufferedReader(cliente.retrieveMessage(1));

                String linea;
                boolean cuerpo = false;
                int contador = 0;

                while ((linea = reader.readLine()) != null && contador < 20) {
                    if (linea.startsWith("From:") || linea.startsWith("Subject:")) {
                        System.out.println(linea);
                    }

                    if (linea.isEmpty()) {
                        cuerpo = true;
                        System.out.println("\n--- Cuerpo del mensaje ---");
                        continue;
                    }

                    if (cuerpo) {
                        System.out.println(linea);
                        contador++;
                    }
                }

                reader.close(); // cerrar reader al final
            }

            // Cierre de sesión
            cliente.logout();
            cliente.disconnect();
            System.out.println("\nConexión cerrada correctamente.");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}