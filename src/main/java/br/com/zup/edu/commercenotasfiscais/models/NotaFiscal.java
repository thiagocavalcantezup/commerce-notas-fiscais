package br.com.zup.edu.commercenotasfiscais.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "notas_fiscais")
public class NotaFiscal {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "five_digits")
    @GenericGenerator(name = "five_digits", strategy = "br.com.zup.edu.commercenotasfiscais.models.FiveDigitsGenerator")
    private String numeroDaNota;

    @Column(nullable = false)
    private LocalDateTime criadoEm;

    @Column(nullable = false)
    private String nomeComprador;

    @Column(nullable = false)
    private String cpf;

    @Column(nullable = false)
    private String endereco;

    @Column(nullable = false)
    private String email;

    @ElementCollection
    private List<ItemNotaFiscal> itens;

    @Column(nullable = false)
    private BigDecimal valorTotal;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusNotaFiscal status;

    /**
     * @deprecated Construtor de uso exclusivo do Hibernate
     */
    @Deprecated
    public NotaFiscal() {}

    public NotaFiscal(String nomeComprador, String cpf, String endereco, String email,
                      List<ItemNotaFiscal> itens, BigDecimal valorTotal) {
        this.criadoEm = LocalDateTime.now();
        this.nomeComprador = nomeComprador;
        this.cpf = cpf;
        this.endereco = endereco;
        this.email = email;
        this.itens = itens;
        this.valorTotal = valorTotal;
        this.status = StatusNotaFiscal.GERADA;
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

    public String getEmail() {
        return email;
    }

    public List<ItemNotaFiscal> getItens() {
        return itens;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public StatusNotaFiscal getStatus() {
        return status;
    }

    public void setStatus(StatusNotaFiscal status) {
        this.status = status;
    }

}
