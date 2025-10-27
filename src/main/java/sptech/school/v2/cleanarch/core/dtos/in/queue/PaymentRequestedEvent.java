package sptech.school.v2.cleanarch.core.dtos.in.queue;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class PaymentRequestedEvent {
    private String messageId;
    private BigDecimal transactionAmount;
    private String paymentMethodId;
    private String description;
    private Integer installments;
    private String payerEmail;
    private String payerFirstName;
    private String payerIdentificationType;
    private String payerIdentificationNumber;
    private String cardToken;
    private OffsetDateTime happenedAt;

    public String getMessageId() { return messageId; }
    public void setMessageId(String messageId) { this.messageId = messageId; }
    public BigDecimal getTransactionAmount() { return transactionAmount; }
    public void setTransactionAmount(BigDecimal transactionAmount) { this.transactionAmount = transactionAmount; }
    public String getPaymentMethodId() { return paymentMethodId; }
    public void setPaymentMethodId(String paymentMethodId) { this.paymentMethodId = paymentMethodId; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Integer getInstallments() { return installments; }
    public void setInstallments(Integer installments) { this.installments = installments; }
    public String getPayerEmail() { return payerEmail; }
    public void setPayerEmail(String payerEmail) { this.payerEmail = payerEmail; }
    public String getPayerFirstName() { return payerFirstName; }
    public void setPayerFirstName(String payerFirstName) { this.payerFirstName = payerFirstName; }
    public String getPayerIdentificationType() { return payerIdentificationType; }
    public void setPayerIdentificationType(String payerIdentificationType) { this.payerIdentificationType = payerIdentificationType; }
    public String getPayerIdentificationNumber() { return payerIdentificationNumber; }
    public void setPayerIdentificationNumber(String payerIdentificationNumber) { this.payerIdentificationNumber = payerIdentificationNumber; }
    public String getCardToken() { return cardToken; }
    public void setCardToken(String cardToken) { this.cardToken = cardToken; }
    public OffsetDateTime getHappenedAt() { return happenedAt; }
    public void setHappenedAt(OffsetDateTime happenedAt) { this.happenedAt = happenedAt; }
}

