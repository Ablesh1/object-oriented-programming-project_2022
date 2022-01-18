package com.example.po;

import java.util.HashMap;


//This class is about keeping database of clients
public class BankBackend {

    private CurrencyRateDepBack currencyRate;
    private StockRateDepBack stockRate;
    private ReportsDepBack reportsDep;
    private TransfersDepBack transfersDep;

    public CurrencyRateDepBack getCurrencyRate() {
        return currencyRate;
    }

    public StockRateDepBack getStockRate() {
        return stockRate;
    }

    //We can use HashMap to catalogue NPCs
    //This way it will work faster
    //We can assume that the player will be the first client
    private HashMap<Integer, NPC> database;

    public BankBackend setBankBackend(){
        return this;
    }


    public BankBackend(){
        currencyRate = new CurrencyRateDepBack();
        stockRate = new StockRateDepBack();
        reportsDep = new ReportsDepBack();
        transfersDep = new TransfersDepBack();
        this.database = new HashMap<Integer, NPC>();
        //NPC(Integer idNumber, String name, String surname, Integer pesel, double Debit)
        addClient(new NPC(1, "Anon", "Anonimowy", 2137213721, 2137, this, 2137));
    }

    //Dodawanie i usuwanie NPC
    public void addClient(NPC npc){
        database.put(npc.getPersId(), npc);
    }

    public void removeCliend(Integer persid){
        database.remove(persid);
    }

    public NPC getClient(Integer persid){
        return database.get(persid);
    }
}
