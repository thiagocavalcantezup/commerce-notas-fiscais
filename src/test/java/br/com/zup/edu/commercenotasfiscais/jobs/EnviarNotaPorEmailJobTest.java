package br.com.zup.edu.commercenotasfiscais.jobs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.Properties;

import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.ActiveProfiles;

import br.com.zup.edu.commercenotasfiscais.models.ItemNotaFiscal;
import br.com.zup.edu.commercenotasfiscais.models.NotaFiscal;
import br.com.zup.edu.commercenotasfiscais.models.StatusNotaFiscal;
import br.com.zup.edu.commercenotasfiscais.repositories.NotaFiscalRepository;

@SpringBootTest
@ActiveProfiles("test")
public class EnviarNotaPorEmailJobTest {

    @Autowired
    private EnviarNotaPorEmailJob enviarNotaPorEmailJob;

    @Autowired
    private NotaFiscalRepository notaFiscalRepository;

    @Autowired
    private EntityManager entityManager;

    @MockBean
    private JavaMailSender javaMailSender;

    @BeforeEach
    void setUp() {
        notaFiscalRepository.deleteAll();
    }

    @Test
    void deveEnviarTodasAsNotasGeradas() {
        // given
        NotaFiscal nota1 = new NotaFiscal(
            "Comprador 1", "123456768909", "Rua X, Bairro Y", "teste1@example.com",
            List.of(new ItemNotaFiscal("Produto A", 2L, new BigDecimal("10.00"))),
            new BigDecimal("20.00")
        );

        NotaFiscal nota2 = new NotaFiscal(
            "Comprador 2", "98765432100", "Rua Q, Bairro W", "teste2@example.com",
            List.of(new ItemNotaFiscal("Produto B", 3L, new BigDecimal("30.00"))),
            new BigDecimal("90.00")
        );

        NotaFiscal nota3 = new NotaFiscal(
            "Comprador 3", "98765432100", "Rua R, Bairro S", "teste3@example.com",
            List.of(new ItemNotaFiscal("Produto C", 1L, new BigDecimal("230.00"))),
            new BigDecimal("230.00")
        );
        nota3.setStatus(StatusNotaFiscal.GERADA_E_ENVIADA);

        NotaFiscal nota4 = new NotaFiscal(
            "Comprador 4", "98765432100", "Rua F, Bairro G", "teste4@example.com",
            List.of(new ItemNotaFiscal("Produto D", 5L, new BigDecimal("50.00"))),
            new BigDecimal("250.00")
        );
        nota4.setStatus(StatusNotaFiscal.GERADA_E_ENVIADA);

        notaFiscalRepository.saveAll(List.of(nota1, nota2, nota3, nota4));

        Properties prop = new Properties();
        prop.put("mail.smtp.auth", false);
        prop.put("mail.smtp.starttls.enable", false);
        prop.put("mail.smtp.host", "localhost");
        prop.put("mail.smtp.port", "2525");
        Session session = Session.getInstance(prop);

        // when
        when(javaMailSender.createMimeMessage()).thenReturn(new MimeMessage(session));
        enviarNotaPorEmailJob.executa();

        // then
        assertEquals(0L, countByStatus(StatusNotaFiscal.GERADA));
        assertEquals(4L, countByStatus(StatusNotaFiscal.GERADA_E_ENVIADA));
        verify(javaMailSender, times(2)).send(any(MimeMessage.class));
    }

    private Long countByStatus(StatusNotaFiscal statusNotaFiscal) {
        return entityManager.createQuery(
            "SELECT COUNT(n) FROM NotaFiscal n WHERE n.status = :status", Long.class
        ).setParameter("status", statusNotaFiscal).getSingleResult();
    }

}
