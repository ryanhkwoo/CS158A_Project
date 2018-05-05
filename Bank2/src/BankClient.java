import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import javax.swing.*;

public class BankClient {

    private static Socket s;
    private static OutputStreamWriter os;
    private static PrintWriter out;
    private JFrame frame;
    private JTextField txtAccountNumber;
    private JTextField txtAmount;
    private JTextField txtBalance;

    /**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				BankClient window = new BankClient();
				window.frame.setVisible(true);

			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}


    /**
	 * Create the application.
	 */
	public BankClient() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

        JButton btnDeposit = new JButton("Deposit");
		btnDeposit.setBounds(115, 207, 86, 29);
		frame.getContentPane().add(btnDeposit);
        btnDeposit.addActionListener(this::btnDepositActionPerformed);


        JButton btnWithdraw = new JButton("Withdraw");
		btnWithdraw.setBounds(193, 207, 93, 29);
		frame.getContentPane().add(btnWithdraw);
        btnWithdraw.addActionListener(this::btnWithdrawActionPerformed);


        JButton btnQuit = new JButton("Quit");
		btnQuit.setBounds(280, 207, 75, 29);
		frame.getContentPane().add(btnQuit);
        btnQuit.addActionListener(this::btnQuitActionPerformed);


        JButton btnCheckBalance = new JButton("Check Balance");
		btnCheckBalance.setBounds(6, 207, 117, 29);
		frame.getContentPane().add(btnCheckBalance);
        btnCheckBalance.addActionListener(this::btnCheckBalanceActionPerformed);

		txtAccountNumber = new JTextField("");
		txtAccountNumber.setBounds(224, 59, 130, 26);
		frame.getContentPane().add(txtAccountNumber);
		txtAccountNumber.setColumns(10);

		txtBalance = new JTextField("");
		txtBalance.setColumns(10);
		txtBalance.setBounds(224, 103, 130, 26);
		frame.getContentPane().add(txtBalance);

		txtAmount = new JTextField("");
		txtAmount.setColumns(10);
		txtAmount.setBounds(224, 141, 130, 26);
		frame.getContentPane().add(txtAmount);

        JLabel lblNewLabel = new JLabel("Account Number");
		lblNewLabel.setBounds(71, 64, 141, 16);
		frame.getContentPane().add(lblNewLabel);

        JLabel lblBalance = new JLabel("Balance");
		lblBalance.setBounds(71, 108, 61, 16);
		frame.getContentPane().add(lblBalance);

        JLabel lblAmount = new JLabel("Amount");
		lblAmount.setBounds(71, 146, 61, 16);
		frame.getContentPane().add(lblAmount);

        JButton btnClear = new JButton("Clear");
		btnClear.setBounds(349, 207, 75, 29);
		frame.getContentPane().add(btnClear);
        btnClear.addActionListener(this::btnClearActionPerformed);
	}


    private void btnClearActionPerformed(ActionEvent e) {
        txtAccountNumber.setText("");
        txtAmount.setText("");
        txtBalance.setText("");
    }

    private void btnCheckBalanceActionPerformed(ActionEvent e) {
        try {
            setInNout();
            String str = "Check";
            String accountNum = txtAccountNumber.getText().trim();
            out.println(str);
            out.println(accountNum);
            os.flush();

            BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
            String balance = br.readLine();
            txtBalance.setText(balance);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void btnWithdrawActionPerformed(ActionEvent e) {
        String accountNum = txtAccountNumber.getText().trim();
        String amount = txtAmount.getText().trim();

        try {
            setInNout();
            out.println("Withdraw");
            out.println(accountNum);
            out.println(amount);
            os.flush();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void btnQuitActionPerformed(ActionEvent e) {
        try {
            String str = "Quit";
            setInNout();
            out.println(str);
            os.flush();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.exit(0);
    }

    private void btnDepositActionPerformed(ActionEvent e) {
        String accountNum = txtAccountNumber.getText().trim();
        String amount = txtAmount.getText().trim();
        try {
            setInNout();
            out.println("Deposit");
            out.println(accountNum);
            out.println(amount);
            os.flush();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    private void setInNout() throws IOException {
        InetAddress localhost = InetAddress.getLocalHost();
        String hostIP = localhost.getHostAddress();
        int port = 9000;
        s = new Socket(hostIP, port);
        os = new OutputStreamWriter(s.getOutputStream());
        out = new PrintWriter(os);
    }


}
