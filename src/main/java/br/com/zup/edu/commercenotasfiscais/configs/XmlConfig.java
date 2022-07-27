package br.com.zup.edu.commercenotasfiscais.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

@Configuration
public class XmlConfig {
    @Bean
    XmlMapper xmlMapper(MappingJackson2XmlHttpMessageConverter converter) {
        return (XmlMapper) converter.getObjectMapper();
    }
}
