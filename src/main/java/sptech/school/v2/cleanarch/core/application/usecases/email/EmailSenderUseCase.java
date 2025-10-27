package sptech.school.v2.cleanarch.core.application.usecases.email;

import org.springframework.stereotype.Service;
import sptech.school.v2.cleanarch.core.application.gateways.email.EmailSenderGateway;

@Service
public class EmailSenderUseCase {
    private final EmailSenderGateway emailSenderGateway;

    public EmailSenderUseCase(EmailSenderGateway emailSenderGateway) {
        this.emailSenderGateway = emailSenderGateway;
    }

    public void sendEmail(String to, String subject, String body) {
        emailSenderGateway.send(to, subject, body);
    }
}
