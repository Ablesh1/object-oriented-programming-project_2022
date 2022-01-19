package com.example.po.NPChandling;
import com.example.po.backends.BankBackend;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Random;
import java.io.Serializable;

//Here be general NPC and their behaviours
//We will put some special NPCs in different classes
public class NPC extends Thread implements Serializable{

    BankBackend bankBackend;

    private Integer personID;
    private Integer pesel;
    private String name;
    private String surname;
    private NPCAccount npcAccount;
    private Double personBelongings;
    private double monthlyIncome;
    private static File fout;
    private static PrintWriter writer;

    public void setBankBackend(BankBackend bankBackend) {
        this.bankBackend = bankBackend;
    }

    //Logging stuff
    private static Logger logger;

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

        this.logger = Logger.getLogger(getClass().getName());
        this.fout = new File("NPClogging");

        try {
            this.writer = new PrintWriter(fout);
        } catch (FileNotFoundException e) {
            logger.log(Level.WARNING, "NPClogging has not been found");
        }


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
        System.out.println(this.getPersName() + " " + this.getSurname() + " FUCKING KILLED HIMSELF");
        this.bankBackend.removeClient(this.personID);
        this.iWantToDie = 1;
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

    public String getPersName() {
        return this.name;
    }

    public String getSurname() {
        return this.surname;
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

            ////Charitable person is very likely to help the poorest soul in the bank
            ////It can do other things as well
            ////It doesn`t invest any money
            ////May do money laundring because of that
            case("charitable"):
                if(whatToDo == 0){
                    logger.log(Level.INFO, "Charitable person" + this.getPersName() + " " + this.getSurname()
                    + " decides to do nothing ");
                }
                else if (whatToDo <= 6){

                    Integer thePoorestOne = lookforpoorestclient();

                    logger.log(Level.INFO, "Charitable person" + this.getPersName() + " " + this.getSurname()
                            + " decides to transfer money to a poor person with ID" + thePoorestOne);

                    //Bacause you send money to ID not person themself
                    //Max 80% of what the person have
                    this.bankBackend.transferMoney(this.personID, thePoorestOne, random.nextDouble(this.npcAccount.getAccountMoney() * 0.8));
                }
                else if(whatToDo == 7){

                    logger.log(Level.INFO, "Charitable person" + this.getPersName() + " " + this.getSurname()
                            + " got some extra money this month");

                    double thisMonthRandExpenses = random.nextDouble(this.npcAccount.getAccountMoney() * 0.6);
                    this.npcAccount.payment(thisMonthRandExpenses);
                    //////Dodaj tyle kredytu ile brakuje jakby co
                    }

                else{
                    if(this.npcAccount.getAccountMoney() > 70000){
                        ////Did some taxes and bought 1kg of uranium
                        this.npcAccount.withdrawFromAccount(50000.32);
                        this.personBelongings -= 50000;

                        logger.log(Level.INFO, "Charitable person" + this.getPersName() + " " + this.getSurname()
                                + " Did some taxes and bought kg of uranium");

                        writer.write("Charitable person" + this.getPersName() + " " + this.getSurname()
                                + " Did some taxes and bought kg of uranium");

                    }
                }

                //Let them have some chance to pay more or less
                //Taxes included so why bother
                this.incrasePersonBelongings(monthlyIncome * random.nextDouble(3.6));

                //How much money can you have in pockets really?
                if(this.personBelongings >= 2000){
                    this.npcAccount.depositOnAccount(personBelongings * random.nextDouble(0.3, 1));
                    logger.log(Level.INFO, "Charitable person " + this.getPersName() + " " + this.getSurname()
                            + " decided to deposit some pocket money");


                }

                //Dodaj może że te inwestycyjne oddają kasę automatycznie po trzech miesiącach bo nie chce mi się
                //Tego programować

                break;

            ////Investor does a lot of investing mostly. Rarely do any other stuff
            case("investor"):
                logger.log(Level.INFO, "Investor person " + this.getPersName() + " " + this.getSurname()
                        + " did nothing in particular");

                break;

            /////Normie is a balanced person that doesn`t do any hardcore stuff
            /////He is much less charitable, may do some investing
            /////Sends money to family for e.g. and stuff
            /////It usually takes have some pocket money with them
            case("normie"):
                if(whatToDo < 4){
                    this.bankBackend.transferMoney(this.getPersonID(), this.bankBackend.getRandomPerson(), random.nextDouble(this.npcAccount.getAccountMoney() * 0.4));

                    logger.log(Level.INFO, "Normie personn" + this.getPersName() + " " + this.getSurname()
                            + " decided to transfer some money");

                }
                if (whatToDo >= 4 && whatToDo < 7){
                    double thisMonthRandExpenses = random.nextDouble(this.npcAccount.getAccountMoney() * 0.6);
                    this.npcAccount.payment(thisMonthRandExpenses);

                    logger.log(Level.INFO, "normie person " + this.getPersName() + " " + this.getSurname()
                            + " payed for some random stuff");

                }
                if(whatToDo >= 7 && whatToDo <= 8){
                    ////Invest some money

                    logger.log(Level.INFO, "Normie person " + this.getPersName() + " " + this.getSurname()
                            + " decided to invest some money");

                }

                if(whatToDo == 9){
                    double thisMonthIncomeExtra = random.nextDouble(2500);
                    ////Przyjrzyj się temu, niech się dodaje do inwestycji.
                    this.npcAccount.setBankInvestment(thisMonthIncomeExtra);

                    logger.log(Level.INFO, "normie person " + this.getPersName() + " " + this.getSurname()
                            + " managed to earn some extra stuff this month");

                }

                this.incrasePersonBelongings(monthlyIncome * random.nextDouble(0.6, 1.8));
                if(this.personBelongings >= 3600){
                    this.npcAccount.depositOnAccount(personBelongings * random.nextDouble(0.3, 0.6));
                }

                break;

            //Testing
            //Deep lore is here
            //For future programming
            //This huy will kill himself shortly after start of the game
            //It`s the cornerstone of the plot
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
        System.out.println("HAAAALO JEST TU KTO? " + this.getSurname());
        try {
            Thread.sleep(1200);
        } catch (InterruptedException e) {
            System.out.println("Inteerupted stage I");
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
            }
            catch(InterruptedException e){
                System.out.println("Inteerupted stage II");
                continue;
            }
        }
        logger.log(Level.FINE, this.getName() + " " + this.getSurname() + " is dead");
        return;
    }
}
