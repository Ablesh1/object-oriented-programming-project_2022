package com.example.po.backends;

public class AccountsDep extends Department{

    public AccountsDep(){
        super();
        //System.out.println("Bruuuh");
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
