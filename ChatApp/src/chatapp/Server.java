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
// import utility class for img processing
import javax.imageio.ImageIO;
// import buffer for image data
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
// import class for file manip
import java.io.File;
import java.io.FileInputStream;
import java.util.Scanner;
// import class to handle IOExceptions
//import java.io.IOException;
/**
 *
 * @author PUSER
 */
public class Server {
    public static void main(String[] args) throws Exception // Use try catch later 
    {
        //variables/constant declaration
        String controlString;
        String fileName;
        Scanner readUserInput;
        
        // show server is running
        System.out.println("Server Running!");
        
        //******************ESTABLISH SERVER SOCKET & BIND TO PORT/SETUP COMMUNICATION SOCKET WITH CLIENT*****************************************
        // create server socket
        //server socket listening on port 9999
        ServerSocket serverSocket = new ServerSocket(9999);
        // 
        System.out.println("System is waiting for client request");
        // Establishing socket connection
        // Server listening for connection
        // (Accept request from client)
        Socket linkSocket = serverSocket.accept();
        // Connection Notification
        System.out.println("Client Connected!");
        // read from input stream
        //****************************************************************************************************************************************
        
        
        BufferedReader br = new BufferedReader(new InputStreamReader(linkSocket.getInputStream()));
        System.out.println("Message recieved");
        
        String str  = br.readLine();
        // Output message
        System.out.println("Client Data : " + str.replaceAll("[|||]", "\n"));
        // host A public key
        //ObjectInputStream is = new ObjectInputStream(linkSocket.getInputStream());//place in try with resources
        // read value and cast 
        //Key pubA = (Key)is.readObject();//catch IOException & ClassNotFoundException
        // show public key
        //System.out.println("Client Public Key : " + pubA);
        
        // verify host A hashed challenge
       // byte[] hashA = (byte[])is.readObject();
        // See what's in hash
        /*System.out.print("Hashed Pass A: [ ");
        for (int i : hashA){
            System.out.print(i);
            System.out.print(" ");
        }
        System.out.println("]");**/
        
        //System.out.println("Message read");
        //**********************Read Image*******************************************************
        // Following order of precedence
        // generate input stream
        // make an image input stream
        // read from stream
        // store in a buffer
        //BufferedImage img = ImageIO.read(ImageIO.createImageInputStream(linkSocket.getInputStream()));
        //System.out.println("Image received");
        // place in file
        //ImageIO.write(img, "jpg", new File("Images/ServerImg/GeronaCopy")); // specify file path
        // view on form panel
        
        // close socket
        //linkSocket.close(); // take out once placed in try with resources
        //*****************************************************************************************************************************
        
        //*********************************SETUP IO STREAMS TO ENABLE IMAGE TRANSFER***************************************************
        //Initialize Data Input Stream on server end: Enable server to read data from the linked communication socket Input stream
        DataInputStream dataInputStream = new DataInputStream(linkSocket.getInputStream());
        //Initialize Data Output Stream on server end: Enable server to write data to the linked communication socket Input stream
        DataOutputStream dataOutputStream = new DataOutputStream(linkSocket.getOutputStream());
        //****************************************************************************************************************************
        
        //********************************RESUME DATA (IMAGE) TRANSFER TO CLIENT*****************************************************
        try
        {
            controlString = dataInputStream.readUTF(); //read controlString value pushed from client end
            System.out.print("Enter file name (Gerona.jpg) to send image: "); //promt user to enter file name "Gerona.jpg"
            readUserInput = new Scanner(System.in);
            fileName = readUserInput.nextLine(); //store image name to fileName
            readUserInput.close(); //close scanner input stream
            
            if (!controlString.equals("stop"))
            {
                System.out.println("...sending " + fileName);
                dataOutputStream.writeUTF(fileName);//write file name to the linked communication socket
                dataOutputStream.flush();//push file name to client
                
                //create file instance to send and pass in file name
                //Creates a new File instance by converting the given pathname (file name) string into an abstract pathname
                File myFile = new File(fileName);
                
                //FileInputStream obtains input bytes from a file in a file system. 
                //FileInputStream is used for reading streams of raw bytes such as image data.
                FileInputStream fileInputStream = new FileInputStream(myFile);
                long fileSize = (int)myFile.length();//get the file size
                
                byte[] byteArray = new byte[1024]; //data read from file Input Stream will passed into the array as a content holder
                int readCheck; //variable to store the FileInputStream.read() method return value
                
                dataOutputStream.writeUTF(Long.toString(fileSize));//write file size to the linked communication socket
                dataOutputStream.flush();//push file size to client
                
                System.out.println("Size: " + fileSize +"B");
                System.out.println("Buffer size: " + serverSocket.getReceiveBufferSize() + "B" );
                
                //while the return value of the read() method is not equal to "-1" (indicates end of file)
                //keep passing data bits from image file Input Stream into the byte array.
                while((readCheck = fileInputStream.read(byteArray)) != -1)
                {
                    dataOutputStream.write(byteArray, 0, readCheck);//write all content of image file contained in array to the linked communication socket
                    dataOutputStream.flush();//push all image content to client
                }
                
                fileInputStream.close();
                dataOutputStream.flush();
            }
            dataOutputStream.writeUTF("stop");
            System.out.println("Send Complete");
            dataOutputStream.flush();
            
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.out.println("An error occured");
        }
        dataInputStream.close();
        linkSocket.close();
        serverSocket.close();
    }   //**************************************************************************************************************************************************
    
}

