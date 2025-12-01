package sptech.school.v2.cleanarch.infra.persistence.query.dashboard.payment;

import org.springframework.stereotype.Component;
import sptech.school.v2.cleanarch.core.application.gateways.dashboard.payment.PaymentDashQueryGateway;
import sptech.school.v2.cleanarch.core.dtos.internal.dashboard.payment.PaymentStatsDTO;
import sptech.school.v2.cleanarch.core.dtos.internal.dashboard.payment.PaymentTableDTO;
import sptech.school.v2.cleanarch.core.dtos.out.dashboard.payment.PaymentDashResponseDTO;
import sptech.school.v2.cleanarch.domain.enumerated.PaymentStatus;
import sptech.school.v2.cleanarch.infra.persistence.repository.dashboard.payment.PaymentDashJpaRepository;
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
                    String subjectString = p.getSubjects();

                    double durationD = p.getLessonDuration() == null ? 0 : p.getLessonDuration();
                    int hours = (int) durationD;

                    double totalRevenue = p.getTotalRevenue() == null ? 0 : p.getTotalRevenue();

                    String status = p.getPaymentStatus() == null ? null : p.getPaymentStatus().name().toLowerCase();

                    String subjectsDisplay = subjectString == null ? "" :
                            subjectString.replace(",", ", ");

                    return new PaymentTableDTO(
                            p.getId(),
                            p.getTeacherName(),
                            subjectsDisplay,
                            p.getHourlyRate(),
                            hours,
                            totalRevenue,
                            status
                    );
                })
                .collect(Collectors.toList());

        return new PaymentDashResponseDTO(stats, recent);
    }
}