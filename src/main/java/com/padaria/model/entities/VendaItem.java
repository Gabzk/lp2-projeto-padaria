package com.padaria.model.entities;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Classe que representa um item de venda.
 */
public class VendaItem {
    public final SimpleStringProperty id, idProduto, qtd;
    public final SimpleStringProperty data;
    public final SimpleStringProperty total, precoProduto, subtotal;
    public final SimpleStringProperty nomeProduto, categoriaProduto;

    /**
     * Construtor que inicializa um item de venda com ID, data e total.
     * @param id ID do item de venda.
     * @param data Data da venda.
     * @param total Total da venda.
     */
    public VendaItem(int id, LocalDateTime data, double total) {
        this.id = new SimpleStringProperty(String.valueOf(id));
        // Formata a data para o padrão brasileiro
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        this.data = new SimpleStringProperty(data.format(formatter));
        this.total = new SimpleStringProperty(String.valueOf(total));
        this.idProduto = new SimpleStringProperty("");
        this.nomeProduto = new SimpleStringProperty("");
        this.categoriaProduto = new SimpleStringProperty("");
        this.precoProduto = new SimpleStringProperty("");
        this.qtd = new SimpleStringProperty("");
        this.subtotal = new SimpleStringProperty("");
    }

    /**
     * Construtor que inicializa um item de venda com detalhes do produto.
     * @param idProduto ID do produto.
     * @param nomeProduto Nome do produto.
     * @param categoriaProduto Categoria do produto.
     * @param precoProduto Preço do produto.
     * @param qtd Quantidade do produto.
     * @param subtotal Subtotal do item de venda.
     */
    public VendaItem(int idProduto, String nomeProduto, String categoriaProduto, double precoProduto, int qtd, double subtotal) {
        this.id = new SimpleStringProperty("");
        this.data = new SimpleStringProperty("");
        this.total = new SimpleStringProperty("");
        this.idProduto = new SimpleStringProperty(String.valueOf(idProduto));
        this.nomeProduto = new SimpleStringProperty(nomeProduto);
        this.categoriaProduto = new SimpleStringProperty(categoriaProduto);
        this.precoProduto = new SimpleStringProperty(String.valueOf(precoProduto));
        this.qtd = new SimpleStringProperty(String.valueOf(qtd));
        this.subtotal = new SimpleStringProperty(String.valueOf(subtotal));
    }
}