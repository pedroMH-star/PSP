public class Suministrador extends Thread {
    private final Buffer buffer;

    public Suministrador(Buffer buffer, String nombre) {
        super(nombre);
        this.buffer = buffer;
    }

    @Override
    public void run() {
        try {
            while (true) {
                buffer.add(getName());
                Thread.sleep(500); // Espera de 500 ms
            }
        } catch (InterruptedException e) {
            System.out.println(getName() + " interrumpido");
        }
    }
}