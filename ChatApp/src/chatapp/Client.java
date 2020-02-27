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
import java.io.OutputStream;
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
/**
 *
 * @author PUSER
 */
public class Client {

    /**
     * @param args the command line arguments
     */
    private final double PI = 3.242; 
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
        
        // State ip address / host name
        String ip = "127.0.0.1";
        // State port number
        // Use a free port number 1024 - 65535
        int port = 9999;
        
        // Open a Socket
        // Send Request to Server
        System.out.println("Client Up.");
        Socket s = new Socket(ip,port);
        System.out.println("Socket Request Sent");
        OutputStreamWriter usher = new OutputStreamWriter(s.getOutputStream());
        //usher.write(str);
        PrintWriter out = new PrintWriter(usher);
        out.println(str);
        out.flush();
        System.out.println("Message Sent");
        
        /*
        // Open output stream
        OutputStream os = s.getOutputStream();
        
        // make writer(usher) aware of location to be written to
        OutputStreamWriter usher = new OutputStreamWriter(os);
        
        // write to output stream
        usher.write(str);
        
        // send request
        os.flush();
*/
        
    }
    
}

