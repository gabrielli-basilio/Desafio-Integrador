package com.gestaopedidos.dao;

import com.gestaopedidos.exception.ValidacaoException;
import com.gestaopedidos.infra.ConexaoBanco;
import java.sql.*;

public class RelatorioDAO {

    public void produtosMaisVendidos() {
        String sql = """
            SELECT p.nome, SUM(ip.quantidade) AS total_vendido,
                   SUM(ip.quantidade * ip.valor_unitario) AS receita_total
            FROM item_pedido ip
            JOIN produto p ON p.id_produto = ip.id_produto
            GROUP BY p.id_produto, p.nome
            ORDER BY total_vendido DESC
        """;
        try (Connection conn = ConexaoBanco.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            System.out.println("\n=============================");
            System.out.println("   PRODUTOS MAIS VENDIDOS    ");
            System.out.println("=============================");
            System.out.printf("%-25s %10s %15s%n", "Produto", "Qtd Vendida", "Receita Total");
            System.out.println("---------------------------------------------");
            while (rs.next()) {
                System.out.printf("%-25s %10d %15s%n",
                    rs.getString("nome"),
                    rs.getInt("total_vendido"),
                    String.format("R$ %.2f", rs.getDouble("receita_total"))
                );
            }
            System.out.println("=============================");

        } catch (SQLException e) {
            throw new ValidacaoException("Erro ao gerar relatório: " + e.getMessage());
        }
    }

    public void pedidosPorCliente() {
        String sql = """
            SELECT c.nome, COUNT(p.id_pedido) AS total_pedidos,
                   SUM(ip.quantidade * ip.valor_unitario) AS valor_total,
                   AVG(ip.quantidade * ip.valor_unitario) AS ticket_medio
            FROM cliente c
            JOIN pedido p ON p.id_cliente = c.id_cliente
            JOIN item_pedido ip ON ip.id_pedido = p.id_pedido
            GROUP BY c.id_cliente, c.nome
            ORDER BY total_pedidos DESC
        """;
        try (Connection conn = ConexaoBanco.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            System.out.println("\n=============================");
            System.out.println("    PEDIDOS POR CLIENTE      ");
            System.out.println("=============================");
            System.out.printf("%-25s %8s %15s %15s%n", "Cliente", "Pedidos", "Valor Total", "Ticket Médio");
            System.out.println("-------------------------------------------------------");
            while (rs.next()) {
                System.out.printf("%-25s %8d %15s %15s%n",
                    rs.getString("nome"),
                    rs.getInt("total_pedidos"),
                    String.format("R$ %.2f", rs.getDouble("valor_total")),
                    String.format("R$ %.2f", rs.getDouble("ticket_medio"))
                );
            }
            System.out.println("=============================");

        } catch (SQLException e) {
            throw new ValidacaoException("Erro ao gerar relatório: " + e.getMessage());
        }
    }
}