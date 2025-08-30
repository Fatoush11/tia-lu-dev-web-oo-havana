package br.com.fooddelivery.service;

import br.com.fooddelivery.model.*;
import br.com.fooddelivery.repository.BancoDeDados;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * Esta classe serve como um ponto de entrada simplificado para todas as operações de negócio do sistema.
 * Ela desacopla a camada de apresentação (View) da complexidade das camadas de modelo e repositório.
 * Evitando que o CLI tenha que interagir diretamente com o BancoDeDados
 */

public class FoodDeliveryFacade {
    private final BancoDeDados bancoDeDados;

    public FoodDeliveryFacade() {
        // Pega a instância única do nosso banco de dados
        this.bancoDeDados = BancoDeDados.getInstancia();
    }

    //  Gerenciamento de Cardápio
    public ItemCardapio cadastrarNovoItem(String nome, double preco) {
        return this.bancoDeDados.addItemCardapio(nome, preco);
    }

    public List<ItemCardapio> listarItensCardapio() {
        return this.bancoDeDados.getCardapio();
    }

    //  Gerenciamento de Clientes
    public Cliente cadastrarNovoCliente(String nome, String telefone) {
        return this.bancoDeDados.addCliente(nome, telefone);
    }

    public List<Cliente> listarClientes() {
        return this.bancoDeDados.getClientes();
    }

    //  Gerenciamento de Pedidos
    public Pedido registrarNovoPedido(int codigoCliente) {
        Cliente cliente = this.bancoDeDados.getClientePorCodigo(codigoCliente);
        if (cliente == null) {
            return null; // Cliente não encontrado
        }
        return this.bancoDeDados.criarNovoPedido(cliente);
    }

    public boolean adicionarItemAoPedido(int numeroPedido, int codigoItem, int quantidade) {
        Pedido pedido = this.bancoDeDados.getPedidoPorNumero(numeroPedido);
        ItemCardapio itemCardapio = this.bancoDeDados.getItemPorCodigo(codigoItem);

        if (pedido != null && itemCardapio != null) {
            ItemPedido itemPedido = new ItemPedido(itemCardapio, quantidade);
            pedido.adicionarItem(itemPedido);
            return true;
        }
        return false;
    }

    public boolean atualizarStatusPedido(int numeroPedido) {
        Pedido pedido = this.bancoDeDados.getPedidoPorNumero(numeroPedido);
        if (pedido != null) {
            return pedido.avancarStatus();
        }
        return false;
    }

    public List<Pedido> consultarPedidosPorStatus(StatusPedido status) {
        return this.bancoDeDados.getPedidos()
                .stream()
                .filter(pedido -> pedido.getStatus() == status)
                .collect(Collectors.toList());
    }

    //  Relatórios
    public String gerarRelatorioVendasSimplificado() {
        List<Pedido> pedidos = this.bancoDeDados.getPedidos();
        int totalPedidos = pedidos.size();
        double valorTotal = pedidos.stream().mapToDouble(Pedido::getValorTotal).sum();

        NumberFormat formatadorMoeda = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

        return " Relatório de Vendas (Simplificado) \n" +
                "Total de Pedidos no Dia: " + totalPedidos + "\n" +
                "Valor Total Arrecadado: " + formatadorMoeda.format(valorTotal) + "\n";
    }

    public String gerarRelatorioVendasDetalhado() {
        StringBuilder relatorio = new StringBuilder();
        relatorio.append(" Relatório de Vendas (Detalhado) \n");

        NumberFormat formatadorMoeda = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        List<Pedido> pedidos = this.bancoDeDados.getPedidos();

        if (pedidos.isEmpty()) {
            relatorio.append("Nenhum pedido registrado hoje.\n");
        } else {
            for (Pedido pedido : pedidos) {
                relatorio.append("----------------------------------------\n");
                relatorio.append("Pedido Nº: ").append(pedido.getNumero()).append("\n");
                relatorio.append("Cliente: ").append(pedido.getCliente().getNome()).append("\n");
                relatorio.append("Itens:\n");
                for (ItemPedido item : pedido.getItens()) {
                    relatorio.append("  - ").append(item.getItemCardapio().getNome())
                            .append(" (").append(item.getQuantidade()).append("x)")
                            .append(" - Subtotal: ").append(formatadorMoeda.format(item.getSubtotal()))
                            .append("\n");
                }
                relatorio.append("Valor Total do Pedido: ").append(formatadorMoeda.format(pedido.getValorTotal())).append("\n");
                relatorio.append("Status: ").append(pedido.getStatus()).append("\n");
            }
        }
        return relatorio.toString();
    }
}