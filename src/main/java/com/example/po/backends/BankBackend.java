package com.example.po.backends;

import com.example.po.NPChandling.NPC;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

//This class is about keeping database of clients
public class BankBackend implements Serializable{

    private static CurrencyRateDep currencyRate;
    private static StockRateDep stockRate;
    private static ReportsDep reportsDep;
    private static TransfersDep transfersDep;
    private static Writer writter = new Writer();

    private static int location;

    //We can use HashMap to catalogue NPCs
    //This way it will work faster
    //We can assume that the player will be the first client
    private HashMap<Integer, NPC> database;

    private Integer thePoorOne;
    private Integer randomClient;

    public BankBackend(){
        currencyRate = new CurrencyRateDep();
        stockRate    = new StockRateDep();
        reportsDep   = new ReportsDep(this);
        transfersDep = new TransfersDep(this);

        this.database = new HashMap<Integer, NPC>();
        this.randomClient = 2;
        this.thePoorOne = 2;

        addClient(new NPC(1, "Donald", "Trump", 2137213721, 1000.0,1000.0, 10,true,0.0, 0, this, 10000.0, "Character", 2137.2137));

        //These clients' data is saved in Client.dat
        /*addClient(new NPC(2, "Jurij", "Owsienko",797404004,2141.0,  0.0,0, true, 0.0,0,this,500.07,"Charitable",9000.0));
        addClient(new NPC(3, "Kotori",  "Itsuka",854627322,1421.0,  0.0,0, true, 0.0,0,this,305.85,"Normal",    3500.0));
        addClient(new NPC(4, "Kurumi","Tokisaki",364521527,2135.0,  0.0,0, true, 0.0,0,this,246.45,"Normal",    1860.0));

        addClient(new NPC(5, "Shinobu", "Oshino",124512389,9560.0,0.0,    0, true, 0.0,0,this,121.0, "可愛い",2000.0));
        addClient(new NPC(6, "Elon",    "Musk",  987654321,9000.0,0.0,    0, true, 0.0,0,this,1000.0,"Lucky",-1000.0));
        addClient(new NPC(7, "John",    "Debtor",567356852,1000.0,5000.0,20,true,0.0,0,this,10.0,  "Madao",1000.0));
        addClient(new NPC(8, "Hasegawa","Taizou",658123567,1000.0,5000.0,20,true,0.0,0,this,2.99,  "Madao",1000.0));
        addClient(new NPC(9,"Test", "Kaminari",  36452152, 1400.0,0.0,    0, true, 0.0,0,this,640.0, "Test", 400.0));

        addClient(new NPC(10, "King of Rohan", "Theoden",666666666,66666.6,0.0,0,true,0.0,0,this,6666.6,"Evil",100000.0));

        saver(database);
        */
        loader();
    }

    public static ReportsDep getReportsDep() {
        return reportsDep;
    }

    public static TransfersDep getTransfersDep() {
        return transfersDep;
    }

    public CurrencyRateDep getCurrencyRate() {
        return currencyRate;
    }

    public StockRateDep getStockRate() {
        return stockRate;
    }

    public void setDatabase(HashMap<Integer, NPC> database) {
        this.database = database;
    }

    public HashMap<Integer, NPC> getDatabase() {
        return database;
    }

    //Adding or removing NPC
    public void saver(HashMap<Integer, NPC> database){

        try (FileOutputStream fos = new FileOutputStream("Client.dat");
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {

                ArrayList<NPC> saviour = new ArrayList<>();
                database.forEach((k, v) -> {saviour.add(v); });
                saviour.remove(0);

                // Write list of objects to file
                oos.writeObject(saviour);
                oos.close();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    // First two lines read the values from the file and create a list of NPCs
    // Then each NPC is added to the bank, and we aggregate the bank to NPCs
    // Then the thread is started
    public void loader(){
        try {
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("Client.dat"));
            ArrayList<NPC> retriever = (ArrayList<NPC>) inputStream.readObject();
            for(int i = 0; i < retriever.size(); i++){
                this.addClient(retriever.get(i));
                retriever.get(i).setBankBackend(this);
                retriever.get(i).start();
            }
        } catch ( IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void addClient(NPC npc){
        database.put(database.size() + 1, npc);
    }

    public void removeClient(Integer personID){
        database.remove(personID);
        HashMap<Integer, NPC> replacer = new HashMap<>();

        // Update the database loop
        // From Player to personID
        for(int j = 1; j < personID; j ++){
            replacer.put(j, database.get(j));
        }

        for(int i = personID + 1; i <= database.size() + 1; i++){
            replacer.put(i - 1, database.get(i));
            database.get(i).setPersonID(i - 1);
        }
        setDatabase(replacer);
    }

    public NPC getClient(Integer personID){
        return database.get(personID);
    };

    public Integer getRandomPerson(){
        Thread randomThread = new Thread(new Runnable() {
            @Override
            public void run() {
                Random random = new Random();
                Set<Integer> randomClients = database.keySet();
                Integer helper = random.nextInt(1, randomClients.size());
                NPC chosencClient = database.get(helper);
                //System.out.println(helper);
                randomClient = chosencClient.getPersonID();
                return;
            }
        });
        randomThread.start();
        return randomClient;
    }

    // It must be done on a separate thread
    // Otherwise might cause bottlenecks
    public void transferMoney(Integer from, Integer to, double howMuchFrom){
        NPC giver = getClient(from);
        NPC receiver = getClient(to);
        ArrayList<Integer> overseer = new ArrayList<Integer>();

        // 0 means that we have not received money
        // 1 means that transaction is ready from one side
        overseer.add(0);
        overseer.add(0);

        // Cancel transfer in mild case of death
        if(overseer.get(0) == 0 && giver != null && receiver != null){

            //First step - gather the right amount of money
            double containerFrom = howMuchFrom;
            overseer.set(0, 1);

            // Turn out you have to check it once
            // If is null will go to else apparently
            if(overseer.get(1) == 0){
                // Second step - take the money from giver
                giver.withdraw(howMuchFrom);
                overseer.set(1, 1);

                if(overseer.get(0) == 1 && overseer.get(1) == 1){
                    // Third step - give the money to receiver
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                    String finalString = LocalTime.now().format(formatter) + "\t\tFrom\t" + from + " \tto\t " + to + "\t" + howMuchFrom;

                    try {
                        writter.writeTransfers(finalString);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return;
                }
                else{
                    System.out.println("Transfer cancelled due to unexpected case of death");
                    // We have to undo the previous move if something happens
                    giver.deposit(howMuchFrom);
                    return;
                }
            }
            else{
                System.out.println("Transfer cancelled due to urgent will to die");
                // Here nothing happened yet thankfully
                return;
            }
        }
    }

    // I have no heart to try if this abomination works
    // I lied. It actually works pretty well
    public Integer askForPoor(){
        Thread poorThread = new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap<Double, Integer> poorClients = new HashMap();

                database.forEach((k,v) -> poorClients.put(v.getAccountMoney(), k));

                Double min = Collections.min(poorClients.keySet());
                Integer minId = poorClients.get(min);
                NPC thePoorOneNpc = database.get(minId);
                thePoorOne = thePoorOneNpc.getPersonID();

                if(thePoorOne == null){
                    //Warning - potential money laundering
                    return;
                }
            }
        });
        poorThread.start();
        return this.thePoorOne;
    }

    // It`s effectively a getter just for one function only
    public Integer tellThePoor(Integer key){
        return key;
    }
}
