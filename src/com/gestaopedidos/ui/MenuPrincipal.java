package com.gestaopedidos.ui;

import java.util.Scanner;

public class MenuPrincipal {

    private final Scanner scanner;
    private final MenuCliente menuCliente;
    private final MenuProduto menuProduto;
    private final MenuPedido menuPedido;

    public MenuPrincipal(Scanner scanner) {
        this.scanner = scanner;
        this.menuCliente = new MenuCliente(scanner);
        this.menuProduto = new MenuProduto(scanner);
        this.menuPedido = new MenuPedido(scanner);
    }

    public void exibir() {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n=== SISTEMA DE GESTÃO DE PEDIDOS ===");
            System.out.println("1 - Clientes");
            System.out.println("2 - Produtos");
            System.out.println("3 - Pedidos");
            System.out.println("0 - Sair");
            System.out.print("Opção: ");

            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1 -> menuCliente.exibir();
                case 2 -> menuProduto.exibir();
                case 3 -> menuPedido.exibir();
                case 0 -> System.out.println("Encerrando sistema...");
                default -> System.out.println("Opção inválida!");
            }
        }
    }
}