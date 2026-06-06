package com.gestaopedidos.ui;

import com.gestaopedidos.exception.ValidacaoException;
import com.gestaopedidos.model.Cliente;
import com.gestaopedidos.service.ClienteService;
import java.util.List;
import java.util.Scanner;

public class MenuCliente {

    private final ClienteService clienteService = new ClienteService();
    private final Scanner scanner;

    public MenuCliente(Scanner scanner) {
        this.scanner = scanner;
    }

    public void exibir() {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n=== MENU CLIENTE ===");
            System.out.println("1 - Cadastrar Cliente");
            System.out.println("2 - Listar Clientes");
            System.out.println("3 - Remover Cliente");
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
                case 3 -> remover();
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
            if (nome.matches(".*\\d+.*")) {
                System.out.println("Erro: Nome não pode conter números!");
                return;
            }
            if (nome.matches(".*[!@#$%^&*()_+=\\[\\]{}|;:',.<>?/\\\\\"]+.*")) {
                System.out.println("Erro: Nome não pode conter símbolos!");
                return;
            }

            System.out.print("CPF (só números): ");
            String cpf = scanner.nextLine();

            if (cpf.trim().length() != 11) {
                System.out.println("Erro: CPF deve ter exatamente 11 dígitos!");
                return;
            }

            System.out.print("Email: ");
            String email = scanner.nextLine();

            clienteService.cadastrar(nome, cpf, email);

        } catch (ValidacaoException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void remover() {
        try {
            listar();

            System.out.print("ID do Cliente a remover: ");
            int id;
            try {
                id = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Erro: ID inválido!");
                return;
            }

            System.out.print("Tem certeza? (s/n): ");
            String confirmacao = scanner.nextLine();

            if (confirmacao.equalsIgnoreCase("s")) {
                clienteService.deletar(id);
            } else {
                System.out.println("Operação cancelada!");
            }

        } catch (ValidacaoException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void listar() {
        List<Cliente> clientes = clienteService.listarTodos();
        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado!");
            return;
        }
        System.out.println("\n=== CLIENTES ===");
        clientes.forEach(System.out::println);
    }
}