package com.padaria.model.dao;

import com.padaria.db.DB;
import com.padaria.model.entities.Produto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementação JDBC do ProdutoDao.
 * Gerencia operações de banco de dados para a entidade Produto.
 */
public class ProdutoDaoJDBC implements ProdutoDao {
    private final Connection conn;

    /**
     * Construtor que inicializa a conexão com o banco de dados.
     * @param conn Conexão com o banco de dados.
     */
    public ProdutoDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    /**
     * Insere um novo produto no banco de dados.
     * @param produto Produto a ser inserido.
     */
    @Override
    public void inserir(Produto produto) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "INSERT INTO produtos (nome, categoria, preco, quantidade, validade) VALUES (?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            st.setString(1, produto.getNome());
            st.setString(2, produto.getCategoria());
            st.setDouble(3, produto.getPreco());
            st.setInt(4, produto.getQuantidade());
            st.setDate(5, Date.valueOf(produto.getValidade()));
            int linhasAfetadas = st.executeUpdate();

            if (linhasAfetadas > 0) {
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    produto.setId(id);
                } else {
                    throw new RuntimeException("Erro inesperado! Nenhuma linha foi afetada.");
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir um novo produto: " + e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    /**
     * Atualiza um produto existente no banco de dados.
     * @param produto Produto a ser atualizado.
     */
    @Override
    public void atualizar(Produto produto) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "UPDATE produtos SET nome = ?, categoria = ?, preco = ?, quantidade = ?, validade = ? WHERE id = ?"
            );
            st.setString(1, produto.getNome());
            st.setString(2, produto.getCategoria());
            st.setDouble(3, produto.getPreco());
            st.setInt(4, produto.getQuantidade());
            st.setDate(5, Date.valueOf(produto.getValidade()));
            st.setInt(6, produto.getId());
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar um produto: " + e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    /**
     * Remove um produto do banco de dados pelo seu ID.
     * @param id ID do produto a ser removido.
     */
    @Override
    public void removerPorId(int id) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("DELETE FROM produtos WHERE id = ?");
            st.setInt(1, id);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao remover um produto: " + e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    /**
     * Busca um produto no banco de dados pelo seu ID.
     * @param id ID do produto a ser buscado.
     * @return Produto encontrado ou null se não encontrado.
     */
    @Override
    public Produto buscarPorId(int id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("SELECT * FROM produtos WHERE id = ?");
            st.setInt(1, id);

            rs = st.executeQuery();
            if (rs.next()) {
                Produto produto = new Produto();
                produto.setId(rs.getInt("id"));
                produto.setNome(rs.getString("nome"));
                produto.setCategoria(rs.getString("categoria"));
                produto.setPreco(rs.getDouble("preco"));
                produto.setQuantidade(rs.getInt("quantidade"));
                produto.setValidade(rs.getDate("validade").toLocalDate());

                return produto;
            }

            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(st);
        }
    }

    /**
     * Busca todos os produtos no banco de dados.
     * @return Lista de todos os produtos.
     */
    @Override
    public List<Produto> buscarTodos() {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement("SELECT * FROM produtos");
            rs = st.executeQuery();

            List<Produto> produtos = new ArrayList<>();

            while (rs.next()) {
                Produto produto = new Produto();
                produto.setId(rs.getInt("id"));
                produto.setNome(rs.getString("nome"));
                produto.setCategoria(rs.getString("categoria"));
                produto.setPreco(rs.getDouble("preco"));
                produto.setQuantidade(rs.getInt("quantidade"));
                produto.setValidade(rs.getDate("validade").toLocalDate());
                produtos.add(produto);
            }

            return produtos;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar todos os produtos: " + e.getMessage());
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(st);
        }
    }
}