package com.example.po;
import com.example.po.backends.BankBackend;

//Each NPC has his own account
public class NPCAccount {
    NPC owner;
    BankBackend bankBackend;

    //Account tracks how indebt or rich is NPC
    private double deposit;
    private double credit;

    //Balance will be handy when doing AI
    private double balance;

    public NPCAccount(NPC npc, double deposit, BankBackend bankBackendPass){
        owner = npc;
        this.deposit = deposit;
        this.credit = 0;
        bankBackend = bankBackendPass;
    }

    //Add withdrawFromAccount
    public void depositOnAccount(double toDeposit){
        if (owner.getPersonBelongings() >= toDeposit){
        this.deposit += toDeposit;
        owner.setPersonBelongings(toDeposit);
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
