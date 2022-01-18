package com.example.po;
import com.example.po.backends.BankBackend;

//Each NPC has his own account
public class NPCAccount {
    NPC owner;
    BankBackend bankBackend;

    //Account tracks how in debt or rich is NPC
    private Double accountMoney;
    private Double deposit;
    private Double credit;

    //Balance will be handy when doing AI
    private double balance;

    public NPCAccount(NPC npc, Double accountMoney, BankBackend bankBackendPass){
        owner = npc;
        this.accountMoney = accountMoney;
        this.deposit = 0.0;
        this.credit = 0.0;
        bankBackend = bankBackendPass;
    }

    public void depositOnAccount(Double toDeposit){
        if (owner.getPersonBelongings() >= toDeposit){
        this.accountMoney += toDeposit;
        owner.setPersonBelongings(owner.getPersonBelongings() - toDeposit);
        }
    }

    public void  withdrawFromAccount(Double toWithdraw) {
        if (this.accountMoney - toWithdraw >= 0.0) {
            this.accountMoney -= toWithdraw;
            owner.setPersonBelongings(owner.getPersonBelongings() + toWithdraw);
        }
    }

    public Double checkCredit(){
        if (this.accountMoney < 0) {
            this.credit += this.accountMoney;
            this.accountMoney = 0.0;
        }

        if (this.accountMoney > 0 && this.credit > 0){
            this.balance = this.accountMoney - this.credit;
            if (this.balance >= 0){
                this.accountMoney = this.balance;
                this.credit = 0.0;
            }
        }
        return this.credit;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public Double getAccountMoney() {
        return accountMoney;
    }

}
