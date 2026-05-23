package com.Iankyo.cli;

import com.Iankyo.model.BankAccount;
import com.Iankyo.model.SavingsAccount;
import com.Iankyo.service.BankService;

import java.util.Scanner;

public class BankCLI {
    private Scanner scanner;
    private BankService bankService;

    public BankCLI(BankService bankService) {
        this.bankService = bankService;
        this.scanner = new Scanner(System.in);
    }

    public void start(){
        while (true){
            printMenu();
            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option){
                case 1: createAccount(); break;
                case 2: deposit(); break;
                case 3: withdraw(); break;
                case 4: transfer(); break;
                case 5: listAllAccounts(); break;
                case 6: totalBalance(); break;
                case 7: richestAccount(); break;
                case 8: applyInterest(); break;
                case 0: return;
            }
        }
    }

    public void printMenu(){
        System.out.println("=== CLI Finance ===");
        System.out.println("1. Create account");
        System.out.println("2. Deposit");
        System.out.println("3. Withdraw");
        System.out.println("4. Transfer");
        System.out.println("5. List accounts");
        System.out.println("6. Total balance");
        System.out.println("7. Richest account");
        System.out.println("8. Apply interest");
        System.out.println("0. Exit");
        System.out.println("===================");
    }

    public void createAccount() {
        try {
            System.out.println("== CREATE ACCOUNT ==");
            System.out.println("Hey! what is your name?");
            String name = scanner.nextLine();
            System.out.println("Choice you account type:");
            System.out.println("1. Checking");
            System.out.println("2. Savings");
            int option = scanner.nextInt();
            scanner.nextLine();

            if (name.isBlank()) {
                throw new IllegalArgumentException("Name cannot be empty.");
            }

            if (option < 1 || option > 2) {
                throw new IllegalArgumentException("Invalid option");
            }

            switch (option) {
                case 1:
                    bankService.createAccount(name, "checking");
                    break;
                case 2:
                    bankService.createAccount(name, "savings");
                    break;
            }

            System.out.println("Account created successfully!");
            bankService.lastAccountCreated()
                    .ifPresent(a -> System.out.println("Your account number is: " + a.getAccountNumber()));

        } catch (IllegalArgumentException e){
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e){
            System.out.println("Unexpected error: " + e.getMessage());
        }
    }

    public void deposit(){
        try {
            System.out.println("==== DEPOSIT ====");
            System.out.println("What is your account number?");
            int accountNumber = scanner.nextInt();
            scanner.nextLine();
            System.out.println("How much do you want to deposit?");
            double amount = scanner.nextDouble();
            scanner.nextLine();

            bankService.deposit(amount, accountNumber);
            System.out.println("R$" + amount + "has deposited successfully");

        } catch (IllegalArgumentException e){
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e){
            System.out.println("Unexpected error: " + e.getMessage());
        }
    }

    public void withdraw(){
        try {
            System.out.println("==== WITHDRAW ====");
            System.out.println("What is you account number?");
            int accountNumber = scanner.nextInt();
            scanner.nextLine();
            System.out.println("How much do you want to withdraw?");
            double amount = scanner.nextDouble();
            scanner.nextLine();

            bankService.withdraw(amount, accountNumber);
            System.out.println("Successfully!");
        } catch (IllegalArgumentException e){
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e){
            System.out.println("Unexpected error: " + e.getMessage());
        }
    }

    public void transfer(){
        try {
            System.out.println("==== TRANSFER ====");
            System.out.println("What is your account number?");
            int from = scanner.nextInt();
            scanner.nextLine();
            System.out.println("What is number of account do you want to transfer to?");
            bankService.listAllAccounts();
            int to = scanner.nextInt();
            scanner.nextLine();
            System.out.println("How much do you transfer?");
            double amount = scanner.nextDouble();
            scanner.nextLine();

            bankService.transfer(from, to, amount);

            System.out.println("R$ " + amount + "has been transferred");
        } catch (IllegalArgumentException e){
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e){
            System.out.println("Unexpected error: " + e.getMessage());
        }
    }

    public void listAllAccounts(){
        System.out.println("=== LIST ACCOUNTS ===");
        bankService.listAllAccounts();
    }

    public void totalBalance(){
        System.out.println("=== TOTAL BALANCE ===");
        System.out.println("R$ " + bankService.totalBalance());
    }

    public void richestAccount(){
        System.out.println("=== RICHEST ACCOUNT ===");
        bankService.richestAccount()
                .ifPresent(a -> System.out.println(a));
    }

    public void applyInterest(){
        try {
            System.out.println("=== APPLY INTEREST ===");
            System.out.println("What is your account number?");
            int accountNumber = scanner.nextInt();
            scanner.nextLine();

            BankAccount currentAccount = bankService.findAccount(accountNumber);

            if (currentAccount instanceof SavingsAccount) {
                SavingsAccount savings = (SavingsAccount) currentAccount;
                savings.applyInterest();
            } else {
                System.out.println("This account is not a savings account");
            }

        } catch (IllegalArgumentException e){
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e){
            System.out.println("Unexpected error: " + e.getMessage());
        }

    }

}
