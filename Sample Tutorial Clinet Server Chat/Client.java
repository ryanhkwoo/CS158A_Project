import java.io.*;
import java.net.*; 
public class Client {
	public static void main(String[] args) throws Exception {
     		Socket socket = new Socket("127.0.0.1", 40000);
		System.out.println("Input message & Enter to send!"); 
		System.out.println("To exit, e.g. CMD Ctrl+C");               

		/* read message from client chat */               
     		BufferedReader readClientMessage = new BufferedReader(new InputStreamReader(System.in));
                 
		/* send message to server */             
     		OutputStream outputStreamServer = socket.getOutputStream(); 
		PrintWriter printWriteToServer = new PrintWriter(outputStreamServer, true);
 		
		/* receive message from server */
     		InputStream inputStreamServer = socket.getInputStream();
     		BufferedReader serverReader = new BufferedReader(new InputStreamReader(inputStreamServer));
 
     		String serverMessage, messageToServer;               
     		while(true) {
        		messageToServer = readClientMessage.readLine();
        		printWriteToServer.println(messageToServer);
        		printWriteToServer.flush();
			
			/* message from server */
        		if((serverMessage = serverReader.readLine()) != null) {
            			System.out.println(serverMessage);
        		}         
      		}               
    	}                    
}                        
