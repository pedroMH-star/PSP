import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.Random;

/**
 * @autor Pedro Martínez Herrero
 * @since 02/11/2025
 * @until 03/11/2025
 * Actividad: UT3 - Programación Multihilo (Semaphore)
 */

public class SalaVideojuegos {

    // Creamos un semáforo con 4 permisos, que representa las 4 máquinas disponibles
    private static final Semaphore salaDisponible = new Semaphore(4);

    // Contadores para estadísticas
    private static final AtomicInteger jugadoresAtendidos = new AtomicInteger(0);
    private static final AtomicInteger tiempoTotal = new AtomicInteger(0);

    public static void main(String[] args) {
        System.out.println("Simulación iniciada: solo 4 jugadores pueden jugar al mismo tiempo\n");

        // Crear 10 hilos, uno por cada jugador que intenta acceder a la sala
        Thread[] jugadores = new Thread[10];
        for (int i = 1; i < jugadores.length; i++) {
            jugadores[i] = new Thread(new Jugador("Jugador " + (i + 1)));
            jugadores[i].start(); // Se inicia el hilo jugador, ejecuntando el método run()
        }

        // Esperar a que todos terminen
        for (Thread jugador : jugadores) {
            try {
                jugador.join();
            } catch (InterruptedException e) {
                System.out.println("Error al esperar a los jugadores");
            }
        }

        // Mostrar estadísticas finales
        int total = jugadoresAtendidos.get();
        int promedio = (total > 0) ? tiempoTotal.get() / total : 0;
        System.out.println("\n# ESTADÍSTICAS FINALES #");
        System.out.println("Jugadores atendidos: " + total);
        System.out.println("Tiempo promedio de juego: " + promedio + " ms");
    }

    // Clase jugador: clase interna que implementa Runnable
    static class Jugador implements Runnable {
        private String nombre;
        private Random random = new Random();

        public Jugador(String nombre) {
            this.nombre = nombre;
        }

        @Override
        public void run() {
            try {
                // 1. Cada jugador intenta adquierir un permiso del semáforo
                System.out.println(nombre + " está intentando entrar a la sala...");

                    // Intentar entrar a la sala (bloquea si no hay espacio)
                    salaDisponible.acquire();

                // 2. Una vez adquierido el permiso, el jugador entra a sala
                System.out.println(nombre + " ha entrado a la sala. Jugadores actuales: " + (4 - salaDisponible.availablePermits()) + "/4");

                // 3. Simularemos el tiempo de juego con un retardo aleatorio entre 2 a 5 segundos
                int tiempoJuego = random.nextInt(2000) + 2000;
                Thread.sleep(tiempoJuego);

                // 4. Al terminar, el jugador abandona sala
                jugadoresAtendidos.incrementAndGet();
                tiempoTotal.addAndGet(tiempoJuego);

                System.out.println(nombre + " ha terminado de jugar (tiempo: " + tiempoJuego + " ms)");

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println(nombre + " fue interrumpido.");
            } finally {
                // Muy importante: se libera el permiso del semáforo
                // Esto permite que otro jugador pueda entrar
                // Mejora posible: solo liberar si se llegó a adquirir el permiso
                salaDisponible.release();
                System.out.println(nombre + " ha salido de la sala. Jugadores actuales: " + (4 - salaDisponible.availablePermits()) + "/4\n");
            }
        }
    }
}