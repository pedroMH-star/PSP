/**
 * @author Pedro Martínez Herrero
 * @since 20/10/2025
 * @until 21/10/2025
 * Actividad: Estados de un hilo en Java
 */
public class EstadosHilosDemo {
    public static void main(String[] args) throws InterruptedException {
        // Crear dos hilos con tareas diferentes
        Thread hilo1 = new Thread(new MiTarea("Hilo 1"));
        Thread hilo2 = new Thread(new MiTarea("Hilo 2"));

        // Estado antes de start()
        System.out.println("Estado inicial de hilo1: " + hilo1.getState());
        System.out.println("Estado inicial de hilo2: " + hilo2.getState());

        // Iniciar hilos
        hilo1.start();
        hilo2.start();

        // Estado inmediatamente después de start()
        System.out.println("Estado después de start() de hilo1: " + hilo1.getState());
        System.out.println("Estado después de start() de hilo2: " + hilo2.getState());

        // Esperar a que los hilos terminen
        while (hilo1.isAlive() || hilo2.isAlive()) {
            System.out.println("Monitoreando estados: hilo1=" + hilo1.getState() + ", hilo2=" + hilo2.getState());
            Thread.sleep(200); // retardo para no saturar la consola
        }

        // Estado final después de que los hilos terminan
        System.out.println("Estado final de hilo1: " + hilo1.getState());
        System.out.println("Estado final de hilo2: " + hilo2.getState());
    }
}