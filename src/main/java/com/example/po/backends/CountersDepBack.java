package com.example.po.backends;

import com.example.po.Department;

//extends Department
public class CountersDepBack extends Department {


    public CountersDepBack() {
        super();
    }

    @Override
    public void refresh(){
        try{
            //System.out.println("Counters do calculations");
            Thread.sleep(2600);
        } catch (InterruptedException interruptedException) {
            return;
        }
    }
}
