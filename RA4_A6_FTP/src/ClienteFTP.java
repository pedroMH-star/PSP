// Problemas de esta solución, posibles optimizaciones:
/*
    No hay manejo de errores específicos ni validaciones
    No cierra recursos adecuadamente
 */

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import java.io.FileOutputStream;
import java.io.IOException;

/*
 * author: Pedro Martínez Herrero
 * @since: 07/01/2026
 * @until: 07/01/2026
 * Actividad: Cliente FTP
 */

public class ClienteFTP {

    // Incluir los datos del SERVIDOR DE PRUEBA:  text.rebex.net
    private static final String SERVIDOR = "test.rebex.net";
    private static final String USUARIO = "demo";
    private static final String PASSWORD = "password";

    public static void main(String[] args) {
        FTPClient ftpClient = new FTPClient();

        // Bloque try-catch-finally
        try {
            // Conectamos con el servidor
            conectarServidor(ftpClient, SERVIDOR, USUARIO, PASSWORD);

            // Listamos archivos disponibles
            listarArchivos(ftpClient);

            // Descargamos el archivo
            descargarArchivo(ftpClient, "readme.txt", "readme_descargado.txt");
            descargarArchivoPorExtension(ftpClient, ".txt");

        } catch (Exception e){
            System.err.println("Error de FTP: " + e.getMessage());
            e.printStackTrace();

        } finally {
            // Cerramos la conexión
            cerrarConexion(ftpClient);
        }
    }

    private static void conectarServidor(FTPClient ftpClient,String servidor, String usuario, String password) throws IOException {
        ftpClient.connect(servidor);
        if (ftpClient.login(usuario, password)) {
            ftpClient.enterLocalPassiveMode();
            System.out.println("Conectado correctamente con el servidor: " + servidor);
        } else {
            throw new IOException("No se pudo iniciar sesión en el servidor FTP");
        }
    }

    private static void listarArchivos(FTPClient ftpClient) throws IOException {
        System.out.println("Listado de archivos en el servidor:");
        FTPFile[] archivos = ftpClient.listFiles();
        for (FTPFile archivo : archivos) {
            System.out.println("- " + archivo.getName());
        }
    }

    private static void descargarArchivo(FTPClient ftpClient, String archivoRemoto, String archivoLocal) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(archivoLocal)) {
            if (ftpClient.retrieveFile(archivoRemoto, fos)) {
                System.out.println("Archivo descargado correctamente: " + archivoLocal);
            } else {
                System.out.println("No se pudo descargar archivo: " + archivoLocal);
            }
        }
    }

    private static void descargarArchivoPorExtension(FTPClient ftpClient, String extension) throws IOException {
        FTPFile[] archivos = ftpClient.listFiles();
        for (FTPFile archivo : archivos) {
            if (archivo.getName().endsWith(extension)) {
                String nombreLocal = "descargado_" + archivo.getName();
                descargarArchivo(ftpClient, archivo.getName(), nombreLocal);
            }
        }
    }

    private static void cerrarConexion(FTPClient ftpClient) {
        try {
            if (ftpClient.isConnected()) {
                ftpClient.logout();
                ftpClient.disconnect();
                System.out.println("Conexión cerrada correctamente");
            }
        } catch (IOException e) {
            System.out.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }
}