/**
 * Hilo que representa un suministrador de elementos
 * Añade elementos al buffer cada 500ms de forma concurrente
 *
 * @author Pedro Martínez Herrero
 * @since 30/09/2025
 */
public class Suministrador extends Thread {
    private Buffer buffer;

    private int operaciones = 10; // Número máximo de operaciones del hilo

    /**
     * Constructor del suministrador
     *
     * @param buffer Buffer compartido
     * @param nombre Nombre del hilo
     */
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