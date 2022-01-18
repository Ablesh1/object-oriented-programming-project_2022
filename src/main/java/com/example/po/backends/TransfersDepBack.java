package com.example.po.backends;

import com.example.po.Department;

public class TransfersDepBack extends Department {

    public TransfersDepBack() {
        super();
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
