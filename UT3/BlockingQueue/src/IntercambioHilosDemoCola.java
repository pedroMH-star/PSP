import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.Random;

/**
 * @author Pedro Martínez Herrero
 * @since 27/10/2025
 * @until 29/10/2025
 * Actividad: Simulación del problema Productor-Consumidor utilizando BlockingQueue
 * para lograr sincronización y comunicación segura entre hilos
 */

public class IntercambioHilosDemoCola {
    public static void main(String[] args) {
        // Creamos la cola compartida con capacidad para 5 elementos
        BlockingQueue<Integer> colaCompartida = new ArrayBlockingQueue<>(5);

        // Creamos los hilos de productor y consumidor
        Thread productor = new Thread(new Productor(colaCompartida));
        Thread consumidor = new Thread(new Consumidor(colaCompartida));

        // Iniciamos los hilos
        productor.start();
        consumidor.start();
    }
}

// Clase Productor
class Productor implements Runnable {
    private BlockingQueue<Integer> cola;
    private Random random = new Random();

    public Productor(BlockingQueue<Integer> cola) {
        this.cola = cola;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < 10; i++) { // Producirá 10 números
                int numero = random.nextInt(100) + 1;
                cola.put(numero); // Inserta el número (bloquea si la cola está llena)
                System.out.println("Productor: Generé dato " + numero);
                Thread.sleep(500); // Simula tiempo de producción
            }
            cola.put(-1); // Señal de fin de producción
        } catch (InterruptedException e) {
            System.out.println("Productor interrumpido.");
        }
    }
}

// Clase Consumidor
class Consumidor implements Runnable {
    private BlockingQueue<Integer> cola;

    public Consumidor(BlockingQueue<Integer> cola) {
        this.cola = cola;
    }

    @Override
    public void run() {
        try {
            while (true) {
                int numero = cola.take(); // Toma un número (bloquea si la cola está vacía)
                if (numero == -1) {
                    System.out.println("Consumidor: Fin del proceso.");
                    break;
                }
                System.out.println("Consumidor: Procesé dato " + numero);
                Thread.sleep(800); // Simula tiempo de consumo
            }
        } catch (InterruptedException e) {
            System.out.println("Consumidor interrumpido.");
        }
    }
}