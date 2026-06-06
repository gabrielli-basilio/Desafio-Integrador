package com.gestaopedidos.ui;

import com.gestaopedidos.exception.ValidacaoException;
import com.gestaopedidos.model.Produto;
import com.gestaopedidos.model.enums.Categoria;
import com.gestaopedidos.service.ProdutoService;

import java.util.List;
import java.util.Scanner;

public class MenuProduto {

    private final ProdutoService produtoService = new ProdutoService();
    private final Scanner scanner;

    public MenuProduto(Scanner scanner) {
        this.scanner = scanner;
    }

    public void exibir() {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n=== MENU PRODUTO ===");
            System.out.println("1 - Cadastrar Produto");
            System.out.println("2 - Listar Produtos");
            System.out.println("0 - Voltar");
            System.out.print("Opção: ");

            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1 -> cadastrar();
                case 2 -> listar();
                case 0 -> System.out.println("Voltando...");
                default -> System.out.println("Opção inválida!");
            }
        }
    }

    private void cadastrar() {
        try {
            System.out.print("Nome: ");
            String nome = scanner.nextLine();

            System.out.print("Preço: ");
            double preco = scanner.nextDouble();
            scanner.nextLine();

            System.out.print("Quantidade em estoque: ");
            int estoque = scanner.nextInt();
            scanner.nextLine();

            System.out.println("Categorias:");
            System.out.println("1 - LIVROS");
            System.out.println("2 - ELETRONICOS");
            System.out.println("3 - ROUPAS");
            System.out.println("4 - CALCADOS");
            System.out.print("Escolha: ");
            int opcaoCategoria = scanner.nextInt();
            scanner.nextLine();

            Categoria categoria = switch (opcaoCategoria) {
                case 1 -> Categoria.LIVROS;
                case 2 -> Categoria.ELETRONICOS;
                case 3 -> Categoria.ROUPAS;
                case 4 -> Categoria.CALCADOS;
                default -> throw new ValidacaoException("Categoria inválida!");
            };

            produtoService.cadastrar(nome, preco, estoque, categoria);

        } catch (ValidacaoException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void listar() {
        List<Produto> produtos = produtoService.listarTodos();
        if (produtos.isEmpty()) {
            System.out.println("Nenhum produto cadastrado!");
            return;
        }
        System.out.println("\n=== PRODUTOS ===");
        produtos.forEach(System.out::println);
    }
}