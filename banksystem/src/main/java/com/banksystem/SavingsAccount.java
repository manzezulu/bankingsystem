package com.banksystem;

public class SavingsAccount extends BankAccount {
    private double interestRate;

    public SavingsAccount(String accountNumber, String accountHolderName, double initialBalance, double interestRate) {
        super(accountNumber, accountHolderName, initialBalance);
        this.interestRate = interestRate;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public void applyInterest() {
        double interest = balance * (interestRate / 100);
        balance += interest;
        System.out.printf("Interest of $%.2f applied at %.2f%%. New balance: $%.2f%n", interest, interestRate, balance);
    }

    @Override
    public String getAccountType() {
        return "Savings";
    }

    @Override
    public String toString() {
        return super.toString() + "," + interestRate;
    }
}
