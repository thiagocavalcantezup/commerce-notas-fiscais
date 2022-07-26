package br.com.zup.edu.commercenotasfiscais.events;

import java.math.BigDecimal;

public class ItemVendaEvent {

    private Long id;
    private String nome;
    private Long quantidade;
    private BigDecimal valor;

    public ItemVendaEvent() {
    }

    public ItemVendaEvent(Long id, String nome, Long quantidade, BigDecimal valor) {
        this.id = id;
        this.nome = nome;
        this.quantidade = quantidade;
        this.valor = valor;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Long getQuantidade() {
        return quantidade;
    }

    public BigDecimal getValor() {
        return valor;
    }

}
