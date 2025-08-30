package br.com.fooddelivery.model;

/**
 * Classe de Modelo (ou Entidade) que representa um cliente do restaurante.
 * A responsabilidade desta classe é unicamente armazenar os dados de um cliente.
 */
public class Cliente {
    private int codigo;
    private String nome;
    private String telefone;

    public Cliente(int codigo, String nome, String telefone) {
        this.codigo = codigo;
        this.nome = nome;
        this.telefone = telefone;
    }

    // Getters
    // Métodos públicos para permitir que outras partes do sistema acessem os dados de forma controlada.

    public int getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public String getTelefone() {
        return telefone;
    }

    // Poderiamos adicionar validações nos dados de entrada. Com o Spring Boot, usaríamos
    // anotações como @NotBlank no nome e @Pattern no telefone para garantir a integridade dos dados
    // antes mesmo de serem salvos.


    @Override
    public String toString() {
        return "Cód: " + codigo + ", Nome: " + nome + ", Tel: " + telefone;
    }
}