package com.padaria.model.dao;

import com.padaria.db.DB;
import com.padaria.model.entities.Venda;
import com.padaria.model.entities.VendaProdutos;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementação JDBC do VendaDao.
 * Gerencia operações de banco de dados para a entidade Venda.
 */
public class VendaDaoJDBC implements VendaDao {
    private final Connection conn;

    /**
     * Construtor que inicializa a conexão com o banco de dados.
     * @param conn Conexão com o banco de dados.
     */
    public VendaDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    /**
     * Lista todas as vendas do banco de dados.
     * @return Lista de vendas.
     */
    @Override
    public List<Venda> listaVendas() {
        PreparedStatement stVendas = null;
        ResultSet rsVendas = null;
        List<Venda> vendas = new ArrayList<>();
        String sqlVendas = "select * from vendas order by data_hora desc";

        try {
            stVendas = conn.prepareStatement(sqlVendas);
            rsVendas = stVendas.executeQuery();
            while (rsVendas.next()) {
                Venda venda = new Venda();
                venda.setId(rsVendas.getInt("id"));
                venda.setData(rsVendas.getTimestamp("data_hora").toLocalDateTime());
                venda.setValorTotal(rsVendas.getDouble("total"));

                // Consulta separada para os itens da venda
                PreparedStatement stItens = null;
                ResultSet rsItens = null;
                String sqlItens = "select * from venda_produtos where venda_id = ?";
                try {
                    stItens = conn.prepareStatement(sqlItens);
                    stItens.setInt(1, venda.getId());
                    rsItens = stItens.executeQuery();
                    List<VendaProdutos> itens = new ArrayList<>();
                    while (rsItens.next()) {
                        VendaProdutos vendaProdutos = new VendaProdutos();
                        vendaProdutos.setId(rsItens.getInt("id"));
                        vendaProdutos.setIdVenda(rsItens.getInt("venda_id"));
                        vendaProdutos.setIdProduto(rsItens.getInt("produto_id"));
                        vendaProdutos.setQuantidade(rsItens.getInt("quantidade"));
                        vendaProdutos.setSubTotal(rsItens.getDouble("subtotal"));
                        vendaProdutos.setProduto(DaoFactory.createProdutoDao().buscarPorId(rsItens.getInt("produto_id")));
                        itens.add(vendaProdutos);
                    }
                    venda.setItens(itens);
                } finally {
                    DB.closeResultSet(rsItens);
                    DB.closeStatement(stItens);
                }

                vendas.add(venda);
            }

            return vendas;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DB.closeResultSet(rsVendas);
            DB.closeStatement(stVendas);
        }

        return null;
    }

    /**
     * Insere uma nova venda no banco de dados.
     * @param venda Venda a ser inserida.
     */
    @Override
    public void inserir(Venda venda) {
        PreparedStatement stVenda = null;
        PreparedStatement stItens = null;
        PreparedStatement stEstoque = null;
        ResultSet rs = null;

        String sqlVenda = "INSERT INTO vendas (data_hora, total) VALUES (?, ?)";
        String sqlItens = "INSERT INTO venda_produtos (venda_id, produto_id, quantidade, subtotal) VALUES (?, ?, ?, ?)";
        String sqlEstoque = "UPDATE produtos SET quantidade = quantidade - ? WHERE id = ?";

        try {
            // Iniciar transação
            conn.setAutoCommit(false);

            // Inserir a venda
            stVenda = conn.prepareStatement(sqlVenda, PreparedStatement.RETURN_GENERATED_KEYS);
            stVenda.setTimestamp(1, Timestamp.valueOf(venda.getData()));
            stVenda.setDouble(2, venda.getValorTotal());
            int linhasAfetadas = stVenda.executeUpdate();

            if (linhasAfetadas > 0) {
                rs = stVenda.getGeneratedKeys();
                if (rs.next()) {
                    venda.setId(rs.getInt(1));
                } else {
                    throw new SQLException("Erro ao obter o ID da venda inserida.");
                }
            } else {
                throw new SQLException("Erro ao inserir a venda.");
            }

            // Inserir os produtos associados à venda
            stItens = conn.prepareStatement(sqlItens);
            stEstoque = conn.prepareStatement(sqlEstoque);

            for (VendaProdutos vp : venda.getItens()) {
                // Inserindo os itens na tabela venda_produtos
                stItens.setInt(1, venda.getId());
                stItens.setInt(2, vp.getIdProduto());
                stItens.setInt(3, vp.getQuantidade());
                stItens.setDouble(4, vp.getSubTotal());
                stItens.executeUpdate();

                // Atualizando o estoque do produto
                stEstoque.setInt(1, vp.getQuantidade());
                stEstoque.setInt(2, vp.getIdProduto());
                stEstoque.executeUpdate();
            }

            // Confirmar a transação
            conn.commit();
            System.out.println("Venda registrada com sucesso!");

        } catch (SQLException e) {
            try {
                conn.rollback(); // Reverte todas as operações caso ocorra erro
                System.out.println("Transação revertida devido a erro: " + e.getMessage());
            } catch (SQLException rollbackEx) {
                System.out.println("Erro ao reverter transação: " + rollbackEx.getMessage());
            }
            e.printStackTrace();

        } finally {
            // Restaurar auto-commit e fechar recursos
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DB.closeResultSet(rs);
            DB.closeStatement(stVenda);
            DB.closeStatement(stItens);
            DB.closeStatement(stEstoque);
        }
    }
}