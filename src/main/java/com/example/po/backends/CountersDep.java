package com.example.po.backends;
import com.example.po.Department;

import java.io.Serializable;

public class CountersDep extends Department{

    public CountersDep() {
        super();
        //System.out.println("Bruuuh");
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
