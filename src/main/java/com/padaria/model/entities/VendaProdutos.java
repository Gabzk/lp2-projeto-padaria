package com.padaria.model.entities;

/**
 * Classe que representa os produtos de uma venda.
 */
public class VendaProdutos {
    private int id;
    private int idVenda;
    private int idProduto;
    private int quantidade;
    private double subTotal;
    private Produto produto;

    /**
     * Construtor padrão.
     */
    public VendaProdutos() {}

    /**
     * Obtém o ID do produto da venda.
     * @return ID do produto da venda.
     */
    public int getId() {
        return id;
    }

    /**
     * Define o ID do produto da venda.
     * @param id ID do produto da venda.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtém o ID da venda.
     * @return ID da venda.
     */
    public int getIdVenda() {
        return idVenda;
    }

    /**
     * Define o ID da venda.
     * @param idVenda ID da venda.
     */
    public void setIdVenda(int idVenda) {
        this.idVenda = idVenda;
    }

    /**
     * Obtém o ID do produto.
     * @return ID do produto.
     */
    public int getIdProduto() {
        return idProduto;
    }

    /**
     * Define o ID do produto.
     * @param idProduto ID do produto.
     */
    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    /**
     * Obtém a quantidade do produto.
     * @return Quantidade do produto.
     */
    public int getQuantidade() {
        return quantidade;
    }

    /**
     * Define a quantidade do produto.
     * @param quantidade Quantidade do produto.
     */
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    /**
     * Obtém o subtotal do produto.
     * @return Subtotal do produto.
     */
    public double getSubTotal() {
        return subTotal;
    }

    /**
     * Define o subtotal do produto.
     * @param subTotal Subtotal do produto.
     */
    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    /**
     * Obtém o produto.
     * @return Produto.
     */
    public Produto getProduto() {
        return produto;
    }

    /**
     * Define o produto.
     * @param produto Produto.
     */
    public void setProduto(Produto produto) {
        this.produto = produto;
    }
}