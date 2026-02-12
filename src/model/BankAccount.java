package model;

import exception.AccountLockedException;
import exception.InsufficientFundsException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BankAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    private String accountNumber;
    private String accountHolder;
    private String pin;
    private double balance;
    private boolean isLocked;
    private int failedAttempts;
    private List<Transaction> transactions;

    public BankAccount(String accountHolder, String pin, double initialBalance) {
        this.accountNumber = UUID.randomUUID().toString().substring(0, 8);
        this.accountHolder = accountHolder;
        this.pin = pin;
        this.balance = initialBalance;
        this.transactions = new ArrayList<>();
        this.failedAttempts = 0;
        this.isLocked = false;
    }

    public boolean validatePin(String inputPin) throws AccountLockedException {
        if (isLocked) throw new AccountLockedException("Account is locked.");

        if (pin.equals(inputPin)) {
            failedAttempts = 0;
            return true;
        } else {
            failedAttempts++;
            if (failedAttempts >= 3) {
                isLocked = true;
                throw new AccountLockedException("Account locked after 3 failed attempts.");
            }
            throw new AccountLockedException("Invalid PIN. Attempts left: " + (3 - failedAttempts));
        }
    }

    public void deposit(double amount) {
        balance += amount;
        transactions.add(new Transaction(TransactionType.DEPOSIT, amount, "Self deposit"));
    }

    public void withdraw(double amount) throws InsufficientFundsException {
        if (amount > balance) throw new InsufficientFundsException("Insufficient funds.");
        balance -= amount;
        transactions.add(new Transaction(TransactionType.WITHDRAW, amount, "Self withdrawal"));
    }

    public void transferTo(BankAccount receiver, double amount) throws InsufficientFundsException {
        if (amount > balance) throw new InsufficientFundsException("Insufficient funds for transfer.");

        balance -= amount;
        transactions.add(new Transaction(TransactionType.TRANSFER_SENT, amount, "To: " + receiver.getAccountNumber()));

        receiver.balance += amount;
        receiver.transactions.add(new Transaction(TransactionType.TRANSFER_RECEIVED, amount, "From: " + this.accountNumber));
    }

    public String getAccountNumber() { return accountNumber; }
    public double getBalance() { return balance; }
    public List<Transaction> getTransactions() { return transactions; }
}
