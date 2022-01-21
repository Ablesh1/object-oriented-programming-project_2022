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
        transfersDep = new TransfersDep();
        this.database = new HashMap<Integer, NPC>();
        this.randomClient = 2;
        this.thePoorOne = 2;

        //NPC(Integer idNumber, String name, String surname, Integer pesel, double Debit)

        addClient(new NPC(1, "Anon", "Anonimowy", 2137213721, 1000.0,0.0, 0,0.0, 0, this, 10000.0, "Character", 2137));

        //Ci klienci są już zapisani w Client.dat
        addClient(new NPC(2, "Jurij", "Owsienko", 797404004, 4141.0,0.0, 0,0.0, 0, this, 1200.0, "charitable", 2500));
        addClient(new NPC(3, "Morshu", "Easter Egg", 854627322, 1421.0,0.0, 0,0.0, 0, this, 600.99, "normie", 35000));
        addClient(new NPC(4, "Pan", "Alber", 36452152, 21352.0,0.0, 0,0.0, 0, this, 62.0, "normie", 18600));;

        addClient(new NPC(5, "Testificate", "Kaminari", 36452152, 1400.0,0.0, 0,0.0, 0, this, 640.0, "testificate", 25.0));

        saver(database);

        loader();



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


        } catch (IOException ex) {
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
            ArrayList<NPC> retriver = (ArrayList<NPC>) inputStream.readObject();
            for(int i = 0; i < retriver.size(); i++){
                this.addClient(retriver.get(i));
                retriver.get(i).setBankBackend(this);
                retriver.get(i).start();
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
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("Od " + from + " do " + to + " " + howMuchFrom);
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
        });
        transferThread.start();
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

    public CurrencyRateDep getCurrencyRate() {
        return currencyRate;
    }

    public StockRateDep getStockRate() {
        return stockRate;
    }
}
