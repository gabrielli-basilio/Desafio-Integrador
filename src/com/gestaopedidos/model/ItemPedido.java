package com.gestaopedidos.model;

public class ItemPedido {
    private final int id;
    private final int idPedido;
    private final int idProduto;
    private final double valorUnitario;
    private final int quantidade;

    public ItemPedido(int id, int idPedido, int idProduto, double valorUnitario, int quantidade) {
        this.id = id;
        this.idPedido = idPedido;
        this.idProduto = idProduto;
        this.valorUnitario = valorUnitario;
        this.quantidade = quantidade;
    }

    public int getId() { return id; }
    public int getIdPedido() { return idPedido; }
    public int getIdProduto() { return idProduto; }
    public double getValorUnitario() { return valorUnitario; }
    public int getQuantidade() { return quantidade; }

    @Override
    public String toString() {
        return "ID Produto: " + idProduto + " | Quantidade: " + quantidade + " | Valor Unit.: R$" + valorUnitario;
    }
}