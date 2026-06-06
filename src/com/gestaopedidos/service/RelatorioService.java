package com.gestaopedidos.service;

import com.gestaopedidos.dao.RelatorioDAO;

public class RelatorioService {

    private final RelatorioDAO relatorioDAO = new RelatorioDAO();

    public void produtosMaisVendidos() {
        relatorioDAO.produtosMaisVendidos();
    }

    public void pedidosPorCliente() {
        relatorioDAO.pedidosPorCliente();
    }
}