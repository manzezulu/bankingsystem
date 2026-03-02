package com.banksystem;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    private static final AccountManager manager = new AccountManager();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════╗");
        System.out.println("║     Welcome to ManziBankZ    ║");
        System.out.println("╚══════════════════════════════╝");

        boolean running = true;
        while (running) {
            printMenu();
            int choice = readInt("Choose an Option");
            System.out.println();

            try {
                switch (choice) {
                    case 1 -> createAccount();
                    case 2 -> deposit();
                    case 3 -> withdraw();
                    case 4 -> transfer();
                    case 5 -> checkBalance();
                    case 6 -> manager.printAllAccounts();
                    case 7 -> manager.printTransactionHistory();
                    case 8 -> {
                        manager.saveAccounts();
                        System.out.println("Goodbye");
                        running = false;
                    }
                    default -> System.out.println("Invalid Option. Please choose 1-8");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
            System.out.println();
            
        }
        scanner.close();
    }

        //----Menu Actions
    private static void createAccount() {
        System.out.println("Account type: 1) Savings 2) current");
        int type = readInt("Select: ");
        String name = readString("Account holder name: ");
        double deposit = readDouble("Initial deposit amount: R");

        if (type == 1) {
            double rate = readDouble("Interest rate (R per year): ");
            manager.createSavingsAccount(name, deposit, rate);
        } else if (type == 2) {
            double overdraft = readDouble("Overdraft limit: R");
            manager.createCurrentAccount(name, deposit, overdraft);
        } else {
            System.out.println("Invalid account type.");
        }
    }
    
    private static void deposit() {
        String number = readString("Account number: ");
        double amount = readDouble("Deposit amount: R");
        manager.deposit(number, amount);
    }
       private static void withdraw() {
        String number = readString("Account number: ");
        double amount = readDouble("Withdrawal amount: R");
        manager.withdraw(number, amount);
    }


    private static void transfer() {
        String from = readString("Source Account number: ");
        String to = readString("Destination account number: ");
        double amount = readDouble("Transfer amount: R");
        manager.transfer(from, to, amount);
    }

    private static void checkBalance() {
        String number = readString("Account number: ");
        manager.checkBalance(number);
    }

// ------helper functions
    private static void printMenu() {
        System.out.println("─".repeat(35));
        System.out.println("  1. Create Account");
        System.out.println("  2. Deposit Funds");
        System.out.println("  3. Withdraw Funds");
        System.out.println("  4. Transfer Funds");
        System.out.println("  5. Check Balance");
        System.out.println("  6. List All Accounts");
        System.out.println("  7. Transaction History");
        System.out.println("  8. Save & Exit");
        System.out.println("─".repeat(35));
    }

    private static int readInt(String prompt){
        while(true) {
            System.out.println(prompt);
            try{
                int value = scanner.nextInt();
                scanner.nextLine();
                return value;
            } catch (InputMismatchException e){
                scanner.nextLine();
                System.out.println("Please enter a valid integer.");
            }
        }
    }
        private static double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                double value = scanner.nextDouble();
                scanner.nextLine();
                return value;
            } catch (InputMismatchException e) {
                scanner.nextLine();
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private static String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
}