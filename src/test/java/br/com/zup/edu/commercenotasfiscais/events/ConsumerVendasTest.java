package br.com.zup.edu.commercenotasfiscais.events;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import br.com.zup.edu.commercenotasfiscais.models.NotaFiscal;
import br.com.zup.edu.commercenotasfiscais.repositories.NotaFiscalRepository;

@SpringBootTest
@ActiveProfiles("test")
public class ConsumerVendasTest {

    @Autowired
    private ConsumerVendas consumerVendas;

    @Autowired
    private NotaFiscalRepository notaFiscalRepository;

    @BeforeEach
    void setUp() {
        notaFiscalRepository.deleteAll();
    }

    @Test
    void deveCadastrarUmaNotaFiscal() throws Exception {
        // given
        VendaEvent vendaEvent = new VendaEvent(
            UUID.randomUUID(),
            new CompradorVendaEvent(
                "Teste", "12345678909", "test@example.com", "Rua X, Bairro Y",
                LocalDate.now().minusYears(30)
            ), List.of(),
            new PagamentoVendaEvent(
                UUID.randomUUID(), "Cartão de Crédito", StatusPagamentoEvent.APROVADO
            )
        );

        // when
        consumerVendas.receber(vendaEvent);
        List<NotaFiscal> notasFiscais = notaFiscalRepository.findAll();

        // then
        assertEquals(1, notasFiscais.size());
    }

}
