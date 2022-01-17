package com.example.po;

//Here be general NPC and their behaviours
//We will put some special NPCs in different classes
public class NPC extends Thread{

    BankBackend bankBackend;

    private Integer persId;
    private String name;
    private String surname;
    private Integer pesel;
    private NPCaccount npcAccount;

    //Character describes how the NPC behaves
    private String character;
    private int iWantToDie;

    public Integer getPersId() {
        return persId;
    }

    public NPC(Integer idNumber, String name, String surname, Integer pesel, double debit, BankBackend bankBackend){
        this.persId = idNumber;
        this.name = name;
        this.surname = surname;
        this.pesel = pesel;
        this.bankBackend = bankBackend;
        this.npcAccount = new NPCaccount(this, debit, bankBackend);

        //Not yet
        this.iWantToDie = 0;
    }


    public void run(){
        while (iWantToDie != 1){
            //Do something
            try{
            Thread.sleep(3600);}
            catch(InterruptedException e){
                continue;
                }
        }

        this.Suicide();

    }

    //Suicide method kills the NPC
    //Yes, this is the first funciton I wrote
    //Yes, it`s only to show that we know how to do it
    public int Suicide(){
        System.out.println(this.persId + "FUCKING KILLED HIMSELF");
        this.interrupt();
        return 1;
    }
}
