import java.util.List;
import java.util.ArrayList;

/*
 * Clase que crea y gestiona múltiples hilos para generar un archivo de texto
 * mediante instancias de {@link Lenguaje}
 *
 * <p>Simula la ejecución concurrente de varios procesos que escriben en el
 *    mismo archivo, controlando que todos los hilos terminen antes de finalizar</p>
 *
 * <p>El archivo generado contiene líneas de texto aleatorio según las reglas
 *    definidas en la clase Lenguaje</p>
 *
 * @author Pedro Martínez Herrero
 * @since 07/10/2025
 */

public class Colaborar {

    /**
     * Método principal que inicia la simulación
     *
     * @param args Argumentos de línea de comandos (no se utilizan en esta aplicación)
     */
    public static void main(String[] args) {
        String fichero= "miFicheriDeLenguajePMH.txt";
        int numProcesos = 10;
        int incrementoLineas = 10;
        int numLetrasPorLinea = 10;

        // Inciamos una lista para almacenar los hilos
        List<Thread> hilos = new ArrayList<>();

        // Creamos y lanzamos los hilos
        for(int i =1; i < numLetrasPorLinea; i++){
            // Numeros de líneas por hacer en cada instancia
            int lineasPorHacer = i * incrementoLineas;

            // Creamos el proceso (o tarea) del objeto Lenguaje
            Lenguaje miLenguaje = new Lenguaje(fichero,lineasPorHacer, numLetrasPorLinea);

            // Creamos el hilo para ejecutar la tarea
           Thread thread = new Thread(miLenguaje);
            hilos.add(thread);
            thread.start();
        }

        // Esperamos a que todos los hilos terminen
        for (Thread thread : hilos){
            try {
                thread.join();
            } catch (InterruptedException e) {
                System.out.println("Error al esperar el hilo: " + e.getMessage());

            }
        }

        System.out.println("Se ha generado el archivo " + fichero);

    }
}