package br.com.zup.edu.commercenotasfiscais.models;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ItemNotaFiscal {

    @Column(nullable = false, name = "nome_item")
    private String nome;

    @Column(nullable = false, name = "quantidade_item")
    private Long quantidade;

    @Column(nullable = false, name = "valor_item")
    private BigDecimal valor;

    public ItemNotaFiscal() {}

    public ItemNotaFiscal(String nome, Long quantidade, BigDecimal valor) {
        this.nome = nome;
        this.quantidade = quantidade;
        this.valor = valor;
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

    public BigDecimal getTotal() {
        return valor.multiply(BigDecimal.valueOf(quantidade));
    }

}
