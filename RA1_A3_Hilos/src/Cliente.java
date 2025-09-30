/**
 * Hilo que representa un cliente que consume elementos
 * Extrae elementos del buffer cada 1000ms de forma concurrente
 *
 * @author Pedro Martínez Herrero
 * @since 30/09/2025
 */
public class Cliente extends Thread {
    private Buffer buffer;
    private int operaciones = 10; // Número máximo de operaciones del hilo

    /**
     * Constructor del cliente
     *
     * @param buffer Buffer compartido
     * @param nombre Nombre del hilo
     */
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