import java.util.Random; // Esto importa la clase Random de Java

/**
 * @autor Pedro Martínez Herrero
 * @since 22/09/2025
 * @until 23/09/2025
 */

public class RandomNumbers {
    public static void main(String[] args) {
        Random rand = new Random(); // Debe ser java.util.Random
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 35; i++) {
            int numero = rand.nextInt(101); // Números entre 0 y 100
            sb.append(numero);
            if (i < 34) {
                // append() añade texto al final del contenido actual de un StringBuilder
                sb.append(" "); // Separador entre números
            }
        }

        System.out.println(sb.toString());
    }
}