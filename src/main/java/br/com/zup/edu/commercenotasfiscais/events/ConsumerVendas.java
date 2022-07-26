package br.com.zup.edu.commercenotasfiscais.events;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class ConsumerVendas {

    private final ObjectMapper objectMapper;

    public ConsumerVendas(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "vendas", groupId = "commerce-notas-fiscais")
    public void receber(VendaEvent vendaEvent) throws Exception {
        System.out.println(objectMapper.writeValueAsString(vendaEvent));
    }
}
