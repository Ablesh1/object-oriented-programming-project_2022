package com.example.po.backends;
import com.example.po.NPChandling.NPC;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

//This class is about keeping database of clients
public class BankBackend {

    private CurrencyRateDepBack currencyRate;
    private StockRateDepBack stockRate;
    private ReportsDepBack reportsDep;
    private TransfersDepBack transfersDep;


    //We can use HashMap to catalogue NPCs
    //This way it will work fasters
    //We can assume that the player will be the first client
    private HashMap<Integer, NPC> database;

    //I hate it. I really do.
    private Integer thePoorOne;

    public BankBackend(){
        currencyRate = new CurrencyRateDepBack();
        stockRate = new StockRateDepBack();
        reportsDep = new ReportsDepBack();
        transfersDep = new TransfersDepBack();
        this.database = new HashMap<Integer, NPC>();
        //NPC(Integer idNumber, String name, String surname, Integer pesel, double Debit)
        addClient(new NPC(1, "Morshu", "Easter Egg", 2137213721, 100000.0,0.0, 0,0.0, 0, this, 10000.0, "charitable", 213777));
        addClient(new NPC(2, "Anon", "Anonimowy", 2137213721, 1000.0,0.0, 0,0.0, 0, this, 10000.0, "character", 213777));

    }

    //Dodawanie i usuwanie NPC
    public void addClient(NPC npc){
        database.put(npc.getPersonID(), npc);
    }

    public void removeClient(Integer personID){
        database.remove(personID);
    }

    //It must do on a separate thread
    //Otherwise might cause bottlenecks
    public void transferMoney(Integer from, Integer to, double howMuchFrom){
        Thread transferThread = new Thread(new Runnable() {
            @Override
            public void run() {
                NPC giver = getClient(from);
                NPC reciever = getClient(to);
                ArrayList<Integer> overseer = new ArrayList<Integer>();

                //0 means that we have not received money
                //1 means that transaction is ready from one side
                overseer.add(0);
                overseer.add(0);

                if(overseer.get(0) == 0){

                    //First step - gather the right amount of money
                    double containerFrom = howMuchFrom;
                    overseer.set(0, 1);

                    if(overseer.get(1) == 0){
                        //Second step - take the money from giver
                        giver.withdraw(howMuchFrom);
                        overseer.set(1, 1);

                        if(overseer.get(0) == 1 && overseer.get(1) == 1){
                            //Third step - give the money to reciever
                            reciever.deposit(howMuchFrom);
                            return;
                        }
                        else{
                            //We have to undo the previous move if something happens
                            giver.deposit(howMuchFrom);
                            return;
                        }
                    }

                    else{
                        //Here nothing happened yet thankfully
                        return;
                    }
                }
            }
        });

    }

    public void investMoney(){}


    public NPC getClient(Integer personID){
        return database.get(personID);
    };


    //I habe no heart to try if this abomination works
    //I lied. It actually works pretty well
    public Integer askforPoor(){
        Thread poorThread = new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap<Double, Integer> poorClients = new HashMap();

                //For lambdas there are some points too
                database.forEach((k,v) -> poorClients.put(v.getAccountMoneyAI(), k));
                //FOR COLLECTIONS TOO

                //Big brain time so large I can`t comperhend
                //Thanks Tzzentch
                Double min = Collections.min(poorClients.keySet());
                Integer minId = poorClients.get(min);
                NPC thePoorOneNpc = database.get(minId);
                thePoorOne = thePoorOneNpc.getPersonID();

                if(thePoorOne == null){
                    //Warning - potential money laundring
                    return;
                }
                }
        });
        poorThread.start();
        return this.thePoorOne;
    }

    //It`s effectively a getter just for one funciton only
    public Integer tellThePoor(Integer key){
        return key;
    }

    public CurrencyRateDepBack getCurrencyRate() {
        return currencyRate;
    }

    public StockRateDepBack getStockRate() {
        return stockRate;
    }
}
