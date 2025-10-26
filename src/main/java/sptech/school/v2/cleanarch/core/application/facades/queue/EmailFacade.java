package sptech.school.v2.cleanarch.core.application.facades.queue;

import org.springframework.stereotype.Service;
import sptech.school.v2.cleanarch.core.application.usecases.command.queue.EmailCommandUseCase;
import sptech.school.v2.cleanarch.core.dtos.out.ContactRequestDTO;

@Service
public class EmailFacade implements EmailFacadeContract {

    private final EmailCommandUseCase commandUseCase;

    public EmailFacade(EmailCommandUseCase commandUseCase) {
        this.commandUseCase = commandUseCase;
    }

    @Override
    public void requestContactEmail(ContactRequestDTO dto) {
        commandUseCase.sendContactRequestedEvent(dto);
    }

    @Override
    public void requestPasswordResetEmail(String email, String resetToken) {
        commandUseCase.sendPasswordResetRequestedEvent(email, resetToken);
    }
}
