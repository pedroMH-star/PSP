/**
 * @autor Pedro Martínez Herrero
 * @since 12/11/2025
 * @until 12/11/2025
 * Examen UT3 Parte 2 - Empleados
 */

public class Empleados implements Runnable {
    private String nombre;

    // Constructor
    public Empleados(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public void run() {
        try {
            // Simula el tiempo de procesamiento de un pedido (1,5 segundos)
            Thread.sleep(1500);
            System.out.println(nombre + " ha terminado de procesar un pedido.");
        } catch (InterruptedException e) {
            System.err.println(nombre + " fue interrumpido durante el procesamiento.");
            Thread.currentThread().interrupt();
        }
    }
}