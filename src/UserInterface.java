import model.BankAccount;
import Service.BankService;

import java.util.Scanner;

public class UserInterface {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        BankService bankService = new BankService();

        System.out.println("==== Welcome to Secure Bank ====");

        // Create Account
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();

        System.out.print("Set 4-digit PIN: ");
        String pin = scanner.nextLine();

        System.out.print("Initial Deposit: ");
        double balance = scanner.nextDouble();

        BankAccount account = bankService.createAccount(name, pin, balance);

        System.out.println("Account Created Successfully!");
        System.out.println("Your Account Number: " + account.getAccountNumber());

        scanner.nextLine(); // clear buffer

        // LOGIN
        System.out.println("\n==== LOGIN ====");
        System.out.print("Enter Account Number: ");
        String accNo = scanner.nextLine();

        System.out.print("Enter PIN: ");
        String loginPin = scanner.nextLine();

        try {
            BankAccount loggedIn = bankService.login(accNo, loginPin);
            System.out.println("Login Successful!\n");

            int choice;
            do {
                System.out.println("\n1. Deposit");
                System.out.println("2. Withdraw");
                System.out.println("3. Check Balance");
                System.out.println("4. Transfer");
                System.out.println("5. View Transactions");
                System.out.println("6. Exit");
                System.out.print("Choice: ");

                choice = scanner.nextInt();

                switch (choice) {

                    case 1:
                        System.out.print("Amount: ");
                        loggedIn.deposit(scanner.nextDouble());
                        System.out.println("Deposit successful!");
                        break;

                    case 2:
                        System.out.print("Amount: ");
                        loggedIn.withdraw(scanner.nextDouble());
                        System.out.println("Withdrawal successful!");
                        break;

                    case 3:
                        System.out.println("Balance: ₹" + loggedIn.getBalance());
                        break;

                    case 4:
                        scanner.nextLine();
                        System.out.print("Receiver Account Number: ");
                        String receiver = scanner.nextLine();
                        System.out.print("Amount: ");
                        double amount = scanner.nextDouble();
                        bankService.transfer(loggedIn.getAccountNumber(), receiver, amount);
                        System.out.println("Transfer successful!");
                        break;

                    case 5:
                        loggedIn.getTransactions().forEach(System.out::println);
                        break;

                    case 6:
                        System.out.println("Goodbye!");
                        break;

                    default:
                        System.out.println("Invalid choice.");
                }

            } while (choice != 6);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        scanner.close();
    }
}
