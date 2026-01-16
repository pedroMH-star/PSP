import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class FTPWithURL {
    public static void main(String[] args) {
        // Esta URL
        String urlString = "ftp://anonymous:password@ftp.example.com/archivo.txt";

        try {
            URL url = new URL(urlString);

            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(url.openStream()))){
                String line;
                while ((line = reader.readLine()) != null){
                    System.out.println(line);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}