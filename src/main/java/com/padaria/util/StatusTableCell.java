package com.padaria.util;

import com.padaria.model.entities.Produto;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

import java.time.LocalDate;

/**
 * Classe que representa uma célula de tabela personalizada para exibir o status de validade e estoque de um produto.
 */
public class StatusTableCell extends TableCell<Produto, LocalDate> {
    private final ImageView imageViewValidade = new ImageView();
    private final ImageView imageViewEstoque = new ImageView();
    private final Tooltip tooltip = new Tooltip();
    private final HBox hBox = new HBox(8);

    /**
     * Construtor da célula de status.
     * Inicializa os componentes gráficos e configura o layout.
     */
    public StatusTableCell() {
        imageViewValidade.setFitHeight(16);
        imageViewValidade.setFitWidth(16);
        imageViewEstoque.setFitHeight(16);
        imageViewEstoque.setFitWidth(16);
        hBox.setAlignment(javafx.geometry.Pos.CENTER);
        Tooltip.install(hBox, tooltip);
    }

    /**
     * Atualiza o item da célula com os ícones de status de validade e estoque.
     *
     * @param item  a data de validade do produto
     * @param empty indica se a célula está vazia
     */
    @Override
    protected void updateItem(LocalDate item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {
            setGraphic(null);
            setText(null);
            setTooltip(null); // Remove a tooltip se a célula estiver vazia
        } else {
            LocalDate hoje = LocalDate.now();
            Produto produto = getTableView().getItems().get(getIndex());
            boolean validadeProxima = false;
            boolean validadeCritica = false;
            boolean estoqueBaixo = produto.getQuantidade() < 10; // Exemplo: estoque baixo se quantidade < 10

            hBox.getChildren().clear(); // Limpa os ícones anteriores

            String tooltipText = "";

            // Verifica a validade do produto
            if (item.isBefore(hoje)) {
                // Produto vencido
                imageViewValidade.setImage(new Image(getClass().getResource("/images/alertaGrave.png").toExternalForm()));
                tooltipText = "Produto vencido";
                hBox.getChildren().add(imageViewValidade);
                validadeCritica = true;
            } else if (item.isBefore(hoje.plusDays(7))) {
                // Produto perto da validade
                imageViewValidade.setImage(new Image(getClass().getResource("/images/alerta.png").toExternalForm()));
                tooltipText = "Produto perto da validade";
                hBox.getChildren().add(imageViewValidade);
                validadeProxima = true;
            } else {
                imageViewValidade.setImage(null);
            }

            // Exibe o ícone de estoque baixo apenas se o estoque estiver baixo e a validade não estiver expirada
            if (estoqueBaixo && !validadeCritica) {
                imageViewEstoque.setImage(new Image(getClass().getResource("/images/aviso.png").toExternalForm()));
                hBox.getChildren().add(imageViewEstoque);

                if (!tooltipText.isEmpty()) {
                    tooltipText += " e estoque baixo";
                } else {
                    tooltipText = "Estoque baixo";
                }
            } else {
                imageViewEstoque.setImage(null);
            }

            tooltip.setText(tooltipText);
            setTooltip(tooltip); // Associa a tooltip à célula
            setGraphic(hBox);
            setText(null);
        }
    }

    /**
     * Cria uma fábrica de células para colunas de tabela que exibem status de validade e estoque.
     *
     * @return uma fábrica de células para colunas de tabela de status
     */
    public static Callback<TableColumn<Produto, LocalDate>, TableCell<Produto, LocalDate>> forTableColumn() {
        return param -> new StatusTableCell();
    }
}