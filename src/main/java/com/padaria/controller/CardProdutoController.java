package com.padaria.controller;

import com.padaria.model.entities.Produto;
import com.padaria.util.Toast;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * Classe controladora para o card de produto.
 * Gerencia a exibição e as ações dos produtos disponíveis.
 */
public class CardProdutoController {
    @FXML private Label produtoNome, produtoPreco, produtoEstoque; // Rótulos para exibir nome, preço e estoque do produto
    @FXML private Button buttonAdicionarCarrinho; // Botão para adicionar o produto ao carrinho
    private Produto produto; // Produto associado ao card

    /**
     * Inicializa a classe controladora.
     */
    public void initialize() {
    }

    /**
     * Define o produto para o card e atualiza os rótulos.
     * @param produto Produto a ser exibido no card.
     */
    public void setProduto(Produto produto) {
        produtoNome.setText(produto.getNome());
        produtoPreco.setText(String.format("Preço: R$ %.2f", produto.getPreco()));
        produtoEstoque.setText(String.format("Estoque: %d", produto.getQuantidade()));
        this.produto = produto;
    }

    /**
     * Lida com a ação de adicionar o produto ao carrinho.
     * Verifica se o produto tem estoque disponível e chama o método incrementarItem do CaixaController.
     */
    @FXML
    private void handleButtonAdicionarCarrinho() {
        if (produto != null && produto.getQuantidade() > 0) {
            CaixaController caixaController = CaixaController.getInstance();
            if (caixaController != null) {
                Produto p = new Produto();
                p.setQuantidade(produto.getQuantidade());
                p.setId(produto.getId());
                p.setNome(produto.getNome());
                p.setPreco(produto.getPreco());
                Stage stage = (Stage) buttonAdicionarCarrinho.getScene().getWindow();

                if (caixaController.incrementarItem(p)) {
                    Toast.show(stage, "Produto adicionado ao carrinho", 1000, Toast.ToastPosition.TOP_CENTER, Toast.ToastType.INFO);
                }
            }
        } else {
            Stage stage = (Stage) buttonAdicionarCarrinho.getScene().getWindow();
            Toast.show(stage, "Produto sem estoque", 1000, Toast.ToastPosition.TOP_CENTER, Toast.ToastType.ERROR);
        }
    }
}