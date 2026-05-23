package com.Iankyo;

import com.Iankyo.cli.BankCLI;
import com.Iankyo.service.BankService;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        BankService bankService = new BankService();
        BankCLI cli = new BankCLI(bankService);
        cli.start();
    }
}