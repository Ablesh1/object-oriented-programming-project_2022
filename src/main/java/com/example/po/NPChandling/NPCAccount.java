package com.example.po.NPChandling;
import com.example.po.backends.BankBackend;

import java.io.IOException;
import java.io.Serializable;

// Each NPC has his own account
public class NPCAccount implements Serializable {
    NPC owner;
    BankBackend bankBackend;

    // Account tracks how in debt or rich is NPC
    private Double accountMoney;

    // Loan & investment stuff

    private Double bankLoan;
    private Integer installmentNumber;
    private Double installmentAmount;
    private Double bankInvestment;
    private Integer investmentDuration;

    private Boolean trust;
    private int counter = 0;
    private double actualDebt = 0;
    private double loanInterestRate = 0.12;
    private double investmentInterestRate = 0.01;


    public NPCAccount(NPC npc, Double accountMoney, Double bankLoan, Integer installmentNumber, Boolean trust, Double bankInvestment, Integer investmentDuration, BankBackend bankBackendPass){
        owner = npc;
        this.accountMoney = accountMoney;
        this.bankLoan = bankLoan;
        this.installmentNumber = installmentNumber;
        this.trust = trust;
        this.bankInvestment = bankInvestment;
        this.investmentDuration = investmentDuration;
        bankBackend = bankBackendPass;
    }

    public Double getAccountMoney() {
        return this.accountMoney;
    }

    public void setAccountMoney(Double accountMoney) {
        this.accountMoney = accountMoney;
    }

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

    public Double getInstallmentAmount() {

        if(this.installmentNumber > 0 && this.getBankLoan() > 0) {

            double q = 1 + (this.loanInterestRate/12);
            double L = Math.pow(q, this.installmentNumber) * (q - 1);
            double M = Math.pow(q, this.installmentNumber) - 1;

            this.installmentAmount = this.bankLoan * L / M;
        }
        return installmentAmount;
    }

    public double getActualDebt() {
        return actualDebt;
    }

    public Boolean getTrust() {
        return trust;
    }

    public Double getBankLoan() {
        return bankLoan;
    }

    public void setBankLoan(Double bankLoan) {
        this.bankLoan = bankLoan;
    }

    public void setInstallmentNumber(Integer installmentNumber) {
        this.installmentNumber = installmentNumber;
    }

    public void depositOnAccount(Double toDeposit){
        if (owner.getPersonBelongings() >= toDeposit){
        this.accountMoney += toDeposit;
        owner.setPersonBelongings(owner.getPersonBelongings() - toDeposit);
        }
    }

    public void withdrawFromAccount(Double toWithdraw) {
        if (this.accountMoney - toWithdraw >= 0.0) {
            this.accountMoney -= toWithdraw;
            owner.setPersonBelongings(owner.getPersonBelongings() + toWithdraw);
        }
    }

    public void takeBankLoan(Double bankLoan, Integer installmentNumber) {
        if(this.trust) {
            this.setBankLoan(this.getBankLoan() + bankLoan);
            this.setInstallmentNumber(installmentNumber);
            this.accountMoney += this.bankLoan;
            this.actualDebt = this.getInstallmentAmount() * this.installmentNumber;
            this.trust = false;
        }
    }

    public void payInstallment() {
        if (this.accountMoney - this.getInstallmentAmount() >= 0 &&  this.actualDebt > 0 && this.counter < this.installmentNumber) {
            this.accountMoney -= this.getInstallmentAmount();
            this.actualDebt -= this.getInstallmentAmount();
            this.counter ++;
            if (this.actualDebt < 0.001) {
                this.actualDebt = 0;
            }
        }

        else if (this.accountMoney - this.getInstallmentAmount() < 0 && this.actualDebt > 0 && this.counter < this.installmentNumber) {
            // Closes investment when does not have money for monthly installments
            this.closeInvestment();
            if(this.accountMoney - this.getInstallmentAmount() >= 0) {
                this.accountMoney -= this.getInstallmentAmount();
                this.actualDebt -= this.getInstallmentAmount();
                this.counter++;
                if (this.actualDebt < 0.001) {
                    this.actualDebt = 0.0;
                    this.bankLoan = 0.0;
                }
            }
            else {
                // System.out.println("Ara ara, we are spending more money than we have");
                System.out.println("Ara ara, we are having serious debt problems");
            }
        }

        if((this.actualDebt < 0.01 && this.actualDebt > 0.001) || this.actualDebt == 0.0){
            this.actualDebt = 0.0;
            this.bankLoan = 0.0;
            this.counter = 0;
            this.trust = true;
        }
    }

    public void makeBankInvestment(Double bankInvestment, Integer investmentDuration) {
        if(this.accountMoney - bankInvestment >= 0) {
            this.accountMoney -= bankInvestment;
            this.bankInvestment += bankInvestment;
            this.investmentDuration = investmentDuration;
        }
    }

    public void closeInvestment() {
        this.accountMoney += bankInvestment * (1 + this.investmentInterestRate) * getInvestmentDuration();
        this.bankInvestment =  0.0;
    }

    public void payTaxes(double taxes){
        this.accountMoney -= taxes;
    }
}
