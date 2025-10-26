package sptech.school.application.events;

import java.time.OffsetDateTime;

public class ContactRequestedEvent {
    private String messageId;
    private String nome;
    private String email;
    private String celular;
    private String mensagem;
    private OffsetDateTime happenedAt;

    public ContactRequestedEvent() {}

    public String getMessageId() { return messageId; }
    public void setMessageId(String messageId) { this.messageId = messageId; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getCelular() { return celular; }
    public void setCelular(String celular) { this.celular = celular; }
    public String getMensagem() { return mensagem; }
    public void setMensagem(String mensagem) { this.mensagem = mensagem; }
    public OffsetDateTime getHappenedAt() { return happenedAt; }
    public void setHappenedAt(OffsetDateTime happenedAt) { this.happenedAt = happenedAt; }
}
