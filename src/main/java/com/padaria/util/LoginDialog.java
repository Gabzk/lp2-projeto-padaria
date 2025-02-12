package com.padaria.util;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;

public class LoginDialog {

    @FXML
    private Label labelTitulo;
    @FXML
    private TextField campoLogin;
    @FXML
    private PasswordField campoSenha;
    @FXML
    private Button buttonLogar;

    private boolean autenticado = false;

    /**
     * Exibe a janela de login.
     *
     * @param owner       o Stage proprietário
     * @param titulo      o título da janela de login
     * @param userCorreto o nome de usuário correto
     * @param passCorreto a senha correta
     * @return true se o usuário for autenticado com sucesso; false caso contrário
     */
    public static boolean exibirLogin(Stage owner, String titulo, String userCorreto, String passCorreto) {
        try {
            FXMLLoader loader = new FXMLLoader(LoginDialog.class.getResource("/views/login.fxml"));
            VBox root = loader.load();

            LoginDialog controller = loader.getController();
            controller.labelTitulo.setText(titulo);

            Stage stage = new Stage();
            stage.initOwner(owner);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UTILITY);
            stage.setResizable(false);
            stage.setTitle("Login");

            Scene scene = new Scene(root);
            stage.setScene(scene);

            controller.buttonLogar.setOnAction(event -> {
                String user = controller.campoLogin.getText();
                String pass = controller.campoSenha.getText();

                if (user.equals(userCorreto) && pass.equals(passCorreto)) {
                    controller.autenticado = true;
                    stage.close();
                } else {
                    Toast.showWarning(owner, "Usuário ou senha inválidos");
                }
            });

            stage.showAndWait(); // Espera o usuário interagir com a janela

            return controller.autenticado;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}