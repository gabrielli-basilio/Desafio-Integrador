package com.gestaopedidos.ui;

import com.gestaopedidos.exception.EstoqueInsuficienteException;
import com.gestaopedidos.exception.ValidacaoException;
import com.gestaopedidos.model.Pedido;
import com.gestaopedidos.service.PedidoService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class MenuPedido {

    private final PedidoService pedidoService = new PedidoService();
    private final Scanner scanner;

    public MenuPedido(Scanner scanner) {
        this.scanner = scanner;
    }

    public void exibir() {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n=== MENU PEDIDO ===");
            System.out.println("1 - Criar Pedido");
            System.out.println("2 - Listar Pedidos");
            System.out.println("3 - Ver Detalhes do Pedido");
            System.out.println("0 - Voltar");
            System.out.print("Opção: ");

            try {
                opcao = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Opção inválida! Tente novamente.");
                continue;
            }

            switch (opcao) {
                case 1 -> criar();
                case 2 -> listar();
                case 3 -> verDetalhes();
                case 0 -> System.out.println("Voltando...");
                default -> System.out.println("Opção inválida!");
            }
        }
    }

    private void criar() {
        try {
            System.out.print("ID do Cliente: ");
            int idCliente;
            try {
                idCliente = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Erro: ID inválido!");
                return;
            }

            Map<Integer, Integer> produtos = new HashMap<>();
            String continuar = "s";

            while (continuar.equalsIgnoreCase("s")) {
                System.out.print("ID do Produto: ");
                int idProduto;
                try {
                    idProduto = Integer.parseInt(scanner.nextLine().trim());
                } catch (NumberFormatException e) {
                    System.out.println("Erro: ID inválido!");
                    return;
                }

                System.out.print("Quantidade: ");
                int quantidade;
                try {
                    quantidade = Integer.parseInt(scanner.nextLine().trim());
                } catch (NumberFormatException e) {
                    System.out.println("Erro: Quantidade inválida!");
                    return;
                }

                produtos.put(idProduto, quantidade);

                System.out.print("Adicionar mais produto? (s/n): ");
                continuar = scanner.nextLine();
            }

            pedidoService.criarPedido(idCliente, produtos);

        } catch (EstoqueInsuficienteException | ValidacaoException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void verDetalhes() {
        try {
            listar();

            System.out.print("ID do Pedido: ");
            int id;
            try {
                id = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Erro: ID inválido!");
                return;
            }

            pedidoService.exibirDetalhes(id);

        } catch (ValidacaoException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void listar() {
        List<Pedido> pedidos = pedidoService.listarTodos();
        if (pedidos.isEmpty()) {
            System.out.println("Nenhum pedido cadastrado!");
            return;
        }
        System.out.println("\n=== PEDIDOS ===");
        pedidos.forEach(System.out::println);
    }
}