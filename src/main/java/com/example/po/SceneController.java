package com.example.po;

import com.example.po.backends.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;

import java.io.IOException;

public class SceneController{

    @FXML
    TextArea CurrencyArea;
    @FXML
    TextArea StockArea;
    @FXML
    TextField ATMArea;
    @FXML
    TextField CurrentMoneyArea;
    @FXML
    Button DepositButton;


    //private CurrencyRateDepBack currencyRate;
    //private StockRateDepBack stockRate;

    private Stage stage;
    private Scene scene;
    private Parent root;

    //This is the least convoluted and safest way of
    //Assigning the bank to the GUI
    private BankBackend bankBackend;
    //Kontrolne do wyłączania
    private TransfersDepBack transfers;
    private AccountsDepBack accounts;
    private ReportsDepBack reports;
    private OfficeGUI office;
    private CountersDepBack counters;

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

    public void switchToTransfers(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Transfers.fxml"));
        stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToReports(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Reports.fxml"));
        stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToAccounts(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Accounts.fxml"));
        stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void updateCurrentMoney(){
        NPC player = bankBackend.getClient(1);
        CurrentMoneyArea.setText(String.valueOf(player.howMuchMoney()));
    }

    public void deposit(ActionEvent event) throws IOException{
        NPC player = bankBackend.getClient(1);
        try{
        player.deposit(Integer.valueOf(ATMArea.getText()));}
        catch(NumberFormatException e){
            ;
        }
        updateCurrentMoney();
    }
    public void withdraw(ActionEvent event) throws IOException{
        NPC player = bankBackend.getClient(1);
        try{
            player.deposit(Integer.valueOf(ATMArea.getText()) * -1);
        }
        catch(NumberFormatException e){
            ;
        }
        updateCurrentMoney();
    }

    public void toATM(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("ATM.fxml"));
        stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void OnClicked(ActionEvent event) throws IOException {
        System.out.println("Something happened");
    }

    //Wyświetlanie obecnej giełdy spółek i walut
    public void ShowCurrency() throws IOException{
        Thread threadCurrency = new Thread(new Runnable() {
            @Override
            public void run() {

                CurrencyRateDepBack HelperCurrency = bankBackend.getCurrencyRate();
                String[] Binder = HelperCurrency.getCurrencyDataBase();
                while(Binder[7] == null){
                    try{
                        Thread.sleep(10);
                        System.out.println("Bruh");
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

    public void ShowStock() throws IOException {
        Thread threadStock = new Thread(new Runnable() {
            @Override
            public void run() {

                StockRateDepBack HelperStock = bankBackend.getStockRate();
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

    //Wychodzenie z programu
    public void exit(ActionEvent event){
        System.exit(2137);
        accounts.kill();
        transfers.kill();
        reports.kill();
        counters.kill();
    }
}
