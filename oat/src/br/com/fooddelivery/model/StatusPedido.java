package br.com.fooddelivery.model;


public enum StatusPedido {
    ACEITO,
    PREPARANDO,
    FEITO,
    AGUARDANDO_ENTREGADOR,
    SAIU_PARA_ENTREGA,
    ENTREGUE

    // Poderíamos adicionar métodos a este Enum. Por exemplo, um metodo
    // isFinalizado() que retorna 'true' se o status for ENTREGUE, simplificando lógicas futuras.
}