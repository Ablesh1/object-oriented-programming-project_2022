package com.example.po;

import com.example.po.NPChandling.NPC;
import com.example.po.backends.*;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class SceneController{

    private int where;

    //Bank account
    @FXML
    Button UpdateButton;

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //Stocks
    @FXML
    TextArea CurrencyArea;
    @FXML
    TextArea StockArea;

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //Deposits and withdraws
    @FXML
    TextField ATMArea;
    @FXML
    TextField CurrentMoneyArea;
    @FXML
    TextField BelongingsArea;
    @FXML
    Button DepositButton;
    @FXML
    Button WithdrawButton;

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //Loans
    @FXML
    TextField LoanArea;
    @FXML
    TextField InstallmentNumber;
    @FXML
    TextField LoanLeft;
    @FXML
    TextField InstallmentAmount;
    @FXML
    Button LoanButton;
    @FXML
    Button PayButton;

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //Investments
    @FXML
    TextField InvestmentArea;
    @FXML
    TextField InvestmentDuration;
    @FXML
    TextField InvestmentAmount;
    @FXML
    Button InvestmentButton;
    @FXML
    Button CloseInvestButton;

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //Office part
    @FXML
    TextArea depositsTextArea;
    @FXML
    TextArea withdrawsTextArea;
    @FXML
    TextArea transfersTextArea;
    @FXML
    TextArea accountsTextArea;
    @FXML
    ComboBox accountsComboBox;

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //Create client part
    @FXML
    Button clientAddButton;
    @FXML
    Button clientDiscardButton;
    @FXML
    TextField nameTextField;
    @FXML
    TextField surnameTextField;
    @FXML
    TextField persBelongsTextField;
    @FXML
    TextField peselTextField;
    @FXML
    TextField characterComboBox;
    @FXML
    TextArea addClientTextArea;
    @FXML
    TextField monthlyIncomeTextArea;

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private Stage stage;
    private Scene scene;
    private Parent root;

    //This is the least convoluted and safest way of
    //Assigning the bank to the GUI
    private BankBackend bankBackend;
    //Kontrolne do wyłączania
    private TransfersDep transfers;
    private AccountsDep accounts;
    private ReportsDep reports;
    private OfficeGUI office;
    private CountersDep counters;

    //Kontroler SceneController odpala się za każdym
    //Przejściem między scenami
    //Uwaga

    public SceneController() {
        this.bankBackend = Global.bankBackend;
    }

    public void switchToHall(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Hall.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToOffice(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Office.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        this.where = 0;
    }

    public void switchToCounters(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Counters.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void switchToBankAccount(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("BankAccount.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToCurrencyRate(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("CurrencyRate.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToStockRate(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("StockRate.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToATM(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("ATM.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToLoan(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("BankLoan.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToInvestment(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("BankInvestment.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void switchToTransfers(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Transfers.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void switchToReports(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Reports.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToDepositsReport(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("DepositsReport.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToWithdrawsReport(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("WithdrawsReport.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToBigStockRate(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("BigStockRate.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void switchToAccounts(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Accounts.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        this.where = 1;
    }

    public void switchToCreation(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("ClientCreator.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        this.where = 2;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void addClient() {
        try {
            String name = nameTextField.getText();
            String surname = surnameTextField.getText();
            Integer pesel = Integer.valueOf(peselTextField.getText());
            Double personBelongs = Double.valueOf(persBelongsTextField.getText());
            String personality = characterComboBox.getText();
            Integer idNum = bankBackend.getDatabase().size() + 1;
            Double monthlyIncome = Double.valueOf(monthlyIncomeTextArea.getText());

            NPC newNPC = new NPC(idNum, name, surname, pesel, 1000.0, 0.0, 1, true, 0.0, 0, bankBackend, personBelongs, personality, monthlyIncome);
            bankBackend.addClient(newNPC);
        } catch (NumberFormatException f) {
            addClientTextArea.setText("\n       Pass the correct values: \n\n" +
                                      "    Possible personalities are:\n\n" +
                                      "\t\t  Normal\n" +
                                      "\t\t  Charitable\n" +
                                      "\t\t  Lucky\n" +
                                      "\t\t  Evil\n" +
                                      "\t\t  可愛い\n" +
                                      "\t\t  Madao");
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //Method that rounds output to n decimal places
    public static Double roundAvoid(Double value, int places) {
        double scale = Math.pow(10, places);
        return Math.round(value * scale) / scale;
    }

    //Temporary method for the buttons
    public void OnClicked(ActionEvent event) throws IOException {
        System.out.println("Something happened");
    }

    /////////////////////////////////////////////////////////////////

    public void updateCurrentMoney() {
        NPC player = bankBackend.getClient(1);
        CurrentMoneyArea.setText(String.valueOf(roundAvoid(player.getAccountMoney(), 2)));
    }

    public void updatePersonBelongings() {
        NPC player = bankBackend.getClient(1);
        BelongingsArea.setText(String.valueOf(roundAvoid(player.getPersonBelongings(), 2)));
    }

    public void updateInstallmentAmount() {
        NPC player = bankBackend.getClient(1);
        InstallmentAmount.setText(String.valueOf(roundAvoid(player.getInstallmentAmount(), 2)));
    }

    public void updateActualDebt() {
        NPC player = bankBackend.getClient(1);
        LoanLeft.setText(String.valueOf(roundAvoid(player.getActualDebt(), 2)));
    }

    public void updateInvestment() {
        NPC player = bankBackend.getClient(1);
        InvestmentAmount.setText(String.valueOf(roundAvoid(player.getBankInvestment(), 2)));
    }

    /////////////////////////////////////////////////////////////////

    public void updateTransfers() {
        where = 0;
        transfersTextArea.clear();
        //ArrayList<String> transfers = bankBackend.getTransfersDep().showLastTransfers();
        Thread transfersThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(where == 3){
                    StringBuilder stringBuilder = new StringBuilder();
                    ArrayList<String> transfers = bankBackend.getTransfersDep().showLastTransfers();
                    for (int i = 0; i < transfers.size(); i++) {
                        stringBuilder.append(transfers.get(i) + "\n");
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    transfersTextArea.setText(String.valueOf(stringBuilder));
                }
                return;
            }
        });
        /*
        for (int i = 0; i < transfers.size(); i++) {
            transfersTextArea.appendText(transfers.get(i) + "\n");
        }*/
        where = 3;
        transfersThread.start();
    }

    /////////////////////////////////////////////////////////////////

    public void updateDeposits() {
        where = 0;
        depositsTextArea.clear();
        //ArrayList<String> deposits = bankBackend.getTransfersDep().showLastDeposits();
        Thread depositsThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(where == 3){
                    StringBuilder stringBuilder = new StringBuilder();
                    ArrayList<String> deposits = bankBackend.getReportsDep().showLastDeposits();
                    for (int i = 0; i < deposits.size(); i++) {
                        stringBuilder.append(deposits.get(i) + "\n");
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    depositsTextArea.setText(String.valueOf(stringBuilder));
                }
                return;
            }
        });
        /*
        for (int i = 0; i < deposits.size(); i++) {
            depositsTextArea.appendText(deposits.get(i) + "\n");
        }*/
        where = 3;
        depositsThread.start();
    }

    public void updateWithdraws() {
        where = 0;
        withdrawsTextArea.clear();
        //ArrayList<String> withdraws = bankBackend.getTransfersDep().showLastWithdraws();
        Thread withdrawsThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(where == 3){
                    StringBuilder stringBuilder = new StringBuilder();
                    ArrayList<String> withdraws = bankBackend.getReportsDep().showLastWithdraws();
                    for (int i = 0; i < withdraws.size(); i++) {
                        stringBuilder.append(withdraws.get(i) + "\n");
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    withdrawsTextArea.setText(String.valueOf(stringBuilder));
                }
                return;
            }
        });
        /*
        for (int i = 0; i < withdraws.size(); i++) {
            withdrawsTextArea.appendText(withdraws.get(i) + "\n");
        }*/
        where = 3;
        withdrawsThread.start();
    }

    /////////////////////////////////////////////////////////////////

    public void updateAccounts() {
        this.where = 0;
        try {
            accountsComboBox.getItems().clear();
        }
        catch (Exception e){
            System.out.println(e);
            return;
        }

        HashMap<Integer, NPC> npcs = bankBackend.getDatabase();

        for (int i = 1; i <= npcs.size(); i++) {
            accountsComboBox.getItems().add(i);
    }
        this.where = 1;
        try{
        comboAction();}
        catch (NegativeArraySizeException n){
            accountsComboBox.getItems().clear();
            updateAccounts();
        }
    }

    public void killHimNow() {
        if (bankBackend.getClient((Integer) accountsComboBox.getSelectionModel().getSelectedItem()) != null) {
            NPC serialSuicide = bankBackend.getClient((Integer) accountsComboBox.getSelectionModel().getSelectedItem());
            if (serialSuicide.getPersonID() != 1) {
                serialSuicide.suicide();
                try {
                    updateAccounts();
                } catch (Exception exception) {
                    ;
                }
                //System.out.println(bankBackend.getDatabase());
            }
        }
    }

    public ComboBox getAccountComboBox(){
        return this.accountsComboBox;
    }

    public TextArea getAccountTextArea() {
        return this.accountsTextArea;
    }


    //Event event
    @FXML
    private void comboAction(){

       /* NPC sNPC = bankBackend.getClient((Integer) 1);

        if (sNPC != null) {

            //Sometimes there may be an error here
            //Not sure what causes it
            //aTA.clear();

            try{
                accountsTextArea.setText("\n\t   Mr/Mrs:\t\t\t\t " + sNPC.getPersonName() + " " + sNPC.getSurname()
                        + "\n\t   Account balance:\t\t " + roundAvoid(sNPC.getAccountMoney(), 2)
                        + "\n\t   Debt:\t\t\t\t " + roundAvoid(sNPC.getActualDebt(), 2)
                        + "\n\t   Investment:\t\t\t " + roundAvoid(sNPC.getBankInvestment(), 2)); }
            catch(Exception n){
                where = 0;
            }} */

        Thread accountsThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(where == 6){
                    ComboBox aCB = getAccountComboBox();
                    TextArea aTA = getAccountTextArea();
                    NPC sNPC = bankBackend.getClient((Integer) aCB.getSelectionModel().getSelectedItem());

                    if (sNPC != null) {

                        //Sometimes there may be an error here
                        //Not sure what causes it
                        //aTA.clear();

                        try{
                        aTA.setText("\n\t   Mr/Mrs:\t\t\t\t " + sNPC.getPersonName() + " " + sNPC.getSurname()
                                + "\n\t   Account balance:\t\t " + roundAvoid(sNPC.getAccountMoney(), 2)
                                + "\n\t   Debt:\t\t\t\t " + roundAvoid(sNPC.getActualDebt(), 2)
                                + "\n\t   Investment:\t\t\t " + roundAvoid(sNPC.getBankInvestment(), 2)); }
                        catch(NegativeArraySizeException | IndexOutOfBoundsException n){
                            where = 0;
                            return;
                        }

                        try {
                            Thread.sleep(200);
                        }
                        catch (InterruptedException e) {
                            return;
                        }
                    }
                    else {
                        return;
                    }
                }}
        });

        //It`s to ensure that only one thread exist
        if(where != 6)
        where = 6;
        accountsThread.start();
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void updateEverything(ActionEvent event) throws IOException {
        NPC player = bankBackend.getClient(1);

        updateCurrentMoney();
        updateInvestment();
        updateActualDebt();
    }

    public void deposit(ActionEvent event) throws IOException {
        NPC player = bankBackend.getClient(1);
        try {
            player.deposit(Integer.valueOf(ATMArea.getText()));
        } catch (NumberFormatException e) {
            ;
        }
        updateCurrentMoney();
        updatePersonBelongings();
    }

    public void withdraw(ActionEvent event) throws IOException {
        NPC player = bankBackend.getClient(1);
        try {
            player.withdraw(Integer.valueOf(ATMArea.getText()));
        } catch (NumberFormatException e) {
            ;
        }
        updateCurrentMoney();
        updatePersonBelongings();
    }

    public void loan(ActionEvent event) throws IOException {
        NPC player = bankBackend.getClient(1);
        try {
            player.takeLoan(Double.valueOf(LoanArea.getText()), Integer.valueOf(InstallmentNumber.getText()));
        } catch (NumberFormatException e) {
            ;
        }
        updateCurrentMoney();
        updateInstallmentAmount();
        updateActualDebt();
    }

    public void pay(ActionEvent event) throws IOException {
        NPC player = bankBackend.getClient(1);
        try {
            player.payLoan();
        } catch (NumberFormatException e) {
            ;
        }
        updateCurrentMoney();
        updateInstallmentAmount();
        updateActualDebt();
    }

    public void investment(ActionEvent event) throws IOException {
        NPC player = bankBackend.getClient(1);
        try {
            player.makeInvestment(Double.valueOf(InvestmentArea.getText()), Integer.valueOf(InvestmentDuration.getText()));
        } catch (NumberFormatException e) {
            ;
        }
        updateCurrentMoney();
        updateInvestment();
    }

    public void close(ActionEvent event) throws IOException {
        NPC player = bankBackend.getClient(1);
        try {
            player.closeInvestment();
        } catch (NumberFormatException e) {
            ;
        }
        updateCurrentMoney();
        updateInvestment();
    }

    //Display Stock and Currencies
    public void showCurrency() throws IOException {
        Thread threadCurrency = new Thread(new Runnable() {
            @Override
            public void run() {

                CurrencyRateDep HelperCurrency = bankBackend.getCurrencyRate();
                String[] Binder = HelperCurrency.getCurrencyDataBase();
                while (Binder[7] == null) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException i) {
                        ;
                    }
                }
                CurrencyArea.setText("\n     " + Binder[0] + "\n");
                CurrencyArea.appendText("     " + Binder[1] + "\n");
                CurrencyArea.appendText("     " + Binder[2] + "\n");
                CurrencyArea.appendText("     " + Binder[3] + "\n");
                CurrencyArea.appendText("     " + Binder[4] + "\n");
                CurrencyArea.appendText("     " + Binder[5] + "\n");
                CurrencyArea.appendText("     " + Binder[6] + "\n");
                CurrencyArea.appendText("     " + Binder[7] + "\n");
            }
        });
        threadCurrency.start();
    }

    public void showStock() throws IOException {
        Thread threadStock = new Thread(new Runnable() {
            @Override
            public void run() {

                StockRateDep HelperStock = bankBackend.getStockRate();
                String[] Binder = HelperStock.getStockDataBase();
                while (Binder[7] == null) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException i) {
                        ;
                    }
                }
                StockArea.setText("\n     " + Binder[0] + "\n");
                StockArea.appendText("     " + Binder[1] + "\n");
                StockArea.appendText("     " + Binder[2] + "\n");
                StockArea.appendText("     " + Binder[3] + "\n");
                StockArea.appendText("     " + Binder[4] + "\n");
                StockArea.appendText("     " + Binder[5] + "\n");
                StockArea.appendText("     " + Binder[6] + "\n");
                StockArea.appendText("     " + Binder[7] + "\n");
            }
        });
        threadStock.start();
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //Exit program
    public void exit(ActionEvent event) {
        if(bankBackend == null){
            this.bankBackend = Global.bankBackend;
        }

        bankBackend.saver(bankBackend.getDatabase());
        System.exit(2137);
    }
}
