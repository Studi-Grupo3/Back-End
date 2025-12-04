package sptech.school.v2.cleanarch.core.application.gateways.payment;

public interface PreferenceGateway {
    String createPreference(double amount, String payerEmail);
}