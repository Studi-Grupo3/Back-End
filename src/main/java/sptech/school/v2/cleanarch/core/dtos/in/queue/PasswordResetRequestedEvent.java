package sptech.school.v2.cleanarch.core.dtos.in.queue;

import java.time.OffsetDateTime;

public class PasswordResetRequestedEvent {
    private String messageId;
    private String email;
    private String resetToken;
    private OffsetDateTime happenedAt;

    public PasswordResetRequestedEvent() {}

    public String getMessageId() { return messageId; }
    public void setMessageId(String messageId) { this.messageId = messageId; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getResetToken() { return resetToken; }
    public void setResetToken(String resetToken) { this.resetToken = resetToken; }
    public OffsetDateTime getHappenedAt() { return happenedAt; }
    public void setHappenedAt(OffsetDateTime happenedAt) { this.happenedAt = happenedAt; }
}
