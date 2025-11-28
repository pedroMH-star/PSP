import java.io.*;
import java.net.*;
import java.util.Scanner;

/**
 * author: Pedro Martínez Herrero
 * @since 25/11/2025
 * @until 26/11/2025
 * Actividad: UT4 - Cliente UDP
 */

public class UDPClient {

    // Definimos el puerto y host del servidor al que nos conectaremos
    private static final int SERVER_PORT = 12345;
    private static final String SERVER_HOST = "localhost"; // o la IP del servidor

    public static void main(String[] args) {

        // Scanner para leer la entrada del usuario
        Scanner scanner = new Scanner(System.in);

        // Bloque try-with-resources: Creamos un DatagramSocket para enviar/recibir datagramas
        try (DatagramSocket clientSocket = new DatagramSocket()) {

            System.out.println("Cliente UDP iniciado. Escribe @ayuda@ para ver los comandos disponibles.");

            String message = ""; // Variable para almacenar el mensaje que enviaremos

            // Bucle principal: se mantiene hasta que el usuario escriba "@salir@"
            while (!message.equalsIgnoreCase("@salir@")) {

                // Mostramos opciones al usuario
                System.out.println("\n### Menú ###");
                System.out.println("1. Consultar sensor");
                System.out.println("2. Listado de sensores");
                System.out.println("3. Ayuda");
                System.out.println("4. Salir");
                System.out.print("Elige una opción: ");

                String input = scanner.nextLine(); // Leer toda la línea
                int option;

                try {
                    option = Integer.parseInt(input); // Intentar convertir a número
                } catch (NumberFormatException e) {
                    System.out.println("Opción incorrecta. Debes introducir un número del 1 al 4");
                    continue; // Volver al inicio del bucle
                }

                // Selección de mensaje según la opción
                switch (option) {
                    case 1:
                        System.out.print("Introduce el nombre del sensor: ");
                        String sensorName = scanner.nextLine().toLowerCase();
                        message = "@sensor#" + sensorName + "@";
                        break;

                    case 2:
                        message = "@listadoSensores@";
                        break;

                    case 3:
                        message = "@ayuda@";
                        break;

                    case 4:
                        message = "@salir@";
                        break;

                    default:
                        System.out.println("Opción incorrecta. Intenta de nuevo.");
                        continue; // Volvemos al inicio del bucle
                }

                // Convertimos el mensaje en bytes para enviarlo
                byte[] sendBuffer = message.getBytes();

                // Creamos un DatagramPacket con los datos, host y puerto del servidor
                DatagramPacket sendPacket = new DatagramPacket(
                        sendBuffer,
                        sendBuffer.length,
                        InetAddress.getByName(SERVER_HOST),
                        SERVER_PORT
                );

                // Enviamos el paquete al servidor
                clientSocket.send(sendPacket);

                // Preparamos buffer para recibir respuesta del servidor
                byte[] receiveBuffer = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);

                // Esperamos a recibir la respuesta
                clientSocket.receive(receivePacket);

                // Convertimos la respuesta a String y la mostramos
                String serverResponse = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("\nRespuesta del servidor:\n" + serverResponse);
            }

            System.out.println("Cliente finalizado.");

        } catch (UnknownHostException e) {
            System.err.println("Host desconocido: " + e.getMessage());
        } catch (SocketException e) {
            System.err.println("Error al crear el socket: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error de entrada/salida: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}