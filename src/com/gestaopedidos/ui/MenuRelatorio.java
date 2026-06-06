package com.gestaopedidos.ui;

import com.gestaopedidos.exception.ValidacaoException;
import com.gestaopedidos.service.RelatorioService;
import java.util.Scanner;

public class MenuRelatorio {

    private final RelatorioService relatorioService = new RelatorioService();
    private final Scanner scanner;

    public MenuRelatorio(Scanner scanner) {
        this.scanner = scanner;
    }

    public void exibir() {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n=== RELATÓRIOS ===");
            System.out.println("1 - Produtos Mais Vendidos");
            System.out.println("2 - Pedidos por Cliente");
            System.out.println("0 - Voltar");
            System.out.print("Opção: ");

            try {
                opcao = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Opção inválida!");
                continue;
            }

            try {
                switch (opcao) {
                    case 1 -> relatorioService.produtosMaisVendidos();
                    case 2 -> relatorioService.pedidosPorCliente();
                    case 0 -> System.out.println("Voltando...");
                    default -> System.out.println("Opção inválida!");
                }
            } catch (ValidacaoException e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }
    }
}