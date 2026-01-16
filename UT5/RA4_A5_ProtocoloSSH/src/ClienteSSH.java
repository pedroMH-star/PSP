import com.jcraft.jsch.JSchException;
import java.io.IOException;

import java.io.IOException;

public class ClienteSSH {
    public static void main(String[] args) {

        try {

            // Conectamos al servidor
            SSHConnector sshConnector = new SSHConnector();

        } catch (IOException e) {
            System.err.println("Error en la conexión SSH: " + e.getMessage());
        }
    }
}