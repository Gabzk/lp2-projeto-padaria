package com.padaria.model.dao;

import com.padaria.model.entities.Venda;

import java.util.List;

/**
 * Interface para operações de banco de dados relacionadas à entidade Venda.
 */
public interface VendaDao {

    /**
     * Lista todas as vendas do banco de dados.
     * @return Lista de vendas.
     */
    List<Venda> listaVendas();

    /**
     * Insere uma nova venda no banco de dados.
     * @param venda Venda a ser inserida.
     */
    void inserir(Venda venda);
}