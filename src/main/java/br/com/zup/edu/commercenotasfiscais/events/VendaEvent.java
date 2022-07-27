package br.com.zup.edu.commercenotasfiscais.events;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import br.com.zup.edu.commercenotasfiscais.models.ItemNotaFiscal;
import br.com.zup.edu.commercenotasfiscais.models.NotaFiscal;

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

    public NotaFiscal toModel() {
        List<ItemNotaFiscal> itensNotaFiscal = itens.stream().map(ItemVendaEvent::toModel).collect(Collectors.toList());
        BigDecimal valorTotal = itensNotaFiscal.stream().map(ItemNotaFiscal::getTotal).reduce(BigDecimal.ZERO,
                BigDecimal::add);

        return new NotaFiscal(comprador.getNome(), comprador.getCpf(), comprador.getEndereco(), itensNotaFiscal,
                valorTotal);
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
