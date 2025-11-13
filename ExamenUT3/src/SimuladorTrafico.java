import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @autor Pedro Martínez Herrero
 * @since 12/11/2025
 * @until 12/11/2025
 * Examen UT3 - Programación Multihilo (Simulador de tráfico)
 */

public class SimuladorTrafico {
    public static void main(String[] args) {
        List<String> metaCompartida = Collections.synchronizedList(new ArrayList<>());

        // Crear hilos
        Moto Moto1 = new Moto("Moto 1", metaCompartida);
        Moto Moto2 = new Moto("Moto 2", metaCompartida);

        Thread Coche1 = new Thread(new Coche("Coche 1", metaCompartida));
        Thread Coche2 = new Thread(new Coche("Coche 2", metaCompartida));


        // Iniciar hilos
        Moto1.start();
        Moto2.start();
        Coche1.start();
        Coche2.start();


        // Seguimiento de estados
        while (Moto1.isAlive() || Moto2.isAlive() || Coche1.isAlive() || Coche2.isAlive()) {
            System.out.println("\nEstado de la Moto 1: " + Moto1.getState());
            System.out.println("Estado de la Moto 2: " + Moto2.getState());
            System.out.println("Estado del Coche 1: " + Coche1.getState());
            System.out.println("Estado del Coche 2: " + Coche2.getState());
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                System.err.println("Seguimiento de estados interrumpido.");
                Thread.currentThread().interrupt();
            }
        }

        System.out.println("\nSimulación finalizada");
        System.out.println("\nOrden de llegada:");

        // 5. MOSTRAR RESULTADOS
        System.out.println("\nOrden de llegada: ");

        // Recorremos la lista compartida para imprimir la clasificación
        for (int i = 0; i < metaCompartida.size(); i++) {
            // Imprimimos la posición (i+1) y el nombre del corredor que está en esa posición de la lista
            System.out.println((i + 1) + ". " + metaCompartida.get(i));
        }
    }
}