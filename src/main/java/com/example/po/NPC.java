package com.example.po;
import com.example.po.backends.BankBackend;

//Here be general NPC and their behaviours
//We will put some special NPCs in different classes
public class NPC extends Thread{

    BankBackend bankBackend;

    private Integer personID;
    private Integer pesel;
    private String name;
    private String surname;
    private NPCAccount npcAccount;
    private Double personBelongings;

    //Character describes how the NPC behaves
    private String character;
    private int iWantToDie;

    public NPC(Integer idNumber, String name, String surname, Integer pesel, Double accountMoney, Double bankLoan, Integer installmentNumber, Double bankInvestment, Integer investmentDuration, BankBackend bankBackend, Double personBelongings){
        this.personID = idNumber;
        this.name = name;
        this.surname = surname;
        this.pesel = pesel;
        this.bankBackend = bankBackend;
        this.npcAccount = new NPCAccount(this, accountMoney, bankLoan, installmentNumber, bankInvestment, investmentDuration, bankBackend);
        this.personBelongings = personBelongings;

        //Not yet
        this.iWantToDie = 0;
        this.start();
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public Integer getPersonID() {
        return personID;
    }

    //Ile masz przy sobie?
    public Double getPersonBelongings() {
        return personBelongings;
    }

    public void setPersonBelongings(double personBelongings) {
        this.personBelongings = personBelongings;
    }

    public Double getAccountMoneyAI() {
        return this.npcAccount.getAccountMoney();
    }

    public Double getInstallmentAmount() {
        return this.npcAccount.getInstallmentAmount();
    }

    public Double getActualDebt() {
        return this.npcAccount.getActualDebt();
    }

    public Double getBankInvestment() {
        return this.npcAccount.getBankInvestment();
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //Suicide method kills the NPC
    //Yes, this is the first function I wrote
    //Yes, it`s only to show that we know how to do it
    public int Suicide(){
        System.out.println(this.personID + "FUCKING KILLED HIMSELF");
        this.bankBackend.removeClient(this.personID);
        return 1;
    }

    public void deposit(double deposit){
        npcAccount.depositOnAccount(deposit);
    }

    public void withdraw(double withdraw) {
        npcAccount.withdrawFromAccount(withdraw);
    }

    public void takeLoan(double loan, int number) {
        npcAccount.takeBankLoan(loan, number);
    }

    public void payLoan() {
        npcAccount.payInstallment();
    }

    public void makeInvestment(double investment, int number) {
        npcAccount.makeBankInvestment(investment, number);
    }

    public void closeInvestment() {
        npcAccount.closeInvestment();
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void run(){
        while (iWantToDie != 1){
            //Do something
            try{
                Thread.sleep(3600);
                //Chce to zmienic bo usuwam metode checkCredit
                System.out.println(this.npcAccount.checkCredit());
            }
            catch(InterruptedException e){
                continue;
            }
        }
        this.Suicide();
    }
}
