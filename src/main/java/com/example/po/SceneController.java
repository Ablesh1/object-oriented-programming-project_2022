package com.example.po;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;

public class SceneController {

    CurrencyRate currencyRate = new CurrencyRate();

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

    public void ShowCurrencies() throws IOException {
        System.out.println(Arrays.toString(this.currencyRate.getCurrencyDataBase()));
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
