package com.padaria.controller;

import com.padaria.model.dao.DaoFactory;
import com.padaria.model.entities.Venda;
import com.padaria.model.entities.VendaItem;
import com.padaria.model.entities.VendaProdutos;
import com.padaria.util.PrecoTableCell;
import com.padaria.util.PrecoTreeTableCell;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Classe controladora para a tela de vendas.
 * Gerencia a exibição, filtragem e estatísticas das vendas.
 */
public class VendaController {

    @FXML private TreeTableView<VendaItem> tabelaVendas; // Tabela para exibir as vendas
    @FXML private TreeTableColumn<VendaItem, String> colunaId, colunaTotal, colunaIdProduto, colunaPrecoProduto, colunaQtd, colunaSubtotal; // Colunas da tabela de vendas
    @FXML private TreeTableColumn<VendaItem, String> colunaNomeProduto, colunaCategoriaProduto; // Colunas para exibir detalhes dos produtos vendidos
    @FXML private TreeTableColumn<VendaItem, String> colunaData; // Coluna para exibir a data da venda
    @FXML private RadioButton radioButtonDia, radioButtonMes, radioButtonPeriodo; // Botões de rádio para selecionar o período de filtragem
    @FXML private DatePicker datePickerInicio, datePickerFinal; // Seletores de data para o período de filtragem
    @FXML private Label labelVendasTotais, labelValorMedio; // Rótulos para exibir estatísticas das vendas

    /**
     * Inicializa a classe controladora.
     * Configura as colunas da tabela e atualiza as vendas.
     */
    @FXML
    public void initialize() {
        radioButtonMes.setSelected(true);
        configurarColunas();
        atualizarVendas();
    }

    /**
     * Atualiza a lista de vendas exibida na tabela.
     * Filtra as vendas e atualiza as estatísticas.
     */
    private void atualizarVendas() {
        List<Venda> vendas = DaoFactory.createVendaDao().listaVendas();
        List<Venda> vendasFiltradas = filtrarVendas(vendas);
        atualizarEstatisticas(vendasFiltradas);
        carregarVendas(vendasFiltradas);
    }

    /**
     * Filtra a lista de vendas com base no período selecionado.
     * @param vendas Lista de vendas a ser filtrada.
     * @return Lista de vendas filtrada.
     */
    private List<Venda> filtrarVendas(List<Venda> vendas) {
        LocalDate hoje = LocalDate.now();

        if (radioButtonDia.isSelected()) {
            return vendas.stream().filter(v -> v.getData().toLocalDate().isEqual(hoje)).collect(Collectors.toList());
        }
        if (radioButtonMes.isSelected()) {
            return vendas.stream().filter(v -> !v.getData().toLocalDate().isBefore(hoje.minusMonths(1))).collect(Collectors.toList());
        }
        if (radioButtonPeriodo.isSelected() && datePickerInicio.getValue() != null && datePickerFinal.getValue() != null) {
            return vendas.stream().filter(v -> !v.getData().toLocalDate().isBefore(datePickerInicio.getValue()) && !v.getData().toLocalDate().isAfter(datePickerFinal.getValue())).collect(Collectors.toList());
        }
        return vendas;
    }

    /**
     * Atualiza as estatísticas das vendas exibidas.
     * @param vendas Lista de vendas para calcular as estatísticas.
     */
    private void atualizarEstatisticas(List<Venda> vendas) {
        double total = vendas.stream().mapToDouble(Venda::getValorTotal).sum();
        double media = vendas.isEmpty() ? 0.0 : total / vendas.size();

        labelVendasTotais.setText(String.format("R$ %.2f", total));
        labelValorMedio.setText(String.format("R$ %.2f", media));
    }

    /**
     * Lida com a seleção dos botões de rádio.
     * Habilita ou desabilita os seletores de data com base na seleção.
     */
    @FXML
    private void handleRadioButton() {
        boolean periodoSelecionado = radioButtonPeriodo.isSelected();
        datePickerInicio.setDisable(!periodoSelecionado);
        datePickerFinal.setDisable(!periodoSelecionado);
        if (!periodoSelecionado) {
            atualizarVendas();
        }
    }

    /**
     * Lida com a ação do botão aplicar.
     * Atualiza a lista de vendas com base no período selecionado.
     */
    @FXML
    private void handleButtonAplicar() {
        if (radioButtonPeriodo.isSelected() && datePickerInicio.getValue() != null && datePickerFinal.getValue() != null) {
            atualizarVendas();
        }
    }

    /**
     * Carrega a lista de vendas na tabela.
     * @param vendas Lista de vendas a ser exibida.
     */
    private void carregarVendas(List<Venda> vendas) {
        TreeItem<VendaItem> root = new TreeItem<>(new VendaItem(0, LocalDateTime.now(), 0.0));
        tabelaVendas.setRoot(root);
        tabelaVendas.setShowRoot(false);

        for (Venda venda : vendas) {
            TreeItem<VendaItem> vendaItem = new TreeItem<>(new VendaItem(venda.getId(), venda.getData(), venda.getValorTotal()));

            for (VendaProdutos vendaProdutos : venda.getItens()) {
                vendaItem.getChildren().add(new TreeItem<>(new VendaItem(
                        vendaProdutos.getIdProduto(), vendaProdutos.getProduto().getNome(),
                        vendaProdutos.getProduto().getCategoria(), vendaProdutos.getProduto().getPreco(),
                        vendaProdutos.getQuantidade(), vendaProdutos.getSubTotal()
                )));
            }
            root.getChildren().add(vendaItem);
        }
    }

    /**
     * Configura as colunas da tabela de vendas.
     */
    private void configurarColunas() {
        colunaId.setCellValueFactory(param -> param.getValue().getValue().id);
        colunaTotal.setCellValueFactory(param -> param.getValue().getValue().total);
        colunaIdProduto.setCellValueFactory(param -> param.getValue().getValue().idProduto);
        colunaNomeProduto.setCellValueFactory(param -> param.getValue().getValue().nomeProduto);
        colunaCategoriaProduto.setCellValueFactory(param -> param.getValue().getValue().categoriaProduto);
        colunaPrecoProduto.setCellValueFactory(param -> param.getValue().getValue().precoProduto);
        colunaQtd.setCellValueFactory(param -> param.getValue().getValue().qtd);
        colunaSubtotal.setCellValueFactory(param -> param.getValue().getValue().subtotal);
        colunaData.setCellValueFactory(param -> param.getValue().getValue().data);
        colunaPrecoProduto.setCellFactory(PrecoTreeTableCell.forTreeTableColumn());
        colunaTotal.setCellFactory(PrecoTreeTableCell.forTreeTableColumn());
        colunaSubtotal.setCellFactory(PrecoTreeTableCell.forTreeTableColumn());
    }
}