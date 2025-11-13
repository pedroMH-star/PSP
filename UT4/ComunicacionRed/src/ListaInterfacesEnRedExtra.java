import java.io.*;
import java.net.*;
import java.util.*;

/**
 * @autor Pedro Martínez Herrero
 * @since 11/11/2025
 * @until 11/11/2025
 * Actividad: UT4 - Comunicación en Red (Extra)
 */

public class ListaInterfacesEnRedExtra {
    public static void main(String[] args) {
        try (PrintWriter writer = new PrintWriter("network_info.txt")) {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();

            writer.println("# INFORME DE INTERFACES DE RED #\n");

            while (interfaces.hasMoreElements()) {
                NetworkInterface interfaz = interfaces.nextElement();
                writer.println("Interfaz: " + interfaz.getDisplayName());
                writer.println("Activa: " + interfaz.isUp());

                for (InterfaceAddress iterador : interfaz.getInterfaceAddresses()) {
                    InetAddress address = iterador.getAddress();
                    writer.println("  IP: " + address.getHostAddress());
                    writer.println("  Máscara: " + iterador.getNetworkPrefixLength());
                    writer.println("  Broadcast: " + iterador.getBroadcast());
                }

                writer.println(" ");
            }

            System.out.println("Datos guardados en 'network_info.txt'");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}