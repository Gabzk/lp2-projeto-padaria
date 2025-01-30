package com.padaria;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/application.fxml"));
            primaryStage.getIcons().add(new Image(getClass().getResource("/images/icon.png").toString()));
            primaryStage.setScene(new Scene(root, 960, 720));
            primaryStage.setTitle("Forno Mágico");
            primaryStage.show();
        } catch (Exception e) {
            System.out.println("Erro ao carregar a tela principal: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}