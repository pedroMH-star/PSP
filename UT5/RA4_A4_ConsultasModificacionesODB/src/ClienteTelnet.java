import org.apache.commons.net.examples.util.IOUtil;
import org.apache.commons.net.telnet.TelnetClient;
import java.net.*;
import java.io.*;

// telehack.com 23

public class ClienteTelnet {
    public static void main(String[] args) {

        // Comparar que se han pasado los argumentos correctos
        if (args.length < 2) {
            System.out.println("ERROR: Indicar servidor y puerto.");
            System.exit(1);
        }

        // Obtenemos el servidor y el puerto introducidos desde la línea de comandos
        String servidor = args[0];
        int puerto = Integer.parseInt(args[1]);
        boolean conectado = false;


        // Crear un objeto TelnetClient de Apache Commons Net
        TelnetClient Cliente = new TelnetClient();

        try {
            // Conectamos al servidor
            System.out.println("Conectando a " + servidor + " en el puerto " + puerto + "...");

            // El método connect() establece la conexión TCP con el servidor
            Cliente.connect(servidor, puerto);
            conectado = true;
            System.out.println("Conexión establecida. Escriba los comandos y presione Enter.");

            // Redirigir entrada y salida estándar para interactuar con el servidor TELNET
            // IOUtil.readwriter() gestiona...
            // ...
            // Este método se ejecuta en un bucle hasta que la conexión se cierra
            IOUtil.readWrite(Cliente.getInputStream(), Cliente.getOutputStream(), System.in, System.out);

            Cliente.disconnect();
            conectado = false;
            System.out.println("Desconectando...");

        } catch (IOException e) {
            System.err.println("Error en la conexión TELNET: " + e.getMessage());
            System.exit(2);
        }

        // Conectarse al servidor
    }
}
