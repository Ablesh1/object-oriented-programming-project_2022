package com.example.po;


//Each NPC has his own account
public class NPCaccount {
    NPC owner;
    BankBackend bankBackend;

    //Account tracks how indebt or rich is NPC
    private double deposit;
    private double credit;

    public NPCaccount(NPC npc, double deposit, BankBackend bankBackendPass){
        owner = npc;
        this.deposit = deposit;
        this.credit = 0;
        bankBackend = bankBackendPass;
    }

    public void depositOnAccount(double todeposit){
        if (owner.getPersBelongings() >= todeposit){
        this.deposit += todeposit;
        owner.setPersBelongings(todeposit);
        }
    }

    public double checkCredit(){
        return this.credit;
    }

    public double currentMoney(){
        return deposit;
    }
}
