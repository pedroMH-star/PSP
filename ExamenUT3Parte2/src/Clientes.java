import java.util.concurrent.Semaphore;

/**
 * @autor Pedro Martínez Herrero
 * @since 12/11/2025
 * @until 12/11/2025
 * Examen UT3 Parte 2 - Clientes
 */

public class Clientes implements Runnable {
    private Semaphore semaphore;
    private String nombre;

    // Constructor
    public Clientes(Semaphore semaphore, String nombre) {
        this.semaphore = semaphore;
        this.nombre = nombre;
    }

    @Override
    public void run() {
        try {
            // Adquiere un permiso del semáforo
            semaphore.acquire();
            System.out.println(nombre + " está realizando un pedido...");

            // Simula el tiempo de realización del pedido (1 segundo)
            Thread.sleep(1000);

            System.out.println(nombre + " ha terminado su pedido.");

        } catch (InterruptedException e) {
            System.err.println(nombre + " fue interrumpido durante el pedido.");
            Thread.currentThread().interrupt();
        } finally {
            // Libera el permiso para que otro cliente pueda hacer un pedido
            semaphore.release();
        }
    }
}