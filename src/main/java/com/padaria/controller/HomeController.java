package com.padaria.controller;

import com.mysql.cj.log.Log;
import com.padaria.util.LoginDialog;
import com.padaria.util.Toast;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * Classe controladora para a tela inicial.
 * Gerencia as ações dos botões de administrador e caixa.
 */
public class HomeController {



    @FXML
    private Button buttonAdmin; // Botão para acessar a tela de administração
    @FXML
    private Button buttonCaixa; // Botão para acessar a tela de caixa

    /**
     * Lida com a ação do botão de administrador.
     * Exibe o diálogo de login e, se bem-sucedido, carrega a tela de administração.
     */
    @FXML
    public void handleButtonAdmin() {
        Stage stage = (Stage) buttonAdmin.getScene().getWindow();
        if (LoginDialog.exibirLogin(stage, "Login de Administrador", "admin", "admin")) {
            System.out.println("Login efetuado com sucesso");
            try {
                Parent newView = FXMLLoader.load(getClass().getResource("/views/application.fxml"));
                stage.setScene(new Scene(newView));
                Toast.showSuccess(stage, "Login efetuado com sucesso");
            } catch (Exception e) {
                System.out.println("Erro ao carregar a tela de administração: " + e.getMessage());
            }
        } else {
            System.out.println("Login cancelado");
        }
    }

    /**
     * Lida com a ação do botão de caixa.
     * Exibe o diálogo de login e, se bem-sucedido, carrega a tela de caixa.
     */
    @FXML
    public void handleButtonCaixa() {
        Stage stage = (Stage) buttonCaixa.getScene().getWindow();
        if(LoginDialog.exibirLogin(stage, "Login de Caixa", "caixa", "caixa")) {
            System.out.println("Login efetuado com sucesso");
            try {
                Parent newView = FXMLLoader.load(getClass().getResource("/views/caixa.fxml"));
                stage.setScene(new Scene(newView));
                Toast.showSuccess(stage, "Login efetuado com sucesso");
            } catch (Exception e) {
                System.out.println("Erro ao carregar a tela de caixa: " + e.getMessage());
            }
        } else {
            System.out.println("Login cancelado");
        }
    }
}