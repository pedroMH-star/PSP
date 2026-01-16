import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class FTPLocalhost {
    public static void main(String[] args) {
        // Esta URL intenta acceder a un servidor FTP público de prueba
        // Formato: ftp://usuario:contraseña@servidor/ruta/archivo
        //String urlString = "ftp://anonymous:password@ftp.example.com/archivo.txt";
        //ftp://demo:password@test.rebex.net/readme.txt

        // SERVIDOR DE PRUEBA: test.rebex.net
        // Este servidor está mantenido para pruebas de desarrollo
        String servidor = "localhost";
        String usuario = "alumno";
        String password = "1234";
        String archivo = "ejemplo1.txt";

        // Construir la URL con formato: ftp://usuario:password@servidor/archivo
        String urlString = String.format("ftp://%s:%s@%s/%s", usuario, password, servidor, archivo);

        try {
            URL url = new URL(urlString);

            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(url.openStream()))){
                System.out.println("--- CONTENIDO DEL ARCHIVO ---\n");
                String line;
                while ((line = reader.readLine()) != null){
                    System.out.println(line);
                }
                System.out.println("\n--- FIN DEL ARCHIVO ---");

            }

        }catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}

