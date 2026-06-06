package com.gestaopedidos.model;

import com.gestaopedidos.model.enums.StatusPedido;
import java.time.LocalDateTime;

public class Pedido {
    private final int id;
    private final int idCliente;
    private final StatusPedido status;
    private final LocalDateTime dataHora;

    public Pedido(int id, int idCliente, StatusPedido status, LocalDateTime dataHora) {
        this.id = id;
        this.idCliente = idCliente;
        this.status = status;
        this.dataHora = dataHora;
    }

    public int getId() { return id; }
    public int getIdCliente() { return idCliente; }
    public StatusPedido getStatus() { return status; }
    public LocalDateTime getDataHora() { return dataHora; }

    @Override
    public String toString() {
        return "ID: " + id + " | Cliente ID: " + idCliente + " | Status: " + status + " | Data: " + dataHora;
    }
}