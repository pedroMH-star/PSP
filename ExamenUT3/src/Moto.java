import java.util.List;

/**
 * @autor Pedro Martínez Herrero
 * @since 12/11/2025
 * @until 12/11/2025
 * Examen UT3 - Moto
 */

public class Moto extends Thread {
    // Atributos
    private final String nombre;
    private final List<String> metaCompartida;

    // Constructor
    public Moto(String nombre, List<String> metaCompartida) {
        this.nombre = nombre;
        this.metaCompartida = metaCompartida;
    }

    @Override
    public void run() {
        // Carrera con 10 puntos intermedios de control
        for (int i = 1; i <= 10; i++) {
            System.out.println(nombre + " ha avanzado al punto " + i);
            try {
                Thread.sleep(500); // Simula el mismo tiempo de avance para cada uno de los corredores
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        // Al llegar a la meta, se ha de registrar que ha llegado el corredor
        synchronized (metaCompartida) {
            metaCompartida.add(nombre);
        }
        System.out.println(nombre + " ha completado su recorrido");
    }
}