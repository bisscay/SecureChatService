/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatapp;

/**
 *
 * @author bAe_Des_Arni_Shro
 */
// import server socket class
import java.net.ServerSocket;
// import socket class
import java.net.Socket;
// import input stream reader
import java.io.InputStreamReader;
// import Buffer reader
import java.io.BufferedReader;
// import input stream library for objects
import java.io.ObjectInputStream;
// import
import java.security.Key;

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
        // Server listening for connection
        // (Accept request from client)
        Socket s = ss.accept();
        // Connection Notification
        System.out.println("Client Connected!");
        // read from input stream
        BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
        System.out.println("Message recieved");
        
        String str  = br.readLine();
        // Output message
        System.out.println("Client Data : " + str.replaceAll("[|||]", "\n"));
        // host A public key
        ObjectInputStream is = new ObjectInputStream(s.getInputStream());//place in try with resources
        // read value and cast 
        Key pubA = (Key)is.readObject();//catch IOException & ClassNotFoundException
        // show public key
        System.out.println("Client Public Key : " + pubA);
        
        // verify host A hashed challenge
        byte[] hashA = (byte[])is.readObject();
        // See what's in hash
        System.out.print("Hashed Pass A: [ ");
        for (int i : hashA){
            System.out.print(i);
            System.out.print(" ");
        }
        System.out.println("]");
        
        System.out.println("Message read");
        // close socket
        s.close(); // take out once placed in try with resources
    }
}

