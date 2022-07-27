package br.com.zup.edu.commercenotasfiscais.events;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

public class CompradorVendaEvent {

    private String nome;
    private String cpf;
    private String email;
    private String endereco;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataNascimento;

    public CompradorVendaEvent() {}

    public CompradorVendaEvent(String nome, String cpf, String email, String endereco,
                               LocalDate dataNascimento) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.endereco = endereco;
        this.dataNascimento = dataNascimento;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getEmail() {
        return email;
    }

    public String getEndereco() {
        return endereco;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

}
