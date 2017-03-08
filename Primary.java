 import java.net.*;
import java.io.*;

public class Primary {
    public static void main(String[] args) throws IOException {
        
        //
        // sockets and other variables declaration
        //
        // maximum number of clients connected: 10
        //
               
        ServerSocket serverSocket = null;
        Socket[] client_sockets;
        client_sockets = new Socket[10];
        String inputLineB = null;
        String inputLineC = null;
        String outputLine = null;
        PrintWriter[] s_out;
        s_out = new PrintWriter[10];
        BufferedReader[] s_in;
        s_in = new BufferedReader[10];
        
        
        //
        //get port number from the command line
        //
        int nPort = 4444; // default 
        if (args[0] != null) nPort = Integer.parseInt(args[0]);
        
        boolean bListening = true;
        
		String[] sMessages;
		sMessages = new String[10];
		int nMsg = 0;
		boolean bAnyMsg = false;
		boolean bAlive = true;
		
		
		//
		// initialize some var's for array handling
		//
        int s_count = 0;
        int i = 0;
        int k = 0;
        int j = 0;
        
        //
        // create server socket
        //
        try {
            serverSocket = new ServerSocket(nPort);
            
        } catch (IOException e) {
            System.err.println("Could not listen on port: " + args[0]);
            System.exit(-1);
        }
        
        //
        // this variable defines how many clients are connected
        //
        int nClient = 0;
                
        //
        // set timeout on the socket so the program does not
        // hang up
        //
        serverSocket.setSoTimeout(1000);
        
        //
        // main server loop
        //
        String flag = "7E";
        String address = "";
		
        while (bListening){
			
			
			try {
				
				//P0
				//
				client_sockets[0]=serverSocket.accept();
				client_sockets[1]=serverSocket.accept();

				s_out[0] = new PrintWriter(client_sockets[0].getOutputStream(),true);
				s_in[0] = new BufferedReader(new InputStreamReader(client_sockets[0].getInputStream()));
				
				s_out[1] = new PrintWriter(client_sockets[1].getOutputStream(),true);
				s_in[1] = new BufferedReader(new InputStreamReader(client_sockets[1].getInputStream()));
				
				s_out[0].println(flag + "B" + "SNRM" + flag); //send the SNRM to new connection
				s_out[1].println(flag + "C" + "SNRM" + flag); //send the SNRM to new connection
							
				while(inputLineB == null) {
					try {
						inputLineB = s_in[0].readLine();
					} catch (IOException e) {}
					
					if (!inputLineB.contains("UA")) {
						inputLineB = null;
					}
				}
				
				while(inputLineC == null) {
					try {
						inputLineC = s_in[1].readLine();
					} catch (IOException e) {}
					
					if (!inputLineC.contains("UA")) {
						inputLineC = null;
					}
				}
				
				//S1
				//
				
				
				
				
				bAnyMsg = true;
				sMessages[nMsg]=" " + s_in[s_count].readLine() + " joined";
				nMsg ++;
							
				// 
				// increment count of clients
				//
				s_count++;
			}
			catch (InterruptedIOException e) {}

			System.out.println(" ");
			
			//
			// is there anything to send
			//
			if (bAnyMsg)
			{
				System.out.println("Got request");
				
				
				//
				// all messages sent - clear messages array
				//
				for (j=0;j<nMsg;j++)
						sMessages[j] = null;
				bAnyMsg = false;
				nMsg = 0;
			}
			
			
					String decoded = "";
					
					
						s_out[i].println("RSP" + decoded);
						decoded = "";
						//
						// messages counter is always incremented AFTER
						// storing message in the array
						nMsg++;
					}
					//
					// else here would mean there was NACK frame send by 
					// the client - NACK on SELECT means no data
					//
					
					
					//
					// bAlive is used to determine if there
					// is at least one active client
					// used to close server if all
					// clients disconnect
					//	
					
					
					
			
			 
		
			
			//
			// stop server automatically when
			// all clients disconnect
			//
			// no active clients
			//
			
	
		
		//
		// close all sockets
		//
		
		for (i=0;i<s_count;i++){
			client_sockets[i].close();
		}
        
        serverSocket.close();
        
    }// end main 
}// end of class MyServer
