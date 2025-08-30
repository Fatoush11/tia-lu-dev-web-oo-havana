package br.com.fooddelivery.model;

/**
 * Classe Associativa que usaremos para conectar um Pedido a um ItemCardapio.
 * Resolvendo assim o relacionamento Muitos-para-Muitos, permitindo a gente possa guardar
 * informações específicas daquela associação, como a quantidade e o preço no momento da compra.
 */
public class ItemPedido {
    private int quantidade;
    private double precoUnitario; // "Congela" o preço no momento da compra.
    private ItemCardapio itemCardapio;

    public ItemPedido(ItemCardapio itemCardapio, int quantidade) {
        this.itemCardapio = itemCardapio;
        this.quantidade = quantidade;
        this.precoUnitario = itemCardapio.getPreco(); // Garantimos que o preço não mude se o cardápio tiver atualizacao.
    }

    public double getSubtotal() {
        return quantidade * precoUnitario;
    }

    // Getters

    public int getQuantidade() {
        return quantidade;
    }

    public double getPrecoUnitario() {
        return precoUnitario;
    }

    public ItemCardapio getItemCardapio() {
        return itemCardapio;
    }

    // Como um passo futuro, poderiamos tornar essa classe uma entidade própria no banco de dados,
    // com um relacionamento @ManyToOne com Pedido e @ManyToOne com ItemCardapio.

    @Override
    public String toString() {
        return itemCardapio.getNome() + " (" + quantidade + "x)";
    }
}