package com.example.po;

import com.example.po.NPChandling.NPC;
import com.example.po.backends.*;
import javafx.event.ActionEvent;
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

public class SceneController{

    //Bank account
    @FXML
    Button UpdateButton;

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //Ttocks
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
    TextArea transfersTextArea;
    @FXML
    TextArea accountsTextArea;
    @FXML
    ComboBox accountsComboBox;

    //private CurrencyRateDepBack currencyRate;
    //private StockRateDepBack stockRate;

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

    public SceneController(){
        this.bankBackend = Global.bankBackend;
    }

    public void switchToHall(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Hall.fxml"));
        stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToOffice(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Office.fxml"));
        stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToCounters(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Counters.fxml"));
        stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void switchToBankAccount(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("BankAccount.fxml"));
        stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToCurrencyRate(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("CurrencyRate.fxml"));
        stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToStockRate(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("StockRate.fxml"));
        stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToATM(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("ATM.fxml"));
        stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToLoan(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("BankLoan.fxml"));
        stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToInvestment(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("BankInvestment.fxml"));
        stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void switchToTransfers(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Transfers.fxml"));
        stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void switchToReports(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Reports.fxml"));
        stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToClientReport(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("ClientReport.fxml"));
        stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToBigStockRate(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("BigStockRate.fxml"));
        stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void switchToAccounts(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Accounts.fxml"));
        stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //I don't like this name
    public void switchToTransferReports(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("TransfersReport.fxml"));
        stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //Method that rounds output to n decimal places
    public static Double roundAvoid(Double value, int places) {
        double scale = Math.pow(10, places);
        return Math.round(value * scale) / scale;
    }

    public void OnClicked(ActionEvent event) throws IOException {
        System.out.println("Something happened");
    }

    /////////////////////////////////////////////////////////////////

    public void updateCurrentMoney(){
        NPC player = bankBackend.getClient(1);
        CurrentMoneyArea.setText(String.valueOf(roundAvoid(player.getAccountMoney(), 2)));
    }

    public void updatePersonBelongings() {
        NPC player = bankBackend.getClient(1);
        BelongingsArea.setText(String.valueOf(roundAvoid(player.getPersonBelongings(), 2)));
    }

    public void updateInstallmentAmount() {
        NPC player = bankBackend.getClient(1);
        InstallmentAmount.setText(String.valueOf(roundAvoid(player.getInstallmentAmount(),2)));
    }

    public void updateActualDebt() {
        NPC player = bankBackend.getClient(1);
        LoanLeft.setText(String.valueOf(roundAvoid(player.getActualDebt(),2)));
    }

    public void updateInvestment() {
        NPC player = bankBackend.getClient(1);
        InvestmentAmount.setText(String.valueOf(roundAvoid(player.getBankInvestment(), 2)));
    }

    /////////////////////////////////////////////////////////////////

    public void updateTransfers(){
        transfersTextArea.clear();
        ArrayList<String> transfers = bankBackend.getTransfersDep().showLastTransfers();
        for(int i = 0; i < transfers.size(); i++){
            transfersTextArea.appendText(transfers.get(i) + "\n");
        }
    }

    public void updateAccounts(){
        ArrayList<NPC> npcs= new ArrayList<>();
        accountsComboBox.getItems().clear();
        bankBackend.getDatabase().forEach((k, v) -> {npcs.add(v); });
        for(int i = 0; i < npcs.size(); i++ ){
            //NPC cNPC = npcs.get(i);
            //accountsTextArea.appendText(cNPC.getPersonName() + " " + cNPC.getSurname() + " o id " + cNPC.getPersonID())
            accountsComboBox.getItems().add(npcs.get(i).getPersonID());
        }
    }

    @FXML
    private void comboAction(ActionEvent event) {
        NPC sNPC = bankBackend.getClient((Integer) accountsComboBox.getSelectionModel().getSelectedItem());
        accountsTextArea.clear();
        accountsTextArea.appendText("\n\t   Mr/Mrs:\t\t\t\t " + sNPC.getPersonName() + " " + sNPC.getSurname()
                                     + "\n\t   Account balance:\t\t " + roundAvoid(sNPC.getAccountMoney(), 2)
                                     + "\n\t   Debt:\t\t\t\t " + roundAvoid(sNPC.getActualDebt(), 2)
                                     + "\n\t   Investment:\t\t\t " + roundAvoid(sNPC.getBankInvestment(), 2));
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void updateEverything(ActionEvent event) throws IOException{
        NPC player = bankBackend.getClient(1);
        
        updateCurrentMoney();
        updateInvestment();
        updateActualDebt();
        //updatePersonBelongings();
        //updateInstallmentAmount();
    }

    public void deposit(ActionEvent event) throws IOException{
        NPC player = bankBackend.getClient(1);
        try{
            player.deposit(Integer.valueOf(ATMArea.getText()));
        }
        catch(NumberFormatException e){
            ;
        }
        updateCurrentMoney();
        updatePersonBelongings();
    }

    public void withdraw(ActionEvent event) throws IOException{
        NPC player = bankBackend.getClient(1);
        try{
            player.withdraw(Integer.valueOf(ATMArea.getText()));
        }
        catch(NumberFormatException e){
            ;
        }
        updateCurrentMoney();
        updatePersonBelongings();
    }

    public void loan(ActionEvent event) throws IOException{
        NPC player = bankBackend.getClient(1);
        try{
            player.takeLoan(Double.valueOf(LoanArea.getText()), Integer.valueOf(InstallmentNumber.getText()));
        }
        catch(NumberFormatException e){
            ;
        }
        updateCurrentMoney();
        updateInstallmentAmount();
        updateActualDebt();
    }

    public void pay(ActionEvent event) throws IOException {
        NPC player = bankBackend.getClient(1);
        try{
            player.payLoan();
        }
        catch(NumberFormatException e){
            ;
        }
        updateCurrentMoney();
        updateInstallmentAmount();
        updateActualDebt();
    }

    public void investment(ActionEvent event) throws IOException{
        NPC player = bankBackend.getClient(1);
        try{
            player.makeInvestment(Double.valueOf(InvestmentArea.getText()), Integer.valueOf(InvestmentDuration.getText()));
        }
        catch(NumberFormatException e){
            ;
        }
        updateCurrentMoney();
        updateInvestment();
    }

    public void close(ActionEvent event) throws IOException{
        NPC player = bankBackend.getClient(1);
        try{
            player.closeInvestment();
        }
        catch(NumberFormatException e){
            ;
        }
        updateCurrentMoney();
        updateInvestment();
    }

    //Display Stock and Currencies
    public void showCurrency() throws IOException{
        Thread threadCurrency = new Thread(new Runnable() {
            @Override
            public void run() {

                CurrencyRateDep HelperCurrency = bankBackend.getCurrencyRate();
                String[] Binder = HelperCurrency.getCurrencyDataBase();
                while(Binder[7] == null){
                    try{
                        Thread.sleep(10);
                    }
                    catch (InterruptedException i){
                        ;
                    }
                }
                CurrencyArea.setText(    "\n     " + Binder[0] + "\n");
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
                while(Binder[7] == null){
                    try{
                        Thread.sleep(10);
                    }
                    catch (InterruptedException i){
                        ;
                    }
                }
                StockArea.setText(    "\n     " + Binder[0] + "\n");
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
    public void exit(ActionEvent event){
        System.exit(2137);
        accounts.kill();
        transfers.kill();
        reports.kill();
        counters.kill();
    }
}
