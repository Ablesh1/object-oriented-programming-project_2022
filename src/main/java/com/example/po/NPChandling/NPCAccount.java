package com.example.po.NPChandling;
import com.example.po.backends.BankBackend;

import java.io.IOException;
import java.io.Serializable;

//Each NPC has his own account
public class NPCAccount implements Serializable {
    NPC owner;
    BankBackend bankBackend;

    //Account tracks how in debt or rich is NPC
    private Double accountMoney;
    private Double credit;

    //Balance will be handy when doing AI
    private double balance;

    private Double bankLoan;
    private Integer installmentNumber;
    private Double installmentAmount;
    private Double bankInvestment;
    private Integer investmentDuration;

    private int counter = 0;
    private boolean trust = true;
    private double actualDebt = 0;
    private double interestRate = 0.12;


    public NPCAccount(NPC npc, Double accountMoney, Double bankLoan, Integer installmentNumber, Double bankInvestment, Integer investmentDuration, BankBackend bankBackendPass){
        owner = npc;
        this.accountMoney = accountMoney;
        this.bankLoan = bankLoan;
        this.installmentNumber = installmentNumber;
        this.bankInvestment = bankInvestment;
        this.investmentDuration = investmentDuration;
        bankBackend = bankBackendPass;
        this.credit = 0.0;
    }

    public Double getAccountMoney() {
        return accountMoney;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    ///////////////////////////////////////////////////////////
    //Dopierdol tu co się stanie jak zapłacisz więcej niż masz
    public void payment(double howMuch){
        this.accountMoney -= howMuch;
    }
    ////////////////////////////////////////////////////////////

    public Double getBankInvestment() {
        return bankInvestment;
    }

    public Integer getInvestmentDuration() {
        return investmentDuration;
    }

    public void setBankInvestment(Double bankInvestment) {
        this.bankInvestment = bankInvestment;
    }

    public void setInvestmentDuration(Integer investmentDuration) {
        this.investmentDuration = investmentDuration;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public Double getInstallmentAmount() {

        if(this.installmentNumber > 0 && this.bankLoan > 0) {

            double q = 1 + (this.interestRate/12);
            double L = Math.pow(q, this.installmentNumber) * (q - 1);
            double M = Math.pow(q, this.installmentNumber) - 1;

            this.installmentAmount = this.bankLoan * L / M;
        }
        return installmentAmount;
    }

    public double getActualDebt() {
        return actualDebt;
    }

    public void setBankLoan(Double bankLoan) {
        this.bankLoan = bankLoan;
    }

    public void setInstallmentNumber(Integer installmentNumber) {
        this.installmentNumber = installmentNumber;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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

    public void takeBankLoan(Double bankLoan, Integer installmentNumber) {
        if(this.trust) {
            setBankLoan(bankLoan);
            setInstallmentNumber(installmentNumber);
            this.accountMoney += this.bankLoan;
            this.actualDebt = getInstallmentAmount() * this.installmentNumber;
            this.trust = false;
        }
    }

    public void payInstallment() {
        if (this.accountMoney - getInstallmentAmount() >= 0 &&this.actualDebt > 0 && this.counter < this.installmentNumber) {
            this.accountMoney -= getInstallmentAmount();
            this.actualDebt -= getInstallmentAmount();
            this.counter ++;
            if (this.actualDebt < 0.001) {
                this.actualDebt = 0;
            }
        }
        else if((this.actualDebt < 0.01 && this.actualDebt > 0.001) || this.actualDebt == 0){
            this.actualDebt = 0;
            this.counter = 0;
            this.trust = true;
        }
    }

    public void makeBankInvestment(Double bankInvestment, Integer investmentDuration) {
        if(this.accountMoney - bankInvestment >= 0) {
            this.accountMoney -= bankInvestment;
            this.bankInvestment += bankInvestment;
        }
    }

    public void closeInvestment() {
        this.accountMoney += bankInvestment;
        this.bankInvestment =  0.0;
    }



    //Mogę to usunąć?
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
}
