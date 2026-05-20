package com.Iankyo.model;

public class SavingsAccount extends BankAccount{
    private double interestRate;
    public SavingsAccount(int accountNumber, String accountHolder, double interestRate){
        super(accountNumber, accountHolder);
        this.interestRate = interestRate;
    }

    public void applyInterest(){
        double rate = getBalance() * interestRate;
        setBalance(getBalance() + rate);
    }



}
