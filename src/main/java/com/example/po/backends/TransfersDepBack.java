package com.example.po.backends;
import com.example.po.Department;

import java.io.Serializable;

public class TransfersDepBack extends Department{

    public TransfersDepBack() {
        super();
        //System.out.println("Bruuuh");
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
