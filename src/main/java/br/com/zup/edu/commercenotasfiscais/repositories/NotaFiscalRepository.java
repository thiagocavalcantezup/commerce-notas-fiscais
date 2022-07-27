package br.com.zup.edu.commercenotasfiscais.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.zup.edu.commercenotasfiscais.models.NotaFiscal;

public interface NotaFiscalRepository extends JpaRepository<NotaFiscal, String> {

}
