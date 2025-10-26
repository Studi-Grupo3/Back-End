package sptech.school.v2.cleanarch.core.application.facades.queue;

import sptech.school.v2.cleanarch.core.dtos.out.ContactRequestDTO;

public interface EmailFacadeContract {
    void requestContactEmail(ContactRequestDTO dto);
    void requestPasswordResetEmail(String email, String resetToken);
}
