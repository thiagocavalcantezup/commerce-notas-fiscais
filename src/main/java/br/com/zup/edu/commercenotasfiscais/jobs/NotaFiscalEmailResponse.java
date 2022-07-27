package br.com.zup.edu.commercenotasfiscais.jobs;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import br.com.zup.edu.commercenotasfiscais.models.NotaFiscal;

public class NotaFiscalEmailResponse {

    private String numeroDaNota;
    private LocalDateTime criadoEm;
    private String nomeComprador;
    private String cpf;
    private String endereco;
    private List<ItemNotaFiscalEmailResponse> itens;
    private BigDecimal valorTotal;

    /**
     * @deprecated Construtor de uso exclusivo do Hibernate
     */
    @Deprecated
    public NotaFiscalEmailResponse() {
    }

    public NotaFiscalEmailResponse(NotaFiscal notaFiscal) {
        this.numeroDaNota = notaFiscal.getNumeroDaNota();
        this.criadoEm = notaFiscal.getCriadoEm();
        this.nomeComprador = notaFiscal.getNomeComprador();
        this.cpf = notaFiscal.getCpf();
        this.endereco = notaFiscal.getEndereco();
        this.itens = notaFiscal.getItens().stream().map(ItemNotaFiscalEmailResponse::new).collect(Collectors.toList());
        this.valorTotal = notaFiscal.getValorTotal();
    }

    public String getNumeroDaNota() {
        return numeroDaNota;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }

    public String getNomeComprador() {
        return nomeComprador;
    }

    public String getCpf() {
        return cpf;
    }

    public String getEndereco() {
        return endereco;
    }

    public List<ItemNotaFiscalEmailResponse> getItens() {
        return itens;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

}
