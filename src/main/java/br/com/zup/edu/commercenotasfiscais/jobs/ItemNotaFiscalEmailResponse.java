package br.com.zup.edu.commercenotasfiscais.jobs;

import java.math.BigDecimal;

import br.com.zup.edu.commercenotasfiscais.models.ItemNotaFiscal;

public class ItemNotaFiscalEmailResponse {

    private String nome;
    private Long quantidade;
    private BigDecimal valor;

    public ItemNotaFiscalEmailResponse() {}

    public ItemNotaFiscalEmailResponse(ItemNotaFiscal itemNotaFiscal) {
        this.nome = itemNotaFiscal.getNome();
        this.quantidade = itemNotaFiscal.getQuantidade();
        this.valor = itemNotaFiscal.getValor();
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
