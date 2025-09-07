package sptech.school.v2.cleanarch.domain.enumerated;

public enum PaymentStatus {
    PENDING("No payment has been made"),
    PARTIAL("50% payment made"),
    PAID("Full payment made"),
    CANCELLED("Payment cancelled");

    private final String description;

    PaymentStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}

