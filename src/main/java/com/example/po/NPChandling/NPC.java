package com.example.po.NPChandling;
import com.example.po.backends.BankBackend;
import com.example.po.backends.Writer;

import java.io.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Random;
import java.io.Serializable;

// Here be general NPC and their behaviours
// We will put some special NPCs in different classes
public class NPC extends Thread implements Serializable{

    BankBackend bankBackend;

    // NPC features
    private Integer personID;
    private String name;
    private String surname;
    private Integer pesel;
    private NPCAccount npcAccount;
    private Double personBelongings;
    private String personality;
    private Double monthlyIncome;

    // Character describes how the NPC behaves
    private int iWantToDie;

    // Useful methods
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

        this.fileOut = new File("NPClogging.txt");
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

    public Integer getPersonID() {
        return personID;
    }

    public String getPersonName() {
        return this.name;
    }

    public String getSurname() {
        return this.surname;
    }

    // $$$ in your bank account
    public Double getAccountMoney() {
        return this.npcAccount.getAccountMoney();
    }

    // $$$ in your pocket.
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

    public Double getMonthlyIncome() {
        return monthlyIncome;
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

    public void setMonthlyIncome(Double monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
    }

    public void deposit(double deposit){
        npcAccount.depositOnAccount(deposit);
        Writer writer = new Writer();

        StringBuilder stringBuilder = new StringBuilder();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        stringBuilder.append( LocalTime.now().format(formatter) +"\t" + this.getSurname() + "\t\t   deposits        " + deposit);
        String finalString = stringBuilder.toString();

        try {
            writer.writeDeposits(finalString);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void withdraw(double withdraw) {
        npcAccount.withdrawFromAccount(withdraw);
        Writer writer = new Writer();

        StringBuilder stringBuilder = new StringBuilder();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        stringBuilder.append(LocalTime.now().format(formatter) + "\t" + this.getSurname() + "\t\t   withdraws       " + withdraw);
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

    // Method that rounds output to n decimal places
    public static Double roundAvoid(Double value, int places) {
        double scale = Math.pow(10, places);
        return Math.round(value * scale) / scale;
    }

    // Suicide method kills the NPC
    // Yes, this is the first function I wrote
    // Yes, it`s only to show that we know how to do it
    public int suicide(){
        System.out.println(this.getPersonName() + " " + this.getSurname() + " is no longer among us");
        this.bankBackend.removeClient(this.personID);
        this.iWantToDie = 1;
        return 1;
    }

    // This will return id of the poorest person in bank
    public Integer lookForThePoorestClient(){
        return bankBackend.askForPoor();
    }

    // And everyone has gone mad
    public void npcBehaviour(NPC npc){

        Random random = new Random();
        // AI will want to do thing based on this variable
        int whatToDo = random.nextInt(10);

        // I love Yandere Dev
        switch (personality){

            // Charitable person is very likely to help the poorest soul in the bank
            // He can do other things as well
            // He does not invest any money
            // May do money laundering because of that
            // He sometimes does mad stuff
            case("Charitable"):

                if(whatToDo == 0){
                    logger.log(Level.INFO, "Charitable person - " + this.getPersonName() + " " + this.getSurname() + " decides to do nothing ");
                }
                // Charitable stuff
                else if(whatToDo <= 4){

                    Integer thePoorestOne = lookForThePoorestClient();

                    logger.log(Level.INFO, "Charitable person - " + this.getPersonName() + " " + this.getSurname() + " decides to transfer money to the poorest person with ID" + thePoorestOne);

                    // Because you send money to ID not person themselves
                    // Max 80% of what the person have
                    this.bankBackend.transferMoney(this.personID, thePoorestOne, Math.round(random.nextDouble(this.npcAccount.getAccountMoney() * 0.8 )));
                }

                // Payments - brutal
                if(whatToDo == 5 || whatToDo == 6){

                    double thisMonthRandExpenses = random.nextDouble(this.npcAccount.getAccountMoney() * 0.6 + 100);

                    // Pay if you can afford
                    if(this.npcAccount.getAccountMoney() >= thisMonthRandExpenses){

                        logger.log(Level.INFO, "Charitable person - " + this.getPersonName() + " " + this.getSurname() + " paid monthly taxes");

                        this.npcAccount.payTaxes(thisMonthRandExpenses);

                        writer.write("Charitable person - " + this.getPersonName() + " " + this.getSurname() + " paid monthly taxes");
                    }
                    // Debts
                    else{
                        // Pay if you can take a loan
                        if(this.npcAccount.getTrust()) {
                            logger.log(Level.INFO, "Charitable person - " + this.getPersonName() + " " + this.getSurname() + " took a loan for 5 months and paid monthly taxes");

                            this.npcAccount.takeBankLoan(thisMonthRandExpenses - this.npcAccount.getAccountMoney() + 1000.0, 5);
                            this.npcAccount.payTaxes(thisMonthRandExpenses);

                            writer.write("Charitable person - " + this.getPersonName() + " " + this.getSurname() + " took a loan for 5 months and paid monthly taxes");
                        }
                        // Die if you cant
                        else{
                            // An investment is your last chance
                            this.npcAccount.closeInvestment();

                            if(this.npcAccount.getAccountMoney() >= thisMonthRandExpenses) {

                                logger.log(Level.INFO, "Charitable person - " + this.getPersonName() + " " + this.getSurname() + " paid monthly taxes");

                                this.npcAccount.payTaxes(thisMonthRandExpenses);

                                writer.write("Charitable person - " + this.getPersonName() + " " + this.getSurname() + " paid monthly taxes");
                            }
                            else {
                                logger.log(Level.INFO, "Charitable person - " + this.getPersonName() + " " + this.getSurname() + " committed suicide on purpose");

                                this.suicide();

                                writer.write("Charitable person - " + this.getPersonName() + " " + this.getSurname() + " committed suicide on purpose");
                            }
                        }
                    }
                }

                // Mad stuff
                if(whatToDo == 7){
                    if(this.npcAccount.getAccountMoney() > 30000) {

                        logger.log(Level.INFO, "Charitable person - " + this.getPersonName() + " " + this.getSurname() + " saved children in Africa");

                        this.withdraw(10000.0);
                        this.personBelongings -= 10000.0;

                        writer.write("Charitable person - " + this.getPersonName() + " " + this.getSurname() + " saved children in Africa");
                    }
                }

                // More mad stuff
                if(whatToDo == 8){
                    if(this.npcAccount.getAccountMoney() > 50000) {

                        logger.log(Level.INFO, "Charitable person - " + this.getPersonName() + " " + this.getSurname() + " prevented hunger in the Middle East");

                        this.withdraw(30000.0);
                        this.personBelongings -= 30000.0;

                        writer.write("Charitable person - " + this.getPersonName() + " " + this.getSurname() + " prevented hunger in the Middle East");
                    }
                }

                // More and more mad stuff
                else {
                    if(this.npcAccount.getAccountMoney() > 70000){

                        // Did some taxes and bought 1 kg of uranium
                        logger.log(Level.INFO, "Charitable person - " + this.getPersonName() + " " + this.getSurname() + " did some taxes and bought 1 kg of uranium");

                        this.withdraw(50000);
                        this.personBelongings -= 50000;

                        writer.write("Charitable person - " + this.getPersonName() + " " + this.getSurname() + " did some taxes and bought 1 kg of uranium");
                    }
                }

                // Let them have some chance to earn more or less
                // Taxes included so why bother
                this.incomeOnAccount(roundAvoid((monthlyIncome * random.nextDouble(3.6)),2));

                // How much money can you have in pockets really?
                if(this.personBelongings >= 1000){

                    logger.log(Level.INFO, "Charitable person - " + this.getPersonName() + " " + this.getSurname() + " decided to deposit some pocket money");

                    this.deposit((double) (Math.round(personBelongings * random.nextDouble(0.3, 1))));

                    writer.write("Charitable person - " + this.getPersonName() + " " + this.getSurname() + " decided to deposit some pocket money");
                }

                break;

            // Normal is a balanced person that does not do any hardcore stuff
            // He is much less charitable, may do some investing
            // Sends money to family for e.g. and stuff
            // He usually takes have some pocket money with him
            case("Normal"):
                if(whatToDo < 4){

                    logger.log(Level.INFO, "Normal person - " + this.getPersonName() + " " + this.getSurname() + " decided to transfer some money");

                    this.bankBackend.transferMoney(this.getPersonID(), this.bankBackend.getRandomPerson(), Math.round(random.nextDouble(this.npcAccount.getAccountMoney() * 0.4)));

                    writer.write("Normal person - " + this.getPersonName() + " " + this.getSurname() + " decided to transfer some money");
                }

                // Payments - brutal
                if(whatToDo == 5 || whatToDo == 6){

                    double thisMonthRandExpenses = random.nextDouble(this.npcAccount.getAccountMoney() * 0.6 + 100);

                    // Pay if you can afford
                    if(this.npcAccount.getAccountMoney() >= thisMonthRandExpenses){

                        logger.log(Level.INFO, "Normal person - " + this.getPersonName() + " " + this.getSurname() + " paid monthly taxes");

                        this.npcAccount.payTaxes(thisMonthRandExpenses);

                        writer.write("Normal person - " + this.getPersonName() + " " + this.getSurname() + " paid monthly taxes");
                    }
                    // Debts
                    else{
                        // Pay if you can take a loan
                        if(this.npcAccount.getTrust()) {
                            logger.log(Level.INFO, "Normal person - " + this.getPersonName() + " " + this.getSurname() + " took a loan for 5 months and paid monthly taxes");

                            this.npcAccount.takeBankLoan(thisMonthRandExpenses - this.npcAccount.getAccountMoney() + 1000.0, 5);
                            this.npcAccount.payTaxes(thisMonthRandExpenses);

                            writer.write("Normal person - " + this.getPersonName() + " " + this.getSurname() + " took a loan for 5 months and paid monthly taxes");
                        }
                        // Die if you cant
                        else{
                            // An investment is your last chance
                            this.npcAccount.closeInvestment();

                            if(this.npcAccount.getAccountMoney() >= thisMonthRandExpenses) {

                                logger.log(Level.INFO, "Normal person - " + this.getPersonName() + " " + this.getSurname() + " paid monthly taxes");

                                this.npcAccount.payTaxes(thisMonthRandExpenses);

                                writer.write("Normal person - " + this.getPersonName() + " " + this.getSurname() + " paid monthly taxes");
                            }
                            else {
                                logger.log(Level.INFO, "Normal person - " + this.getPersonName() + " " + this.getSurname() + " committed suicide on purpose");

                                this.suicide();

                                writer.write("Normal person - " + this.getPersonName() + " " + this.getSurname() + " committed suicide on purpose");
                            }
                        }
                    }
                }

                // Invest some money
                if(whatToDo == 7){

                    logger.log(Level.INFO, "Normal person - " + this.getPersonName() + " " + this.getSurname() + " decided to invest some money");

                    this.npcAccount.makeBankInvestment(5000.0, 1);

                    writer.write("Normal person - " + this.getPersonName() + " " + this.getSurname() + " decided to invest some money");
                }

                // More investments
                if(whatToDo == 8){

                    logger.log(Level.INFO, "Normal person - " + this.getPersonName() + " " + this.getSurname() + " managed to earn some extra stuff this month");

                    Double thisMonthIncomeExtra = (double) Math.round(random.nextDouble(2500.0));

                    this.npcAccount.makeBankInvestment(thisMonthIncomeExtra, 1);

                    writer.write("Normal person - " + this.getPersonName() + " " + this.getSurname() + " managed to earn some extra stuff this month");
                }

                // Close an investment
                if(whatToDo == 9){

                    logger.log(Level.INFO, "Normal person - " + this.getPersonName() + " " + this.getSurname() + " decided to close an investment this month");

                    this.npcAccount.closeInvestment();

                    writer.write("Normal person - " + this.getPersonName() + " " + this.getSurname() + " decided to close an investment this month");
                }

                // Let them have some chance to earn more or less
                // Taxes included so why bother
                this.incomeOnAccount(roundAvoid((monthlyIncome * random.nextDouble(0.6, 1.8)),2));

                if(this.personBelongings >= 1000){

                    logger.log(Level.INFO, "Normal person - " + this.getPersonName() + " " + this.getSurname() + " decided to deposit some pocket money");

                    this.deposit((double) Math.round(personBelongings * random.nextDouble(0.3, 0.6)));

                    writer.write("Normal person - " + this.getPersonName() + " " + this.getSurname() + " decided to deposit some pocket money");
                }

                break;

            // 可愛い is a partly balanced person that omits payments and other obligations due to her cuteness
            // She has many simps that send her money on her OnlyFans
            // Sends money to family, and may do some investing
            // She is addicted to heroin due to overwhelming popularity
            // She usually takes have some pocket money with her
            case("可愛い"):
                if(whatToDo < 2){

                    logger.log(Level.INFO, "可愛い person - " + this.getPersonName() + " " + this.getSurname() + " decided to transfer some money");

                    this.bankBackend.transferMoney(this.getPersonID(), this.bankBackend.getRandomPerson(), Math.round(random.nextDouble(this.npcAccount.getAccountMoney() * 0.4)));

                    writer.write("可愛い person - " + this.getPersonName() + " " + this.getSurname() + " decided to transfer some money");
                }

                // OnlyFans
                if(whatToDo == 3 || whatToDo == 4){

                    logger.log(Level.INFO, "可愛い person - " + this.getPersonName() + " " + this.getSurname() + " updated her profile on OnlyFans");

                    Double thisMonthIncomeExtra = (double) Math.round(random.nextDouble(2500.0));

                    this.npcAccount.makeBankInvestment(thisMonthIncomeExtra, 1);

                    writer.write("可愛い person - " + this.getPersonName() + " " + this.getSurname() + " updated her profile on OnlyFans");
                }

                // Send money to family
                if(whatToDo == 5){

                    double thisMonthRandExpenses = random.nextDouble(this.npcAccount.getAccountMoney() * 0.4 + 100);

                    if(this.npcAccount.getAccountMoney() >= thisMonthRandExpenses){

                        logger.log(Level.INFO, "可愛い person - " + this.getPersonName() + " " + this.getSurname() + " supported her family this month");

                        this.npcAccount.payTaxes(thisMonthRandExpenses);

                        writer.write("s可愛い person - " + this.getPersonName() + " " + this.getSurname() + " supported her family this month");
                    }
                    else{
                        logger.log(Level.INFO, "可愛い person - " + this.getPersonName() + " " + this.getSurname() + " could not support her family this month");
                        writer.write("s可愛い person - " + this.getPersonName() + " " + this.getSurname() + " could not support her family this month");
                    }
                }

                // Take some pills
                if(whatToDo == 6){
                    if(this.npcAccount.getAccountMoney() > 25000) {

                        logger.log(Level.INFO, "可愛い person - " + this.getPersonName() + " " + this.getSurname() + " took her monthly dose of pills");

                        this.withdraw(20000.0);
                        this.personBelongings -= 20000.0;

                        writer.write("可愛い person - " + this.getPersonName() + " " + this.getSurname() + " took her monthly dose of pills");
                    }
                }

                // Invest some money
                if(whatToDo == 7 || whatToDo == 8){

                    logger.log(Level.INFO, "可愛い person - " + this.getPersonName() + " " + this.getSurname() + " decided to invest some money");

                    this.npcAccount.makeBankInvestment(5000.0, 1);

                    writer.write("可愛い person - " + this.getPersonName() + " " + this.getSurname() + " decided to invest some money");
                }

                // Close an investment
                if(whatToDo == 9){

                    logger.log(Level.INFO, "可愛い person - " + this.getPersonName() + " " + this.getSurname() + " decided to close an investment this month");

                    this.npcAccount.closeInvestment();

                    writer.write("可愛い person - " + this.getPersonName() + " " + this.getSurname() + " decided to close an investment this month");
                }

                // Let them have some chance to earn more or less
                // Taxes included so why bother
                this.incomeOnAccount(roundAvoid((monthlyIncome * random.nextDouble(0.6, 1.8)),2));

                if(this.personBelongings >= 1000){

                    logger.log(Level.INFO, "可愛い person - " + this.getPersonName() + " " + this.getSurname() + " decided to deposit some pocket money");

                    this.deposit((double) Math.round(personBelongings * random.nextDouble(0.3, 0.6)));

                    writer.write("可愛い person - " + this.getPersonName() + " " + this.getSurname() + " decided to deposit some pocket money");
                }

                break;

            // Lucky is a quite balanced person that has more luck than the others
            // He does more investing
            // Money magically appear on his account when on debt
            // Sends money to family for e.g. and stuff
            // He usually takes have some pocket money with him
            case("Lucky"):
                if(whatToDo < 2){

                    logger.log(Level.INFO, "Lucky " + this.getPersonName() + " " + this.getSurname() + " decided to transfer some money");

                    this.bankBackend.transferMoney(this.getPersonID(), this.bankBackend.getRandomPerson(), Math.round(random.nextDouble(this.npcAccount.getAccountMoney() * 0.4)));

                    writer.write("Lucky " + this.getPersonName() + " " + this.getSurname() + " decided to transfer some money");
                }

                // Payments - brutal
                if(whatToDo < 4){

                    double thisMonthRandExpenses = random.nextDouble(this.npcAccount.getAccountMoney() * 0.6 + 100);

                    // Pay if you can afford
                    if(this.npcAccount.getAccountMoney() >= thisMonthRandExpenses){

                        logger.log(Level.INFO, "Normal person - " + this.getPersonName() + " " + this.getSurname() + " paid monthly taxes");

                        this.npcAccount.payTaxes(thisMonthRandExpenses);

                        writer.write("Normal person - " + this.getPersonName() + " " + this.getSurname() + " paid monthly taxes");
                    }
                    // Debts or miracle?
                    else{
                        logger.log(Level.INFO, "Lucky " + this.getPersonName() + " " + this.getSurname() + " paid monthly taxes due to the miraculous source of money");

                        this.npcAccount.setAccountMoney(this.npcAccount.getAccountMoney() + thisMonthRandExpenses + 100);
                        this.npcAccount.payTaxes(thisMonthRandExpenses);

                        writer.write("Lucky " + this.getPersonName() + " " + this.getSurname() + " paid monthly taxes due to the miraculous source of money");
                    }
                }

                // Invest some money
                if(whatToDo == 5 || whatToDo == 6){

                    logger.log(Level.INFO, "Lucky " + this.getPersonName() + " " + this.getSurname() + " decided to invest some money");

                    this.npcAccount.makeBankInvestment(10000.0, 1);

                    writer.write("Lucky " + this.getPersonName() + " " + this.getSurname() + " decided to invest some money");
                }

                // More investments
                if(whatToDo == 7 || whatToDo == 8){

                    logger.log(Level.INFO, "Lucky " + this.getPersonName() + " " + this.getSurname() + " manipulated the stock this month");

                    Double thisMonthIncomeExtra = (double) Math.round(random.nextDouble(2500.0));
                    this.npcAccount.makeBankInvestment(thisMonthIncomeExtra, 1);

                    writer.write("Lucky " + this.getPersonName() + " " + this.getSurname() + " manipulated the stock this month");
                }

                // Close an investment
                if(whatToDo == 9){

                    logger.log(Level.INFO, "Lucky " + this.getPersonName() + " " + this.getSurname() + " decided to close an investment this month");

                    this.npcAccount.closeInvestment();

                    writer.write("Lucky " + this.getPersonName() + " " + this.getSurname() + " decided to close an investment this month");
                }

                // Let them have some chance to earn more or less
                // Taxes included so why bother
                // Elon Musk has +/- income

                if(this.getMonthlyIncome() >= 0){

                    this.incomeOnAccount(roundAvoid((monthlyIncome * random.nextDouble(0.6, 1.8)),2));
                }
                else if(this.npcAccount.getAccountMoney() + this.getMonthlyIncome() >= 0){

                    this.incomeOnAccount(roundAvoid((monthlyIncome * random.nextDouble(0.6, 1.8)),2));
                }
                else{
                    logger.log(Level.INFO, "Lucky " + this.getPersonName() + " " + this.getSurname() + " miraculously reversed his income");

                    this.setMonthlyIncome(getMonthlyIncome() * (-1));
                    this.incomeOnAccount(roundAvoid((monthlyIncome * random.nextDouble(0.6, 1.8)),2));

                    writer.write("Lucky " + this.getPersonName() + " " + this.getSurname() + " miraculously reversed his income");
                }

                // Sometimes revere income
                if(whatToDo >= 7){

                    logger.log(Level.INFO, "Lucky " + this.getPersonName() + " " + this.getSurname() + " miraculously reversed his income");

                    this.setMonthlyIncome(getMonthlyIncome() * (-1));

                    writer.write("Lucky " + this.getPersonName() + " " + this.getSurname() + " miraculously reversed his income");
                }

                if(this.personBelongings >= 1000){

                    logger.log(Level.INFO, "Lucky " + this.getPersonName() + " " + this.getSurname() + " decided to deposit some pocket money");

                    this.deposit((double) Math.round(personBelongings * random.nextDouble(0.3, 0.6)));

                    writer.write("Lucky " + this.getPersonName() + " " + this.getSurname() + " decided to deposit some pocket money");
                }

                break;

            // Madao is a looser with huge debts, low income and more payments
            // He is one step away from bankruptcy, must take next loans to survive
            // He does not send money to family since he has been inherited
            // He seldom does investments, he is eager to spend money on alcohol
            // He usually takes have some pocket money with him
            case("Madao"):

                // Payments - brutal
                if(whatToDo >= 3){

                    double thisMonthRandExpenses = random.nextDouble(this.npcAccount.getAccountMoney() * 0.6 + 100);

                    // Pay if you can afford
                    if(this.npcAccount.getAccountMoney() >= thisMonthRandExpenses){

                        logger.log(Level.INFO, "Madao " + this.getPersonName() + " " + this.getSurname() + " paid monthly taxes");

                        this.npcAccount.payTaxes(thisMonthRandExpenses);

                        writer.write("Madao " + this.getPersonName() + " " + this.getSurname() + " paid monthly taxes");
                    }
                    // Debts
                    else{
                        // Pay if you can take a loan
                        if(this.npcAccount.getTrust()) {
                            logger.log(Level.INFO, "Madao " + this.getPersonName() + " " + this.getSurname() + " took a loan for 5 months and paid monthly taxes");

                            this.npcAccount.takeBankLoan(thisMonthRandExpenses - this.npcAccount.getAccountMoney() + 1000.0, 5);
                            this.npcAccount.payTaxes(thisMonthRandExpenses);

                            writer.write("Madao - " + this.getPersonName() + " " + this.getSurname() + " took a loan for 5 months and paid monthly taxes");
                        }
                        // Die if you cant
                        else{
                            // An investment is your last chance
                            this.npcAccount.closeInvestment();

                            if(this.npcAccount.getAccountMoney() >= thisMonthRandExpenses) {

                                logger.log(Level.INFO, "Madao " + this.getPersonName() + " " + this.getSurname() + " paid monthly taxes");

                                this.npcAccount.payTaxes(thisMonthRandExpenses);

                                writer.write("Madao " + this.getPersonName() + " " + this.getSurname() + " paid monthly taxes");
                            }
                            else {
                                logger.log(Level.INFO, "Madao " + this.getPersonName() + " " + this.getSurname() + " committed suicide on purpose");

                                this.suicide();

                                writer.write("Madao " + this.getPersonName() + " " + this.getSurname() + " committed suicide on purpose");
                            }
                        }
                    }
                }

                // Take a loan
                if(whatToDo == 4 || whatToDo == 5){
                    if(this.npcAccount.getTrust()) {

                        logger.log(Level.INFO, "Madao " + this.getPersonName() + " " + this.getSurname() + " took an additional loan for 5 months.");

                        this.takeLoan(5000.0, 20);

                        writer.write("Madao " + this.getPersonName() + " " + this.getSurname() + " took an additional loan for 5 months.");
                    }
                }

                // Buy some alcohol
                if(whatToDo == 6){
                    if(this.npcAccount.getAccountMoney() > 5000) {

                        logger.log(Level.INFO, "Madao " + this.getPersonName() + " " + this.getSurname() + " drowns his sorrows in alcohol.");

                        this.withdraw(1000.0);
                        this.personBelongings -= 1000.0;

                        writer.write("Madao " + this.getPersonName() + " " + this.getSurname() + " drowns his sorrows in alcohol.");
                    }
                }

                if(whatToDo == 7){

                    logger.log(Level.INFO, "Madao " + this.getPersonName() + " " + this.getSurname() + " decided to transfer some money");

                    this.bankBackend.transferMoney(this.getPersonID(), this.bankBackend.getRandomPerson(), Math.round(random.nextDouble(this.npcAccount.getAccountMoney() * 0.8)));

                    writer.write("Madao " + this.getPersonName() + " " + this.getSurname() + " decided to transfer some money");
                }

                // Invest some money
                if(whatToDo == 8){

                    logger.log(Level.INFO, "Madao " + this.getPersonName() + " " + this.getSurname() + " decided to invest some money");

                    this.npcAccount.makeBankInvestment(1000.0, 1);

                    writer.write("Madao " + this.getPersonName() + " " + this.getSurname() + " decided to invest some money");
                }

                // Close an investment
                if(whatToDo == 9){

                    logger.log(Level.INFO, "Madao " + this.getPersonName() + " " + this.getSurname() + " decided to close an investment this month");

                    this.npcAccount.closeInvestment();

                    writer.write("Madao " + this.getPersonName() + " " + this.getSurname() + " decided to close an investment this month");
                }

                //D o not let Madao have some chance to earn more or less
                // Taxes included so why bother
                this.incomeOnAccount(roundAvoid((monthlyIncome * random.nextDouble(0.4, 1.0)),2));

                if(this.personBelongings >= 1000){

                    logger.log(Level.INFO, "Madao " + this.getPersonName() + " " + this.getSurname() + " decided to deposit some pocket money");

                    this.deposit((double) Math.round(personBelongings * random.nextDouble(0.3, 0.6)));

                    writer.write("Madao " + this.getPersonName() + " " + this.getSurname() + " decided to deposit some pocket money");
                }

                break;

            // Evil is not balanced person that does do hardcore stuff
            // He usually takes have some stolen money with him
            case("Evil"):
                if(whatToDo < 2){

                    logger.log(Level.INFO, "Evil " + this.getPersonName() + " " + this.getSurname() + " decided to transfer some stolen money");

                    this.bankBackend.transferMoney(this.getPersonID(), this.bankBackend.getRandomPerson(), Math.round(random.nextDouble(this.npcAccount.getAccountMoney() * 0.4)));

                    writer.write("Evil " + this.getPersonName() + " " + this.getSurname() + " decided to transfer some stolen money");
                }

                // Payments - brutal
                else if(whatToDo == 3){

                    double thisMonthRandExpenses = random.nextDouble(this.npcAccount.getAccountMoney() * 0.6 + 100);

                    // Pay if you can afford
                    if(this.npcAccount.getAccountMoney() >= thisMonthRandExpenses){

                        logger.log(Level.INFO, "Evil " + this.getPersonName() + " " + this.getSurname() + " paid monthly taxes");

                        this.npcAccount.payTaxes(thisMonthRandExpenses);

                        writer.write("Evil " + this.getPersonName() + " " + this.getSurname() + " paid monthly taxes");
                    }
                    // Debts
                    else{
                        // Pay if you can take a loan
                        if(this.npcAccount.getTrust()) {
                            logger.log(Level.INFO, "Evil " + this.getPersonName() + " " + this.getSurname() + " took a loan for 5 months and paid monthly taxes");

                            this.npcAccount.takeBankLoan(thisMonthRandExpenses - this.npcAccount.getAccountMoney() + 1000.0, 5);
                            this.npcAccount.payTaxes(thisMonthRandExpenses);

                            writer.write("Evil " + this.getPersonName() + " " + this.getSurname() + " took a loan for 5 months and paid monthly taxes");
                        }
                        // Die if you cant
                        else{
                            // An investment is your last chance
                            this.npcAccount.closeInvestment();

                            if(this.npcAccount.getAccountMoney() >= thisMonthRandExpenses) {

                                logger.log(Level.INFO, "Evil " + this.getPersonName() + " " + this.getSurname() + " paid monthly taxes");

                                this.npcAccount.payTaxes(thisMonthRandExpenses);

                                writer.write("Evil " + this.getPersonName() + " " + this.getSurname() + " paid monthly taxes");
                            }
                            else {
                                logger.log(Level.INFO, "Evil " + this.getPersonName() + " " + this.getSurname() + " committed suicide on purpose");

                                this.suicide();

                                writer.write("Evil " + this.getPersonName() + " " + this.getSurname() + " committed suicide on purpose");
                            }
                        }
                    }
                }

                // Invest some stolen money
                if(whatToDo == 4){

                    logger.log(Level.INFO, "Evil " + this.getPersonName() + " " + this.getSurname() + " managed to rob some extra banks this month");

                    Double thisMonthIncomeExtra = (double) Math.round(random.nextDouble(2500.0));

                    this.npcAccount.makeBankInvestment(thisMonthIncomeExtra, 1);

                    writer.write("Evil " + this.getPersonName() + " " + this.getSurname() + " managed to rob some extra banks this month");
                }

                // Close an investment
                if(whatToDo == 5){

                    logger.log(Level.INFO, "Evil " + this.getPersonName() + " " + this.getSurname() + " decided to close an investment this month");

                    this.npcAccount.closeInvestment();

                    writer.write("Evil person " + this.getPersonName() + " " + this.getSurname() + " decided to close an investment this month");
                }


                if(whatToDo == 6){
                    if(this.npcAccount.getAccountMoney() > 50000) {

                        logger.log(Level.INFO, "Evil " + this.getPersonName() + " " + this.getSurname() + " conquered Isengard");

                        this.withdraw(30000.0);
                        this.personBelongings -= 30000.0;

                        writer.write("Evil " + this.getPersonName() + " " + this.getSurname() + " conquered Isengard");
                    }
                }

                // Cheating
                if(whatToDo == 7){
                    if(this.npcAccount.getAccountMoney() > 1000) {

                        logger.log(Level.INFO, "Evil " + this.getPersonName() + " " + this.getSurname() + " cheated in the casino");

                        this.withdraw(1000.0);
                        this.personBelongings += 40000.0;

                        writer.write("Evil " + this.getPersonName() + " " + this.getSurname() + " cheated in the casino");
                    }
                }

                // Wasting money
                if(whatToDo == 8){
                    if(this.npcAccount.getAccountMoney() > 100000) {

                        logger.log(Level.INFO, "Evil " + this.getPersonName() + " " + this.getSurname() + " splurged money on stupid things");

                        this.withdraw(100000.0);
                        this.personBelongings -= 100000.0;

                        writer.write("Evil " + this.getPersonName() + " " + this.getSurname() + " splurged money on stupid things");
                    }
                }


                if(whatToDo == 9){
                    if(this.npcAccount.getAccountMoney() > 10000) {

                        logger.log(Level.INFO, "Evil " + this.getPersonName() + " " + this.getSurname() + " committed some extra sth this month");

                        this.withdraw(10000.0);
                        this.personBelongings += 20000.0;

                        writer.write("Evil " + this.getPersonName() + " " + this.getSurname() + " committed some extra sth this month");
                    }
                }

                // Let them have some chance to earn more or less
                // Taxes included so why bother
                this.incomeOnAccount(roundAvoid((monthlyIncome * random.nextDouble(0.6, 1.8)),2));

                if(this.personBelongings >= 1000){

                    logger.log(Level.INFO, "Evil " + this.getPersonName() + " " + this.getSurname() + " decided to deposit some stolen money");

                    this.deposit((double) Math.round(personBelongings * random.nextDouble(0.3, 0.6)));

                    writer.write("Evil " + this.getPersonName() + " " + this.getSurname() + " decided to deposit some stolen money");
                }

                break;

            // We won`t be doing stuff for the player
            case("Character"):
                logger.log(Level.INFO, " Player kinda exist ");
                break;

            // Testing
            // Deep lore is here
            // For future programming
            // This huy will kill himself shortly after start of the game
            // It`s the cornerstone of the plot
            case("Test"):
                break;
        };

        // Everyone must pay available monthly installments
        if(this.npcAccount.getActualDebt() > 0) {

            logger.log(Level.INFO, this.getPersonName() + " " + this.getSurname() + " tried to pay an obligatory installment");

            this.npcAccount.payInstallment();

            writer.write(this.getPersonName() + " " + this.getSurname() + " tried to pay an obligatory installment");

        }
    }

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
            // Do something
            try{
                // Moving the code to different function
                // Please bear in mind that it may be suboptimal as invoking a function can be CPU demanding
                // But in this case it may help with code-readability

                try{
                npcBehaviour(this);}
                catch(IllegalArgumentException i){
                    ;
                }
                // Let`s assume it`s how long one-month takes//
                Thread.sleep(860);
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
