import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @autor Pedro Martínez Herrero
 * @since 22/09/2025
 * @until 23/09/2025
 */

public class NumRandom2 {
    public static void main(String[] args) {

        // 1. Creamos una instancia para usar los métodos de esta clase
        NumRandom2 numRandom = new NumRandom2();

        // 2. Generamos 35 numeros aleatorios
        List<Integer> numeros = numRandom.generarNumeros();

        // 3. Mostramos los números por la salida estándar
        // System.out.print(numeros);
        numRandom.mostrarNumeros(numeros);

        Random rand = new Random(); // Debe ser java.util.Random
        StringBuilder sb = new StringBuilder();

    }

    // DEFINIMOS LOS MÉTODOS DE LA CLASE
    public List<Integer> generarNumeros() {
        // Instanciamos un generador de números aleatorios
        Random rand = new Random(); // Debe ser java.util.Random

        // Gerneramos la lista donde queramos almacenar los números aleatorios
        List<Integer> numeros = new ArrayList<>();

        // Bucle para generar los 35 números aleatorios
        for (int i = 0; i < 35; i++) {
            int numero = rand.nextInt(101);
            // Números entre 0 y 100
            numeros.add(numero);
        }
        // Devolvemos los números generados
        return numeros;
    }

    // Método que muestra los números por consola
    public void mostrarNumeros(List<Integer> numeros) {
        for (int numero : numeros) {
            System.out.print(numero + " ");
        }
        System.out.println(); // Salto de línea al final
    }
}