import java.io.*;

/**
 * @autor Pedro Martínez Herrero
 * @since 22/09/2025
 * @until 23/09/2025
 */

public class Collaboration {
    public static void main(String[] args) {

        int numInstancias = 10; // Número de procesos que queremos ejecutar (10 instancias de RandomNumbers)
        String archivoSalida = "numeros.txt";

        // Usamos try-with-resources para crear un BufferedWriter y asegurarnos de que se cierre automáticamente
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivoSalida))) {

            // Obtener el classpath actual del proyecto para poder ejecutar RandomNumbers correctamente
            String classpath = System.getProperty("java.class.path");

            for (int i = 0; i < numInstancias; i++) {
                // Ejecutar RandomNumbers usando el mismo classpath
                ProcessBuilder pb = new ProcessBuilder("java", "-cp", classpath, "RandomNumbers");
                pb.redirectErrorStream(true);
                Process proceso = pb.start();

                // Leemos la salida del proceso línea por línea
                BufferedReader reader = new BufferedReader(new InputStreamReader(proceso.getInputStream()));
                String linea = reader.readLine();

                if (linea != null) {
                    writer.write(linea); // Escribimos esa línea en el fichero numeros.txt
                    writer.newLine(); // Salto de linea
                } else {
                    System.out.println("Advertencia: La clase RandomNumbers no ha hecho salida en la instancia " + (i + 1));
                }

                proceso.waitFor(); // Espera a que el proceso termine antes de continuar con el siguiente
            }

            System.out.println("Se ha generado el archivo " + archivoSalida);
        } catch (IOException | InterruptedException e) { // Captura errores de E/S y de interrupciones del proceso
            e.printStackTrace(); // Muestra información del error en consola
        }
    }
}