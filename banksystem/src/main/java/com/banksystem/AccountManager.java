package com.banksystem;

import java.io.*;
import java.util.*;

public class AccountManager {
    private final Map<String, BankAccount> accounts = new LinkedHashMap<>();
    private final List<Transaction> transactionHistory = new ArrayList<>();
    private static final String DATA_FILE = "data/accounts.txt";
    private int accountCounter = 1000;

    public AccountManager() {
        loadAccounts();
    }

    // ── Account creation ────────────────────────────────────────────────────────

    public SavingsAccount createSavingsAccount(String holderName, double initialDeposit,
                                               double interestRate) {
        String number = generateAccountNumber();
        SavingsAccount account = new SavingsAccount(number, holderName, initialDeposit, interestRate);
        accounts.put(number, account);
        System.out.printf("Savings account created. Account number: %s%n", number);
        return account;
    }

    public CurrentAccount createCurrentAccount(String holderName, double initialDeposit,
                                               double overdraftLimit) {
        String number = generateAccountNumber();
        CurrentAccount account = new CurrentAccount(number, holderName, initialDeposit, overdraftLimit);
        accounts.put(number, account);
        System.out.printf("Current account created. Account number: %s%n", number);
        return account;
    }

    private String generateAccountNumber() {
        return "ACC" + (++accountCounter);
    }

    // ── Core operations ─────────────────────────────────────────────────────────

    public void deposit(String accountNumber, double amount) {
        BankAccount account = getAccount(accountNumber);
        account.deposit(amount);
        transactionHistory.add(new Transaction(
                Transaction.Type.DEPOSIT, accountNumber, amount, "Deposit"));
    }

    public void withdraw(String accountNumber, double amount) {
        BankAccount account = getAccount(accountNumber);
        account.withdraw(amount);
        transactionHistory.add(new Transaction(
                Transaction.Type.WITHDRAWAL, accountNumber, amount, "Withdrawal"));
    }

    public void transfer(String fromNumber, String toNumber, double amount) {
        BankAccount from = getAccount(fromNumber);
        BankAccount to   = getAccount(toNumber);
        from.withdraw(amount);
        to.deposit(amount);
        transactionHistory.add(new Transaction(
                Transaction.Type.TRANSFER, fromNumber, amount,
                "Transfer to " + toNumber));
        transactionHistory.add(new Transaction(
                Transaction.Type.TRANSFER, toNumber, amount,
                "Transfer from " + fromNumber));
        System.out.printf("Transferred $%.2f from %s to %s%n", amount, fromNumber, toNumber);
    }

    public void checkBalance(String accountNumber) {
        BankAccount account = getAccount(accountNumber);
        account.checkBalance();
    }

    public void printAllAccounts() {
        if (accounts.isEmpty()) {
            System.out.println("No accounts found.");
            return;
        }
        System.out.println("─".repeat(55));
        accounts.values().forEach(account -> account.checkBalance());
        System.out.println("─".repeat(55));
    }

    public void printTransactionHistory() {
        if (transactionHistory.isEmpty()) {
            System.out.println("No transactions recorded.");
            return;
        }
        transactionHistory.forEach(System.out::println);
    }

    // ── File persistence ─────────────────────────────────────────────────────────

    public void saveAccounts() {
        File dir = new File("data");
        dir.mkdirs();
        try (PrintWriter writer = new PrintWriter(new FileWriter(DATA_FILE))) {
            for (BankAccount account : accounts.values()) {
                writer.println(account.toString());
            }
            System.out.println("Accounts saved successfully.");
        } catch (IOException e) {
            System.err.println("Error saving accounts: " + e.getMessage());
        }
    }

    private void loadAccounts() {
        File file = new File(DATA_FILE);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length < 4) continue;

                String number  = parts[0];
                String holder  = parts[1];
                double balance = Double.parseDouble(parts[2]);
                String type    = parts[3];

                BankAccount account;
                if ("Savings".equalsIgnoreCase(type)) {
                    double rate = parts.length > 4 ? Double.parseDouble(parts[4]) : 0.0;
                    account = new SavingsAccount(number, holder, balance, rate);
                } else {
                    double overdraft = parts.length > 4 ? Double.parseDouble(parts[4]) : 0.0;
                    account = new CurrentAccount(number, holder, balance, overdraft);
                }
                accounts.put(number, account);

                // Keep counter ahead of loaded account numbers
                try {
                    int num = Integer.parseInt(number.replace("ACC", ""));
                    if (num >= accountCounter) accountCounter = num;
                } catch (NumberFormatException ignored) {}
            }
            System.out.println("Accounts loaded from file.");
        } catch (IOException e) {
            System.err.println("Error loading accounts: " + e.getMessage());
        }
    }

    // ── Helpers ──────────────────────────────────────────────────────────────────

    private BankAccount getAccount(String accountNumber) {
        BankAccount account = accounts.get(accountNumber);
        if (account == null) {
            throw new NoSuchElementException("Account not found: " + accountNumber);
        }
        return account;
    }
}
