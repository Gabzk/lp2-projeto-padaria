package com.padaria.controller;

import com.padaria.model.entities.Produto;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * Classe controladora para o item do carrinho.
 * Gerencia a exibição e as ações dos itens no carrinho de compras.
 */
public class CardItemController {
    @FXML
    private Label itemNome; // Rótulo para o nome do produto
    @FXML
    private Label itemPreco; // Rótulo para o preço do produto
    @FXML
    private Label itemQuantidade; // Rótulo para a quantidade do produto
    @FXML
    private Label subTotalPreco; // Rótulo para o subtotal do produto

    private Produto produto; // Produto associado ao item do carrinho

    /**
     * Define o produto para o item do carrinho e atualiza os rótulos.
     * @param produto Produto a ser exibido no item do carrinho.
     */
    public void setItem(Produto produto) {
        this.produto = produto;
        itemNome.setText(produto.getNome());
        itemPreco.setText(String.format("R$ %.2f Cada", produto.getPreco()));
        itemQuantidade.setText(String.valueOf(produto.getQuantidade()));
        subTotalPreco.setText(String.format("Subtotal: R$ %.2f", produto.getPreco() * produto.getQuantidade()));
    }

    /**
     * Lida com a ação de decrementar a quantidade do produto no carrinho.
     * Chama o método decrementarItem do CaixaController.
     */
    public void handleDecrementarButton() {
        CaixaController caixaController = CaixaController.getInstance();
        caixaController.decrementarItem(produto);
    }

    /**
     * Lida com a ação de incrementar a quantidade do produto no carrinho.
     * Chama o método incrementarItem do CaixaController.
     */
    public void handleIncrementarButton() {
        CaixaController caixaController = CaixaController.getInstance();
        caixaController.incrementarItem(produto);
    }

    /**
     * Lida com a ação de remover o produto do carrinho.
     * Chama o método removerItem do CaixaController.
     */
    public void handleRemoverButton() {
        CaixaController caixaController = CaixaController.getInstance();
        caixaController.removerItem(produto);
    }
}