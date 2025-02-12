package com.padaria.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Classe controladora para a aplicação principal.
 * Gerencia as abas e lida com a navegação entre diferentes visualizações.
 */
public class ApplicationController {

    @FXML
    private Tab tabProdutos; // Aba para exibir produtos
    @FXML
    private Tab tabVendas; // Aba para exibir vendas
    @FXML
    private TabPane tabPane; // TabPane contendo as abas

    private Stage stage; // Referência para o palco principal

    /**
     * Inicializa a classe controladora.
     * Carrega o conteúdo das abas de produtos e vendas.
     */
    public void initialize() {
        try {
            // Carrega a visualização de produtos e define como conteúdo da aba de produtos
            FXMLLoader produtosLoader = new FXMLLoader(getClass().getResource("/views/produtos.fxml"));
            tabProdutos.setContent(produtosLoader.load());

            // Carrega a visualização de vendas e define como conteúdo da aba de vendas
            FXMLLoader vendasLoader = new FXMLLoader(getClass().getResource("/views/vendas.fxml"));
            tabVendas.setContent(vendasLoader.load());

            // Define a referência do palco após a cena ser carregada
            Platform.runLater(() -> stage = (Stage) tabPane.getScene().getWindow());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Lida com a ação de voltar ao menu principal.
     * Carrega a visualização inicial e define como a cena do palco.
     */
    @FXML
    public void handleVoltarMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/home.fxml"));
            stage.setScene(new Scene(loader.load()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}