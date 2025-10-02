package sptech.school.application.service;

import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import sptech.school.domain.dto.response.ContactRequestDTO;

public class ContactService {

    private final JavaMailSender mailSender;
    private final String fromAddress;
    private final String contatoDestino;

    public ContactService(JavaMailSender mailSender,
                          String fromAddress,
                          String contatoDestino) {
        this.mailSender = mailSender;
        this.fromAddress = fromAddress;
        this.contatoDestino = contatoDestino;
    }

    public void enviarContato(ContactRequestDTO dto) throws MailException {
        org.springframework.mail.SimpleMailMessage message = new org.springframework.mail.SimpleMailMessage();
        message.setFrom(fromAddress);
        message.setTo(contatoDestino);
        message.setSubject("Novo contato de " + dto.getNome());

        StringBuilder corpo = new StringBuilder();
        corpo.append("Você recebeu uma nova mensagem de contato no site.\n\n");
        corpo.append("Nome: ").append(dto.getNome()).append("\n");
        corpo.append("E-mail: ").append(dto.getEmail()).append("\n");
        if (dto.getCelular() != null && !dto.getCelular().isBlank()) {
            corpo.append("Celular: ").append(dto.getCelular()).append("\n");
        }
        corpo.append("\nMensagem:\n").append(dto.getMensagem()).append("\n");
        corpo.append("\n--\nEnviado automaticamente.");
        message.setText(corpo.toString());

        mailSender.send(message);
    }
}