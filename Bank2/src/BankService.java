import java.io.*;
import java.net.Socket;

public class BankService implements Runnable{

    private Socket s;
    private Bank bank;

    public BankService(Socket aSocket, Bank aBank){
        bank = aBank;
        s = aSocket;
    }


    @Override
    public void run(){
        try {
            String str = "";
            BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
            OutputStreamWriter os = new OutputStreamWriter(s.getOutputStream());
            PrintWriter out = new PrintWriter(os);

            while(!str.equals("Quit")){
                str = br.readLine();
                System.out.println("Command: " + str);

                switch (str) {
                    case "Deposit": {
                        int accountNum = Integer.parseInt(br.readLine());
                        double amount = Double.parseDouble(br.readLine());
                        bank.deposit(accountNum - 1, amount);
                        break;
                    }
                    case "Check": {
                        int accountNum = Integer.parseInt(br.readLine());
                        double balance = bank.getBalance(accountNum -1 );
                        out.println(balance);
                        os.flush();
                        break;
                    }
                    case "Withdraw": {
                        int accountNum = Integer.parseInt(br.readLine());
                        double amount = Double.parseDouble(br.readLine());
                        bank.withdraw(accountNum - 1 , amount);
                        break;
                    }
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }
}
