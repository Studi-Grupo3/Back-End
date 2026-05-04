package sptech.school.v2.cleanarch.core.application.facades.queue;

import org.springframework.stereotype.Service;
import sptech.school.v2.cleanarch.core.application.usecases.email.EmailSenderUseCase;
import sptech.school.v2.cleanarch.core.dtos.out.ContactRequestDTO;

@Service
public class EmailFacade implements EmailFacadeContract {

    private final EmailSenderUseCase emailSenderUseCase;

    public EmailFacade(EmailSenderUseCase emailSenderUseCase) {
        this.emailSenderUseCase = emailSenderUseCase;
    }

    @Override
    public void requestContactEmail(ContactRequestDTO dto) {
        String subject = "Novo contato de " + dto.getNome();
        String body = String.format(
                "Nome: %s\nEmail: %s\nCelular: %s\nMensagem: %s",
                dto.getNome(), dto.getEmail(), dto.getCelular(), dto.getMensagem()
        );
        emailSenderUseCase.sendEmail(dto.getEmail(), subject, body);
    }

    @Override
    public void requestPasswordResetEmail(String email, String resetToken) {
        String subject = "Recuperação de senha - Studi";
        String body = "Seu código de recuperação é: " + resetToken;
        emailSenderUseCase.sendEmail(email, subject, body);
    }
}
