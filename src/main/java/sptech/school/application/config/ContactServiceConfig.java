package sptech.school.application.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import sptech.school.application.service.ContactService;

@Configuration
public class ContactServiceConfig {

    @Bean
    public ContactService contactService(JavaMailSender mailSender,
                                         @Value("${spring.mail.username}") String fromAddress,
                                         @Value("${contato.email.destino}") String contatoDestino) {
        return new ContactService(mailSender, fromAddress, contatoDestino);
    }
}