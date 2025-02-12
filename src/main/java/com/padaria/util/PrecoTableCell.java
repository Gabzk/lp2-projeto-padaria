package com.padaria.util;

import com.padaria.model.entities.Produto;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Classe que representa uma célula de tabela personalizada para exibir preços formatados como moeda.
 */
public class PrecoTableCell extends TableCell<Produto, Double> {
    private final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

    /**
     * Atualiza o item da célula com o valor formatado como moeda.
     *
     * @param item  o valor a ser exibido na célula
     * @param empty indica se a célula está vazia
     */
    @Override
    protected void updateItem(Double item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
            setText(null);
        } else {
            setText(currencyFormat.format(item));
        }
    }

    /**
     * Cria uma fábrica de células para colunas de tabela que exibem preços.
     *
     * @return uma fábrica de células para colunas de tabela de preços
     */
    public static Callback<TableColumn<Produto, Double>, TableCell<Produto, Double>> forTableColumn() {
        return param -> new PrecoTableCell();
    }
}