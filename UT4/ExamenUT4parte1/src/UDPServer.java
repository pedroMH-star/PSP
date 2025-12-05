import java.net.*;
import java.util.regex.*;

/**
 * author: Pedro Martínez Herrero
 * @since 29/11/2025
 * @until 01/12/2025
 * Examen UT4 - Servidor UDP
 */

public class UDPServer {
    public static final int PORT = 12345;
    public static final int BUFFER = 4096;
    private final ResultadosEncuesta resultados = new ResultadosEncuesta();

    // Expresiones regulares para validar/parsear mensajes
    private static final Pattern P_RESP = Pattern.compile("@resp#([^#@]+)#([^@]+)@");
    private static final Pattern P_FIN = Pattern.compile("@fin#([^@]+)@");
    private static final Pattern P_RESULT = Pattern.compile("@resultados@");

    public void start() throws Exception {
        DatagramSocket socket = new DatagramSocket(PORT);
        System.out.println("Servidor UDP multihilo escuchando en puerto " + PORT);

        byte[] buffer = new byte[BUFFER];
        while (true) {
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            socket.receive(packet);

            // Copiar los bytes recibidos a un nuevo array para evitar sobreescritura
            byte[] data = new byte[packet.getLength()];
            System.arraycopy(packet.getData(), packet.getOffset(), data, 0, packet.getLength());

            // Crear hilo para procesar este datagrama
            Thread t = new Thread(new ClientHandler(socket, packet.getAddress(), packet.getPort(), data, resultados));
            t.start();
        }
    }

    public static void main(String[] args) {
        UDPServer s = new UDPServer();
        try {
            s.start();
        } catch (Exception e) {
            System.err.println("Error en servidor: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Clase interna para manejar cada datagrama en su propio hilo
    static class ClientHandler implements Runnable {
        private final DatagramSocket socket;
        private final InetAddress address;
        private final int port;
        private final String mensaje;
        private final ResultadosEncuesta resultados;

        ClientHandler(DatagramSocket socket, InetAddress address, int port, byte[] data, ResultadosEncuesta resultados) {
            this.socket = socket;
            this.address = address;
            this.port = port;
            this.mensaje = new String(data).trim();
            this.resultados = resultados;
        }

        @Override
        public void run() {
            try {
                System.out.println("Recibido de " + address + ":" + port + " -> " + mensaje);

                Matcher mResp = P_RESP.matcher(mensaje);
                Matcher mFin = P_FIN.matcher(mensaje);
                Matcher mRes = P_RESULT.matcher(mensaje);

                String respuestaServidor;

                if (mResp.matches()) {
                    String zona = mResp.group(1).trim();
                    String resp = mResp.group(2).trim();
                    resultados.agregarRespuesta(zona, resp);
                    respuestaServidor = "OK: respuesta almacenada. Zona='" + zona + "', respuesta='" + resp + "'";
                } else if (mFin.matches()) {
                    String zona = mFin.group(1).trim();
                    respuestaServidor = resultados.getResumenZona(zona);
                } else if (mRes.matches()) {
                    respuestaServidor = resultados.getResumenGlobal();
                } else {
                    respuestaServidor = "ERROR: formato inválido. Formatos válidos:\n" +
                            "@resp#zona#respuesta@\n@fin#zona@\n@resultados@";
                }

                byte[] out = respuestaServidor.getBytes();
                DatagramPacket outPacket = new DatagramPacket(out, out.length, address, port);
                socket.send(outPacket);
                System.out.println("Enviado a " + address + ":" + port + ": " + respuestaServidor.replaceAll("\n","\\n"));


            } catch (Exception e) {
                System.err.println("Error procesando mensaje: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}