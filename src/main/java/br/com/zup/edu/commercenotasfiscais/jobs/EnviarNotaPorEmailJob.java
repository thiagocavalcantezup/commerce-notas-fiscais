package br.com.zup.edu.commercenotasfiscais.jobs;

import static br.com.zup.edu.commercenotasfiscais.models.StatusNotaFiscal.GERADA;
import static br.com.zup.edu.commercenotasfiscais.models.StatusNotaFiscal.GERADA_E_ENVIADA;

import java.nio.charset.Charset;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import br.com.zup.edu.commercenotasfiscais.models.NotaFiscal;
import br.com.zup.edu.commercenotasfiscais.repositories.NotaFiscalRepository;

@Service
public class EnviarNotaPorEmailJob {
    private static final Logger LOGGER = LoggerFactory.getLogger(EnviarNotaPorEmailJob.class);

    private final TransactionTemplate transactionTemplate;
    private final NotaFiscalRepository notaFiscalRepository;
    private final XmlMapper xmlMapper;
    private final JavaMailSender javaMailSender;

    public EnviarNotaPorEmailJob(TransactionTemplate transactionTemplate, NotaFiscalRepository notaFiscalRepository,
            XmlMapper xmlMapper, JavaMailSender javaMailSender) {
        this.transactionTemplate = transactionTemplate;
        this.notaFiscalRepository = notaFiscalRepository;
        this.xmlMapper = xmlMapper;
        this.javaMailSender = javaMailSender;
    }

    @Scheduled(fixedDelay = 5 * 1000, initialDelay = 5 * 1000)
    public void executa() {
        boolean pendente = true;

        while (pendente) {
            pendente = transactionTemplate.execute((status) -> {
                List<NotaFiscal> notasFiscaisGeradas = notaFiscalRepository
                        .findTop5ByStatusOrderByCriadoEmAsc(GERADA);

                if (notasFiscaisGeradas.isEmpty()) {
                    return false;
                }

                notasFiscaisGeradas.forEach(notaFiscal -> {
                    NotaFiscalEmailResponse notaFiscalEmailResponse = new NotaFiscalEmailResponse(notaFiscal);
                    String notaFiscalEmailResponseXml;

                    try {
                        notaFiscalEmailResponseXml = xmlMapper.writeValueAsString(notaFiscalEmailResponse);
                    } catch (JsonProcessingException e) {
                        LOGGER.error("Erro de processamento do JSON");
                        return;
                    }

                    try {
                        sendMessageWithAttachment(
                                notaFiscal.getEmail(),
                                "Sua compra foi confirmada!",
                                "Sua nota fiscal de número " + notaFiscalEmailResponse.getNumeroDaNota()
                                        + " se encontra anexada ao email.",
                                notaFiscalEmailResponseXml);
                        LOGGER.info("Nota fiscal de número " + notaFiscalEmailResponse.getNumeroDaNota()
                                + " enviada com sucesso");
                    } catch (MessagingException e) {
                        LOGGER.error("Erro no envio de email");
                        return;
                    }

                    notaFiscal.setStatus(GERADA_E_ENVIADA);
                });

                return true;
            });
        }
    }

    // SOURCE: https://www.baeldung.com/spring-email
    private void sendMessageWithAttachment(String to, String subject, String text, String attachment)
            throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom("notasfiscais@zupeducommerce.com.br");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text);

        // SOURCE:
        // https://stackoverflow.com/questions/40590696/javamail-requires-an-inputstreamsource-that-creates-a-fresh-stream-for-every-cal
        // A classe ByteArrayDataSource pode ser usada para mandar uma string como anexo
        ByteArrayDataSource resource = new ByteArrayDataSource(attachment.getBytes(Charset.forName("UTF-8")),
                "text/xml");
        helper.addAttachment("notafiscal.xml", resource);

        javaMailSender.send(message);
    }
}
