package com.banksystem;

public abstract class BankAccount {
    protected String accountNumber;
    protected String accountHolderName;
    protected double balance;

    public BankAccount(String accountNumber, String accountHolderName, double initialBalance) {
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.balance = initialBalance;
    }

    public String getAccountNumber(){
        return accountNumber;
    }
    
    public String getAccountHolderName() {
        return accountHolderName;
    }

    public double getBalance(){
        return balance;
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive");
        } else {
            balance += amount;
            System.out.printf("Deposited R%.2f. New balance: R%.2f%n", amount, balance);
        }
    }

    public void withdraw(double amount) {
        if(amount <= 0){
            throw new IllegalArgumentException("Withdrawal amount must be positive.");
        } else if (amount > balance){
            throw new IllegalStateException("Insufficient funds.");
        } else {
            balance -= amount;
            System.out.printf("Withdrew R%.2f. New balance: R%.2f%n", amount, balance);
        }
    }

    public void checkBalance(){
        System.out.printf("Account [%s] - %s | Balance: R%.2f%n", accountNumber, accountHolderName, balance);
    }

    public abstract String getAccountType();

    @Override
    public String toString() {
        return accountNumber + "," + accountHolderName + "," + balance + "," + getAccountType();
    }
}
