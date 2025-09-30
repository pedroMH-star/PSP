import java.time.LocalTime;

/**
 * Simulación de un sistema concurrente con hilos
 *
 * Esta clase representa un buffer compartido limitado, que actúa como monitor para la sincronización de múltiples hilos: suministradores y clientes
 *
 * Los suministradores añaden elementos y los clientes los extraen de forma concurrente
 *
 * Objetivos:
 *  1. Aplicar conceptos de programación concurrente en Java
 *  2. Garantizar exclusión mutua y evitar condiciones de carrera
 *  3. Usar monitores (synchronized, wait, notifyAll) para controlar el acceso
 *
 *
 * @author Pedro Martínez Herrero
 * @since 30/09/2025
 */

public class Buffer {

    /**
     * Array que almacena los elementos del buffer
     */
    private int[] buffer;

    /**
     * Número de elementos que hay actualmente en el buffer
     */
    private int count = 0;

    /**
     * Tamaño máximo del buffer
     */
    private int size;

    /**
     * Valor siguiente que será añadido al buffer
     */
    private int nextValue = 1;

    /**
     * Constructor de Buffer
     *
     * @param size Tamaño máximo del buffer
     */
    public Buffer(int size) {
        this.size = size;
        buffer = new int[size];
    }

    /**
     * Añade un elemento al buffer.
     * Si el buffer está lleno, el hilo se bloquea hasta que haya espacio.
     *
     * @param productor Nombre del hilo suministrador
     * @throws InterruptedException si el hilo es interrumpido mientras espera
     */
    public synchronized void add(String productor) throws InterruptedException {
        while (count == size) {
            System.out.println(productor + " espera: Buffer lleno");
            wait();
        }
        buffer[count] = nextValue;
        System.out.println(productor + " añade -> " + nextValue);
        count++;
        nextValue++;
        mostrarBuffer();
        notifyAll(); // Notifica a todos los clientes y productores
    }

    /**
     * Extrae un elemento del buffer.
     * Si el buffer está vacío, el hilo se bloquea hasta que haya un elemento.
     *
     * @param cliente Nombre del hilo cliente
     * @return El valor extraído del buffer
     * @throws InterruptedException si el hilo es interrumpido mientras espera
     */
    public synchronized int remove(String cliente) throws InterruptedException {
        while (count == 0) {
            System.out.println(cliente + " espera: Buffer vacío");
            wait();
        }
        int value = buffer[count - 1];
        count--;
        System.out.println(cliente + " extrae -> " + value);
        mostrarBuffer();
        notifyAll(); // Notifica a todos los clientes y productores
        return value;
    }

    /**
     * Muestra el estado actual del buffer en consola
     */
    private void mostrarBuffer() {
        System.out.print("Buffer actual: [");
        for (int i = 0; i < count; i++) {
            System.out.print(buffer[i] + (i < count - 1 ? ", " : ""));
        }
        System.out.println("]");
    }
}