package com.gestaopedidos.service;

import com.gestaopedidos.dao.ClienteDAO;
import com.gestaopedidos.dao.ItemPedidoDAO;
import com.gestaopedidos.dao.PedidoDAO;
import com.gestaopedidos.dao.ProdutoDAO;
import com.gestaopedidos.exception.EstoqueInsuficienteException;
import com.gestaopedidos.exception.ValidacaoException;
import com.gestaopedidos.infra.ConexaoBanco;
import com.gestaopedidos.model.Cliente;
import com.gestaopedidos.model.ItemPedido;
import com.gestaopedidos.model.Pedido;
import com.gestaopedidos.model.Produto;
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
    private final ClienteDAO clienteDAO = new ClienteDAO();

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

    public void exibirDetalhes(int idPedido) {
        Pedido pedido = pedidoDAO.buscarPorId(idPedido);
        if (pedido == null) {
            throw new ValidacaoException("Pedido não encontrado!");
        }

        Cliente cliente = clienteDAO.buscarPorId(pedido.getIdCliente());
        List<ItemPedido> itens = itemPedidoDAO.buscarPorPedido(idPedido);

        double totalGeral = 0;

        System.out.println("\n=============================");
        System.out.println("       DETALHES DO PEDIDO    ");
        System.out.println("=============================");
        System.out.println("Pedido ID: " + pedido.getId());
        System.out.println("Cliente: " + (cliente != null ? cliente.getNome() : "N/A"));
        System.out.println("Data: " + pedido.getDataHora());
        System.out.println("Status: " + pedido.getStatus());
        System.out.println("-----------------------------");
        System.out.println("ITENS:");

        for (ItemPedido item : itens) {
            Produto produto = produtoDAO.buscarPorId(item.getIdProduto());
            double totalItem = item.getValorUnitario() * item.getQuantidade();
            totalGeral += totalItem;
            System.out.printf("- %-20s x%d   R$ %.2f   Total: R$ %.2f%n",
                produto != null ? produto.getNome() : "Produto ID " + item.getIdProduto(),
                item.getQuantidade(),
                item.getValorUnitario(),
                totalItem
            );
        }

        System.out.println("-----------------------------");
        System.out.printf("TOTAL GERAL: R$ %.2f%n", totalGeral);
        System.out.println("=============================");
    }

    public List<Pedido> listarTodos() {
        return pedidoDAO.listarTodos();
    }
}