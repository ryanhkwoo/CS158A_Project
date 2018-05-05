import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	public static void main(String[] args) throws Exception{
		System.out.println("Server is started.");

        ServerSocket ss = new ServerSocket(9000);
        String str = "";
        Bank bank = new Bank(10);

        while(!str.equals("Quit")) {

            System.out.println("Server is waiting for client request.");
            Socket s = ss.accept();

            System.out.println("Client is connected.");

            //BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
            //str = br.readLine();


            //System.out.println("Client Data: " + str);


            BankService service = new BankService(s, bank);
            Thread t = new Thread(service);
            t.start();

        }
	}

}
