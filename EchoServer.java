import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class EchoServer {
    static Connection cn = null;

    public static void main(String[] args) {
        activarServer();
    }

    public static void activarServer () {
        System.out.println("Server iniciado");
        try {
            ServerSocket serverSocket = new ServerSocket(1234);
            Socket clientSocket = serverSocket.accept();
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String inputLine = in.readLine();
            System.out.println("Servidor");
            System.out.println("Recibiendo: " + inputLine);
            String[] partes = inputLine.split(",");
            String msj = partes[0];
        
            Class.forName("org.mariadb.jdbc.Driver");
            cn = DriverManager.getConnection("jdbc:mariadb://192.168.0.12/servidor", "alumnosjava", "uiop098765");
            PreparedStatement prStmt = cn.prepareStatement("INSERT INTO mensaje (msj) VALUES (?)");
            prStmt.setString(1, msj);
            
            prStmt.executeUpdate();
            cn.close();

            String outputLine = "";
            if (!inputLine.equalsIgnoreCase("salir")) {
                outputLine = String.valueOf(inputLine);
            }

            System.out.println("Enviando: " + outputLine);
            out.println(outputLine);
            out.close();
            in.close();
            clientSocket.close();
            serverSocket.close();
            
            if (!inputLine.equalsIgnoreCase("salir")) {
                activarServer();
            }
        } catch (Exception ex) {
            System.out.println("Error"+ex.toString());
        }
    }
}
