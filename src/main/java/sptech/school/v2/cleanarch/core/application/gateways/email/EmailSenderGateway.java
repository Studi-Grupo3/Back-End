package sptech.school.v2.cleanarch.core.application.gateways.email;

public interface EmailSenderGateway {
    void send(String to, String subject, String body);
}
