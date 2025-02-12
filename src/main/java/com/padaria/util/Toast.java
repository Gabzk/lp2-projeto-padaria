package com.padaria.util;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class Toast {

    /**
     * Tipos de Toast disponíveis.
     */
    public enum ToastType {
        SUCCESS, ERROR, WARNING, INFO
    }

    /**
     * Posições possíveis para exibir o Toast.
     */
    public enum ToastPosition {
        TOP_CENTER, CENTER, BOTTOM_CENTER,
        TOP_RIGHT, BOTTOM_RIGHT, TOP_LEFT, BOTTOM_LEFT
    }

    private static final int DEFAULT_DURATION = 2000;
    private static final ToastPosition DEFAULT_POSITION = ToastPosition.BOTTOM_CENTER;

    /**
     * Exibe um Toast com mensagem informativa.
     *
     * @param owner   o Stage proprietário
     * @param message a mensagem a ser exibida
     */
    public static void show(Stage owner, String message) {
        show(owner, message, DEFAULT_DURATION, DEFAULT_POSITION, ToastType.INFO);
    }

    /**
     * Exibe um Toast com mensagem e tipo especificados.
     *
     * @param owner   o Stage proprietário
     * @param message a mensagem a ser exibida
     * @param type    o tipo de Toast
     */
    public static void show(Stage owner, String message, ToastType type) {
        show(owner, message, DEFAULT_DURATION, DEFAULT_POSITION, type);
    }

    /**
     * Exibe um Toast com mensagem, duração, posição e tipo especificados.
     *
     * @param owner         o Stage proprietário
     * @param message       a mensagem a ser exibida
     * @param durationMillis a duração do Toast em milissegundos
     * @param position      a posição do Toast
     * @param type          o tipo de Toast
     */
    public static void show(Stage owner, String message, int durationMillis, ToastPosition position, ToastType type) {
        Platform.runLater(() -> createToast(owner, message, durationMillis, position, type));
    }

    /**
     * Cria e exibe o Toast com as configurações especificadas.
     *
     * @param owner         o Stage proprietário
     * @param message       a mensagem a ser exibida
     * @param durationMillis a duração do Toast em milissegundos
     * @param position      a posição do Toast
     * @param type          o tipo de Toast
     */
    private static void createToast(Stage owner, String message, int durationMillis, ToastPosition position, ToastType type) {
        Stage stage = new Stage();
        stage.initOwner(owner);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setAlwaysOnTop(true);

        Label label = new Label(message);
        label.setWrapText(true);
        label.setMaxWidth(400);
        label.setPadding(new Insets(10));
        label.getStyleClass().addAll("toast-label", type.name().toLowerCase());

        StackPane root = new StackPane(label);
        root.setOpacity(0);
        root.setStyle("-fx-background-radius: 5;");
        root.getStyleClass().add("toast-root");

        Scene scene = new Scene(root);
        scene.setFill(null);
        scene.getStylesheets().add(Toast.class.getResource("/styles/toast.css").toExternalForm());

        stage.setScene(scene);
        stage.sizeToScene();

        stage.show();

        Platform.runLater(() -> positionStage(stage, owner, position));

        FadeTransition fadeIn = new FadeTransition(Duration.millis(300), root);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.play();

        FadeTransition fadeOut = new FadeTransition(Duration.millis(300), root);
        fadeOut.setDelay(Duration.millis(durationMillis));
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);
        fadeOut.setOnFinished(e -> stage.close());
        fadeOut.play();
    }

    /**
     * Posiciona o Stage do Toast na posição especificada.
     *
     * @param stage    o Stage do Toast
     * @param owner    o Stage proprietário
     * @param position a posição do Toast
     */
    private static void positionStage(Stage stage, Stage owner, ToastPosition position) {
        double ownerX = owner.getX();
        double ownerY = owner.getY();
        double ownerWidth = owner.getWidth();
        double ownerHeight = owner.getHeight();

        double stageWidth = stage.getWidth();
        double stageHeight = stage.getHeight();

        switch (position) {
            case TOP_CENTER:
                stage.setX(ownerX + (ownerWidth - stageWidth) / 2);
                stage.setY(ownerY + ownerHeight * 0.1);
                break;
            case CENTER:
                stage.setX(ownerX + (ownerWidth - stageWidth) / 2);
                stage.setY(ownerY + (ownerHeight - stageHeight) / 2);
                break;
            case BOTTOM_CENTER:
                stage.setX(ownerX + (ownerWidth - stageWidth) / 2);
                stage.setY(ownerY + ownerHeight * 0.9 - stageHeight);
                break;
            case TOP_RIGHT:
                stage.setX(ownerX + ownerWidth * 0.95 - stageWidth);
                stage.setY(ownerY + ownerHeight * 0.1);
                break;
            case BOTTOM_RIGHT:
                stage.setX(ownerX + ownerWidth * 0.95 - stageWidth);
                stage.setY(ownerY + ownerHeight * 0.9 - stageHeight);
                break;
            case TOP_LEFT:
                stage.setX(ownerX + ownerWidth * 0.05);
                stage.setY(ownerY + ownerHeight * 0.1);
                break;
            case BOTTOM_LEFT:
                stage.setX(ownerX + ownerWidth * 0.05);
                stage.setY(ownerY + ownerHeight * 0.9 - stageHeight);
                break;
        }
    }

    /**
     * Exibe um Toast de sucesso.
     *
     * @param owner   o Stage proprietário
     * @param message a mensagem a ser exibida
     */
    public static void showSuccess(Stage owner, String message) {
        show(owner, message, ToastType.SUCCESS);
    }

    /**
     * Exibe um Toast de erro.
     *
     * @param owner   o Stage proprietário
     * @param message a mensagem a ser exibida
     */
    public static void showError(Stage owner, String message) {
        show(owner, message, ToastType.ERROR);
    }

    /**
     * Exibe um Toast de aviso.
     *
     * @param owner   o Stage proprietário
     * @param message a mensagem a ser exibida
     */
    public static void showWarning(Stage owner, String message) {
        show(owner, message, ToastType.WARNING);
    }

    /**
     * Exibe um Toast informativo.
     *
     * @param owner   o Stage proprietário
     * @param message a mensagem a ser exibida
     */
    public static void showInfo(Stage owner, String message) {
        show(owner, message, ToastType.INFO);
    }
}