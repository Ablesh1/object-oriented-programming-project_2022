package com.example.po;
import javafx.scene.chart.PieChart;

import java.util.HashMap;
import java.util.Map;


//This class is about keeping database of clients
public class BankBackend {

    //We can use HashMap to catalogue NPCs
    //This way it will work faster
    //We can assume that the player will be the first client
    private HashMap<Long, NPC> Database;

    public BankBackend setBankBackend(){
        return this;
    }


    public BankBackend(){
        this.Database = new HashMap<Long, NPC>();
    }

    //Dodawanie i usuwanie NPC
    public void addClient(NPC npc){
        Database.put(npc.getId(), npc);
    }

    public void removeCliend(Integer id){
        Database.remove(id);
    }
}
