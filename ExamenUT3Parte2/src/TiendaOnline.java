import java.util.concurrent.Semaphore;

/**
 * @autor Pedro Martínez Herrero
 * @since 12/11/2025
 * @until 12/11/2025
 * Examen UT3 Parte 2 - TiendaOnline
 */

public class TiendaOnline {
    public static void main(String[] args) {
        // Semáforo con 2 permisos: solo 2 clientes pueden hacer pedidos a la vez
        Semaphore semaforo = new Semaphore(2);

        // Crear 4 clientes
        Thread cliente1 = new Thread(new Clientes(semaforo, "Cliente 1"));
        Thread cliente2 = new Thread(new Clientes(semaforo, "Cliente 2"));
        Thread cliente3 = new Thread(new Clientes(semaforo, "Cliente 3"));
        Thread cliente4 = new Thread(new Clientes(semaforo, "Cliente 4"));

        // Iniciar los hilos de los clientes
        cliente1.start();
        cliente2.start();
        cliente3.start();
        cliente4.start();

        // Esperar a que todos los clientes terminen antes de procesar pedidos
        try {
            cliente1.join();
            cliente2.join();
            cliente3.join();
            cliente4.join();
        } catch (InterruptedException e) {
            System.err.println("Error al esperar a que los clientes terminen.");
            Thread.currentThread().interrupt();
        }

        System.out.println("\n--- Todos los clientes han realizado sus pedidos ---\n");

        // Crear empleados
        Thread empleado1 = new Thread(new Empleados("Empleado 1"));
        Thread empleado2 = new Thread(new Empleados("Empleado 2"));
        Thread empleado3 = new Thread(new Empleados("Empleado 3"));
        Thread empleado4 = new Thread(new Empleados("Empleado 4"));

        // Asignar diferentes prioridades
        empleado1.setPriority(Thread.MAX_PRIORITY);   // Máxima prioridad
        empleado2.setPriority(Thread.MIN_PRIORITY);   // Mínima prioridad
        empleado3.setPriority(Thread.NORM_PRIORITY);  // Normal
        empleado4.setPriority(Thread.NORM_PRIORITY);  // Normal

        // Iniciar los empleados
        empleado1.start();
        empleado2.start();
        empleado3.start();
        empleado4.start();

        // Esperar a que todos los empleados terminen
        try {
            empleado1.join();
            empleado2.join();
            empleado3.join();
            empleado4.join();
        } catch (InterruptedException e) {
            System.err.println("Error al esperar a que los empleados terminen.");
            Thread.currentThread().interrupt();
        }

        System.out.println("\nTodos los pedidos han sido procesados.");
    }
}