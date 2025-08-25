package sptech.school.application.usecase;

public interface EmailSender {
    void send(String to, String subject, String body);
}
