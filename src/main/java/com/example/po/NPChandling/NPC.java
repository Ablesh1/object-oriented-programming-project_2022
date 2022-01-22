package com.example.po.NPChandling;
import com.example.po.backends.BankBackend;
import com.example.po.backends.Writer;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Random;
import java.io.Serializable;

//Here be general NPC and their behaviours
//We will put some special NPCs in different classes
public class NPC extends Thread implements Serializable{

    BankBackend bankBackend;

    //NPC features
    private Integer personID;
    private String name;
    private String surname;
    private Integer pesel;
    private NPCAccount npcAccount;
    private Double personBelongings;
    private String personality;
    private Double monthlyIncome;

    //Character describes how the NPC behaves
    private int iWantToDie;

    //Useful methods
    private static File fileOut;
    private static Logger logger;
    private static PrintWriter writer;

    public NPC(Integer idNumber, String name, String surname, Integer pesel, Double accountMoney, Double bankLoan, Integer installmentNumber, Boolean trust, Double bankInvestment, Integer investmentDuration, BankBackend bankBackend, Double personBelongings, String personality, Double monthlyIncome){
        this.personID = idNumber;
        this.name = name;
        this.surname = surname;
        this.pesel = pesel;
        this.bankBackend = bankBackend;
        this.npcAccount = new NPCAccount(this, accountMoney, bankLoan, installmentNumber, trust, bankInvestment, investmentDuration, bankBackend);
        this.personBelongings = personBelongings;
        this.personality = personality;
        this.monthlyIncome = monthlyIncome;

        this.fileOut = new File("NPClogging");
        this.logger = Logger.getLogger(getClass().getName());

        try {
            this.writer = new PrintWriter(fileOut);
        }
        catch (FileNotFoundException e) {
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

    public String getPersonName() {
        return this.name;
    }

    public String getSurname() {
        return this.surname;
    }

    //$$$ in your bank account
    public Double getAccountMoney() {
        return this.npcAccount.getAccountMoney();
    }

    //$$$ in your pocket.
    public Double getPersonBelongings() {
        return personBelongings;
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

    public void setPersonID(Integer personID) {
        this.personID = personID;
    }

    public void setPersonBelongings(double personBelongings) {
        this.personBelongings = personBelongings;
    }

    public void setBankBackend(BankBackend bankBackend) {
        this.bankBackend = bankBackend;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void deposit(double deposit){
        npcAccount.depositOnAccount(deposit);
    }

    public void withdraw(double withdraw) {
        npcAccount.withdrawFromAccount(withdraw);
        Writer writer = new Writer();

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.getPersonName() + " " + this.getPersonName() + " withdraws " + withdraw);
        String finalString = stringBuilder.toString();

        try {
            writer.writeWithdraws(finalString);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
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

    public void incomeOnAccount(Double income) {
        npcAccount.setAccountMoney(npcAccount.getAccountMoney() + income);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //AI be here
    //My intention here is to write the AI so bad I will never be allowed to work with it ever again

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //Suicide method kills the NPC
    //Yes, this is the first function I wrote
    //Yes, it`s only to show that we know how to do it
    public int suicide(){
        System.out.println(this.getPersonName() + " " + this.getSurname() + " is no longer among us");
        this.bankBackend.removeClient(this.personID);
        this.iWantToDie = 1;
        return 1;
    }

    //This will return id of the poorest person in bank
    public Integer lookForThePoorestClient(){
        return bankBackend.askForPoor();
    }

    //And everyone has gone mad
    public void npcBehaviour(NPC npc){

        Random random = new Random();
        //AI will want to do thing based on this variable
        int whatToDo = random.nextInt(10);

        //I love Yandere Dev
        switch (personality){

            ////Charitable person is very likely to help the poorest soul in the bank
            ////He can do other things as well
            ////He does not invest any money
            ////May do money laundering because of that
            ////He sometimes does mad stuff
            case("Charitable"):

                if(whatToDo == 0){
                    logger.log(Level.INFO, "Charitable person - " + this.getPersonName() + " " + this.getSurname() + " decides to do nothing ");
                }
                //Charitable stuff
                else if(whatToDo <= 4){

                    Integer thePoorestOne = lookForThePoorestClient();

                    logger.log(Level.INFO, "Charitable person - " + this.getPersonName() + " " + this.getSurname() + " decides to transfer money to the poorest person with ID" + thePoorestOne);

                    //Because you send money to ID not person themselves
                    //Max 80% of what the person have
                    this.bankBackend.transferMoney(this.personID, thePoorestOne, Math.round(random.nextDouble(this.npcAccount.getAccountMoney() * 0.8 )));
                }

                //Payments - brutal
                else if(whatToDo == 5 || whatToDo == 6){

                    double thisMonthRandExpenses = random.nextDouble(this.npcAccount.getAccountMoney() * 0.6);

                    //Pay if you can afford
                    if(this.npcAccount.getAccountMoney() >= thisMonthRandExpenses){

                        logger.log(Level.INFO, "Charitable person - " + this.getPersonName() + " " + this.getSurname() + " paid monthly taxes");

                        this.npcAccount.payTaxes(thisMonthRandExpenses);

                        writer.write("Charitable person - " + this.getPersonName() + " " + this.getSurname() + " paid monthly taxes");
                    }
                    //Debts
                    else{
                        //Pay if you can take a loan
                        if(this.npcAccount.getTrust()) {
                            logger.log(Level.INFO, "Charitable person - " + this.getPersonName() + " " + this.getSurname() + " took a loan for 5 months and paid monthly taxes");

                            this.npcAccount.takeBankLoan(thisMonthRandExpenses - this.npcAccount.getAccountMoney() + 1000.0, 5);
                            this.npcAccount.payTaxes(thisMonthRandExpenses);

                            writer.write("Charitable person - " + this.getPersonName() + " " + this.getSurname() + " took a loan for 5 months and paid monthly taxes");
                        }
                        //Die if you cant
                        else{
                            //An investment is your last chance
                            this.npcAccount.closeInvestment();

                            if(this.npcAccount.getAccountMoney() >= thisMonthRandExpenses) {

                                logger.log(Level.INFO, "Charitable person - " + this.getPersonName() + " " + this.getSurname() + " paid monthly taxes");

                                this.npcAccount.payTaxes(thisMonthRandExpenses);

                                writer.write("Charitable person - " + this.getPersonName() + " " + this.getSurname() + " paid monthly taxes");
                            }
                            else {
                                logger.log(Level.INFO, "Charitable person - " + this.getPersonName() + " " + this.getSurname() + " committed suicide on purpose");

                                this.suicide();

                                logger.log(Level.INFO, "Charitable person - " + this.getPersonName() + " " + this.getSurname() + " committed suicide on purpose");
                            }
                        }
                    }
                }

                //Mad stuff
                else if(whatToDo == 7){
                    if(this.npcAccount.getAccountMoney() > 30000)

                    logger.log(Level.INFO, "Charitable person - " + this.getPersonName() + " " + this.getSurname() + " saved children in Africa and went for whores");

                    this.withdraw(10000.0);
                    this.personBelongings -= 10000.0;

                    writer.write("Charitable person - " + this.getPersonName() + " " + this.getSurname() + " saved children in Africa and went for whores");
                }

                //More mad stuff
                else if(whatToDo == 8){
                    if(this.npcAccount.getAccountMoney() > 50000)

                        logger.log(Level.INFO, "Charitable person - " + this.getPersonName() + " " + this.getSurname() + " prevented genocide in the Middle East");

                    this.withdraw(30000.0);
                    this.personBelongings -= 30000.0;

                    writer.write("Charitable person - " + this.getPersonName() + " " + this.getSurname() + " prevented genocide in the Middle East");
                }

                //More and more mad stuff
                else {
                    if(this.npcAccount.getAccountMoney() > 70000){

                        ////Did some taxes and bought 1 kg of uranium
                        logger.log(Level.INFO, "Charitable person - " + this.getPersonName() + " " + this.getSurname() + " did some taxes and bought 1 kg of uranium");

                        this.withdraw(50000);
                        this.personBelongings -= 50000;

                        writer.write("Charitable person - " + this.getPersonName() + " " + this.getSurname() + " did some taxes and bought 1 kg of uranium");
                    }
                }

                //Let them have some chance to earn more or less
                //Taxes included so why bother
                this.incomeOnAccount((double) Math.round(monthlyIncome * random.nextDouble(3.6)));

                //How much money can you have in pockets really?
                if(this.personBelongings >= 2000){

                    logger.log(Level.INFO, "Charitable person - " + this.getPersonName() + " " + this.getSurname() + " decided to deposit some pocket money");

                    this.npcAccount.depositOnAccount((double) (Math.round(personBelongings * random.nextDouble(0.3, 1))));

                    writer.write("Charitable person - " + this.getPersonName() + " " + this.getSurname() + " decided to deposit some pocket money");
                }

                break;

            ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            /////Normal is a balanced person that does not do any hardcore stuff
            /////He is much less charitable, may do some investing
            /////Sends money to family for e.g. and stuff
            /////he usually takes have some pocket money with him
            case("Normal"):
                if(whatToDo < 4){

                    logger.log(Level.INFO, "Normal person - " + this.getPersonName() + " " + this.getSurname() + " decided to transfer some money");

                    this.bankBackend.transferMoney(this.getPersonID(), this.bankBackend.getRandomPerson(), Math.round(random.nextDouble(this.npcAccount.getAccountMoney() * 0.4)));

                    writer.write("Normal person - " + this.getPersonName() + " " + this.getSurname() + " decided to transfer some money");
                }

                //Payments - brutal
                else if(whatToDo == 5 || whatToDo == 6){

                    double thisMonthRandExpenses = random.nextDouble(this.npcAccount.getAccountMoney() * 0.6);

                    //Pay if you can afford
                    if(this.npcAccount.getAccountMoney() >= thisMonthRandExpenses){

                        logger.log(Level.INFO, "Normal person - " + this.getPersonName() + " " + this.getSurname() + " paid monthly taxes");

                        this.npcAccount.payTaxes(thisMonthRandExpenses);

                        writer.write("Normal person - " + this.getPersonName() + " " + this.getSurname() + " paid monthly taxes");
                    }
                    //Debts
                    else{
                        //Pay if you can take a loan
                        if(this.npcAccount.getTrust()) {
                            logger.log(Level.INFO, "Normal person - " + this.getPersonName() + " " + this.getSurname() + " took a loan for 5 months and paid monthly taxes");

                            this.npcAccount.takeBankLoan(thisMonthRandExpenses - this.npcAccount.getAccountMoney() + 1000.0, 5);
                            this.npcAccount.payTaxes(thisMonthRandExpenses);

                            writer.write("Normal person - " + this.getPersonName() + " " + this.getSurname() + " took a loan for 5 months and paid monthly taxes");
                        }
                        //Die if you cant
                        else{
                            //An investment is your last chance
                            this.npcAccount.closeInvestment();

                            if(this.npcAccount.getAccountMoney() >= thisMonthRandExpenses) {

                                logger.log(Level.INFO, "Normal person - " + this.getPersonName() + " " + this.getSurname() + " paid monthly taxes");

                                this.npcAccount.payTaxes(thisMonthRandExpenses);

                                writer.write("Normal person - " + this.getPersonName() + " " + this.getSurname() + " paid monthly taxes");
                            }
                            else {
                                logger.log(Level.INFO, "Normal person - " + this.getPersonName() + " " + this.getSurname() + " committed suicide on purpose");

                                this.suicide();

                                logger.log(Level.INFO, "Normal person - " + this.getPersonName() + " " + this.getSurname() + " committed suicide on purpose");
                            }
                        }
                    }
                }

                //Invest some money
                if(whatToDo == 7){

                    logger.log(Level.INFO, "Normal person " + this.getPersonName() + " " + this.getSurname() + " decided to invest some money");

                    this.npcAccount.makeBankInvestment(10000.0, 1);

                    writer.write("Normal person " + this.getPersonName() + " " + this.getSurname() + " decided to invest some money");
                }

                //More investments
                if(whatToDo == 8){

                    logger.log(Level.INFO, "Normal person " + this.getPersonName() + " " + this.getSurname() + " managed to earn some extra stuff this month");

                    Double thisMonthIncomeExtra = (double) Math.round(random.nextDouble(2500.0));

                    this.npcAccount.makeBankInvestment(thisMonthIncomeExtra, 1);

                    logger.log(Level.INFO, "Normal person " + this.getPersonName() + " " + this.getSurname() + " managed to earn some extra stuff this month");
                }

                //Close an investment
                if(whatToDo == 9){

                    logger.log(Level.INFO, "Normal person " + this.getPersonName() + " " + this.getSurname() + " decided to close an investment this month");

                    this.npcAccount.closeInvestment();

                    logger.log(Level.INFO, "Normal person " + this.getPersonName() + " " + this.getSurname() + " decided to close an investment this month");
                }

                //Let them have some chance to earn more or less
                //Taxes included so why bother
                this.incomeOnAccount((double) Math.round(monthlyIncome * random.nextDouble(0.6, 1.8)));

                if(this.personBelongings >= 3600){

                    logger.log(Level.INFO, "Normal person - " + this.getPersonName() + " " + this.getSurname() + " decided to deposit some pocket money");

                    this.npcAccount.depositOnAccount((double) Math.round(personBelongings * random.nextDouble(0.3, 0.6)));

                    writer.write("Normal person - " + this.getPersonName() + " " + this.getSurname() + " decided to deposit some pocket money");
                }

                break;

            ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            /////Evil is not balanced person that does do hardcore stuff
            /////He is aggressive
            /////Spends money on committing war crimes
            /////He usually takes have some stolen money with him
            case("Evil"):
                if(whatToDo < 2){

                    logger.log(Level.INFO, "Evil person - " + this.getPersonName() + " " + this.getSurname() + " decided to transfer some stolen money");

                    this.bankBackend.transferMoney(this.getPersonID(), this.bankBackend.getRandomPerson(), Math.round(random.nextDouble(this.npcAccount.getAccountMoney() * 0.4)));

                    writer.write("Evil person - " + this.getPersonName() + " " + this.getSurname() + " decided to transfer some stolen money");
                }

                //Payments - brutal
                else if(whatToDo == 3){

                    double thisMonthRandExpenses = random.nextDouble(this.npcAccount.getAccountMoney() * 0.6);

                    //Pay if you can afford
                    if(this.npcAccount.getAccountMoney() >= thisMonthRandExpenses){

                        logger.log(Level.INFO, "Evil person - " + this.getPersonName() + " " + this.getSurname() + " paid monthly taxes");

                        this.npcAccount.payTaxes(thisMonthRandExpenses);

                        writer.write("Evil person - " + this.getPersonName() + " " + this.getSurname() + " paid monthly taxes");
                    }
                    //Debts
                    else{
                        //Pay if you can take a loan
                        if(this.npcAccount.getTrust()) {
                            logger.log(Level.INFO, "Evil person - " + this.getPersonName() + " " + this.getSurname() + " took a loan for 5 months and paid monthly taxes");

                            this.npcAccount.takeBankLoan(thisMonthRandExpenses - this.npcAccount.getAccountMoney() + 1000.0, 5);
                            this.npcAccount.payTaxes(thisMonthRandExpenses);

                            writer.write("Evil person - " + this.getPersonName() + " " + this.getSurname() + " took a loan for 5 months and paid monthly taxes");
                        }
                        //Die if you cant
                        else{
                            //An investment is your last chance
                            this.npcAccount.closeInvestment();

                            if(this.npcAccount.getAccountMoney() >= thisMonthRandExpenses) {

                                logger.log(Level.INFO, "Evil person - " + this.getPersonName() + " " + this.getSurname() + " paid monthly taxes");

                                this.npcAccount.payTaxes(thisMonthRandExpenses);

                                writer.write("Evil person - " + this.getPersonName() + " " + this.getSurname() + " paid monthly taxes");
                            }
                            else {
                                logger.log(Level.INFO, "Evil person - " + this.getPersonName() + " " + this.getSurname() + " committed suicide on purpose");

                                this.suicide();

                                logger.log(Level.INFO, "Evil person - " + this.getPersonName() + " " + this.getSurname() + " committed suicide on purpose");
                            }
                        }
                    }
                }

                //Invest some stolen money
                if(whatToDo == 4){

                    logger.log(Level.INFO, "Evil person " + this.getPersonName() + " " + this.getSurname() + " managed to rob some extra poor families this month");

                    Double thisMonthIncomeExtra = (double) Math.round(random.nextDouble(2500.0));

                    this.npcAccount.makeBankInvestment(thisMonthIncomeExtra, 1);

                    logger.log(Level.INFO, "Evil person " + this.getPersonName() + " " + this.getSurname() + " managed to rob some extra poor families this month");
                }

                //Close an investment
                if(whatToDo == 5){

                    logger.log(Level.INFO, "Evil person " + this.getPersonName() + " " + this.getSurname() + " decided to close an investment this month");

                    this.npcAccount.closeInvestment();

                    logger.log(Level.INFO, "Evil person " + this.getPersonName() + " " + this.getSurname() + "decided to close an investment this month");
                }

                //War crimes
                if(whatToDo == 6){
                    if(this.npcAccount.getAccountMoney() > 50000)

                        logger.log(Level.INFO, "Evil person - " + this.getPersonName() + " " + this.getSurname() + " conquered Isengard");

                    this.withdraw(30000.0);
                    this.personBelongings -= 30000.0;

                    writer.write("Evil person - " + this.getPersonName() + " " + this.getSurname() + " conquered Isengard");
                }

                //Cheating
                if(whatToDo == 7){
                    if(this.npcAccount.getAccountMoney() > 1000)

                        logger.log(Level.INFO, "Evil person - " + this.getPersonName() + " " + this.getSurname() + " cheated in the casino");

                    this.withdraw(1000.0);
                    this.personBelongings += 30000.0;

                    writer.write("Evil person - " + this.getPersonName() + " " + this.getSurname() + " cheated in the casino");
                }

                //Wasting money
                if(whatToDo == 8){
                    if(this.npcAccount.getAccountMoney() > 100000)

                        logger.log(Level.INFO, "Evil person - " + this.getPersonName() + " " + this.getSurname() + " splurged money on stupid things");

                    this.withdraw(100000.0);
                    this.personBelongings -= 100000.0;

                    writer.write("Evil person - " + this.getPersonName() + " " + this.getSurname() + " splurged money on stupid things");
                }

                //War crimes
                if(whatToDo == 9){
                    if(this.npcAccount.getAccountMoney() > 10000)

                        logger.log(Level.INFO, "Evil person - " + this.getPersonName() + " " + this.getSurname() + " committed some extra war crimes this month");

                    this.withdraw(10000.0);
                    this.personBelongings += 20000.0;

                    writer.write("Evil person - " + this.getPersonName() + " " + this.getSurname() + " committed some extra war crimes this month");
                }

                //Let them have some chance to earn more or less
                //Taxes included so why bother
                this.incomeOnAccount((double) Math.round(monthlyIncome * random.nextDouble(2, 6)));

                if(this.personBelongings >= 3600){

                    logger.log(Level.INFO, "Evil person - " + this.getPersonName() + " " + this.getSurname() + " decided to deposit some stolen money");

                    this.npcAccount.depositOnAccount((double) Math.round(personBelongings * random.nextDouble(0.3, 0.6)));

                    writer.write("Evil person - " + this.getPersonName() + " " + this.getSurname() + " decided to deposit some stolen money");
                }

                break;

            ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            ////Investor does a lot of investing mostly. Rarely do any other stuff
            case("Investor"):
                logger.log(Level.INFO, "Investor person " + this.getPersonName() + " " + this.getSurname() + " did nothing in particular");

                break;

            ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            //We won`t be doing stuff for the player
            case("Character"):
                logger.log(Level.INFO, " Player kinda exist ");
                break;

            ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            //Testing
            //Deep lore is here
            //For future programming
            //This huy will kill himself shortly after start of the game
            //It`s the cornerstone of the plot
            case("Test"):
                break;
        };

        //Everyone must pay available monthly installments
        if(this.npcAccount.getActualDebt() > 0) {

            logger.log(Level.INFO, this.getPersonName() + " " + this.getSurname() + " tried to pay an obligatory installment");

            this.npcAccount.payInstallment();

            writer.write(this.getPersonName() + " " + this.getSurname() + " tried to pay an obligatory installment");
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void run(){
        this.iWantToDie = 0;
        System.out.println("Hello, is anybody here? " + this.getSurname());
        try {
            Thread.sleep(1200);
        }
        catch (InterruptedException e) {
            System.out.println("Interrupted stage I");
            ;
        }
        while (iWantToDie != 1){
            //Do something
            try{
                //Moving the code to different function
                //Please bear in mind that it may be suboptimal as invoking a function can be CPU demanding
                //But in this case it may help with code-readability

                try{
                npcBehaviour(this);}
                catch(IllegalArgumentException i){
                    ;
                }
                //////////////////////////////////////////////
                //Let`s assume it`s how long one-month takes//
                //////////////////////////////////////////////
                Thread.sleep(860);
                //Chce to zmienic bo usuwam metode checkCredit
            }
            catch(InterruptedException e){
                System.out.println("Interrupted stage II");
                continue;
            }
        }
        logger.log(Level.FINE, this.getName() + " " + this.getSurname() + " is dead");
        return;
    }
}
