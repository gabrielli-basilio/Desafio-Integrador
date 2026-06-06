package com.gestaopedidos.service;

import com.gestaopedidos.dao.ProdutoDAO;
import com.gestaopedidos.exception.ValidacaoException;
import com.gestaopedidos.model.Produto;
import com.gestaopedidos.model.enums.Categoria;
import java.util.List;

public class ProdutoService {

    private final ProdutoDAO produtoDAO = new ProdutoDAO();

    public void cadastrar(String nome, double preco, int quantEstoque, Categoria categoria) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new ValidacaoException("Nome não pode ser vazio!");
        }
        if (preco <= 0) {
            throw new ValidacaoException("Preço deve ser positivo!");
        }
        if (quantEstoque < 0) {
            throw new ValidacaoException("Estoque não pode ser negativo!");
        }

        Produto produto = new Produto(0, nome, preco, quantEstoque, categoria);
        produtoDAO.salvar(produto);
        System.out.println("Produto cadastrado com sucesso!");
    }

    public void atualizarEstoque(int id, int quantidade) {
        if (quantidade <= 0) {
            throw new ValidacaoException("Quantidade deve ser positiva!");
        }
        produtoDAO.adicionarEstoque(id, quantidade);
        System.out.println("Estoque atualizado com sucesso!");
    }

    public List<Produto> listarTodos() {
        return produtoDAO.listarTodos();
    }

    public Produto buscarPorId(int id) {
        Produto produto = produtoDAO.buscarPorId(id);
        if (produto == null) {
            throw new ValidacaoException("Produto não encontrado!");
        }
        return produto;
    }
}