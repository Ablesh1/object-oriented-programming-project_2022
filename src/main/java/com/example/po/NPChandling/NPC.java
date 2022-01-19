package com.example.po.NPChandling;
import com.example.po.backends.BankBackend;

import java.util.Random;


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
    private double monthlyIncome;

    //Character describes how the NPC behaves
    private String personality;
    private int iWantToDie;

    public NPC(Integer idNumber, String name, String surname, Integer pesel, Double accountMoney, Double bankLoan, Integer installmentNumber, Double bankInvestment, Integer investmentDuration, BankBackend bankBackend, Double personBelongings, String personality, double monthlyIncome){
        this.personID = idNumber;
        this.name = name;
        this.surname = surname;
        this.pesel = pesel;
        this.bankBackend = bankBackend;
        this.npcAccount = new NPCAccount(this, accountMoney, bankLoan, installmentNumber, bankInvestment, investmentDuration, bankBackend);
        this.personBelongings = personBelongings;
        this.personality = personality;
        this.monthlyIncome = monthlyIncome;

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

    public void incrasePersonBelongings(Double income) {
        this.personBelongings += income;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //AI be here
    //My intention here is to write the AI so bad I will never be allowed to work with it ever again
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void npcBehaviour(NPC npc){
        Random random = new Random();
        //AI will want to do thing based on this variable
        int whatToDo = random.nextInt(10);

        //I HATE YANDEREDEV
        switch (personality){
            case("charitable"):
                if(whatToDo == 0){
                }
                else if (whatToDo > 0 && whatToDo <= 6){
                    Integer thePoorestOne = lookforpoorestclient();
                    //Bacause you send money to ID not person themself
                    //Max 80% of what the person have
                    this.bankBackend.transferMoney(this.personID, thePoorestOne, random.nextDouble(this.npcAccount.getAccountMoney() * 0.8));
                    //System.out.println("Przekazano piniąże temu typowi: " + thePoorestOne);
                }
                else if(whatToDo > 6 && whatToDo < 8){

                    double thisMonthRandExpenses = random.nextDouble(this.npcAccount.getAccountMoney() * 0.6);
                    this.npcAccount.withdrawFromAccount(thisMonthRandExpenses);
                    this.personBelongings -= thisMonthRandExpenses;
                    }
                else{
                    double thisMonthIncomeExtra = random.nextDouble(2500);
                    this.npcAccount.setBankInvestment(thisMonthIncomeExtra);
                }
                this.incrasePersonBelongings(monthlyIncome);

                //Dodaj może że te inwestycyjne oddają kasę automatycznie po trzech miesiącach bo nie chce mi się
                //Tego programować

                break;

            case("investor"):
            ;
            case("normie"):
                break
                ;

            //Testing
            case("testificate"):
                break
            ;

            //We won`t be doing stuff for the player
            case("Character"):
                break;
        };
    }

    //This will return id of poorest person in bank
    public Integer lookforpoorestclient(){
        return bankBackend.askforPoor();
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void run(){
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            ;
        }
        while (iWantToDie != 1){
            //Do something
            try{

                //Moving the code to differend function
                //Bare in mind that it may be suboptimal as invoking a function can be CPU deamnding\
                //But in this case it may help with code-readability
                npcBehaviour(this);
                //////////////////////////////////////////////
                //Let`s assume it`s how long one month takes//
                //////////////////////////////////////////////
                Thread.sleep(8600);
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
