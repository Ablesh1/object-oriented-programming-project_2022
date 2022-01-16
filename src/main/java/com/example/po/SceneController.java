package com.example.po;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;

public class SceneController {

    @FXML
    TextArea CurrencyArea;
    @FXML
    TextArea StockArea;

    CurrencyRate currencyRate = new CurrencyRate();
    StockRate stockRate = new StockRate();

    private Stage stage;
    private Scene scene;
    private Parent root;

    //Kontrolne do wyłączania
    private Transfers transfers;
    private Accounts accounts;
    private Reports reports;
    private Office office;
    private Counters counters;

    //Kontroler SceneController odpala się za każdym
    //Przejściem między scenami
    //Uwaga

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

    public void OnClicked(ActionEvent event) throws IOException {
        System.out.println("Something happened");
    }

    public void ShowCurrency() throws IOException {
        String[] Binder = this.currencyRate.getCurrencyDataBase();
        CurrencyArea.setText(    "\n     " + Binder[0] + "\n");
        CurrencyArea.appendText("     " + Binder[1] + "\n");
        CurrencyArea.appendText("     " + Binder[2] + "\n");
        CurrencyArea.appendText("     " + Binder[3] + "\n");
        CurrencyArea.appendText("     " + Binder[4] + "\n");
        CurrencyArea.appendText("     " + Binder[5] + "\n");
        CurrencyArea.appendText("     " + Binder[6] + "\n");
        CurrencyArea.appendText("     " + Binder[7] + "\n");
    }
    public void ShowStock() throws IOException {
        String[] Binder = this.stockRate.getStockDataBase();
        StockArea.setText(    "\n     " + Binder[0] + "\n");
        StockArea.appendText("     " + Binder[1] + "\n");
        StockArea.appendText("     " + Binder[2] + "\n");
        StockArea.appendText("     " + Binder[3] + "\n");
        StockArea.appendText("     " + Binder[4] + "\n");
        StockArea.appendText("     " + Binder[5] + "\n");
        StockArea.appendText("     " + Binder[6] + "\n");
        StockArea.appendText("     " + Binder[7] + "\n");
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
