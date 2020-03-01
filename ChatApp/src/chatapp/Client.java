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
// import OutputStreamWriter Class
import java.io.OutputStreamWriter;
// import print writer
import java.io.PrintWriter;
// import object output stream class
import java.io.ObjectOutputStream;
//
import static java.lang.System.out;
// import java security package
import java.security.KeyPairGenerator;
// import class to hold keys
import java.security.KeyPair;
// import
import java.security.Key;
// import java.net to use ipv4 address class
import java.net.Inet4Address;
//import for random number generation
import java.security.SecureRandom;
//import required to implement KeySpec.
//Provides classes and interfaces for key specifications and algorithm parameter specifications.
import java.security.spec.*;
//Provides the classes and interfaces for cryptographic operations.
//Import required to implement PBEKeySpec (Password based encryption)
import javax.crypto.spec.*;
//Provides the classes and interfaces for cryptographic operations.
import javax.crypto.SecretKeyFactory;

public class Client implements KeySpec { //
    
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
        // test string (notice the impact on carrige return in console)
        String str = "\nBienvenue\rOla\r\nWillkommen\n";
        // TEST: see what pub & pvt hold
        System.out.println("Public Key: " +pub +"\nPrivate Key: " +pvt);
        System.out.println("Test String: " +str);
        //***********************************generate PassA***********************************************
        //variable to identifier range for random generator
        int rangeValue = 100000;
        //create an instance of class SecureRandom
        SecureRandom randNumber = new SecureRandom(); 
        //PassA: random Number of type string
        //generate a random nuber in range 0 to 99999 and convert to string
        String PassA = Integer.toString(randNumber.nextInt(rangeValue));
        
        //*************************************************************************************************
        
        //**********************************Hashing(PassA)*************************************************
        //generate salt for extendend hashing cryption
        //this will retrive a random byte array.
        SecureRandom random = new SecureRandom(); //create an instance of class SecureRandom
        byte[] salt = new byte[16]; //creates a size 16 array of type byte
        random.nextBytes(salt); //generate random byte and store in array(salt).
        
        KeySpec spec = new PBEKeySpec(PassA.toCharArray(), salt, 65536, 128); //
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");//converts key into key specification (high level cryptographic operation)
        byte[] hash = factory.generateSecret(spec).getEncoded();
        //H(PassA) completed
        //*************************************************************************************************
               
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
        // adobt effective conversion of text to & from binary
        OutputStreamWriter usher = new OutputStreamWriter(s.getOutputStream());// place in try with resources block
        // flush the buffer on println() invocation 
        // replace carridge return and|or newline from sent public key string
        // (if not, only first line of public key is sent)
        PrintWriter out = new PrintWriter(usher);
        //out.println(pub);
        out.println(str.replaceAll("\\r\\n|\\r|\\n", "|||"));
        out.flush();
        System.out.println("Message Sent");
        
        // Following order of precedence
        // create an output stream for the socket 
        // create a writable object output stream that writes to specified stream
        // (enables you to write Java objects to an OutputStream instead of just raw bytes)
        // Class' object is converted to byte before being sent
        // Note: Class has to implement serializable interface
        // Serializable is a marker interface hence has no method
        // It enables objects states of classes that implement it to be stored in bytes
        ObjectOutputStream os = new ObjectOutputStream(s.getOutputStream());// place in try with resources block
        os.writeObject(pub); // why not use something similar for text? why use PrintWriter?
        // Notice that stream takes strings and objects but handles them seperately
        
        // close socket
        s.close();// take out once placed in try with resources
        
    }
    
}

