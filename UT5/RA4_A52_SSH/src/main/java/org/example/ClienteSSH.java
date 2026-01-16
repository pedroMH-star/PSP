package org.example;

import com.jcraft.jsch.*;
import java.io.IOException;
import java.io.InputStream;

/*
* author: Pedro Martínez Herrero
* @since: 17/12/2025
* @until: 17/12/2025
* Actividad: Cliente con SSH (Maven)
 */

public class ClienteSSH {

    // Configuración para conectarte al servidor
    private static final String USER = "demo";
    private static final String HOST = "test.rebex.net";
    private static final int PORT = 22;
    private static final String PASSWORD = "password";

    public static void main(String[] args) throws JSchException, IOException {

        try {
            // PASO 1: Inicializar JSCH y establecer sesión SSH
            JSch jsch = new JSch();

            // Según esas librerías (dependencias de JSCH) hemos de crear una sesión SSH con usuario, host y puerto
            Session session = jsch.getSession(USER, HOST, PORT);

            // Establecemos la contraseña para autenticarnos
            session.setPassword(PASSWORD);

            // Desactivar la verificación estricta de la clave del host
            // NOTA: En producción esto debería estar activado por seguridad
            session.setConfig("StrictHostKeyChecking", "no");

            // Conexión
            session.connect();
            System.out.println("Conectado al host: " + HOST);

            // Abrir canal: Un canal SSH permite realizar operaciones dentro de la sesión
            //"exec" es el tipo de canal para ejecutar comandos
            ChannelExec channel = (ChannelExec) session.openChannel("exec");

            // Definir el comando que se ejecutará en el servidor remoto
            channel.setCommand("ls -l");

            // No enviaremos datos de entrada al comando
            channel.setInputStream(null);

            // Los errores del servidor se mostrarán en nuestra consola de errores
            channel.setErrStream(System.err);

            // Leer la salida del comando
            InputStream input = channel.getInputStream();

            // Conectar el canal
            channel.connect();

            // Leer la salida byte a byte y mostralar en la consola
            // read() devuelve -1 cuando no hay más datos que leer
            int data;
            // byte data;
            while ((data = input.read()) != -1) {
                // Convertimos el byte a carácter e imprimimos
                System.out.print((char) data);
            }

            // Cerrar la conexión
            channel.disconnect();
            session.disconnect();
            System.out.println("\nConexión cerrada");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}