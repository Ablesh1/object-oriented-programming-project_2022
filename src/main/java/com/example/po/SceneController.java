package com.example.po;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    //Kontrolne do wyłączania
    private Przelewy przelewy;
    private Rachunki rachunki;
    private Raporty raporty;
    private Biura biura;

    //Kontroler SceneController odpala się za każdym
    //Przejściem między scenami
    //Uwaga

    public void switchToBiura(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Biura.fxml"));
        stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToHol(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Hol.fxml"));
        stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    public void switchToPrzelewy(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Przelewy.fxml"));
        stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();


    }

    public void switchToRaporty(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Raporty.fxml"));
        stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();


    }

    public void switchToRachunki(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Rachunki.fxml"));
        stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();




    }

    public void OnClicked(ActionEvent event) throws IOException {
        System.out.println("Coś się stało");
    }

    //Wychodzenie z programu
    public void exit(ActionEvent event){
        System.exit(2137);
        rachunki.kill();
        przelewy.kill();
        raporty.kill();
    }
}
