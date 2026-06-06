package com.gestaopedidos.dao;

import com.gestaopedidos.exception.ValidacaoException;
import com.gestaopedidos.infra.ConexaoBanco;
import com.gestaopedidos.model.Produto;
import com.gestaopedidos.model.enums.Categoria;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {

    private Categoria converterCategoria(String valor) {
        return Categoria.valueOf(
            valor.toUpperCase()
                .replace("Ç", "C")
                .replace("Ã", "A")
                .replace("Ô", "O")
                .replace("Ó", "O")
                .replace("Â", "A")
                .replace("É", "E")
                .replace("Ê", "E")
                .replace("Í", "I")
                .replace("Ú", "U")
        );
    }

    public void salvar(Produto produto) {
        String sql = "INSERT INTO produto (nome, valor_unitario, quant_estoque, categoria) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConexaoBanco.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, produto.getNome());
            stmt.setDouble(2, produto.getPreco());
            stmt.setInt(3, produto.getQuantEstoque());
            stmt.setString(4, produto.getCategoria().name());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new ValidacaoException("Erro ao salvar produto: " + e.getMessage());
        }
    }

    public void adicionarEstoque(int id, int quantidade) {
        String sql = "UPDATE produto SET quant_estoque = quant_estoque + ? WHERE id_produto = ?";
        try (Connection conn = ConexaoBanco.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, quantidade);
            stmt.setInt(2, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new ValidacaoException("Erro ao atualizar estoque: " + e.getMessage());
        }
    }

    public List<Produto> listarTodos() {
        List<Produto> produtos = new ArrayList<>();
        String sql = "SELECT * FROM produto";
        try (Connection conn = ConexaoBanco.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                produtos.add(new Produto(
                    rs.getInt("id_produto"),
                    rs.getString("nome"),
                    rs.getDouble("valor_unitario"),
                    rs.getInt("quant_estoque"),
                    converterCategoria(rs.getString("categoria"))
                ));
            }
        } catch (SQLException e) {
            throw new ValidacaoException("Erro ao listar produtos: " + e.getMessage());
        }
        return produtos;
    }

    public Produto buscarPorId(int id) {
        String sql = "SELECT * FROM produto WHERE id_produto = ?";
        try (Connection conn = ConexaoBanco.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Produto(
                        rs.getInt("id_produto"),
                        rs.getString("nome"),
                        rs.getDouble("valor_unitario"),
                        rs.getInt("quant_estoque"),
                        converterCategoria(rs.getString("categoria"))
                    );
                }
            }
        } catch (SQLException e) {
            throw new ValidacaoException("Erro ao buscar produto: " + e.getMessage());
        }
        return null;
    }

    public boolean atualizarEstoque(int idProduto, int quantidade, Connection conn) throws SQLException {
        String sql = "UPDATE produto SET quant_estoque = quant_estoque - ? WHERE id_produto = ? AND quant_estoque >= ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, quantidade);
            stmt.setInt(2, idProduto);
            stmt.setInt(3, quantidade);
            return stmt.executeUpdate() > 0;
        }
    }
}