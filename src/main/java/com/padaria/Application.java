package com.padaria;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Classe principal da aplicação que inicializa a interface gráfica.
 */
public class Application extends javafx.application.Application {

    /**
     * Método de inicialização da aplicação.
     *
     * @param primaryStage o palco principal da aplicação
     */
    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/home.fxml"));
            primaryStage.getIcons().add(new Image(getClass().getResource("/images/icon.png").toString()));
            primaryStage.setScene(new Scene(root, 1280, 720));
            primaryStage.setResizable(false);
            primaryStage.setTitle("Forno Mágico");
            primaryStage.show();
        } catch (Exception e) {
            System.out.println("Erro ao carregar a tela principal: " + e.getMessage());
        }
    }

    /**
     * Método principal que inicia a aplicação.
     *
     * @param args argumentos da linha de comando
     */
    public static void main(String[] args) {
        launch(args);
    }
}