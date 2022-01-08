package com.example.po;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Hol extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Hol.class.getResource("Hol.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Bank Visual Novel");
        stage.setScene(scene);
        stage.show();
        Przelewy przelewy = new Przelewy();
        Rachunki rachunki = new Rachunki();
        Raporty raporty = new Raporty();
    }

 //   public static void main(String[] args) {
 //       launch();
 //   }
}