import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class URLExample {
    public static void main(String[] args) {
        String urlString = "https://api.open-notify.org/astros.json";
        String urlStringUno = "https://api.chucknorris.io/jokes/random";
        String urlStringDos = "https://api.nasa.gov/planetary/apod?api_key=DEMO_KEY";
        String urlStringTres = "https://wttr.in/Murcia?format=j1";

        try {
            // Crear un objeto URL que apunte a un recurso HTTP
            URL url = new URL(urlStringUno);

            // PASO 2: Abrir una conexión y obtener un flujo de entrada (InputStream)
            try (BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()))) {
                System.out.println("------- RESPUESTA DEL SERVIDOR -------");

                // PASO 4: Leer el contenido línea por línea
                // readLine() devuelve null cuando no hay más líneas que leer
                String line;
                while ((line = br.readLine()) != null) {
                    System.out.println(line);
                }

                System.out.println("------- FIN RESPUESTA DEL SERVIDOR -------");

            } // El try-with-resources cierra automáticamente el BufferedReader
            // incluso si ocurre una excepción

        } catch (MalformedURLException e) {
            // Esta exceptión se lanza si la URL no tiene un formato válido
            System.err.println("ERROR: La URL no tiene un formato válido");
            System.err.println("Detalles: " + e.getMessage());

        } catch (IOException e) {
            System.err.println("ERROR");
            System.err.println("Detalles: " + e.getMessage());
        }
    }
}