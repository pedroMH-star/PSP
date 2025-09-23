import java.util.Random; // Esto importa la clase Random de Java

public class RandomNumbers {
    public static void main(String[] args) {
        Random rand = new Random(); // Debe ser java.util.Random
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 35; i++) {
            int numero = rand.nextInt(101); // Números entre 0 y 100
            sb.append(numero);
            if (i < 34) {
                sb.append(" "); // Separador entre números
            }
        }

        System.out.println(sb.toString());
    }
}