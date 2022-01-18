package com.example.po;

import com.example.po.backends.BankBackend;
import javafx.stage.Stage;
import java.io.IOException;

public abstract class Department{

    //Departemant musi wiedzieć w którym jest banku
    BankBackend bankBackend;

    public BankBackend getBankBackend() {
        return bankBackend;
    }

    //Każdy departament ma własny wątek
    //Nie dziedziczymy po thread bo już dziedziczymy po Application
    private Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
            while(true){
                try {
                    refresh();
                    Thread.sleep(200);
                }
                catch(InterruptedException | IOException interruptedException){
                    System.out.println("AAAAAAA");
                    return;
                }
            }
        }
    });

    public Department(){
        thread.start();
    }

    public Department(BankBackend bankBackend){
        this.bankBackend = bankBackend;
        thread.start();
    }

    //@Override
    public void start(Stage primaryStage) throws IOException {

    }

    //Tutaj panele będą się aktualizować oraz zbierać dane z innych klas
    //Które będą cały czas działały w tle
    //Np. Bazy danych klinetów
    public void refresh() throws IOException {
        System.out.println("Bruh");
        //Do something
        ;
    }

    public int kill(){
        thread.interrupt();
        System.out.println("Thread killed");
        return 0;
    }
}
