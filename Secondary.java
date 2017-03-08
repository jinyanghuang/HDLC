import java.awt.*;
import java.applet.*;
import java.net.*;
import javax.swing.*;
import java.io.*;


public class Secondary extends Applet implements Runnable
{
    static boolean bConnected = false;
    
    static Socket mySocket = null;
    static PrintWriter out = null;
    static BufferedReader in = null;
    static String address = null;
    static String sIP = null;
    
    static String fromServer = null;
    static String fromUser = null;

    static TextField textField;
    static TextArea textArea;
    
    static String sConnection = "Not Connected to the chat server!";
    
    Thread thread;
   
        
    public boolean action(Event evt, Object arg) {
        String sTemp = null;
        
        //******************************************
        // close button pressed
        //******************************************
        if (arg == "Close") {
            try {
                if (bConnected)
                    mySocket.close();
            } catch (IOException e) {}
            
            System.exit(0);
        }
        
        //********************************************
        // connect button pressed
        //******************************************
        if (arg == "Connect" && !bConnected) {
                    
            try {
                //
                // get server IP and name of client
                //
                sIP = "127.0.0.1";
                //
                // get client name used for communication to other people
                //
                address = "Encoder";
                
                //
                //get port number
                //
                int nPort = 4444; // default 
//                nPort = Integer.parseInt(JOptionPane.showInputDialog("Enter port number:"));
                
                //
                // connect to the socket
                //
                mySocket = new Socket(sIP, nPort);
                
                // optional - setting socket timeout to 5 secs
                // this is not necessary because application
                // runs with multiple threads
                //
                //mySocket.setSoTimeout(5000);
                                
                bConnected = true;
                //
                // define input and output streams for reading and
                // writing to the socket
                //
                out = new PrintWriter(mySocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(mySocket.getInputStream()));
                out.println(address);
                //
                // set screen messages
                //
                sConnection = "Connected to the chat server!";            
                
       
                
                //
                // define new thread
                //
                thread = new Thread(this);
                thread.start();    
                
            } catch (UnknownHostException e) {
                bConnected = false;
                sConnection = "Not Connected to the chat server!";
                JOptionPane.showMessageDialog(null,"Don't know about host: " + sIP);
            } catch (IOException e) {
                bConnected = false;
                sConnection = "Not Connected to the chat server!";
                JOptionPane.showMessageDialog(null,"Server is not running!"); }        
        }// end of connect button
    
        //*****************************************************    
        // Send Message button pressed
        //*****************************************************    
        if (arg == "Send Message") {
                
            String encoded="";
            
            if (textField.getText() != null){
                //
                // copy content of the message text into 
                // internal buffer for later processing
                // only one message can be stored into the
                // buffer
                //
                fromUser = textField.getText();
               
               
                fromUser=encoded;
                out.println("ACK "+ fromUser );
                textArea.setText(textArea.getText()+ "\n"+address+" says "+fromUser);

                textField.setText("");
            } else {
                fromUser = null;
            }
            
        }
        
        //
        // repaint the screen
        //           
        repaint();
        
        return true;
    }
    
    

    
    //************************************************
    // main
    //
    // main application method for the class
    // it will initialize whole environment
    //
    //************************************************
    public static void main(String args[]){
        String sTemp = null;
        //
        // define window and call standard methods
        //
        System.out.println("Client started.");
        Secondary app = new Secondary();

    
    }// end of main

    //***********************************
    // stop
    //***********************************  
    public void stop() {
        thread.stop();
    }// end of stop

    //***********************************
    // run - thread method
    //***********************************
    public void run() {
        boolean bLoopForever = true;
        while (bLoopForever){
            //
            // call function to read/write from/to server
            //
            checkServer();
            try {
                //
                // put thread into some delay to 
                // give more cpu time to other processing
                //
                thread.sleep(10);
            } catch (InterruptedException e) {}
        }
    }// end of run

    //***********************************
    // checkServer - this is a main client algorithm
    //***********************************
    public static void checkServer(){
    
    String sTemp = null;
   
    String sFrameType = null;
    String flag="7E";
    try {
        //
        // read from the server socket
        //
        if ((fromServer = in.readLine()) != null){
            
        	
        	if (fromServer.contains( "SNRM")) {
            	System.out.println(fromServer);
            	out.println(flag+"A"+"UA"+flag);
            }

            //
            // determine what type of frame has been received
            // this is a simplified way of doing
            //
            sFrameType = fromServer.substring(0,3);
            
           
        
            
            if (sFrameType.equals("RSP")) {
            	if(fromUser!=null){
            	sTemp = textArea.getText();
            	textArea.setText(sTemp + "\n" + "Response from decoder: " + fromServer);
            	}
            	}
            //
         
        }// end of if anything from server
        //
        // trap exceptions while reading/writing from/to server
        //
        }catch (InterruptedIOException e) { }    
         catch (IOException e) { }    

    }// end of checkserver

}// end of class MyClientWin
