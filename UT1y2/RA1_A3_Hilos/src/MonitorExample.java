/**
 * Clase principal que ejecuta la simulación
 * Crea hilos suministradores y clientes y los ejecuta de manera concurrente
 *
 * @author Pedro Martínez Herrero
 * @since 30/09/2025
 */
public class MonitorExample {
    public static void main(String[] args) {
        Buffer buffer = new Buffer(5); // Tamaño máximo 5 elementos

        System.out.println("* PRUEBA 1: 1 Cliente y 1 Suministrador *");
        Suministrador s1 = new Suministrador(buffer, "Suministrador-1");
        Cliente c1 = new Cliente(buffer, "Cliente-1");
        s1.start();
        c1.start();

        try {
            Thread.sleep(15000); // Esperar 15 segundos antes de la siguiente prueba
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("\n* PRUEBA 2: 3 Clientes y 3 Suministradores *");
        for (int i = 2; i <= 4; i++) {
            new Suministrador(buffer, "Suministrador-" + i).start();
            new Cliente(buffer, "Cliente-" + i).start();
        }
    }
}