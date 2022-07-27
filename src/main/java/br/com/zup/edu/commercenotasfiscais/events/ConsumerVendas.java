package br.com.zup.edu.commercenotasfiscais.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import br.com.zup.edu.commercenotasfiscais.models.NotaFiscal;
import br.com.zup.edu.commercenotasfiscais.repositories.NotaFiscalRepository;

@Component
public class ConsumerVendas {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerVendas.class);

    private final NotaFiscalRepository notaFiscalRepository;

    public ConsumerVendas(NotaFiscalRepository notaFiscalRepository) {
        this.notaFiscalRepository = notaFiscalRepository;
    }

    @KafkaListener(topics = "vendas", groupId = "commerce-notas-fiscais")
    public void receber(VendaEvent vendaEvent) throws Exception {
        NotaFiscal notaFiscal = vendaEvent.toModel();
        notaFiscalRepository.save(notaFiscal);
        LOGGER.info("Nota fiscal de número " + notaFiscal.getNumeroDaNota() + " salva com sucesso");
    }

}
