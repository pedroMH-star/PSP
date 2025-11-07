import java.util.List;

/**
 * @autor Pedro Martínez Herrero
 * @since 04/11/2025
 * @until 04/11/2025
 * Actividad: UT3 - Programación Multihilo (Prioridades)
 */

public class Corredor implements Runnable {
    // Atributos
    private final String nombre;
    private final List<String> metaCompartida;

    // Constructor
    public Corredor(String nombre, List<String> metaCompartida) {
        this.nombre = nombre;
        this.metaCompartida = metaCompartida;
    }

    @Override
    public void run() {
        // Carrera con 10 puntos intermedios de control
        for (int i = 1; i <= 10; i++) {
            System.out.println(nombre + " ha avanzado al punto " + i);
            try {
                Thread.sleep(1000); // Simula el mismo tiempo de avance para cada uno de los corredores
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        // Al llegar a la meta, se ha de registrar que ha llegado el corredor
        synchronized (metaCompartida) {
            metaCompartida.add(nombre);
        }
        System.out.println(nombre + " ha cruzado la meta");
    }
}