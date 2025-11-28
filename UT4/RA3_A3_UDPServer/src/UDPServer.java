import java.io.*;
import java.net.*;

/**
 * author: Pedro Martínez Herrero
 * @since 25/11/2025
 * @until 25/11/2025
 * Actividad: UT4 - Servidor UDP
 */

public class UDPServer {

    // Definimos el puerto en el que el servidor escuchará
private static final int SERVER_PORT = 12345;

    // MÉTODO PRINCIPAL
    public static void main(String[] args) {

        // Bloque try-with-resources: dentro de los recursos -> Creamos el socket UDP en el puerto definido
        try (DatagramSocket serverSocket = new DatagramSocket(SERVER_PORT)) {

            // Bloque while infinito para escuchar continuamente las peticiones de los clientes
            while (true) {

                // Preparamos un buffer para recibir el mensaje del cliente
                byte[] receiveBuffer = new byte[1024];
                // Creamos un DatagramPacket para almacenar el datagrama de recepción
                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);

                // Esperar hasta recibir la petición del cliente
                System.out.println("Esperando datagrama del cliente...");
                serverSocket.receive(receivePacket);

                // Extraemos el mensaje (en forma String) del paquete recibido
                String clientMessage = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("Mensaje recibido desde el cliente: " + clientMessage);

                // Procesamos o analizamos el mensaje para identificar la respuesta adecuada
                String serverResponse = processMessage(clientMessage);

                // RESPONDER:
                // Preparar respuesta: Convertimos la respuesta en bytes para su envío
                byte[] sendBuffer = serverResponse.getBytes();

                // Enviar respuesta
                // ¿Qué necesitamos? Los datos (sendBuffer) ...
                DatagramPacket sendPacket = new DatagramPacket(
                        sendBuffer,
                        sendBuffer.length,
                        receivePacket.getAddress(),
                        receivePacket.getPort()
                );
                serverSocket.send(sendPacket);

                System.out.println("Respuesta enviada: " + serverResponse);
            }

        } catch (SocketException e) {
            System.err.println("Error al crear el socket: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error de entrada/salida:" + e.getMessage());
        }
    }

    // Metodo processMessage
    private static String processMessage(String message) {
        // Convertimos todo el mensaje a minúsculas para un procesamiento más sencillo
        String lowerCaseMessage = message.toLowerCase();

        // Manejamos los distintos comandos
        if (lowerCaseMessage.startsWith("@sensor#") && lowerCaseMessage.endsWith("@")) {
            // Extraemos el nombre del sensor
            // Mensaje ejemplo: @sensor#temperatura@
            return handleSensorMessage(message);

        } else  if (lowerCaseMessage.equalsIgnoreCase("@listadoSensores@")) {
            return "Sensores disponibles: temperatura, humedad y viento";

        } else  if (lowerCaseMessage.equals("@ayuda@")) {
            // Devolvemos un pequeño manual de instrucciones
            return getHelpMessage();

        } else if (lowerCaseMessage.equals("@salir@")) {
            return "Cerrando interacción con el servidor. ¡Hasta luego!";

        } else {
            return "Comando no reconocido. Escribe @ayuda@ para más información.";
        }
    }

    // Procesamiento de solicitudes
    private static String handleSensorMessage(String fullMessage) {
        // Ejemplo del fullMessage: @sensor#temperatura@"
        // Separamos por '#'
        // Split divide el String en un array usando '#' como delimitador
        String[] parts = fullMessage.split("#");

        // Si no hay dos elementos en parts, el formato es incorrecto
        if (parts.length < 2) {
            return "Formato de solicitud de sensor inválido. Usa @sensor#nombreSensor@";
        }

        // Extraemos el nombre del sensor (ej: 'temperatura@')
        String sensorPart = parts[1];
        // A veces viene con el '@' al final, lo quitamos
        String sensorName = sensorPart.replace("@", "").toLowerCase();

        // Generamos un valor aleatorio para simular el sensor
        switch (sensorName) {
            case "temperatura":
                // Temperatura entre 20 y 35
                int temp = 20 + (int) (Math.random() * 16);
                return "Temperatura actual: " + temp + "ºC";

            case "humedad":
                // Humedad de 0 a 100%
                int hum = (int) (Math.random() * 101);
                return "Humedad actual: " + hum + "%";

            case "viento":
                // Velocidad del viento en km/h
                int viento = (int) (Math.random() * 51);
                return "Velocidad del viento: " + viento + " km/h";

            default:
                return "Sensor '" + sensorName + "' no reconocido";
        }
    }

    // Mensaje de ayuda
    private static String getHelpMessage() {
        StringBuilder sb = new StringBuilder();
        sb.append("### Comandos disponibles ###\n");
        sb.append("@sensor#nombreSensor@ -> Devuelve el estado del sensor\n");
        sb.append("@listadoSensores@ -> Devuelve un listado de los sensores disponibles\n");
        sb.append("@ayuda@ -> Muestra esta ayuda\n");
        sb.append("@salir@ -> Finaliza la conexión\n");
        return sb.toString();
    }
}