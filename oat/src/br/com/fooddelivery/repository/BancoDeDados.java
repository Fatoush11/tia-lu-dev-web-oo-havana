package br.com.fooddelivery.repository;

import br.com.fooddelivery.model.Cliente;
import br.com.fooddelivery.model.ItemCardapio;
import br.com.fooddelivery.model.Pedido;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementa a camada de acesso a dados (Repository).
 * Utilizando o Padrão de Projeto Singleton, podemos garantir que exista apenas uma
 * instância desta classe em todo o sistema, centralizando o acesso aos dados em memória.
 */

public class BancoDeDados {

    // Atributos para o Singleton e Armazenamento

    // A única instância da classe (Padrão Singleton)
    private static BancoDeDados instancia;

    // Listas para guardar os dados em memória
    private final List<Cliente> clientes;
    private final List<ItemCardapio> cardapio;
    private final List<Pedido> pedidos;

    // Contadores para gerar códigos únicos
    private int proximoCodigoCliente = 1;
    private int proximoCodigoItemCardapio = 1;
    private int proximoNumeroPedido = 1;

    // Construtor Privado (Padrão Singleton)

    // O construtor é privado para que ninguém de fora possa criar uma nova instância
    // Ninguém de fora pode dar um 'new BancoDeDados()'.
    private BancoDeDados() {
        this.clientes = new ArrayList<>();
        this.cardapio = new ArrayList<>();
        this.pedidos = new ArrayList<>();
    }

    // Método de Acesso Global (Padrão Singleton)

    // O método público que dá acesso à única instância
    public static synchronized BancoDeDados getInstancia() {
        if (instancia == null) {
            instancia = new BancoDeDados();
        }
        return instancia;
    }

    // Métodos para Manipular os Dados

    // Clientes
    public Cliente addCliente(String nome, String telefone) {
        Cliente novoCliente = new Cliente(proximoCodigoCliente++, nome, telefone);
        this.clientes.add(novoCliente);
        return novoCliente;
    }

    public List<Cliente> getClientes() {
        return new ArrayList<>(this.clientes); // Retorna uma cópia para proteger a lista original
    }

    public Cliente getClientePorCodigo(int codigo) {
        for (Cliente cliente : this.clientes) {
            if (cliente.getCodigo() == codigo) {
                return cliente;
            }
        }
        return null; // Retorna null se não encontrar
    }

    // Itens do Cardápio
    public ItemCardapio addItemCardapio(String nome, double preco) {
        ItemCardapio novoItem = new ItemCardapio(proximoCodigoItemCardapio++, nome, preco);
        this.cardapio.add(novoItem);
        return novoItem;
    }

    public List<ItemCardapio> getCardapio() {
        return new ArrayList<>(this.cardapio);
    }

    public ItemCardapio getItemPorCodigo(int codigo) {
        for (ItemCardapio item : this.cardapio) {
            if (item.getCodigo() == codigo) {
                return item;
            }
        }
        return null;
    }

    // Pedidos
    public Pedido addPedido(Pedido pedido) {
        // A lógica de criação do pedido fica fora, aqui apenas adicionamos
        this.pedidos.add(pedido);
        return pedido;
    }

    public Pedido criarNovoPedido(Cliente cliente) {
        Pedido novoPedido = new Pedido(proximoNumeroPedido++, cliente);
        this.pedidos.add(novoPedido);
        return novoPedido;
    }

    public List<Pedido> getPedidos() {
        return new ArrayList<>(this.pedidos);
    }

    public Pedido getPedidoPorNumero(int numero) {
        for (Pedido pedido : this.pedidos) {
            if (pedido.getNumero() == numero) {
                return pedido;
            }
        }
        return null;
    }
}