public class MonitorExample {
    public static void main(String[] args) {
        Buffer buffer = new Buffer();

        System.out.println("=== PRUEBA 1: 1 cliente y 1 suministrador ===");
        Suministrador s1 = new Suministrador(buffer, "Suministrador-1");
        Cliente c1 = new Cliente(buffer, "Cliente-1");
        s1.start();
        c1.start();

        try {
            Thread.sleep(5000); // Esperar unos segundos antes de la segunda prueba
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("\n=== PRUEBA 2: 3 clientes y 3 suministradores ===");
        for (int i = 2; i <= 3; i++) {
            new Suministrador(buffer, "Suministrador-" + i).start();
            new Cliente(buffer, "Cliente-" + i).start();
        }
    }
}