package com.gestaopedidos.dao;

import com.gestaopedidos.exception.ValidacaoException;
import com.gestaopedidos.infra.ConexaoBanco;
import com.gestaopedidos.model.ItemPedido;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemPedidoDAO {

    public void salvar(ItemPedido item, Connection conn) throws SQLException {
        String sql = "INSERT INTO item_pedido (id_pedido, id_produto, valor_unitario, quantidade) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, item.getIdPedido());
            stmt.setInt(2, item.getIdProduto());
            stmt.setDouble(3, item.getValorUnitario());
            stmt.setInt(4, item.getQuantidade());
            stmt.executeUpdate();
        }
    }

    public List<ItemPedido> buscarPorPedido(int idPedido) {
        List<ItemPedido> itens = new ArrayList<>();
        String sql = "SELECT * FROM item_pedido WHERE id_pedido = ?";
        try (Connection conn = ConexaoBanco.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idPedido);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    itens.add(new ItemPedido(
                        rs.getInt("id_item_pedido"),
                        rs.getInt("id_pedido"),
                        rs.getInt("id_produto"),
                        rs.getDouble("valor_unitario"),
                        rs.getInt("quantidade")
                    ));
                }
            }
        } catch (SQLException e) {
            throw new ValidacaoException("Erro ao buscar itens: " + e.getMessage());
        }
        return itens;
    }
}