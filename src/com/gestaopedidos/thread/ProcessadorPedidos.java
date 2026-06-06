package com.gestaopedidos.thread;

import com.gestaopedidos.dao.PedidoDAO;
import com.gestaopedidos.infra.ConexaoBanco;
import com.gestaopedidos.model.Pedido;
import com.gestaopedidos.model.enums.StatusPedido;

import java.sql.Connection;
import java.sql.SQLException;

public class ProcessadorPedidos implements Runnable {

    private volatile boolean rodando = true;

    public void parar() {
        rodando = false;
    }

    @Override
    public void run() {
        PedidoDAO pedidoDAO = new PedidoDAO();

        while (rodando) {
            try (Connection conn = ConexaoBanco.conectar()) {
                conn.setAutoCommit(false);

                Pedido pedido = pedidoDAO.buscarPedidoNaFila(conn);

                if (pedido != null) {
                    pedidoDAO.atualizarStatus(pedido.getId(), StatusPedido.PROCESSANDO, conn);
                    conn.commit();

                    System.out.println("\n[THREAD] Processando pedido ID: " + pedido.getId());
                    Thread.sleep(3000);

                    try (Connection conn2 = ConexaoBanco.conectar()) {
                        pedidoDAO.atualizarStatus(pedido.getId(), StatusPedido.FINALIZADO, conn2);
                        System.out.println("[THREAD] Pedido ID: " + pedido.getId() + " finalizado!");
                    }
                } else {
                    conn.rollback();
                    Thread.sleep(5000);
                }

            } catch (SQLException | InterruptedException e) {
                System.out.println("[THREAD] Erro: " + e.getMessage());
            }
        }
    }
}