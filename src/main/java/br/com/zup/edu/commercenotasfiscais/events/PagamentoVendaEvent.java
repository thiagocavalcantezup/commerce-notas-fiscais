package br.com.zup.edu.commercenotasfiscais.events;

import java.util.UUID;

public class PagamentoVendaEvent {

    private UUID id;
    private String forma;
    private StatusPagamentoEvent status;

    public PagamentoVendaEvent() {}

    public PagamentoVendaEvent(UUID id, String forma, StatusPagamentoEvent status) {
        this.id = id;
        this.forma = forma;
        this.status = status;
    }

    public UUID getId() {
        return id;
    }

    public String getForma() {
        return forma;
    }

    public StatusPagamentoEvent getStatus() {
        return status;
    }

}
