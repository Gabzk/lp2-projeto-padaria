package com.padaria.model.entities;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Classe que representa uma venda.
 */
public class Venda {
    private int id;
    private LocalDateTime data;
    private double valorTotal;
    private List<VendaProdutos> itens;

    /**
     * Construtor padrão.
     */
    public Venda() {}

    /**
     * Obtém o ID da venda.
     * @return ID da venda.
     */
    public int getId() {
        return id;
    }

    /**
     * Define o ID da venda.
     * @param id ID da venda.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtém a data da venda.
     * @return Data da venda.
     */
    public LocalDateTime getData() {
        return data;
    }

    /**
     * Define a data da venda.
     * @param data Data da venda.
     */
    public void setData(LocalDateTime data) {
        this.data = data;
    }

    /**
     * Obtém o valor total da venda.
     * @return Valor total da venda.
     */
    public double getValorTotal() {
        return valorTotal;
    }

    /**
     * Define o valor total da venda.
     * @param valorTotal Valor total da venda.
     */
    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    /**
     * Obtém os itens da venda.
     * @return Lista de itens da venda.
     */
    public List<VendaProdutos> getItens() {
        return itens;
    }

    /**
     * Define os itens da venda.
     * @param itens Lista de itens da venda.
     */
    public void setItens(List<VendaProdutos> itens) {
        this.itens = itens;
    }

    /**
     * Retorna uma representação em string da venda.
     * @return String representando a venda.
     */
    @Override
    public String toString() {
        return "Venda{" +
                "id=" + id +
                ", data=" + data +
                ", valorTotal=" + valorTotal +
                '}';
    }
}