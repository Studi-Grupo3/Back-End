// src/main/java/sptech/school/domain/dto/response/dashboard/payment/PaymentStatsDTO.java
package sptech.school.v2.cleanarch.core.dtos.out.dashboard.payment;

public class PaymentStatsDTO {
    private final double totalAmount;
    private final long totalTeachers;
    private final double pendingAmount;
    private final long pendingTeachers;
    private final double realizedAmount;
    private final long realizedTeachers;
    private final double averageAmountPerTeacher;

    public PaymentStatsDTO(double totalAmount,
                           long totalTeachers,
                           double pendingAmount,
                           long pendingTeachers,
                           double realizedAmount,
                           long realizedTeachers,
                           double averageAmountPerTeacher) {
        this.totalAmount = totalAmount;
        this.totalTeachers = totalTeachers;
        this.pendingAmount = pendingAmount;
        this.pendingTeachers = pendingTeachers;
        this.realizedAmount = realizedAmount;
        this.realizedTeachers = realizedTeachers;
        this.averageAmountPerTeacher = averageAmountPerTeacher;
    }

    public double getTotalAmount() { return totalAmount; }
    public long getTotalTeachers() { return totalTeachers; }
    public double getPendingAmount() { return pendingAmount; }
    public long getPendingTeachers() { return pendingTeachers; }
    public double getRealizedAmount() { return realizedAmount; }
    public long getRealizedTeachers() { return realizedTeachers; }
    public double getAverageAmountPerTeacher() { return averageAmountPerTeacher; }
}
