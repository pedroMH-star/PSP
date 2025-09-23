import java.io.*;
import java.util.List;

/**
 * @autor Pedro Martínez Herrero
 * @since 22/09/2025
 * @until 23/09/2025
 */

public class Colaboracion2 extends NumRandom2 {

    // Método que genera 35 números y los guarda en una línea del archivo
    public void GenerarGuardarNumeros(String archivoSalida) {
        // Generamos los números usando el método heredado
        List<Integer> numeros = this.generarNumeros();

        // Usamos try-with-resources para asegurar el cierre del BufferedWriter
        try (BufferedWriter bufWrite = new BufferedWriter(new FileWriter(archivoSalida, true))) {
            for (Integer numero : numeros) {
                bufWrite.write(numero.toString());
                bufWrite.write(" "); // Separador entre números
            }
            bufWrite.newLine(); // Salto de línea al final
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Nombre del archivo de salida
        String archivoSalida = "numeros2.txt";

        // Número de instancias a lanzar
        int numInstancias = 10;

        for (int i = 0; i < numInstancias; i++) {
            Colaboracion2 colaborador = new Colaboracion2();
            colaborador.GenerarGuardarNumeros(archivoSalida);
        }

        System.out.println("Se han generado " + numInstancias*35 + " números en " + archivoSalida);
    }
}