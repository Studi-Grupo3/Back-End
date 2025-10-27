package sptech.school.v2.cleanarch.core.dtos.out.dashboard.payment;

import java.util.List;

public class PaymentDashDTO {
    private PaymentStatsDTO stats;
    private List<PaymentTableDTO> recent;

    public PaymentDashDTO() { }

    public PaymentDashDTO(PaymentStatsDTO stats,
                          List<PaymentTableDTO> recent) {
        this.stats = stats;
        this.recent = recent;
    }

    public PaymentStatsDTO getStats() { return stats; }
    public void setStats(PaymentStatsDTO stats) { this.stats = stats; }

    public List<PaymentTableDTO> getRecent() { return recent; }
    public void setRecent(List<PaymentTableDTO> recent) { this.recent = recent; }
}
