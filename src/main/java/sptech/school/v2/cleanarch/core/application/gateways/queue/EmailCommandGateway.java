package sptech.school.v2.cleanarch.core.application.gateways.queue;

import sptech.school.v2.cleanarch.core.dtos.in.queue.ContactRequestedEvent;
import sptech.school.v2.cleanarch.core.dtos.in.queue.PasswordResetRequestedEvent;

public interface EmailCommandGateway {
    void sendContactEvent(ContactRequestedEvent event);
    void sendPasswordResetEvent(PasswordResetRequestedEvent event);
}
