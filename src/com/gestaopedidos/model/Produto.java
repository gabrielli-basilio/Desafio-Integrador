package com.gestaopedidos.model;

import com.gestaopedidos.model.enums.Categoria;

public class Produto {
    private final int id;
    private final String nome;
    private final double preco;
    private final int quantEstoque;
    private final Categoria categoria;

    public Produto(int id, String nome, double preco, int quantEstoque, Categoria categoria) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.quantEstoque = quantEstoque;
        this.categoria = categoria;
    }

    public int getId() { return id; }
    public String getNome() { return nome; }
    public double getPreco() { return preco; }
    public int getQuantEstoque() { return quantEstoque; }
    public Categoria getCategoria() { return categoria; }

    @Override
    public String toString() {
        return "ID: " + id + " | Nome: " + nome + " | Preço: R$" + preco + " | Estoque: " + quantEstoque + " | Categoria: " + categoria;
    }
}