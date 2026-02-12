package model;

import java.time.LocalDateTime;

public class Transaction {

    private TransactionType type;
    private double amount;
    private String note;
    private LocalDateTime timestamp;

    public Transaction(TransactionType type, double amount, String note) {
        this.type = type;
        this.amount = amount;
        this.note = note;
        this.timestamp = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return type + " | ₹" + amount + " | " + note + " | " + timestamp;
    }
}
