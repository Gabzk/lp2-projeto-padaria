package com.padaria.controller;

import com.padaria.model.dao.DaoFactory;
import com.padaria.model.entities.Produto;
import com.padaria.util.PrecoTableCell;
import com.padaria.util.StatusTableCell;
import com.padaria.util.ConfirmationDialog;
import com.padaria.util.Toast;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.time.LocalDate;

/**
 * Classe controladora para a tela de produtos.
 * Gerencia a exibição, adição, atualização e remoção de produtos.
 */
public class ProdutoController {

    @FXML
    private TableView<Produto> tabelaProdutos; // Tabela para exibir os produtos
    @FXML
    private TableColumn<Produto, Integer> colunaId; // Coluna para exibir o ID do produto
    @FXML
    private TableColumn<Produto, String> colunaNome; // Coluna para exibir o nome do produto
    @FXML
    private TableColumn<Produto, String> colunaCategoria; // Coluna para exibir a categoria do produto
    @FXML
    private TableColumn<Produto, Double> colunaPreco; // Coluna para exibir o preço do produto
    @FXML
    private TableColumn<Produto, Integer> colunaQtd; // Coluna para exibir a quantidade do produto
    @FXML
    private TableColumn<Produto, LocalDate> colunaValidade; // Coluna para exibir a validade do produto
    @FXML
    private TableColumn<Produto, LocalDate> colunaStatus; // Coluna para exibir o status do produto
    @FXML
    private TextField textId; // Campo de texto para o ID do produto
    @FXML
    private TextField textNome; // Campo de texto para o nome do produto
    @FXML
    private TextField textCategoria; // Campo de texto para a categoria do produto
    @FXML
    private TextField textPreco; // Campo de texto para o preço do produto
    @FXML
    private TextField textQtd; // Campo de texto para a quantidade do produto
    @FXML
    private DatePicker dataValidade; // Seletor de data para a validade do produto

    private Stage stage; // Referência para o palco principal

    /**
     * Inicializa a classe controladora.
     * Carrega a tabela de produtos e define a referência do palco.
     */
    @FXML
    public void initialize() {
        carregarTabela();
        Platform.runLater(() -> stage = (Stage) tabelaProdutos.getScene().getWindow());
    }

    /**
     * Lida com o clique do mouse na tabela de produtos.
     * Carrega os detalhes do produto selecionado nos campos de texto.
     * @param event Evento de clique do mouse.
     */
    @FXML
    public void handleMouseClick(MouseEvent event) {
        if (event.getClickCount() == 1) {
            Produto produtoSelecionado = tabelaProdutos.getSelectionModel().getSelectedItem();
            if (produtoSelecionado != null) {
                carregarDetalhesProduto(produtoSelecionado);
            }
        }
    }

    /**
     * Carrega os detalhes do produto nos campos de texto.
     * @param produto Produto a ser exibido nos campos de texto.
     */
    private void carregarDetalhesProduto(Produto produto) {
        textId.setText(String.valueOf(produto.getId()));
        textNome.setText(produto.getNome());
        textCategoria.setText(produto.getCategoria());
        textPreco.setText(String.valueOf(produto.getPreco()));
        textQtd.setText(String.valueOf(produto.getQuantidade()));
        if (produto.getValidade() != null) {
            dataValidade.setValue(produto.getValidade());
        } else {
            dataValidade.setValue(null);
        }
    }

