package com.example.po;
import java.util.HashMap;


//This class is about keeping database of clients
public class BankBackend {

    //We can use HashMap to catalogue NPCs
    //This way it will work faster
    //We can assume that the player will be the first client
    private HashMap<Integer, NPC> Database;

    public BankBackend setBankBackend(){
        return this;
    }


    public BankBackend(){
        this.Database = new HashMap<Integer, NPC>();
    }

}
