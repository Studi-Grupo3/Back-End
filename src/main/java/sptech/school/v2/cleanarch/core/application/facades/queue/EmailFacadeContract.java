package sptech.school.v2.cleanarch.core.application.facades.queue;

import sptech.school.domain.dto.response.ContactRequestDTO;

public interface EmailFacadeContract {
    void requestContactEmail(ContactRequestDTO dto);
    void requestPasswordResetEmail(String email, String resetToken);
}
