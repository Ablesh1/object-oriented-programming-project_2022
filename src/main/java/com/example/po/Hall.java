package com.example.po;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Hall extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Hall.class.getResource("Hall.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Bank Visual Novel");
        stage.setScene(scene);
        stage.show();
        Transfers transfers = new Transfers();
        Accounts accounts = new Accounts();
        Reports reports = new Reports();
        Counters counters = new Counters();
        CurrencyRate currencyRate = new CurrencyRate();
        StockRate stockRate = new StockRate();
    }

 //   public static void main(String[] args) {
 //       launch();
 //   }
}