package com.padaria.controller;

import com.padaria.model.dao.DaoFactory;
import com.padaria.model.entities.Produto;
import com.padaria.model.entities.Venda;
import com.padaria.model.entities.VendaProdutos;
import com.padaria.util.ConfirmationDialog;
import com.padaria.util.Toast;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe controladora para a tela de caixa.
 * Gerencia a adição de produtos ao carrinho, a atualização do carrinho e a efetivação de vendas.
 */
public class CaixaController {
    @FXML
    private FlowPane produtoFlowPane; // Painel para exibir os produtos disponíveis
    @FXML
    private FlowPane carrinhoFlowPane; // Painel para exibir os produtos no carrinho
    @FXML
    private Label totalLabel; // Rótulo para o texto "Total"
    @FXML
    private Label totalValorLabel; // Rótulo para exibir o valor total da compra
    @FXML
    private ScrollPane scrollPane; // Painel de rolagem para os produtos no carrinho
    @FXML
    private Button buttonEfetuarVenda; // Botão para efetuar a venda

    private final List<Produto> carrinho = new ArrayList<>(); // Lista de produtos no carrinho

    private static CaixaController instance; // Instância única do controlador
    private Stage stage; // Referência para o palco principal

    /**
     * Construtor da classe CaixaController.
     * Define a instância única do controlador.
     */
    public CaixaController() {
        instance = this;
    }

    /**
     * Inicializa a classe controladora.
     * Adiciona os produtos ao painel de produtos e define a referência do palco.
     */
    @FXML
    public void initialize() {
        adicionarCardProdutos();
        Platform.runLater(() -> stage = (Stage) produtoFlowPane.getScene().getWindow());
    }

