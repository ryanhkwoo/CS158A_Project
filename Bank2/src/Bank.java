
public class Bank {

    private BankAccount bankArray[];

    public Bank(int numOfAccount) {
        bankArray = new BankAccount[numOfAccount];
        for(int i = 0; i < numOfAccount; i++){
            bankArray[i] = new BankAccount();
        }
    }

    void deposit(int accountNum, double amount){ bankArray[accountNum].deposit(amount); }

    void withdraw(int accountNum, double amount){ bankArray[accountNum].withdraw(amount); }

    double getBalance(int accountNum){ return bankArray[accountNum].getBalance(); }
}
