package com.example.po;


//Each NPC has his own account
public class NPCaccount {
    NPC owner;
    BankBackend bankBackend;

    //Account tracks how indebt or rich is NPC
    private double deposit;
    private double credit;
    private double balance;

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
        if (this.deposit < 0) {
            this.credit += this.deposit;
            this.deposit = 0;
        }

        if (this.deposit > 0 && this.credit > 0){
            this.balance = this.deposit - this.credit;
            if (this.balance >= 0){
                this.deposit = this.balance;
                this.credit = 0;
            }
        }
        return this.credit;
    }

    public double currentMoney(){
        return deposit;
    }
}
