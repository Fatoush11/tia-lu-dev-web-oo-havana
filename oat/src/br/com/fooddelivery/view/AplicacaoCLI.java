package br.com.fooddelivery.view;

import br.com.fooddelivery.model.*;
import br.com.fooddelivery.service.FoodDeliveryFacade;

import java.util.List;
import java.util.Scanner;


 //Classe que representa a camada de Apresentação (View) do sistema.
 // Responsável por interagir com o usuário via Linha de Comando (CLI).
 // Ela conversa exclusivamente com a Facade, sem conhecer os detalhes internos do sistema.


public class AplicacaoCLI {
    private final FoodDeliveryFacade facade;
    private final Scanner scanner;

    public AplicacaoCLI() {
        this.facade = new FoodDeliveryFacade();
        this.scanner = new Scanner(System.in);
    }

    public void iniciar() {
        // Carga inicial de dados para facilitar os testes
        carregarDadosIniciais();

        int opcao;
        do {
            exibirMenuPrincipal();
            try {
                opcao = Integer.parseInt(scanner.nextLine());
                switch (opcao) {
                    case 1: gerenciarCardapio(); break;
                    case 2: gerenciarClientes(); break;
                    case 3: gerenciarPedidos(); break;
                    case 4: gerenciarRelatorios(); break;
                    case 0: System.out.println("Encerrando o sistema..."); break;
                    default: System.out.println("Opção inválida. Tente novamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Erro: Por favor, digite um número válido.");
                opcao = -1; // Para continuar no loop
            }
            pressionarEnterParaContinuar();
        } while (opcao != 0);

        scanner.close();
    }

    private void exibirMenuPrincipal() {
        System.out.println("\n--- FoodDelivery - Sistema de Pedidos ---");
        System.out.println("1. Gerenciar Cardápio");
        System.out.println("2. Gerenciar Clientes");
        System.out.println("3. Gerenciar Pedidos");
        System.out.println("4. Gerar Relatórios");
        System.out.println("0. Sair");
        System.out.print("Escolha uma opção: ");
    }

    // --- Módulos de Gerenciamento ---

    private void gerenciarCardapio() {
        System.out.println("\n--- Gerenciamento de Cardápio ---");
        System.out.println("1. Cadastrar Novo Item");
        System.out.println("2. Listar Itens do Cardápio");
        System.out.print("Escolha uma opção: ");
        int opcao = Integer.parseInt(scanner.nextLine());

        if (opcao == 1) {
            System.out.print("Nome do item: ");
            String nome = scanner.nextLine();
            System.out.print("Preço do item: ");
            double preco = Double.parseDouble(scanner.nextLine());
            facade.cadastrarNovoItem(nome, preco);
            System.out.println("Item cadastrado com sucesso!");
        } else if (opcao == 2) {
            System.out.println("\n--- Itens do Cardápio ---");
            List<ItemCardapio> cardapio = facade.listarItensCardapio();
            if (cardapio.isEmpty()) {
                System.out.println("Nenhum item cadastrado.");
            } else {
                cardapio.forEach(System.out::println);
            }
        }
    }

    private void gerenciarClientes() {
        System.out.println("\n--- Gerenciamento de Clientes ---");
        System.out.println("1. Cadastrar Novo Cliente");
        System.out.println("2. Listar Clientes");
        System.out.print("Escolha uma opção: ");
        int opcao = Integer.parseInt(scanner.nextLine());

        if (opcao == 1) {
            System.out.print("Nome do cliente: ");
            String nome = scanner.nextLine();
            System.out.print("Telefone do cliente: ");
            String telefone = scanner.nextLine();
            facade.cadastrarNovoCliente(nome, telefone);
            System.out.println("Cliente cadastrado com sucesso!");
        } else if (opcao == 2) {
            System.out.println("\n--- Clientes Cadastrados ---");
            List<Cliente> clientes = facade.listarClientes();
            if (clientes.isEmpty()) {
                System.out.println("Nenhum cliente cadastrado.");
            } else {
                clientes.forEach(System.out::println);
            }
        }
    }

    private void gerenciarPedidos() {
        System.out.println("\n--- Gerenciamento de Pedidos ---");
        System.out.println("1. Registrar Novo Pedido");
        System.out.println("2. Adicionar Item a um Pedido");
        System.out.println("3. Atualizar Status de um Pedido");
        System.out.println("4. Consultar Pedidos por Status");
        System.out.print("Escolha uma opção: ");
        int opcao = Integer.parseInt(scanner.nextLine());

        switch (opcao) {
            case 1:
                System.out.print("Digite o código do cliente: ");
                int codCliente = Integer.parseInt(scanner.nextLine());
                Pedido novoPedido = facade.registrarNovoPedido(codCliente);
                if (novoPedido != null) {
                    System.out.println("Pedido Nº " + novoPedido.getNumero() + " registrado com sucesso para o cliente " + novoPedido.getCliente().getNome());
                } else {
                    System.out.println("Cliente não encontrado.");
                }
                break;
            case 2:
                System.out.print("Digite o número do pedido: ");
                int numPedido = Integer.parseInt(scanner.nextLine());
                System.out.print("Digite o código do item do cardápio: ");
                int codItem = Integer.parseInt(scanner.nextLine());
                System.out.print("Digite a quantidade: ");
                int qtd = Integer.parseInt(scanner.nextLine());
                if (facade.adicionarItemAoPedido(numPedido, codItem, qtd)) {
                    System.out.println("Item adicionado com sucesso!");
                } else {
                    System.out.println("Erro: Pedido ou item não encontrado.");
                }
                break;
            case 3:
                System.out.print("Digite o número do pedido para avançar o status: ");
                int numPedidoStatus = Integer.parseInt(scanner.nextLine());
                if (facade.atualizarStatusPedido(numPedidoStatus)) {
                    System.out.println("Status do pedido atualizado com sucesso.");
                } else {
                    System.out.println("Erro: Pedido não encontrado ou já está 'ENTREGUE'.");
                }
                break;
            case 4:
                System.out.println("Status disponíveis: ACEITO, PREPARANDO, FEITO, AGUARDANDO_ENTREGADOR, SAIU_PARA_ENTREGA, ENTREGUE");
                System.out.print("Digite o status para consultar: ");
                try {
                    StatusPedido status = StatusPedido.valueOf(scanner.nextLine().toUpperCase());
                    List<Pedido> pedidos = facade.consultarPedidosPorStatus(status);
                    System.out.println("\n--- Pedidos com Status: " + status + " ---");
                    if (pedidos.isEmpty()) {
                        System.out.println("Nenhum pedido encontrado com este status.");
                    } else {
                        pedidos.forEach(System.out::println);
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println("Status inválido.");
                }
                break;
        }
    }

    private void gerenciarRelatorios() {
        System.out.println("\n--- Módulo de Relatórios ---");
        System.out.println("1. Relatório de Vendas (Simplificado)");
        System.out.println("2. Relatório de Vendas (Detalhado)");
        System.out.print("Escolha uma opção: ");
        int opcao = Integer.parseInt(scanner.nextLine());

        if (opcao == 1) {
            System.out.println(facade.gerarRelatorioVendasSimplificado());
        } else if (opcao == 2) {
            System.out.println(facade.gerarRelatorioVendasDetalhado());
        }
    }

    // --- Métodos Utilitários ---

    private void pressionarEnterParaContinuar() {
        System.out.println("\nPressione Enter para continuar...");
        scanner.nextLine();
    }

    private void carregarDadosIniciais() {
        // Carga para facilitar a demonstração e os testes
        facade.cadastrarNovoItem("Pizza Calabresa", 50.0);
        facade.cadastrarNovoItem("Suco de Laranja 1L", 15.0);
        facade.cadastrarNovoCliente("João da Silva", "11987654321");
        facade.cadastrarNovoCliente("Maria Oliveira", "21912345678");
    }
}