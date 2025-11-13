/**
 * @author Pedro Martínez Herrero
 * @since 20/10/2025
 * @until 21/10/2025
 * Actividad: Estados de un hilo en Java
 */

public class MiTarea implements Runnable {
    private String nombre;

    public MiTarea(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public void run() {
        try {
            for (int i = 1; i <= 5; i++) {
                System.out.println(nombre + " está ejecutando: paso " + i);
                Thread.sleep(500); // retardo de medio segundo
            }
        } catch (InterruptedException e) {
            System.out.println(nombre + " fue interrumpido.");
        }
    }
}