package com.Iankyo.service;

import com.Iankyo.exception.AccountNotFoundException;
import com.Iankyo.model.BankAccount;
import com.Iankyo.model.SavingsAccount;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class BankService {
    List<BankAccount> bank = new ArrayList<>();
    private int accountNumber;

    public void createAccount(String holder, String type, double interestRate) {

        if (holder == null || holder.isBlank()) throw  new IllegalArgumentException("Holder name cannot be empty");

        if (type.equals("savings")) {
            SavingsAccount new_account = new SavingsAccount(accountNumber, holder, interestRate);
            bank.add(new_account);
            accountNumber += 1;
        } else if (type.equals("checking")) {
            BankAccount new_account = new BankAccount(accountNumber, holder);
            bank.add(new_account);
            accountNumber += 1;
        } else {
            throw new IllegalArgumentException("Invalid account type: " + type);
        }
    }

    public BankAccount findAccount(int accountNumber) {
        return bank.stream()
                .filter(a -> a.getAccountNumber() == accountNumber)
                .findFirst()
                .orElseThrow(() -> new AccountNotFoundException());
    }

    public void deposit(double amount, int accountNumber) {
        if (amount <= 0) throw  new IllegalArgumentException("Amount must be positive");
        findAccount(accountNumber).deposit(amount);
    }

    public void withdraw(double amount, int accountNumber) {
        findAccount(accountNumber).withdraw(amount);
    }

    public void transfer(int from, int to, double amount){
        withdraw(amount, from);
        deposit(amount, to);
    }

    public void listAllAccounts(){
        bank.stream()
                .forEach(System.out::println);
    }

    public double totalBalance(){
        return bank.stream()
                .mapToDouble(BankAccount::getBalance)
                .sum();
    }

    public Optional<BankAccount> richestAccount(){
        return  bank.stream()
                .max(Comparator.comparingDouble(BankAccount::getBalance));
    }

}
