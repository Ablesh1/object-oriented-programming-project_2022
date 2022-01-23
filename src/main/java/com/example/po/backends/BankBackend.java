package com.example.po.backends;

import com.example.po.NPChandling.NPC;
import java.io.*;
import java.util.*;

//This class is about keeping database of clients
public class BankBackend implements Serializable{

    private static CurrencyRateDep currencyRate;
    private static StockRateDep stockRate;
    private static ReportsDep reportsDep;
    private static TransfersDep transfersDep;
    private static Writer writter = new Writer();

    //We can use HashMap to catalogue NPCs
    //This way it will work fasters
    //We can assume that the player will be the first client
    private HashMap<Integer, NPC> database;

    //I hate it. I really do.
    private Integer thePoorOne;
    private Integer randomClient;

    public BankBackend(){
        currencyRate = new CurrencyRateDep();
        stockRate = new StockRateDep();
        reportsDep = new ReportsDep();
        transfersDep = new TransfersDep(this);
        this.database = new HashMap<Integer, NPC>();
        this.randomClient = 2;
        this.thePoorOne = 2;



        addClient(new NPC(1, "Karol", "WoiTiWa", 2137213721, 1000.0,1000.0, 10,true,0.0, 0, this, 10000.0, "Character", 2137.2137));

        //These clients' data is saved in Client.dat
        addClient(new NPC(2, "Jurij", "Owsienko",797404004,2141.0,  0.0,0, true, 0.0,0,this,500.07,"Charitable",9000.0));
        addClient(new NPC(3, "Zero",  "Two",     854627322,1421.0,  0.0,0, true, 0.0,0,this,305.85,"Normal",    3500.0));
        addClient(new NPC(4, "Kurumi","Tokisaki",364521527,2135.0,  0.0,0, true, 0.0,0,this,246.45,"Normal",    1860.0));

        addClient(new NPC(5, "Shinobu", "Oshino",124512389,9560.0,  0.0,0, true, 0.0,0,this,121.0, "可愛い", 2000.0));
        addClient(new NPC(6, "Elon",    "Musk",  987654321,9000.0,  0.0,0, true, 0.0,0,this,1000.0,"Lucky",-1000.0));
        addClient(new NPC(7, "John",    "Debtor",567356852,0.0,100000.0,10,true, 0.0,0,this,10.0,  "Madao",400.0));
        addClient(new NPC(8, "Hasegawa","Taizou",658123567,0.0,999999.0,5, false,0.0,0,this,2.99,  "Madao",100.0));
        addClient(new NPC(9,"Test", "Kaminari",  36452152, 1400.0,  0.0,0, true, 0.0,0,this,640.0, "Test", 400.0));

        addClient(new NPC(10, "King of Rohan", "Theoden",666666666,66666.6,0.0,0,true,0.0,0,this,6666.6,"Evil",100000.0));

        //saver(database);

        //loader();
    }

    public static TransfersDep getTransfersDep() {
        return transfersDep;
    }

//Dodawanie i usuwanie NPC

