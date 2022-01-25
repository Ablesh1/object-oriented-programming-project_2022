package com.example.po.backends;

import java.util.ArrayList;

public class ReportsDep extends Department{

    private BankBackend bankBackend;
    private Writer writer;

    public ReportsDep(BankBackend bankBackend){
        super(bankBackend);
        this.writer = new Writer();
    }

    public ArrayList<String> showLastDeposits(){
        return writer.readLastDeposits();
    }

    public ArrayList<String> showLastWithdraws(){
        return writer.readLastWithdraws();
    }

    @Override
    public void refresh(){
        try{
            //System.out.println("Reports do calculations");
            Thread.sleep(2600);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
            return;
        }
    }
}
