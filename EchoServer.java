/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author lambda
 */
import java.io.*;
import java.net.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class EchoServer {
    private static Connection cn;
   
    public static void main(String[] args) {
        activarServer();
    }
    public static void activarServer () {
        try {
            ServerSocket serverSocket = new ServerSocket(1234);
            Socket clientSocket = serverSocket.accept();
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String inputLine = in.readLine();
            System.out.println("Servidor");
            System.out.println("Recibiendo: " + inputLine);
            String outputLine = "";
       
            Class.forName("org.mariadb.jdbc.Driver");
            cn = DriverManager.getConnection("jdbc:mariadb://192.168.0.12/servidor", "alumnosjava", "uiop098765");
            PreparedStatement mssj = cn.prepareStatement("INSERT INTO mensaje (msj) VALUES (?)");
            mssj.setString(1, x);
            mssj.executeUpdate();
            cn.close();
            
            System.out.println("Enviando: " + outputLine);
            out.println(outputLine);
            out.close();
            in.close();
            clientSocket.close();
            serverSocket.close();
            
            if (!inputLine.equalsIgnoreCase("salir")) {
                activarServer();
            }
         
        } catch (IOException ex) {
            System.out.println("Error" + ex);
        }
    }
}
