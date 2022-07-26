package br.com.zup.edu.commercenotasfiscais.events;

import java.util.List;
import java.util.UUID;

public class VendaEvent {

    private UUID codigoPedido;
    private CompradorVendaEvent comprador;
    private List<ItemVendaEvent> itens;
    private PagamentoVendaEvent pagamento;

    public VendaEvent() {
    }

    public VendaEvent(UUID codigoPedido, CompradorVendaEvent comprador, List<ItemVendaEvent> itens,
            PagamentoVendaEvent pagamento) {
        this.codigoPedido = codigoPedido;
        this.comprador = comprador;
        this.itens = itens;
        this.pagamento = pagamento;
    }

    public UUID getCodigoPedido() {
        return codigoPedido;
    }

    public CompradorVendaEvent getComprador() {
        return comprador;
    }

    public List<ItemVendaEvent> getItens() {
        return itens;
    }

    public PagamentoVendaEvent getPagamento() {
        return pagamento;
    }

}
