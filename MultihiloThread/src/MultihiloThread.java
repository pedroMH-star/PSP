/**
 * @author Pedro Martínez Herrero
 * @since 20/10/2025
 * @until 21/10/2025
 * Actividad: Creación de múltiples hilos
 */

public class MultihiloThread {
    public static void main(String[] args) {
        // Crear y ejecutar 8 hilos
        for (int i = 0; i < 8; i++) {
            MultihiloThreadDemo hilo = new MultihiloThreadDemo();
            hilo.start(); // Ejecutar hilo concurrentemente
        }
    }
}