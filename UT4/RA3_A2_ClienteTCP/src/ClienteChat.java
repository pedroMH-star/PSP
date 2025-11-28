import java.io.*;
import java.net.*;

/**
 * @autor Pedro Martínez Herrero
 * @since 18/11/2025
 * @until 19/11/2025
 * Actividad: UT4 - (Cliente socket)
 */

public class ClienteChat {

    public static void main(String[] args) {

        // Declaro la dirección ip del host como String
        String direccionIP = "127.0.0.1"; // localhost

        // Declaro el puerto ¡¡¡EN EL QUE ESTÁ ESCUCHANDO EL SERVIDOR!!!
        int Puerto = 12345;

        // Bloque try - with - resources
        try (
                // a) Instancio un objeto de la clase Socket que representa la conexión del cliente con el servidor
                Socket socketCliente = new Socket(direccionIP, Puerto);

                // b) Instancio un objeto de la clase BufferedReader para leer las respuestas que envia el servidor
                BufferedReader entradaServidor = new BufferedReader(
                        new InputStreamReader(socketCliente.getInputStream())
                );

                // c) Instancio un objeto de la clase PrintWriter para enviar mensajes al servidor
                PrintWriter salidaServidor = new PrintWriter(socketCliente.getOutputStream(), true);

                // d) Instancio un objeto de la clase BufferedReader para leer lo que el usuario escribe por consola
                BufferedReader tecladoUsuario = new BufferedReader(
                        new InputStreamReader(System.in)
                )
        ) {

            System.out.println("Cliente conectado al servidor. Puedes empezar a escribir mensajes.");

            // Bucle infinito para que el cliente este activo y listo para enviar mensajes continuamente
            // De esta manera no necesita reconectarse cada vez
            while (true) {

                // i) Solicitamos por consola al usuario que escriba un mensaje
                System.out.print("Escribe un mensaje: ");

                // ii) el metodo readline() lee la linea completa que el usuario escribe por consola
                String mensaje = tecladoUsuario.readLine();

                // iii) Enviamos el mensaje al servidor
                salidaServidor.println(mensaje);

                // iv) Esperamos la respuesta del servidor y la mostramos
                String respuesta = entradaServidor.readLine();
                System.out.println("Servidor responde: " + respuesta);
            }

        } catch (IOException e) {
            System.out.println("Error en el cliente: " + e.getMessage());
        }
    }
}