    /**
     * Retorna a instância única do controlador.
     * @return Instância do CaixaController.
     */
    public static CaixaController getInstance() {
        return instance;
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

    /**
     * Decrementa a quantidade de um produto no carrinho.
     * Remove o produto do carrinho se a quantidade for zero.
     * @param produto Produto a ser decrementado.
     */
    public void decrementarItem(Produto produto) {
        for (Produto p : carrinho) {
            if (p.equals(produto)) {
                int qtdAtual = p.getQuantidade();
                qtdAtual--;
                if (qtdAtual == 0) {
                    carrinho.remove(p);
                } else {
                    p.setQuantidade(qtdAtual);
                }
                atualizarCarrinho();
                return;
            }
        }
    }

    /**
     * Incrementa a quantidade de um produto no carrinho.
     * Verifica se a quantidade não excede o estoque disponível.
     * @param produto Produto a ser incrementado.
     * @return true se o produto foi incrementado, false se a quantidade máxima foi atingida.
     */
    public Boolean incrementarItem(Produto produto) {
        for (Produto p : carrinho) {
            if (p.equals(produto)) {
                int qtdAtual = p.getQuantidade();
                qtdAtual++;
                if (qtdAtual > DaoFactory.createProdutoDao().buscarPorId(produto.getId()).getQuantidade()) {
                    Toast.show(stage, "Quantidade máxima do estoque atingida", 1000, Toast.ToastPosition.TOP_CENTER, Toast.ToastType.ERROR);
                    return false;
                }
                p.setQuantidade(qtdAtual);
                atualizarCarrinho();
                return true;
            }
        }

        Produto produtoNovo = new Produto();
        produtoNovo.setQuantidade(1);
        produtoNovo.setId(produto.getId());
        produtoNovo.setNome(produto.getNome());
        produtoNovo.setPreco(produto.getPreco());

        carrinho.add(produtoNovo);
        atualizarCarrinho();
        return true;
    }

    /**
     * Remove um produto do carrinho.
     * @param produto Produto a ser removido.
     */
    public void removerItem(Produto produto) {
        for (Produto p : carrinho) {
            if (p.getId() == produto.getId()) {
                carrinho.remove(p);
                atualizarCarrinho();
                return;
            }
        }
    }

    /**
     * Atualiza a exibição dos produtos no carrinho.
     * Recalcula o valor total da compra.
     */
    private void atualizarCarrinho() {
        carrinhoFlowPane.getChildren().clear();

        double total = 0.0;

        for (Produto produto : carrinho) {
            total += produto.getPreco() * produto.getQuantidade();

            try {
                FXMLLoader produtoLoader = new FXMLLoader(getClass().getResource("/views/cardItem.fxml"));
                AnchorPane produtoCarrinho = produtoLoader.load();
                produtoCarrinho.setPrefWidth(scrollPane.getWidth());

                CardItemController cardItemController = produtoLoader.getController();
                cardItemController.setItem(produto);

                carrinhoFlowPane.getChildren().add(produtoCarrinho);
            } catch (Exception e) {
                System.out.println("Erro ao adicionar o produto: " + e.getMessage());
                e.printStackTrace();
            }
        }

        totalValorLabel.setText(String.format("R$ %.2f", total));

        for (Produto p: carrinho) {
            System.out.println(p.getNome()+" "+ p.getQuantidade());
        }
    }

    /**
     * Adiciona os produtos ao painel de produtos.
     * Carrega a visualização de cada produto e define como conteúdo do painel.
     */
    private void adicionarCardProdutos() {
        produtoFlowPane.getChildren().clear();
        List<Produto> produtos = DaoFactory.createProdutoDao().buscarTodos();

        try {
            for (Produto produto : produtos) {
                FXMLLoader produtoLoader = new FXMLLoader(getClass().getResource("/views/cardProduto.fxml"));
                VBox produtoCard = produtoLoader.load();

                CardProdutoController cardProdutoController = produtoLoader.getController();
                cardProdutoController.setProduto(produto);

                produtoFlowPane.getChildren().add(produtoCard);
            }

        } catch (Exception e) {
            System.out.println("Erro ao adicionar o produto: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Efetua a venda dos produtos no carrinho.
     * Verifica se o carrinho está vazio e solicita confirmação do usuário.
     * Registra a venda no banco de dados e limpa o carrinho.
     */
    @FXML
    private void efetuarVenda() {
        if (carrinho.isEmpty()) {
            Toast.show(stage, "Carrinho vazio!", 3000, Toast.ToastPosition.TOP_CENTER, Toast.ToastType.ERROR);
            return;
        } else {
            boolean confirmacao = ConfirmationDialog.show(stage, "Deseja efetuar a venda?");
            if (!confirmacao) {
                return;
            }
        }

        double valorTotal = 0.0;
        List<VendaProdutos> listaVendaProdutos = new ArrayList<>();

        for (Produto p : carrinho) {
            valorTotal += p.getPreco() * p.getQuantidade();
            VendaProdutos vendaProdutos = new VendaProdutos();
            vendaProdutos.setQuantidade(p.getQuantidade());
            vendaProdutos.setSubTotal(p.getPreco() * p.getQuantidade());
            vendaProdutos.setIdProduto(p.getId());
            listaVendaProdutos.add(vendaProdutos);
        }

        Venda venda = new Venda();
        venda.setData(java.time.LocalDateTime.now());
        venda.setItens(listaVendaProdutos);
        venda.setValorTotal(valorTotal);

        DaoFactory.createVendaDao().inserir(venda);
        limparCarrinho(); // Para limpar o carrinho após a venda
        adicionarCardProdutos(); // Para atualizar a tabela de produtos na aba de caixa também

        Stage stage = (Stage) buttonEfetuarVenda.getScene().getWindow();
        Toast.show(stage, "Venda efetuada com sucesso!", 3000,Toast.ToastPosition.TOP_CENTER, Toast.ToastType.SUCCESS);

    }

    /**
     * Limpa o carrinho de compras.
     * Remove todos os produtos do carrinho e atualiza a exibição.
     */
    private void limparCarrinho() {
        carrinho.clear();
        atualizarCarrinho();
    }
}