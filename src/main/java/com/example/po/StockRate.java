package com.example.po;

import javafx.application.Application;
import javafx.beans.binding.When;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class StockRate extends Department{

    private final String[] stockDataBase = new String[8];
    public String[] getStockDataBase() {
        return stockDataBase;
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Hall.class.getResource("StockRate.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Bank Visual Novel");
        stage.setScene(scene);
        stage.show();
    }

    public StockRate() {
        super();
    }

    @Override
    public void refresh() throws IOException {
        this.StockReader();
        try{
            System.out.println("Counters do calculations");
            Thread.sleep(2600);
        } catch (InterruptedException interruptedException) {
            return;
        }
    }

    public String StockRates(String key, String urlCode) throws IOException {

        URL url = new URL(urlCode);
        URLConnection urlConnection = url.openConnection();
        InputStreamReader inputStreamReader = new InputStreamReader(urlConnection.getInputStream());
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        String price = "Not found";
        String title = "Not found";
        String line = bufferedReader.readLine();

        while(line != null) {
            int target = line.indexOf(key);
            if(line.contains(key)) {
                ///////////////////////////////////////////////////////////////////
                int deci = line.indexOf(".", target);
                int start = deci;

                while(line.charAt(start) != '[') {
                    start --;
                }
                price = line.substring(start + 1, deci + 3);
                if(price.contains(",")) {
                    price = price.substring(0, price.length()- 1) + "0";
                }
                ///////////////////////////////////////////////////////////////////
                int quot = line.indexOf("[\"", target);
                int end = quot;

                while(line.charAt(end) != ',') {
                    end ++;
                }
                title = line.substring(quot + 2, end - 1);
                ///////////////////////////////////////////////////////////////////
            }
            line = bufferedReader.readLine();
        }
        return title + "\t---> USD\t\t" + price ;
    }

    public void StockReader() throws IOException {

        String keyFB = "[\"FB\"";
        String urlFB = "https://www.google.com/finance/quote/FB:NASDAQ";

        String keyORCL= "[\"ORCL\"";
        String urlORCL = "https://www.google.com/finance/quote/ORCL:NYSE";

        String keyTSLA = "[\"TSLA\"";
        String urlTSLA = "https://www.google.com/finance/quote/TSLA:NASDAQ";

        String keyAAPL = "[\"AAPL\"";
        String urlAAPL = "https://www.google.com/finance/quote/AAPL:NASDAQ";

        String keyMSFT = "[\"MSFT\"";
        String urlMSFT = "https://www.google.com/finance/quote/MSFT:NASDAQ";

        String keyKO = "[\"KO\"";
        String urlKO = "https://www.google.com/finance/quote/KO:NYSE";

        String keyGOOG = "[\"GOOG\"";
        String urlGOOG = "https://www.google.com/finance/quote/GOOG:NASDAQ";

        String keyCSCO = "[\"CSCO\"";
        String urlCSCO = "https://www.google.com/finance/quote/CSCO:NASDAQ";

        this.stockDataBase[0] = StockRates(keyFB, urlFB);
        this.stockDataBase[1] = StockRates(keyORCL, urlORCL);
        this.stockDataBase[2] = StockRates(keyTSLA, urlTSLA);
        this.stockDataBase[3] = StockRates(keyAAPL, urlAAPL);
        this.stockDataBase[4] = StockRates(keyMSFT, urlMSFT);
        this.stockDataBase[5] = StockRates(keyKO, urlKO);
        this.stockDataBase[6] = StockRates(keyGOOG, urlGOOG);
        this.stockDataBase[7] = StockRates(keyCSCO, urlCSCO);

    }
}



