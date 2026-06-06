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
            System.out.println("3 - Atualizar Estoque");
            System.out.println("0 - Voltar");
            System.out.print("Opção: ");

            try {
                opcao = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Opção inválida! Tente novamente.");
                continue;
            }

            switch (opcao) {
                case 1 -> cadastrar();
                case 2 -> listar();
                case 3 -> atualizarEstoque();
                case 0 -> System.out.println("Voltando...");
                default -> System.out.println("Opção inválida!");
            }
        }
    }

    private void cadastrar() {
        try {
            System.out.print("Nome: ");
            String nome = scanner.nextLine();

            if (nome == null || nome.trim().isEmpty()) {
                System.out.println("Erro: Nome não pode ser vazio!");
                return;
            }

            System.out.print("Preço: ");
            double preco;
            try {
                preco = Double.parseDouble(scanner.nextLine().trim().replace(",", "."));
            } catch (NumberFormatException e) {
                System.out.println("Erro: Preço inválido!");
                return;
            }

            System.out.print("Quantidade em estoque: ");
            int estoque;
            try {
                estoque = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Erro: Quantidade inválida!");
                return;
            }

            System.out.println("Categorias:");
            System.out.println("1 - LIVROS");
            System.out.println("2 - ELETRONICOS");
            System.out.println("3 - ROUPAS");
            System.out.println("4 - CALCADOS");
            System.out.print("Escolha: ");
            int opcaoCategoria;
            try {
                opcaoCategoria = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Erro: Categoria inválida!");
                return;
            }

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

    private void atualizarEstoque() {
        try {
            listar();

            System.out.print("ID do Produto: ");
            int id;
            try {
                id = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Erro: ID inválido!");
                return;
            }

            System.out.print("Quantidade a adicionar: ");
            int quantidade;
            try {
                quantidade = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Erro: Quantidade inválida!");
                return;
            }

            produtoService.atualizarEstoque(id, quantidade);

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