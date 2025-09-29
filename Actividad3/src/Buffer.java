import java.util.LinkedList;
import java.util.Queue;

public class Buffer {
    private final int CAPACIDAD = 5;
    private final Queue<Integer> cola = new LinkedList<>();
    private int contador = 0; // Para identificar los elementos

    // Método para añadir al buffer
    public synchronized void add(String productor) throws InterruptedException {
        while (cola.size() == CAPACIDAD) {
            System.out.println(productor + " espera: Buffer lleno");
            wait();
        }
        contador++;
        cola.add(contador);
        System.out.println(productor + " añadió -> " + contador + " | Buffer: " + cola);
        notifyAll(); // Avisar a los clientes de que hay un nuevo elemento
    }

    // Método para extraer del buffer
    public synchronized void remove(String cliente) throws InterruptedException {
        while (cola.isEmpty()) {
            System.out.println(cliente + " espera: Buffer vacío");
            wait();
        }
        int elemento = cola.poll();
        System.out.println(cliente + " extrajo -> " + elemento + " | Buffer: " + cola);
        notifyAll(); // Avisar a los productores de que hay espacio disponible
    }
}