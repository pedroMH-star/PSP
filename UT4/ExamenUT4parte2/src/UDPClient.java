import java.net.*;
import java.util.concurrent.TimeUnit;

/**
 * author: Pedro Martínez Herrero
 * @since 29/11/2025
 * @until 01/12/2025
 * Examen UT4 - Cliente UDP
 */

public class UDPClient {
    public static final int SERVER_PORT = 12345;
    public static final String SERVER_HOST = "localhost"; // Cambiar si el servidor está en otra máquina
    public static final int TIMEOUT_MS = 3000;


    private DatagramSocket socket;
    private InetAddress serverAddr;


    public UDPClient() throws Exception {
        socket = new DatagramSocket();
        socket.setSoTimeout(TIMEOUT_MS);
        serverAddr = InetAddress.getByName(SERVER_HOST);
    }


    private String sendAndReceive(String mensaje) throws Exception {
        byte[] out = mensaje.getBytes();
        DatagramPacket p = new DatagramPacket(out, out.length, serverAddr, SERVER_PORT);
        System.out.println("Enviando: " + mensaje);
        socket.send(p);


        byte[] buf = new byte[4096];
        DatagramPacket resp = new DatagramPacket(buf, buf.length);
        try {
            socket.receive(resp);
            String r = new String(resp.getData(), 0, resp.getLength());
            System.out.println("Recibido: " + r.replaceAll("\n","\\n"));
            return r;
        } catch (SocketTimeoutException e) {
            System.err.println("Tiempo de espera agotado esperando respuesta del servidor.");
            return null;
        }
    }


    public void runDemo() throws Exception {
    // Simular al menos 2 zonas con 2 respuestas cada una
        // Zona 1
        sendAndReceive("@resp#zona1#Me gustó el servicio@");
        sendAndReceive("@resp#zona1#Atención correcta@");

        // Zona 2
        sendAndReceive("@resp#zona2#Necesita mejoras@");
        sendAndReceive("@resp#zona2#Precio alto@");

        // Finalizar zona1 y recibir resumen
        sendAndReceive("@fin#zona1@");

        // Finalizar zona2 y recibir resumen
        sendAndReceive("@fin#zona2@");

        // Pedir resumen global
        sendAndReceive("@resultados@");

        // Enviar un mensaje malformado para ver la validación
        sendAndReceive("hola servidor");

        socket.close();
    }


    public static void main(String[] args) {
        try {
            UDPClient c = new UDPClient();
            c.runDemo();
        } catch (Exception e) {
            System.err.println("Cliente error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}