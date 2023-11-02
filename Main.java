

import java.util.HashMap;
        import java.util.Map;
        import java.util.Scanner;

class BankAccount {
    String userId;
    private String pin;
    private double balance;
    // Additional fields for transaction history, etc., can be added

    public BankAccount(String userId, String pin, double initialBalance) {
        this.userId = userId;
        this.pin = pin;
        this.balance = initialBalance;
    }

    public boolean authenticate(String userId, String pin) {
        return this.userId.equals(userId) && this.pin.equals(pin);
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited: " + amount);
        } else {
            System.out.println("Invalid amount for deposit.");
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("Withdrawn: " + amount);
        } else {
            System.out.println("Insufficient funds or invalid amount for withdrawal.");
        }
    }

    public void transfer(BankAccount receiver, double amount) {
        if (amount > 0 && amount <= balance) {
            this.balance -= amount;
            receiver.deposit(amount);
            System.out.println("Transferred: " + amount + " to User ID: " + receiver.userId);
        } else {
            System.out.println("Transfer cannot be completed. Insufficient funds or invalid amount.");
        }
    }
}

class ATM {
    private Map<String, BankAccount> accounts;
    private Scanner scanner;

    public ATM() {
        accounts = new HashMap<>();
        scanner = new Scanner(System.in);
    }

    public void addAccount(BankAccount account) {
        accounts.put(account.userId, account);
    }

    public void start() {
        System.out.print("Enter your user ID: ");
        String userId = scanner.nextLine();
        System.out.print("Enter your PIN: ");
        String pin = scanner.nextLine();

        BankAccount userAccount = accounts.get(userId);
        if (userAccount != null && userAccount.authenticate(userId, pin)) {
            showOptions(userAccount);
        } else {
            System.out.println("Authentication failed. Please try again.");
        }
    }

    private void showOptions(BankAccount userAccount) {
        while (true) {
            System.out.println("\nWelcome! Choose an option:");
            System.out.println("1. View Balance");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer");
            System.out.println("5. Quit");

            System.out.println("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Clear the input buffer


            switch (choice) {

                case 1:
                    System.out.println("Balance: " + userAccount.getBalance());
                    break;
                case 2:
                    System.out.print("Enter amount to deposit: ");
                    double depositAmount = scanner.nextDouble();
                    userAccount.deposit(depositAmount);
                    break;
                case 3:
                    System.out.print("Enter amount to withdraw: ");
                    double withdrawAmount = scanner.nextDouble();
                    userAccount.withdraw(withdrawAmount);
                    break;
                case 4:
                    System.out.print("Enter recipient's user ID: ");
                    String recipientId = scanner.nextLine();
                    BankAccount recipient = accounts.get(recipientId);
                    if (recipient != null) {
                        System.out.print("Enter amount to transfer: ");
                        double transferAmount = scanner.nextDouble();
                        userAccount.transfer(recipient, transferAmount);
                    } else {
                        System.out.println("Recipient user ID not found.");
                    }
                    break;
                case 5:
                    System.out.println("Thank you for using the ATM. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {
        ATM atm = new ATM();

        BankAccount account1 = new BankAccount("user1", "1234", 1000.0);
        BankAccount account2 = new BankAccount("user2", "5678", 500.0);

        atm.addAccount(account1);
        atm.addAccount(account2);

        atm.start();
    }
}
