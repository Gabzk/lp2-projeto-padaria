package com.padaria.model.entities;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Classe que representa um produto.
 */
public class Produto {
    private int id;
    private String nome;
    private String categoria;
    private double preco;
    private int quantidade;
    private LocalDate validade;

    /**
     * Construtor padrão.
     */
    public Produto() {}

    /**
     * Construtor com parâmetros.
     * @param nome Nome do produto.
     * @param categoria Categoria do produto.
     * @param preco Preço do produto.
     * @param quantidade Quantidade do produto em estoque.
     * @param validade Data de validade do produto.
     */
    public Produto(String nome, String categoria, double preco, int quantidade, LocalDate validade) {
        this.nome = nome;
        this.categoria = categoria;
        this.preco = preco;
        this.quantidade = quantidade;
        this.validade = validade;
    }

    /**
     * Obtém o ID do produto.
     * @return ID do produto.
     */
    public int getId() {
        return id;
    }

    /**
     * Define o ID do produto.
     * @param id ID do produto.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtém o nome do produto.
     * @return Nome do produto.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define o nome do produto.
     * @param nome Nome do produto.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Obtém a categoria do produto.
     * @return Categoria do produto.
     */
    public String getCategoria() {
        return categoria;
    }

    /**
     * Define a categoria do produto.
     * @param categoria Categoria do produto.
     */
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    /**
     * Obtém o preço do produto.
     * @return Preço do produto.
     */
    public double getPreco() {
        return preco;
    }

    /**
     * Define o preço do produto.
     * @param preco Preço do produto.
     */
    public void setPreco(double preco) {
        this.preco = preco;
    }

    /**
     * Obtém a quantidade do produto em estoque.
     * @return Quantidade do produto.
     */
    public int getQuantidade() {
        return quantidade;
    }

    /**
     * Define a quantidade do produto em estoque.
     * @param quantidade Quantidade do produto.
     */
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    /**
     * Obtém a data de validade do produto.
     * @return Data de validade do produto.
     */
    public LocalDate getValidade() {
        return validade;
    }

    /**
     * Define a data de validade do produto.
     * @param validade Data de validade do produto.
     */
    public void setValidade(LocalDate validade) {
        this.validade = validade;
    }

    /**
     * Verifica se dois produtos são iguais com base no ID.
     * @param o Objeto a ser comparado.
     * @return true se os produtos forem iguais, false caso contrário.
     */
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Produto produto = (Produto) o;
        return id == produto.id;
    }

    /**
     * Gera o código hash do produto com base no ID.
     * @return Código hash do produto.
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    /**
     * Retorna uma representação em string do produto.
     * @return String representando o produto.
     */
    @Override
    public String toString() {
        return "Produto{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", categoria='" + categoria + '\'' +
                ", preco=" + preco +
                ", quantidade=" + quantidade +
                ", validade='" + validade + '\'' +
                '}';
    }
}