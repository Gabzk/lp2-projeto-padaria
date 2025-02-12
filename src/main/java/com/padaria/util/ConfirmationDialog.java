package com.padaria.util;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ConfirmationDialog {

    /**
     * Exibe o diálogo de confirmação de forma síncrona.
     * O método bloqueia até que o usuário clique em "Sim" ou "Não".
     *
     * @param owner   o Stage proprietário
     * @param message a mensagem a ser exibida
     * @return true se o usuário confirmar; false caso contrário.
     */
    public static boolean show(Stage owner, String message) {
        // Usamos um array para armazenar a resposta, pois precisamos de uma variável final/mutável
        final boolean[] result = new boolean[1];

        // Cria o Stage do diálogo
        Stage stage = new Stage();
        stage.initOwner(owner);
        stage.initModality(Modality.APPLICATION_MODAL); // Garante que a janela seja modal
        stage.initStyle(StageStyle.UTILITY);
        stage.setResizable(false);

        // Impede que o usuário feche a janela pelo botão de fechar (X)
        stage.setOnCloseRequest(e -> e.consume());

        // Configuração do Label com a mensagem
        Label label = new Label(message);
        label.setStyle("-fx-font-size: 16px; -fx-text-fill: #333;");
        label.setWrapText(true);
        label.setMaxWidth(350);

        // Botão "Sim"
        Button btnYes = new Button("Sim");
        btnYes.setStyle("-fx-background-color: #28a745; -fx-text-fill: white; -fx-font-size: 16px;");
        btnYes.setMinWidth(80);
        btnYes.setOnAction(e -> {
            result[0] = true;
            stage.close();
        });

        // Botão "Não"
        Button btnNo = new Button("Não");
        btnNo.setStyle("-fx-background-color: #dc3545; -fx-text-fill: white; -fx-font-size: 16px;");
        btnNo.setMinWidth(80);
        btnNo.setOnAction(e -> {
            result[0] = false;
            stage.close();
        });

        // Layout para os botões
        HBox buttonBox = new HBox(15, btnYes, btnNo);
        buttonBox.setAlignment(Pos.CENTER);

        // Layout principal da caixa de diálogo
        VBox root = new VBox(25, label, buttonBox);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(25));
        root.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-border-radius: 12;");

        // Adiciona um efeito de sombra
        StackPane container = new StackPane(root);
        container.setStyle("-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 10, 0.5, 0, 1);");

        // Configura a cena
        Scene scene = new Scene(container);
        stage.setScene(scene);
        stage.sizeToScene();

        // Posicionamento adiado para centralizar o diálogo em relação ao Stage proprietário
        Platform.runLater(() -> {
            double ownerX = owner.getX();
            double ownerY = owner.getY();
            double ownerWidth = owner.getWidth();
            double ownerHeight = owner.getHeight();
            double stageWidth = stage.getWidth();
            double stageHeight = stage.getHeight();
            stage.setX(ownerX + (ownerWidth - stageWidth) / 2);
            stage.setY(ownerY + (ownerHeight - stageHeight) / 2);
        });

        // Exibe o diálogo de forma síncrona (bloqueia até o fechamento)
        stage.showAndWait();

        return result[0];
    }
}