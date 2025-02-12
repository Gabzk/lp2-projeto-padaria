package com.padaria.util;

import com.padaria.model.entities.VendaItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.util.Callback;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Classe que representa uma célula de TreeTable personalizada para exibir preços formatados como moeda.
 */
public class PrecoTreeTableCell extends TreeTableCell<VendaItem, String> {
    private final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

    /**
     * Atualiza o item da célula com o valor formatado como moeda.
     *
     * @param item  o valor a ser exibido na célula
     * @param empty indica se a célula está vazia
     */
    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
            setText(null);
        } else {
            try {
                double valor = Double.parseDouble(item);
                setText(currencyFormat.format(valor));
            } catch (NumberFormatException e) {
                // Caso a conversão falhe, exibe o valor original
                setText(item);
            }
        }
    }

    /**
     * Cria uma fábrica de células para colunas de TreeTable que exibem preços.
     *
     * @return uma fábrica de células para colunas de TreeTable de preços
     */
    public static Callback<TreeTableColumn<VendaItem, String>, TreeTableCell<VendaItem, String>> forTreeTableColumn() {
        return param -> new PrecoTreeTableCell();
    }
}