    /**
     * Carrega a tabela de produtos com os dados do banco de dados.
     */
    private void carregarTabela() {
        try {
            ObservableList<Produto> observableList = FXCollections.observableList(DaoFactory.createProdutoDao().buscarTodos());
            colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
            colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
            colunaCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
            colunaPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));
            colunaQtd.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
            colunaValidade.setCellValueFactory(new PropertyValueFactory<>("validade"));
            colunaStatus.setCellValueFactory(new PropertyValueFactory<>("validade"));
            tabelaProdutos.setItems(observableList);
            colunaStatus.setCellFactory(StatusTableCell.forTableColumn());
            colunaPreco.setCellFactory(PrecoTableCell.forTableColumn());

        } catch (Exception e) {
            System.out.println("Erro ao carregar a tabela de produtos:");
            e.printStackTrace();
        }
    }

    /**
     * Atualiza um produto existente no banco de dados.
     * Exibe uma confirmação antes de atualizar o produto.
     */
    @FXML
    private void atualizarProduto() {
        boolean confirmacao = ConfirmationDialog.show(stage, "Tem certeza que deseja atualizar o produto?");
        if (confirmacao) {
            try {
                Produto produto = new Produto();
                try {
                    produto.setId(Integer.parseInt(textId.getText()));
                } catch (NumberFormatException e) {
                    Toast.show(stage, "Erro ao converter ID para número");
                    return;
                }
                produto.setNome(textNome.getText());
                produto.setCategoria(textCategoria.getText());
                produto.setPreco(Double.parseDouble(textPreco.getText()));
                produto.setQuantidade(Integer.parseInt(textQtd.getText()));
                produto.setValidade(dataValidade.getValue());
                DaoFactory.createProdutoDao().atualizar(produto);
            } catch (NullPointerException e) {
                Toast.show(stage, "Preencha todos os campos");
            } catch (NumberFormatException e) {
                Toast.show(stage, "Preencha os campos corretamente");
            } catch (Exception e) {
                Toast.show(stage, "Erro ao atualizar o produto");
                e.printStackTrace();
            } finally {
                carregarTabela();
            }
        }
    }

    /**
     * Adiciona um novo produto ao banco de dados.
     * Exibe uma confirmação antes de adicionar o produto.
     */
    @FXML
    private void adicionarProduto() {
        boolean confirmacao = ConfirmationDialog.show(stage, "Tem certeza que deseja adicionar o produto?");
        if (confirmacao) {
            try {
                Produto produto = new Produto();
                String nome = textNome.getText();
                if (nome == null || nome.isEmpty()) {
                    Toast.show(stage, "O campo nome é obrigatório");
                    return;
                }
                produto.setNome(textNome.getText());
                produto.setCategoria(textCategoria.getText());
                produto.setPreco(Double.parseDouble(textPreco.getText()));
                produto.setQuantidade(Integer.parseInt(textQtd.getText()));
                produto.setValidade(dataValidade.getValue());
                DaoFactory.createProdutoDao().inserir(produto);
            } catch (NullPointerException e) {
                Toast.show(stage, "Preencha todos os campos");
            } catch (NumberFormatException e) {
                Toast.show(stage, "Preencha os campos corretamente");
            } catch (Exception e) {
                Toast.show(stage, "Erro ao adicionar o produto");
                e.printStackTrace();
            } finally {
                carregarTabela();
            }
        }
    }

    /**
     * Remove um produto existente do banco de dados.
     * Exibe uma confirmação antes de remover o produto.
     */
    @FXML
    private void removerProduto() {
        boolean confirmacao = ConfirmationDialog.show(stage, "Tem certeza que deseja remover o produto?");
        if (confirmacao) {
            try {
                int id;
                try {
                    id = Integer.parseInt(textId.getText());
                } catch (NumberFormatException e) {
                    System.out.println("Erro ao converter ID para número: " + e.getMessage());
                    return;
                }
                DaoFactory.createProdutoDao().removerPorId(id);
                carregarTabela();
            } catch (Exception e) {
                System.out.println("Erro ao remover o produto: " + e.getMessage());
            }
        } else {
            System.out.println("Remoção cancelada pelo usuário.");
        }
    }

    /**
     * Limpa os campos de texto do formulário.
     */
    @FXML
    private void limparCampos() {
        textId.clear();
        textNome.clear();
        textCategoria.clear();
        textPreco.clear();
        textQtd.clear();
        dataValidade.setValue(null);
    }
}