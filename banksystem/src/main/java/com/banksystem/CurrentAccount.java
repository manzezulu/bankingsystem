package com.banksystem;

public class CurrentAccount extends BankAccount {
    private double overdraftLimit;

    public CurrentAccount(String accountNumber, String accountHolderName,
        double initialBalance, double overdraftLimit) {
        super(accountNumber, accountHolderName, initialBalance);
        this.overdraftLimit = overdraftLimit;
    }

    public double getOverdraftLimit() {
        return overdraftLimit;
    }

    public void setOverdraftLimit(double overdraftLimit) {
        this.overdraftLimit = overdraftLimit;
    }

    /**
     * Overrides withdraw to allow overdraft up to the overdraft limit.
     */
    @Override
    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive.");
        }
        if (amount > balance + overdraftLimit) {
            throw new IllegalStateException(
                    String.format("Exceeds overdraft limit. Available: $%.2f (including $%.2f overdraft).",
                            balance + overdraftLimit, overdraftLimit));
        }
        balance -= amount;
        System.out.printf("Withdrew $%.2f. New balance: $%.2f%n", amount, balance);
        if (balance < 0) {
            System.out.printf("Warning: Account is in overdraft by $%.2f%n", Math.abs(balance));
        }
    }

    @Override
    public String getAccountType() {
        return "Current";
    }

    @Override
    public String toString() {
        return super.toString() + "," + overdraftLimit;
    }
}
