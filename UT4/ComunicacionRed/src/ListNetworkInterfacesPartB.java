import java.net.*;
import java.util.*;

/**
 * @autor Pedro Martínez Herrero
 * @since 11/11/2025
 * @until 11/11/2025
 * Actividad: UT4 - Comunicación en Red (Parte B)
 */

public class ListNetworkInterfacesPartB {
    public static void main(String[] args) {
        // En esta parte se amplía el programa anterior para mostrar
        // la configuración IP detallada de cada interfaz
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();

            if (!interfaces.hasMoreElements()) {
                System.out.println("No hay interfaces de red disponibles.");
                return;
            }

            System.out.println("# DETALLE DE INTERFACES DE RED #\n");

            while (interfaces.hasMoreElements()) {
                NetworkInterface interfaz = interfaces.nextElement();

                String name = interfaz.getName();
                String displayName = interfaz.getDisplayName();
                boolean activa = interfaz.isUp();

                System.out.println("Interfaz: " + name + " (" + displayName + ")");
                System.out.print("Estado: " + (activa ? "Activa" : "Inactiva"));

                // Obtenemos la lista de direcciones asociadas a la interfaz
                List<InterfaceAddress> direcciones = interfaz.getInterfaceAddresses();

                if (direcciones.isEmpty()) {
                    System.out.println("  (Sin direcciones asignadas)\n");
                } else {
                    for (InterfaceAddress dir : direcciones) {
                        InetAddress direccion = dir.getAddress();

                        // Distinguimos IPv4 e IPv6 usando instanceof
                        if (direccion instanceof Inet4Address) {
                            System.out.println("  Dirección IPv4: " + direccion.getHostAddress());
                        } else if (direccion instanceof Inet6Address) {
                            System.out.println("  Dirección IPv6: " + direccion.getHostAddress());
                        }

                        // Prefijo de red (longitud de la máscara)
                        short prefijo = dir.getNetworkPrefixLength();
                        System.out.println("  Máscara (prefijo de red): /" + prefijo);

                        // Dirección de broadcast (solo para IPv4)
                        InetAddress broadcast = dir.getBroadcast();
                        if (broadcast != null) {
                            System.out.println("  Broadcast: " + broadcast.getHostAddress());
                        }

                        System.out.println();
                    }
                }

                System.out.print("============================================\n");
            }

        } catch (SocketException e) {
            System.out.println("Error al obtener la configuración de red: " + e.getMessage());
        }
    }
}
