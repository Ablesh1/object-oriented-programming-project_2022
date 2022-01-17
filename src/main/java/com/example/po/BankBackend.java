package com.example.po;

import java.util.HashMap;


//This class is about keeping database of clients
public class BankBackend {

    CurrencyRateDep currencyRate;
    StockRateDep stockRate;
    ReportsDep reportsDep;
    TransfersDep transfersDep;

    //We can use HashMap to catalogue NPCs
    //This way it will work faster
    //We can assume that the player will be the first client
    private HashMap<Long, NPC> database;

    public BankBackend setBankBackend(){
        return this;
    }


    public BankBackend(){
        currencyRate = new CurrencyRateDep();
        stockRate = new StockRateDep();
        reportsDep = new ReportsDep();
        transfersDep = new TransfersDep();
        this.database = new HashMap<Long, NPC>();
        //NPC(Integer idNumber, String name, String surname, Integer pesel, double Debit)
        addClient(new NPC(0001, "Anon", "Anonimowy", 2137213721, 2137, this));
    }

    //Dodawanie i usuwanie NPC
    public void addClient(NPC npc){
        database.put(npc.getId(), npc);
    }

    public void removeCliend(Integer id){
        database.remove(id);
    }

    public NPC getClient(Long id){
        return database.get(id);
    }
}
