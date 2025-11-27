package sptech.school.v2.cleanarch.core.dtos.internal.payment.payment;

import java.time.OffsetDateTime;

public class PaymentCreatedEvent {
    private String messageId;
    private String paymentId;
    private String orderId;
    private Double amount;
    private String status;
    private String payerEmail;
    private OffsetDateTime happenedAt;

    public PaymentCreatedEvent() {}

    // getters / setters
    public String getMessageId() { return messageId; }
    public void setMessageId(String messageId) { this.messageId = messageId; }
    public String getPaymentId() { return paymentId; }
    public void setPaymentId(String paymentId) { this.paymentId = paymentId; }
    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }
    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getPayerEmail() { return payerEmail; }
    public void setPayerEmail(String payerEmail) { this.payerEmail = payerEmail; }
    public OffsetDateTime getHappenedAt() { return happenedAt; }
    public void setHappenedAt(OffsetDateTime happenedAt) { this.happenedAt = happenedAt; }
}
