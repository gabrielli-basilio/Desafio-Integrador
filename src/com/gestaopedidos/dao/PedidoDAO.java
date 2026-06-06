package com.gestaopedidos.dao;

import com.gestaopedidos.exception.ValidacaoException;
import com.gestaopedidos.infra.ConexaoBanco;
import com.gestaopedidos.model.Pedido;
import com.gestaopedidos.model.enums.StatusPedido;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAO {

    public int salvar(Pedido pedido, Connection conn) throws SQLException {
        String sql = "INSERT INTO pedido (id_cliente, status) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, pedido.getIdCliente());
            stmt.setString(2, pedido.getStatus().name());
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        return -1;
    }

    public List<Pedido> listarTodos() {
        List<Pedido> pedidos = new ArrayList<>();
        String sql = "SELECT * FROM pedido";
        try (Connection conn = ConexaoBanco.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                pedidos.add(new Pedido(
                    rs.getInt("id_pedido"),
                    rs.getInt("id_cliente"),
                    StatusPedido.valueOf(rs.getString("status").toUpperCase()),
                    rs.getTimestamp("data_hora").toLocalDateTime()
                ));
            }
        } catch (SQLException e) {
            throw new ValidacaoException("Erro ao listar pedidos: " + e.getMessage());
        }
        return pedidos;
    }

    public void atualizarStatus(int idPedido, StatusPedido status, Connection conn) throws SQLException {
        String sql = "UPDATE pedido SET status = ? WHERE id_pedido = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status.name());
            stmt.setInt(2, idPedido);
            stmt.executeUpdate();
        }
    }

    public Pedido buscarPedidoNaFila(Connection conn) throws SQLException {
        String sql = "SELECT * FROM pedido WHERE status = 'FILA' LIMIT 1 FOR UPDATE SKIP LOCKED";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return new Pedido(
                    rs.getInt("id_pedido"),
                    rs.getInt("id_cliente"),
                    StatusPedido.valueOf(rs.getString("status").toUpperCase()),
                    rs.getTimestamp("data_hora").toLocalDateTime()
                );
            }
        }
        return null;
    }
}