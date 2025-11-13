class Panaderia {
    // La estanteria solo tiene hueco para poner una barra de pan
    private int pan; // Identificaremos el pan como un entero (el pan número 1, el pan número 2, etc...)
    private boolean disponible = false; // Si disponible es falso, es que no hay una barra de pan disponible

    public synchronized void producirPan(int valor) {
        // Cuando hay una barra en la estanteria
        while (!disponible) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        pan = valor;
        disponible = true;
        System.out.println("Producido el pan número: " + valor);
        notifyAll();
    }

    public synchronized int consumirPan() {
        // Cuando no hay un pan disponible
        while (!disponible) {
            try {
                wait(); // Espera a que el panadero produzca pan
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        disponible = false; // El cliente ha comprado el pan y ya no hay ningún pan disponible
        // Simularemos que tarda 3 segundos en llegar un cliente y compra el pan
        try {
            Thread.sleep(3000); // Simula tiempo de consumo
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("Consumido el pan número: " + pan);
        notify();
        return pan;
    }
}

public class ComunicacionEntreHilos{
    public static void main(String[] args) {
        Panaderia p = new Panaderia();
        // Hilo productor: el panadero, puede hacer un máximo de 5 barras de pan
        Thread panadero = new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                p.producirPan(i);
            }
        });

        Thread cliente = new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                p.consumirPan();
            }
        });

        panadero.start();
        cliente.start();
    }
}