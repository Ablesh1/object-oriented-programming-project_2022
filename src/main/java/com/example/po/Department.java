package com.example.po;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public abstract class Department extends Application{

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

    public void kill(){
        thread.interrupt();
        System.out.println("Thread killed");
    }

    //Tutaj panele będą się aktualizować oraz zbierać dane z innych klas
    //Które będą cały czas działały w tle
    //Np. Bazy danych klinetów
    public void refresh() throws IOException {
        System.out.println("Bruh");
        //Do something
        ;
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

    }
}
