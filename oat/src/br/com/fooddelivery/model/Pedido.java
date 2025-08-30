package br.com.fooddelivery.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Classe de Modelo principal, ela representa a transação de um pedido de um cliente.
 * Agregando as informações do cliente, os itens pedidos e o estado atual do pedido.
 */

public class Pedido {
    private int numero;
    private LocalDateTime dataHora;
    private StatusPedido status;
    private Cliente cliente;
    private List<ItemPedido> itens;

    public Pedido(int numero, Cliente cliente) {
        this.numero = numero;
        this.cliente = cliente;
        this.dataHora = LocalDateTime.now(); // Pega a data e hora atuais
        this.status = StatusPedido.ACEITO;   // Todo pedido começa como ACEITO
        this.itens = new ArrayList<>();        // Inicializa a lista de itens
    }

    public void adicionarItem(ItemPedido item) {
        this.itens.add(item);
    }

    public double getValorTotal() {
        double total = 0.0;
        for (ItemPedido item : this.itens) {
            total += item.getSubtotal();
        }
        return total;
    }

    // Método para avançar o status seguindo a regra de negócio
    public boolean avancarStatus() {
        switch (this.status) {
            case ACEITO:
                this.status = StatusPedido.PREPARANDO;
                return true;
            case PREPARANDO:
                this.status = StatusPedido.FEITO;
                return true;
            case FEITO:
                this.status = StatusPedido.AGUARDANDO_ENTREGADOR;
                return true;
            case AGUARDANDO_ENTREGADOR:
                this.status = StatusPedido.SAIU_PARA_ENTREGA;
                return true;
            case SAIU_PARA_ENTREGA:
                this.status = StatusPedido.ENTREGUE;
                return true;
            case ENTREGUE:
                return false; // Não pode avançar depois de entregue
        }
        return false;
    }

    // Getters
    public int getNumero() {
        return numero;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public StatusPedido getStatus() {
        return status;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public List<ItemPedido> getItens() {
        return itens;
    }

    @Override
    public String toString() {
        NumberFormat formatadorMoeda = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        DateTimeFormatter formatadorData = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        return "Pedido Nº: " + numero +
                " | Data: " + dataHora.format(formatadorData) +
                " | Status: " + status +
                " | Cliente: " + cliente.getNome() +
                " | Valor Total: " + formatadorMoeda.format(getValorTotal());
    }
}
// Futuramente, poderiamos adicionar um relacionamento com uma classe Entregador.
// onde quando o status mudasse para SAIU_PARA_ENTREGA, associaríamos um entregador a este pedido.

//  (toString() method)