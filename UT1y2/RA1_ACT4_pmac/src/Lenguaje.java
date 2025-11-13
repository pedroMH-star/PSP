import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

/*
 * Creamos la clase que genera líneas de texto aleatorio y las escribe en un archivo compartido
 *
 * <p>Implementa {@link Runnable} para poder ejecutarse en hilos de manera concurrente</p>
 *
 *  <p>Cada instancia de esta clase genera un número determinado de líneas,
 *     cada línea con un número fijo de letras aleatorias y las escribe en el archivo
 *     indicado en modo append</p>
 *
 * <p>Permite simular escritura concurrente en un archivo.</p>
 *
 * @author Pedro Martínez Herrero
 * @since 07/10/2025
 */
public class Lenguaje implements Runnable {

    private final String fichero; // Nombre del fichero donde se escribirán las líneas generadas
    private final int numLineas; // Número de líneas que se generarán por instancia
    private final int numLetrasPorLinea; //  Número de letras por línea a generar

    /**
     * Constructor de la clase Lenguaje
     *
     * @param fichero Nombre del archivo donde se escriben las líneas
     * @param numLineas Número de líneas a generar
     * @param numLetrasPorLinea Número de letras por línea
     */
    public Lenguaje (String fichero, int numLineas, int numLetrasPorLinea) {
        this.fichero = fichero;
        this.numLineas = numLineas;
        this.numLetrasPorLinea = numLetrasPorLinea;
    }

    /*
     * Creamos el método que genera las lineas de texto aleatorio y las escribe en el archivo
     *
     * <p>Genera las líneas de texto aleatorio y las escribe en el archivo indicado.
     *    Cada línea está compuesta por letras minúsculas aleatorias.
     *    Al finalizar, imprime en consola cuántas líneas escribió el hilo.</p>
     */
    @Override
    public void run() {
        // Generador aleatorio
        Random random = new Random();

        // Creamos archivo
        try (FileWriter fw = new FileWriter(fichero, true)) { // Escribe en modo append
            for (int i = 0; i < numLineas; i++) {
                StringBuilder stringBuilder = new StringBuilder(); // Almacenamos la línea
                for (int j = 0; j < numLetrasPorLinea; j++) {
                    char ramdomChar = (char) ('a' + random.nextInt(26)); // Genera letras en vez de números
                    stringBuilder.append(ramdomChar);
                }
                // Escribir la línea en el archivo
                fw.write(stringBuilder.toString() + "\n");

            }
            System.out.println(Thread.currentThread().getName() + "escribió " + numLineas + " lineas");

        } catch (Exception e) {
            System.err.println("Error al escribir el archivo: " + e.getMessage());
        }

    }

}