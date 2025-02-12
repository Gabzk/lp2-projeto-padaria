package com.padaria.model.dao;

import com.padaria.db.DB;

/**
 * Fábrica de DAOs para criar instâncias de DAOs específicos.
 */
public interface DaoFactory {

    /**
     * Cria uma instância de ProdutoDao.
     * @return uma nova instância de ProdutoDaoJDBC.
     */
    static ProdutoDao createProdutoDao() {
        return new ProdutoDaoJDBC(DB.getConnection());
    }

    /**
     * Cria uma instância de VendaDao.
     * @return uma nova instância de VendaDaoJDBC.
     */
    static VendaDao createVendaDao() {
        return new VendaDaoJDBC(DB.getConnection());
    }
}