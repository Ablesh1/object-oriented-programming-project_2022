package com.example.po;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class Counters extends Department{
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Hall.class.getResource("Counters.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Bank Visual Novel");
        stage.setScene(scene);
        stage.show();
    }

    public Counters() {
        super();
    }

    @Override
    public void refresh(){
        try{
            System.out.println("Counters do calculations");
            Thread.sleep(2600);
        } catch (InterruptedException interruptedException) {
            return;
        }
    }
}
