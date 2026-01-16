import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class FTPDemo {
    public static void main(String[] args) {
        // Esta URL
        String servidor = "test.rebex.net";
        String usuario = "demo";
        String password = "password";
        String archivo = "readme.txt";

        String urlString = String.format("ftp://%s:%s@%s/%s", usuario, password, servidor, archivo);

        try {
            URL url = new URL(urlString);

            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(url.openStream()))){
                System.out.println("--- Contenido del archivo ---\n");
                String line;
                while ((line = reader.readLine()) != null){
                    System.out.println(line);
                }
                System.out.println("\n--- Fin del archivo ---");

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}