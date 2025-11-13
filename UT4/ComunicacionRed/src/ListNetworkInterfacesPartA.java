import java.net.*;
import java.util.*;

/**
 * @autor Pedro Martínez Herrero
 * @since 11/11/2025
 * @until 11/11/2025
 * Actividad: UT4 - Comunicación en Red (Parte A)
 */

public class ListNetworkInterfacesPartA {
    public static void main(String[] args) {
        // 1º) NetworkInterface.getNetworkInterfaces() devuelve un Enumeration
        // que contiene todas las interfaces de red disponibles en el sistema
        // (tarjetas físicas, adaptadores virtuales, loopback, etc.)
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();

            if (!interfaces.hasMoreElements()) {
                System.out.println("No hay interfaces de red disponibles");
                return;
            }

            System.out.println("# LISTADO DE INTERFACES DE RED #\n");

            // 2º) Iteramos sobre cada interfaz de la red encontrada
            while (interfaces.hasMoreElements()) {
                // Dentro del bucle while nextElement() obtiene la siguiente interfaz de la enumeración
                NetworkInterface interfaz = interfaces.nextElement();

                    // a) getName() devuelve el nombre de la interfaz del sistema (ej: eth0, wlan0, lo)
                    // Es el identificador corto usado internamente por el SO
                    String name = interfaz.getName();

                    // b)  getDisplayName() devuelve un nombre descriptivo más legible
                    // En Windows suele ser el nombre completo del adaptador
                    String displayName = interfaz.getDisplayName();

                    // c) isUp() comprueba si la interfaz está actualmente activa/operativa
                    // Devuelve true si está habilitada y funcionando, false en caso contrario
                    boolean activa = interfaz.isUp();

                // Mostramos la información básica de la interfaz
                System.out.println("Interfaz: " + name + " (" + displayName + ")");
                System.out.println("Estado: " + (activa ? "Activa" : "Inactiva"));
                System.out.println("============================================");

            }

        } catch (SocketException e) {
            // SocketException se lanza un error al acceder a las interfaces de red
            // Por ejemplo, por falta de permisos oproblemas en el sistema
            e.printStackTrace();
        }

    }
}