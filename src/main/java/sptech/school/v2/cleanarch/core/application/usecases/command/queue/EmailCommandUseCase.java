package sptech.school.v2.cleanarch.core.application.usecases.command.queue;

import org.springframework.stereotype.Service;
import sptech.school.v2.cleanarch.core.application.gateways.queue.EmailCommandGateway;
import sptech.school.v2.cleanarch.core.dtos.out.ContactRequestDTO;
import sptech.school.v2.cleanarch.core.dtos.in.queue.ContactRequestedEvent;
import sptech.school.v2.cleanarch.core.dtos.in.queue.PasswordResetRequestedEvent;

import java.time.OffsetDateTime;
import java.util.UUID;

@Service
public class EmailCommandUseCase {

    private final EmailCommandGateway gateway;

    public EmailCommandUseCase(EmailCommandGateway gateway) {
        this.gateway = gateway;
    }

    public void sendContactRequestedEvent(ContactRequestDTO dto) {
        ContactRequestedEvent ev = new ContactRequestedEvent();
        ev.setMessageId(UUID.randomUUID().toString());
        ev.setNome(dto.getNome());
        ev.setEmail(dto.getEmail());
        ev.setCelular(dto.getCelular());
        ev.setMensagem(dto.getMensagem());
        ev.setHappenedAt(OffsetDateTime.now());
        gateway.sendContactEvent(ev);
    }

    public void sendPasswordResetRequestedEvent(String email, String resetToken) {
        PasswordResetRequestedEvent ev = new PasswordResetRequestedEvent();
        ev.setMessageId(UUID.randomUUID().toString());
        ev.setEmail(email);
        ev.setResetToken(resetToken);
        ev.setHappenedAt(OffsetDateTime.now());
        gateway.sendPasswordResetEvent(ev);
    }
}
