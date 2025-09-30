import java.time.LocalTime;

/**
 * Simulación de un sistema concurrente con hilos.
 *
 * Esta clase representa un buffer compartido limitado, que actúa como monitor
 * para la sincronización de múltiples hilos: suministradores y clientes.
 * Los suministradores añaden elementos y los clientes los extraen de forma concurrente.
 *
 * Autor: Pedro Martínez Herrero
 * Fecha: 30/09/2025
 */
public class Buffer {

    private int[] buffer;          // Array que almacena los elementos del buffer
    private int count = 0;         // Número de elementos en el buffer
    private int size;              // Tamaño máximo del buffer
    private int nextValue = 1;     // Valor siguiente que será añadido al buffer

    public Buffer(int size) {
        this.size = size;
        buffer = new int[size];
    }

    /**
     * Añade un elemento al buffer.
     * Si el buffer está lleno, el hilo se bloquea hasta que haya espacio.
     * @param productor Nombre del hilo suministrador
     * @throws InterruptedException si el hilo es interrumpido mientras espera
     */
    public synchronized void add(String productor) throws InterruptedException {
        while (count == size) {
            System.out.println(LocalTime.now() + " - " + productor + " espera: Buffer lleno");
            wait();
        }
        buffer[count] = nextValue;
        System.out.println(LocalTime.now() + " - " + productor + " añade -> " + nextValue);
        count++;
        nextValue++;
        mostrarBuffer();
        notifyAll(); // Notifica a todos los clientes y productores
    }

    /**
     * Extrae un elemento del buffer.
     * Si el buffer está vacío, el hilo se bloquea hasta que haya un elemento.
     * @param cliente Nombre del hilo cliente
     * @return El valor extraído del buffer
     * @throws InterruptedException si el hilo es interrumpido mientras espera
     */
    public synchronized int remove(String cliente) throws InterruptedException {
        while (count == 0) {
            System.out.println(LocalTime.now() + " - " + cliente + " espera: Buffer vacío");
            wait();
        }
        int value = buffer[count - 1];
        count--;
        System.out.println(LocalTime.now() + " - " + cliente + " extrae -> " + value);
        mostrarBuffer();
        notifyAll(); // Notifica a todos los clientes y productores
        return value;
    }

    /** Muestra el estado actual del buffer en consola */
    private void mostrarBuffer() {
        System.out.print("Buffer actual: [");
        for (int i = 0; i < count; i++) {
            System.out.print(buffer[i] + (i < count - 1 ? ", " : ""));
        }
        System.out.println("]");
    }

    // ---------------- CLASE SUMINISTRADOR ----------------
    static class Suministrador extends Thread {
        private Buffer buffer;
        private int operaciones = 10; // Número máximo de operaciones del hilo

        public Suministrador(Buffer buffer, String nombre) {
            super(nombre);
            this.buffer = buffer;
        }

        @Override
        public void run() {
            try {
                for (int i = 0; i < operaciones; i++) {
                    buffer.add(getName());
                    Thread.sleep(500); // Intervalo de 500ms
                }
                System.out.println(getName() + " ha terminado sus operaciones.");
            } catch (InterruptedException e) {
                System.out.println(getName() + " interrumpido");
            }
        }
    }

    // ---------------- CLASE CLIENTE ----------------
    static class Cliente extends Thread {
        private Buffer buffer;
        private int operaciones = 10; // Número máximo de operaciones del hilo

        public Cliente(Buffer buffer, String nombre) {
            super(nombre);
            this.buffer = buffer;
        }

        @Override
        public void run() {
            try {
                for (int i = 0; i < operaciones; i++) {
                    buffer.remove(getName());
                    Thread.sleep(1000); // Intervalo de 1000ms
                }
                System.out.println(getName() + " ha terminado sus operaciones.");
            } catch (InterruptedException e) {
                System.out.println(getName() + " interrumpido");
            }
        }
    }

    // ---------------- CLASE PRINCIPAL ----------------
    public static class MonitorExample {
        public static void main(String[] args) {
            Buffer buffer = new Buffer(5); // Tamaño máximo 5 elementos

            System.out.println("=== PRUEBA 1: 1 Cliente y 1 Suministrador ===");
            Suministrador s1 = new Suministrador(buffer, "Suministrador-1");
            Cliente c1 = new Cliente(buffer, "Cliente-1");
            s1.start();
            c1.start();

            try {
                Thread.sleep(15000); // Esperar 15 segundos antes de la siguiente prueba
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("\n=== PRUEBA 2: 3 Clientes y 3 Suministradores ===");
            for (int i = 2; i <= 4; i++) {
                new Suministrador(buffer, "Suministrador-" + i).start();
                new Cliente(buffer, "Cliente-" + i).start();
            }
        }
    }
}