    public void saver(HashMap<Integer, NPC> database){

        try (FileOutputStream fos = new FileOutputStream("Client.dat");
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            ArrayList<NPC> saviour = new ArrayList<>();

            database.forEach((k, v) -> {saviour.add(v); });

            saviour.remove(0);

            // write list of objects to file
            oos.writeObject(saviour);
            oos.close();

        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    //I HATE THE ANTICHRIST
    //DON`T YOU EVER DARE TO LOOK HERE

    //Pierwsze dwie linijki zczytują wartości z pliku i tworzą listę NPCtów
    //Następnie każdy NPC jest dodawany do banku i agregujemy bank do NPC
    //Na zaś zostaje odpalony wątek
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
        /*
         ░░░░░░░░░░░░░░░░░░░░░▄▄▄▄▀▀▀▀▀▀▀▀▄▄▄▄▄▄░░░░░░░░
         ░░░░░░░░░░░░░░░░░░░░░█░░░░▒▒▒▒▒▒▒▒▒▒▒▒░░▀▀▄░░░░
         ░░░░░░░░░░░░░░░░░░░░█░░░▒▒▒▒▒▒░░░░░░░░▒▒▒░░█░░░
         ░░░░░░░░░░░░░░░░░░░█░░░░░░▄██▀▄▄░░░░░▄▄▄░░░░█░░
         ░░░░░░░░░░░░░░░░░▄▀▒▄▄▄▒░█▀▀▀▀▄▄█░░░██▄▄█░░░░█░
         ░░░░░░░░░░░░░░░░█░▒█▒▄░▀▄▄▄▀░░░░░░░░█░░░▒▒▒▒▒░█
         ░░░░░░░░░░░░░░░░█░▒█░█▀▄▄░░░░░█▀░░░░▀▄░░▄▀▀▀▄▒█
         ░░░░░░░░░░░░░░░░░█░▀▄░█▄░█▀▄▄░▀░▀▀░▄▄▀░░░░█░░█░
         ░░░░░░░░░░░░░░░░░░█░░░▀▄▀█▄▄░█▀▀▀▄▄▄▄▀▀█▀██░█░░
         ░░░░░░░░░░░░░░░░░░░█░░░░██░░▀█▄▄▄█▄▄█▄████░█░░░
         ░░░░░░░░░░░░░░░░░░░░█░░░░▀▀▄░█░░░█░█▀██████░█░░
         ░░░░░░░░░░░░░░░░░░░░░▀▄░░░░░▀▀▄▄▄█▄█▄█▄█▄▀░░█░░
        ░░░░░░░░░░░░░░░░░░░░░░░▀▄▄░▒▒▒▒░░░░░░░░░░▒░░░█░░
        ░I hate the Antichrist░░░▀▀▄▄░▒▒▒▒▒▒▒▒▒▒░░░░█░░░
        ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░▀▄▄▄▄▄░░░░░░░░█░░░░
        */

    }

    public void addClient(NPC npc){
        database.put(database.size() + 1, npc);
    }

    public void removeClient(Integer personID){
        database.remove(personID);
        HashMap<Integer, NPC> replacer = new HashMap<>();

        for(int j = 0; j < personID; j ++){
            replacer.put(j, database.get(j));
        }

        for(int i = personID; i < database.size(); i ++){
            database.get(i).setPersonID(database.get(i).getPersonID() - 1);
            replacer.put(database.get(i).getPersonID(), database.get(i));
        }
    }

    public HashMap<Integer, NPC> getDatabase() {
        return database;
    }

    //It must do on a separate thread
    //Otherwise might cause bottlenecks
    public void transferMoney(Integer from, Integer to, double howMuchFrom){
                NPC giver = getClient(from);
                NPC receiver = getClient(to);
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
                            //Third step - give the money to receiver
                            receiver.deposit(howMuchFrom);
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("\t\tFrom\t" + from + " \tto\t " + to + "\t" + howMuchFrom);
                            String finalString = stringBuilder.toString();

                            try {
                                writter.writeTransfers(finalString);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
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

    public Integer getRandomPerson(){
        Thread randomThread = new Thread(new Runnable() {
            @Override
            public void run() {
                Random random = new Random();
                Set<Integer> randomClients = database.keySet();
                Integer helper = random.nextInt(1, randomClients.size());
                //Intellij claims that collection method is SUS
                NPC chosencClient = database.get(helper);
                System.out.println(helper);
                //AMOGUS XD
                randomClient = chosencClient.getPersonID();
                return;
            }
        });
        randomThread.start();
        return randomClient;
    }

    public NPC getClient(Integer personID){
        return database.get(personID);
    };


    //I have no heart to try if this abomination works
    //I lied. It actually works pretty well
    public Integer askForPoor(){
        Thread poorThread = new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap<Double, Integer> poorClients = new HashMap();

                //For lambdas there are some points too
                database.forEach((k,v) -> poorClients.put(v.getAccountMoney(), k));
                //FOR COLLECTIONS TOO

                //Big brain time so large I can`t comprehend
                //Thanks Tzzentch
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

    //It`s effectively a getter just for one function only
    public Integer tellThePoor(Integer key){
        return key;
    }

    public CurrencyRateDep getCurrencyRate() {
        return currencyRate;
    }

    public StockRateDep getStockRate() {
        return stockRate;
    }
}
