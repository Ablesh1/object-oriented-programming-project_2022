package com.example.po;

public class AccountsDepBack extends Department{

    public AccountsDepBack(){
        super();
        System.out.println("Bruuuh");
    }

    @Override
    public void refresh(){
        try{
            //System.out.println("Accounts do calculations");
            Thread.sleep(2600);
        } catch (InterruptedException interruptedException) {
            return;
        }
    }
}
