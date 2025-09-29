public class Cliente extends Thread {
    private final Buffer buffer;

    public Cliente(Buffer buffer, String nombre) {
        super(nombre);
        this.buffer = buffer;
    }

    @Override
    public void run() {
        try {
            while (true) {
                buffer.remove(getName());
                Thread.sleep(1000); // Espera de 1000 ms
            }
        } catch (InterruptedException e) {
            System.out.println(getName() + " interrumpido");
        }
    }
}