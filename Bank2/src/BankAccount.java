public class BankAccount
{
    private double balance;

    public BankAccount() { this.balance = 0.0; }

    public BankAccount(double balance)
    {
        this.balance = balance;
    }

    public double getBalance()
    {
        return this.balance;
    }

    public void deposit(double amount)
    {
        this.balance += amount;
    }

    public void withdraw(double amount)
    {
        this.balance -= amount;
    }


}
