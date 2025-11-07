import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @autor Pedro Martínez Herrero
 * @since 04/11/2025
 * @until 04/11/2025
 * Actividad: UT3 - Programación Multihilo (Prioridades)
 */

// Definimos la clase principal que simulará la carrera
public class CarreraPrioridades {
    public static void main(String[] args) {
        // 1. CREAR EL RECURSO COMPARTIDO
        // Creamos la "línea de meta". Esta lista será compartida por todos los hilos (corredores)
        // Usamos Collections.synchronizedList() para envolver nuestra LinkedList
        // Esto la convierte en "thread-safe": evita condiciones de carrera si dos hilos
        // intentan añadir su nombre a la lista exactamente al mismo tiempo

        List<String> metaCompartida = Collections.synchronizedList(new ArrayList<>());

        // Creamos los hilos (corredores) con sus prioridades
        // Creamos el primer hilo (Corredor 1). Le pasamos la lista compartida para que sepa dónde anotar su llegada
        Thread corredor1 = new Thread(new Corredor("Corredor 1", metaCompartida));
        corredor1.setPriority(Thread.MIN_PRIORITY);

        Thread corredor2 = new Thread(new Corredor("Corredor 2", metaCompartida));
        corredor2.setPriority(Thread.NORM_PRIORITY);

        Thread corredor3 = new Thread(new Corredor("Corredor 3", metaCompartida));
        corredor3.setPriority(Thread.MAX_PRIORITY);

        Thread corredor4 = new Thread(new Corredor("Corredor 4", metaCompartida));
        corredor4.setPriority(Thread.NORM_PRIORITY);

        Thread corredor5 = new Thread(new Corredor("Corredor 5", metaCompartida));
        corredor5.setPriority(Thread.MIN_PRIORITY);

        // 3. INICIAR LA EJECUCIÓN
        corredor1.start();
        corredor2.start();
        corredor3.start();
        corredor4.start();
        corredor5.start();

        // 4. ESPERAR A QUE TODOS TERMINEN
        try{
            corredor1.join();
            corredor2.join();
            corredor3.join();
            corredor4.join();
            corredor5.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // 5. MOSTRAR RESULTADOS
        System.out.println("\nOrden de llegada: ");

        // Recorremos la lista compartida para imprimir la clasificación
        for (int i = 0; i < metaCompartida.size(); i++) {
            // Imprimimos la posición (i+1) y el nombre del corredor que está en esa posición de la lista
            System.out.println((i + 1) + ". " + metaCompartida.get(i));
        }

    }
}