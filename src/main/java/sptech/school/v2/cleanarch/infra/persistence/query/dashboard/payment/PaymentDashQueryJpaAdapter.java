package sptech.school.v2.cleanarch.infra.persistence.query.dashboard.payment;

import org.springframework.stereotype.Component;
import sptech.school.v2.cleanarch.core.application.gateways.dashboard.payment.PaymentDashQueryGateway;
import sptech.school.v2.cleanarch.core.dtos.internal.dashboard.payment.PaymentStatsDTO;
import sptech.school.v2.cleanarch.core.dtos.internal.dashboard.payment.PaymentTableDTO;
import sptech.school.v2.cleanarch.core.dtos.out.dashboard.payment.PaymentDashResponseDTO;
import sptech.school.v2.cleanarch.domain.enumerated.PaymentStatus;
import sptech.school.v2.cleanarch.infra.persistence.repository.dashboard.PaymentDashJpaRepository;
import sptech.school.v2.cleanarch.infra.persistence.repository.dashboard.payment.projections.PaymentAppointmentProjection;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PaymentDashQueryJpaAdapter implements PaymentDashQueryGateway {

    private final PaymentDashJpaRepository repository;

    public PaymentDashQueryJpaAdapter(PaymentDashJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public PaymentDashResponseDTO getPaymentDashData(LocalDateTime start, LocalDateTime end) {
        Double totalAmountObj = repository.sumTotalBetween(start, end);
        double totalAmount = totalAmountObj == null ? 0.0 : totalAmountObj;

        long totalTeachers = repository.countDistinctTeachersBetween(start, end);

        Double pendingAmountObj = repository.sumByPaymentStatusBetween(PaymentStatus.PENDING, start, end);
        double pendingAmount = pendingAmountObj == null ? 0.0 : pendingAmountObj;

        long pendingTeachers = repository.countDistinctTeachersByPaymentStatusBetween(PaymentStatus.PENDING, start, end);

        double realizedAmount = totalAmount - pendingAmount;
        long realizedTeachers = totalTeachers - pendingTeachers;

        double averagePerTeacher = totalTeachers == 0 ? 0.0 : totalAmount / totalTeachers;

        PaymentStatsDTO stats = new PaymentStatsDTO(
                totalAmount,
                totalTeachers,
                pendingAmount,
                pendingTeachers,
                realizedAmount,
                realizedTeachers,
                averagePerTeacher
        );

        List<PaymentAppointmentProjection> appts = repository.findAppointmentsBetween(start, end);

        List<PaymentTableDTO> recent = appts.stream()
                .map(p -> {
                    double hoursD = p.getLessonDuration() == null ? 0 : p.getLessonDuration();
                    int hours = (int) hoursD;
                    double rate = p.getHourlyRate() == null ? 0 : p.getHourlyRate();
                    double total = rate * hoursD;
                    String status = p.getPaymentStatus() == null ? null : p.getPaymentStatus().toLowerCase();
                    return new PaymentTableDTO(
                            p.getId(),
                            p.getTeacherName(),
                            p.getSubject(),
                            p.getHourlyRate(),
                            hours,
                            total,
                            status
                    );
                })
                .collect(Collectors.toList());

        return new PaymentDashResponseDTO(stats, recent);
    }
}
