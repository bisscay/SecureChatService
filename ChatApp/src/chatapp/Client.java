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
// Import Socket Class
import java.net.Socket;
// import OutputStream Class
//import java.io.OutputStream; // not utilized
// import OutputStreamWriter Class
import java.io.OutputStreamWriter;
// import print writer
import java.io.PrintWriter;
// import java security package
import java.security.KeyPairGenerator;
// import class to hold keys
import java.security.KeyPair;
// import
import java.security.Key;
// import java.net to use ipv4 address class
import java.net.Inet4Address;
/**
 *
 * @author PUSER
 */
public class Client {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws   Exception { // avoid throwing (Use try catch)
        // create keypair generator implementing RSA algorithm
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        // specify key size 1024 | 2048
        kpg.initialize(2048);
        // generate key pair and store in container kp
        KeyPair kp = kpg.generateKeyPair();
        // get public key
        Key pub = kp.getPublic();
        // get private key
        Key pvt = kp.getPrivate();
        // test string
        String str = pub.toString();
        // TEST: see what pub & pvt hold
        System.out.println("Public Key: " +pub +"\nPublic Key in String fmt: " +str +"\nPrivate Key: " +pvt);
        
        // State ip address (Same host but different port communication)
        // (InetAddress class represents IP address) 
        // because we are using a Socket ctsr that takes in 
        // InetAddress address,
        // int port, 
        // InetAddress localAddr, and 
        // int localPort
        // Store host A address
        // cast to ensure address is v4 and not v6
        Inet4Address hostA = (Inet4Address)Inet4Address.getByName("localhost"); // catch UnknownHostException
        /*
        // Another Way, then you'd have to put in the values 127.0.0.1 in byte array 
        byte [] test = new byte[4];
        Inet4Address hostA = (Inet4Address)Inet4Address.getByAddress(test);
        */
        // test host A ip address value
        //System.out.println("ip ADDDD: " + hostA);
        // Store host B (same machine)
        Inet4Address hostB = hostA; // TRY: Another networkcard on same Pc (Tease: Same pc can have multiple ip)
        // State port number
        // random free port will be used for host A
        int portA = 0;
        // Use a free port number 1024 - 65535 for host B
        int portB = 9999;
        
        // Open a Socket
        // Send Request to Server
        System.out.println("Client Up.");
        // create a stream and connect to specified ip & port (Destination B)
        // source of connection request can be specified as parameters
        // same source and destination,
        // select a free port 1024-65535 
        // or select 0 for a random free port to be used
        // or leave port as null, this checks if hostB is an anyLocal address 
        // i.e address 0.0.0.0 (sockets that bind to all network cards)
        Socket s = new Socket(hostB,portB,hostA,portA); // Catch IOException // what happens if port B is 0?
        System.out.println("Socket Request Sent");
        // Following order of precedence
        // create an output stream for the socket
        // make the stream writable
        OutputStreamWriter usher = new OutputStreamWriter(s.getOutputStream());// place in try with resources block
        // flush the buffer on println() invocation 
        // (this might be why only first line of public key is printed)
        // Possible solution: alter what initiates flush, or
        // take out carridge return and newline from sent public key string
        PrintWriter out = new PrintWriter(usher);
        out.println(str);
        out.flush();
        System.out.println("Message Sent");
        
        
    }
    
}

