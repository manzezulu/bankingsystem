package com.banksystem;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {
    public enum Type {
        DEPOSIT, WITHDRAWAL, TRANSFER
    }

    private final Type type;
    private final String accountNumber;
    private final double amount;
    private final String description;
    private final LocalDateTime timestamp;

    public Transaction(Type type, String accountNumber, double amount, String description) {
        this.type = type;
        this.accountNumber = accountNumber;
        this.amount = amount;
        this.description = description;
        this.timestamp = LocalDateTime.now();
    }

    public Type getType() {
        return type;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return String.format("[%s] %s | Account: %s | Amount: $%.2f | %s",
                timestamp.format(formatter), type, accountNumber, amount, description);
    }
}
