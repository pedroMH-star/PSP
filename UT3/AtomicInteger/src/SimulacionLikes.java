import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Pedro Martínez Herrero
 * @since 29/10/2025
 * @until 29/10/2025
 * Actividad: Simula un sistema de "likes" en una publicación utilizando AtomicInteger para evitar condiciones
 * de carrera en un entorno multihilo
 */

public class SimulacionLikes {

    // Declara un AtomicInteger llamado contadorLikes  con un valor inicial de 0
    static AtomicInteger contandorLikes = new AtomicInteger(0);

    // Clase interna que representa un usuario que da likes
    private static class Usuario extends Thread {
        private final String nombre;

        public Usuario(String nombre) {
            this.nombre = nombre;
        }

        @Override
        public void run() {
            for (int i = 0; i < 100; i++) {
                int likesActuales = contandorLikes.incrementAndGet();
                System.out.println(nombre + "dio un like. Total de likes: " + likesActuales);

                // Simula un pequeño tiempo aleatorio entre likes
                try {
                    Thread.sleep((long) (Math.random() * 100));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println(nombre + " fue interrumpido.");
                }
            }
        }
    }

    // Método principal
    public static void main(String[] args) {
        // Crear 3 hilos que simulan usuarios
        Thread usuario1 = new Usuario("Usuario 1");
        Thread usuario2 = new Usuario("Usuario 2");
        Thread usuario3 = new Usuario("Usuario 3");

        // Iniciar los hilos
        usuario1.start();
        usuario2.start();
        usuario3.start();

        // Esperar a que los hilos terminen con join
        try {
            usuario1.join();
            usuario2.join();
            usuario3.join();
        } catch (InterruptedException e) {
            System.out.println("Error al esperar la finalización de los hilos");
            Thread.currentThread().interrupt();
        }

        // Mostrar el resultado final
        System.out.println("\nTotal final de likes: " + contandorLikes.get());
    }
}