package br.com.fooddelivery.model;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Classe de Modelo que representa um item vendável no cardápio do restaurante.
 */

public class ItemCardapio {
    private int codigo;
    private String nome;
    private double preco;

    public ItemCardapio(int codigo, String nome, double preco) {
        this.codigo = codigo;
        this.nome = nome;
        this.preco = preco;
    }

    // Getters
    public int getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public double getPreco() {
        return preco;
    }

    // Poderíamos fazer alteracoes futuras, onde em um sistema real, o preço poderia ser representado pela classe
    // BigDecimal para evitar problemas de arredondamento com double em operações financeiras.

    @Override
    public String toString() {
        // Formata o preço para o padrão brasileiro (R$)
        NumberFormat formatadorMoeda = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        return "Cód: " + codigo + ", Item: " + nome + ", Preço: " + formatadorMoeda.format(preco);
    }
}