package br.com.zup.edu.commercenotasfiscais.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.zup.edu.commercenotasfiscais.models.NotaFiscal;
import br.com.zup.edu.commercenotasfiscais.models.StatusNotaFiscal;

public interface NotaFiscalRepository extends JpaRepository<NotaFiscal, String> {

    List<NotaFiscal> findTop5ByStatusOrderByCriadoEmAsc(StatusNotaFiscal status);

}
