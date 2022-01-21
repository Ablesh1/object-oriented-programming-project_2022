package com.example.po.backends;

import com.example.po.Department;

public class ReportsDep extends Department{

    public ReportsDep(){
        super();
        //System.out.println("Bruuuh");
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
