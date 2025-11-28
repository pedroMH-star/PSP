import java.io.*;
import java.net.*;
import java.util.*;
import java.net.ServerSocket;

/**
 * @autor Pedro Martínez Herrero
 * @since 18/11/2025
 * @until 18/11/2025
 * Actividad: UT4 - (Servidor socket)
 */

public class ServidorChat {
    public static void main(String[] args) throws IOException {

        //1º) Definimos el puerto en el que el servidor estará escuchando conexiones
        final int Puerto = 12345;

        //2º) Bloque try-with-resources, para crear el ServerSocket como recurso, y que este se cierre al finalizar
        try(ServerSocket serverSocket = new ServerSocket(Puerto)) {
            System.out.println("Servidor Iniciado. Esperando conexiones en el puerto " + Puerto + "...");
            // a) Bucle infinito: el servidor debe estar siempre escuchando para aceptar a los clientes
            while(true) {
                // i) Dentro del bucle: bloquear la ejecución del programa hasta que un cliente se conecta
                Socket socketCliente = serverSocket.accept();
                System.out.println("Cliente conectado desde: "
                        + socketCliente.getInetAddress().getHostName()
                        + ":" + socketCliente.getPort());

                // ii) Otro try-with-resources (con bloque finally) para gestionar automáticamente los streams de entrada-salida
                try (//ii.a) Dentro de los resources de este try -> leer los mensajes del cliente linea por linea()
                     BufferedReader entrada = new BufferedReader(new InputStreamReader(socketCliente.getInputStream()));
                     //ii.b) También dentro del bloque de recursos de este try, se incluye el código para enviar
                     //      respuestas al cliente -> PrintWriter
                     PrintWriter salida = new PrintWriter(socketCliente.getOutputStream(), true);
                ){
                    String mensajeCliente;
                    //ii.c) Dentro del bloque try (pero ya fuera de los recursos) crear un bucle while que lea los
                    // mensajes mientras que el cliente siga enviando datos (readline () devuelve null cuando el
                    // cliente cierra la conexión)
                    while((mensajeCliente = entrada.readLine()) != null) {
                        //Dentro del while:
                        // 1.- Mostrar en la consola del servidor lo que el cliente ha enviado
                        System.out.println("El cliente dice: " + mensajeCliente);
                        //2.- Construir la respuesta que le vamos a enviar al cliente y enviarla
                        // de vuelta al cliente
                        String respuesta = "Mensaje recibido del cliente. Este es el mensaje del cliente: " + mensajeCliente;
                        salida.println(respuesta);
                    }

                    // iii) Cerrar el bloque try, y abrir la parte del catch para capturar excepciones
                } catch (IOException e) {
                    System.out.println("Error en el servidor: " + e.getMessage());
                    // iv) Abrir el subbloque finally, donde cerraremos el socket del cliente
                } finally {
                    socketCliente.close();
                    System.out.println("Cliente terminado desde: " + socketCliente.getInetAddress().getHostAddress());
                }
            }
            //3º Cerramos el primer bloque try, y abrimos su catch correspondiente para capturar posibles errores que puedan haber al iniciar el sevidor
        } catch (IOException e){
            System.out.println("Error en el servidor: " + e.getMessage());
        }
    }
}