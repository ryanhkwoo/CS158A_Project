import java.io.*; 
import java.net.*;
public class Server {
	public static void main(String[] args) throws Exception {
        	ServerSocket serverSocket = new ServerSocket(40000);
      		System.out.println("Server Ready!");
			System.out.println("To exit, e.g. CMD Ctrl+C");
      		Socket socket = serverSocket.accept( );                          
                
		/* read message from server chat */
      		BufferedReader readServerMessage = new BufferedReader(new InputStreamReader(System.in));
	        
		/* send message to client */    
      		OutputStream outputStreamClient = socket.getOutputStream(); 
      		PrintWriter printWriterToClient = new PrintWriter(outputStreamClient, true);
 		
		/* receive message from client */
      		InputStream inputStreamClient = socket.getInputStream();
      		BufferedReader clientReader = new BufferedReader(new InputStreamReader(inputStreamClient));
 
      		String clientMessage, messageToClient;               
      		while(true) {
        		if((clientMessage = clientReader.readLine()) != null) {
           			System.out.println(clientMessage);         
        		}         
        		messageToClient = readServerMessage.readLine(); 
        		printWriterToClient.println(messageToClient);             
        		printWriterToClient.flush();
      		}               
    	}                    
}                        
