import java.io.*;

public class Collaboration {
    public static void main(String[] args) {
        int numInstancias = 10; // Número de procesos
        String archivoSalida = "numeros.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivoSalida))) {
            // Obtener el classpath actual
            String classpath = System.getProperty("java.class.path");

            for (int i = 0; i < numInstancias; i++) {
                // Ejecutar RandomNumbers usando el mismo classpath
                ProcessBuilder pb = new ProcessBuilder(
                        "java",
                        "-cp",
                        classpath,
                        "RandomNumbers"
                );
                pb.redirectErrorStream(true);
                Process proceso = pb.start();

                BufferedReader reader = new BufferedReader(new InputStreamReader(proceso.getInputStream()));
                String linea = reader.readLine();

                if (linea != null) {
                    writer.write(linea);
                    writer.newLine();
                } else {
                    System.out.println("Advertencia: RandomNumbers no produjo salida en la instancia " + (i + 1));
                }

                proceso.waitFor();
            }

            System.out.println("Se ha generado el archivo " + archivoSalida);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}