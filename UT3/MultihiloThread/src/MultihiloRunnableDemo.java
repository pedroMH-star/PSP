/**
 * @author Pedro Martínez Herrero
 * @since 20/10/2025
 * @until 21/10/2025
 * Actividad: Creación de múltiples hilos
 */

public class MultihiloRunnableDemo implements Runnable {
    @Override
    public void run() {
        // Mostrar el ID del hilo
        System.out.println("Hilo implementando Runnable en ejecución. ID: " + Thread.currentThread().getId());
    }
}