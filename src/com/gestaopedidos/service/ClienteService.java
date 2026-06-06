package com.gestaopedidos.service;

import com.gestaopedidos.dao.ClienteDAO;
import com.gestaopedidos.exception.ValidacaoException;
import com.gestaopedidos.model.Cliente;

import java.util.List;

public class ClienteService {

    private final ClienteDAO clienteDAO = new ClienteDAO();

    public void cadastrar(String nome, String cpf, String email) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new ValidacaoException("Nome não pode ser vazio!");
        }
        if (cpf == null || cpf.trim().length() != 11) {
            throw new ValidacaoException("CPF deve ter 11 dígitos!");
        }
        if (email == null || !email.contains("@")) {
            throw new ValidacaoException("Email inválido!");
        }

        Cliente cliente = new Cliente(0, nome, cpf, email);
        clienteDAO.salvar(cliente);
        System.out.println("Cliente cadastrado com sucesso!");
    }

    public List<Cliente> listarTodos() {
        return clienteDAO.listarTodos();
    }

    public Cliente buscarPorId(int id) {
        Cliente cliente = clienteDAO.buscarPorId(id);
        if (cliente == null) {
            throw new ValidacaoException("Cliente não encontrado!");
        }
        return cliente;
    }
}