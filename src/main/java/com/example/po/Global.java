package com.example.po;

//Here be THE BANK
//We want THE ONE AND ONLY BANK
//WE CANNOT ALLOW FOR IT TO BE MULTIPLE BANKS
//This is the least convoluted solution to pass bank (as global value)
//Into scene controllers
//Without creating one
public class Global {
    public static BankBackend bankBackend;

    public Global(BankBackend bankBackend){
        this.bankBackend = bankBackend;
    }
}
