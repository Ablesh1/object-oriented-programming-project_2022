package com.example.po;

import javafx.application.Application;
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

public class CurrencyRate extends Department{

    private final String[] currencyDataBase = new String[8];
    public String[] getCurrencyDataBase() {
        return currencyDataBase;
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Hall.class.getResource("CurrencyRate.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Bank Visual Novel");
        stage.setScene(scene);
        stage.show();
    }

    public CurrencyRate() {
        super();
    }

    @Override
    public void refresh() throws IOException {
        this.CurrencyReader();
        try{
            System.out.println("Counters do calculations");
            Thread.sleep(2600);
        } catch (InterruptedException interruptedException) {
            return;
        }
    }

    public String CurrencyRates(String key, String urlCode) throws IOException {

        URL url = new URL(urlCode);
        URLConnection urlConnection = url.openConnection();
        InputStreamReader inputStreamReader = new InputStreamReader(urlConnection.getInputStream());
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        String price = "Not found";
        String line = bufferedReader.readLine();
        while(line != null) {
            if(line.contains(key)) {
                int target = line.indexOf(key);
                int deci = line.indexOf(".", target);
                int start = deci;
                while(line.charAt(start) != '[') {
                    start --;
                }
                price = line.substring(start + 1, deci + 5);
            }
            line = bufferedReader.readLine();
        }
        price = (key.substring(1, 4) + "\t--> PLN\t\t" + price);
        return price;
    }

    public void CurrencyReader() throws IOException {

        String keyUSD = "\"USD / PLN\"";
        String urlUSD = "https://www.google.com/finance?q=ko&ei=ga4QWNiFOobBe4LShvAF";

        String keyEUR = "\"EUR / PLN\"";
        String urlEUR = "https://www.google.com/finance/quote/EUR-PLN";

        String keyGBP = "\"GBP / PLN\"";
        String urlGBP = "https://www.google.com/finance/quote/GBP-PLN";

        String keyCHF = "\"CHF / PLN\"";
        String urlCHF = "https://www.google.com/finance/quote/CHF-PLN";

        String keyJPY = "\"JPY / PLN\"";
        String urlJPY = "https://www.google.com/finance/quote/JPY-PLN";

        String keyINR = "\"INR / PLN\"";
        String urlINR = "https://www.google.com/finance/quote/INR-PLN";

        String keyCNY = "\"CNY / PLN\"";
        String urlCNY = "https://www.google.com/finance/quote/CNY-PLN";

        String keyAUD = "\"AUD / PLN\"";
        String urlAUD = "https://www.google.com/finance/quote/AUD-PLN";

        this.currencyDataBase[0] = CurrencyRates(keyUSD, urlUSD);
        this.currencyDataBase[1] = CurrencyRates(keyEUR, urlEUR);
        this.currencyDataBase[2] = CurrencyRates(keyGBP, urlGBP);
        this.currencyDataBase[3] = CurrencyRates(keyCHF, urlCHF);
        this.currencyDataBase[4] = CurrencyRates(keyJPY, urlJPY);
        this.currencyDataBase[5] = CurrencyRates(keyINR, urlINR);
        this.currencyDataBase[6] = CurrencyRates(keyCNY, urlCNY);
        this.currencyDataBase[7] = CurrencyRates(keyAUD, urlAUD);
    }
}



