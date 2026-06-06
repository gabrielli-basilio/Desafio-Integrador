package com.gestaopedidos.service;

import com.gestaopedidos.dao.ItemPedidoDAO;
import com.gestaopedidos.dao.PedidoDAO;
import com.gestaopedidos.dao.ProdutoDAO;
import com.gestaopedidos.exception.EstoqueInsuficienteException;
import com.gestaopedidos.exception.ValidacaoException;
import com.gestaopedidos.infra.ConexaoBanco;
import com.gestaopedidos.model.ItemPedido;
import com.gestaopedidos.model.Pedido;
import com.gestaopedidos.model.enums.StatusPedido;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class PedidoService {

    private final PedidoDAO pedidoDAO = new PedidoDAO();
    private final ProdutoDAO produtoDAO = new ProdutoDAO();
    private final ItemPedidoDAO itemPedidoDAO = new ItemPedidoDAO();

    public void criarPedido(int idCliente, Map<Integer, Integer> produtos) {
        try (Connection conn = ConexaoBanco.conectar()) {
            conn.setAutoCommit(false);

            Pedido pedido = new Pedido(0, idCliente, StatusPedido.FILA, LocalDateTime.now());
            int idPedido = pedidoDAO.salvar(pedido, conn);

            for (Map.Entry<Integer, Integer> entry : produtos.entrySet()) {
                int idProduto = entry.getKey();
                int quantidade = entry.getValue();

                boolean atualizado = produtoDAO.atualizarEstoque(idProduto, quantidade, conn);
                if (!atualizado) {
                    conn.rollback();
                    throw new EstoqueInsuficienteException("Estoque insuficiente para o produto ID: " + idProduto);
                }

                var produto = produtoDAO.buscarPorId(idProduto);
                ItemPedido item = new ItemPedido(0, idPedido, idProduto, produto.getPreco(), quantidade);
                itemPedidoDAO.salvar(item, conn);
            }

            conn.commit();
            System.out.println("Pedido criado com sucesso! ID: " + idPedido);

        } catch (SQLException e) {
            throw new ValidacaoException("Erro ao criar pedido: " + e.getMessage());
        }
    }

    public List<Pedido> listarTodos() {
        return pedidoDAO.listarTodos();
    }
}