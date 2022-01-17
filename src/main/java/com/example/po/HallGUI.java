package com.example.po;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HallGUI extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        BankBackend bankBackend = new BankBackend();
        FXMLLoader fxmlLoader = new FXMLLoader(HallGUI.class.getResource("Hall.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Bank Visual Novel");
        stage.setScene(scene);
        stage.show();
        TransfersDep transfers = new TransfersDep();
        AccountsDep accounts = new AccountsDep();
        ReportsDep reports = new ReportsDep();
        CountersDep counters = new CountersDep();
        CurrencyRate currencyRate = new CurrencyRate();
        StockRateDep stockRate = new StockRateDep();
    }

 //   public static void main(String[] args) {
 //       launch();
 //   }
}