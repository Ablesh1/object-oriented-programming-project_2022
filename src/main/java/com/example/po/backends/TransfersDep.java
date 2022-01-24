package com.example.po.backends;

import com.example.po.Department;
import java.util.ArrayList;

public class TransfersDep extends Department{

    private BankBackend bankBackend;
    private Writer writer;

    public TransfersDep(BankBackend bankBackend) {
        super(bankBackend);
        this.writer = new Writer();
    }

    public ArrayList<String> showLastTransfers(){
        return writer.readLastTransfers();
    }

    @Override
    public void refresh(){
        try{
            //System.out.println("Transfers do calculations");
            Thread.sleep(2600);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
            return;
        }
    }
}
