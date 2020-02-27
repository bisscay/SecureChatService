/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatapp;

/**
 *
 * @author bAe_Des_Arni_Shra
 */
// import server socket class
import java.net.ServerSocket;
// import socket class
import java.net.Socket;
// import input stream reader
import java.io.InputStreamReader;
// import Buffer reader
import java.io.BufferedReader;

/**
 *
 * @author PUSER
 */
public class Server {
    public static void main(String[] args) throws Exception // Use try catch later 
    {
        // show server is running
        System.out.println("Server Running!");
        // create server socket
        ServerSocket ss = new ServerSocket(9999);
        // 
        System.out.println("System is waiting for client request");
        // Establishing socket connection
        // Accept request from client
        Socket s = ss.accept();
        // Connection Notification
        System.out.println("Client Connected!");
        // read from input stream
        BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
        System.out.println("Message recieved");
        
        
        String str  = br.readLine();
        
        
        System.out.println("Message read");
        // Output message
        System.out.println("Client Data : " + str);
    }
}

