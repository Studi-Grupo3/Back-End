package sptech.school.v2.cleanarch.infra.persistence.query.dashboard.overview;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import sptech.school.domain.enumerated.PaymentStatus;
import sptech.school.v2.cleanarch.core.application.gateways.dashboard.overview.OverviewDashQueryGateway;
import sptech.school.v2.cleanarch.core.dtos.internal.dashboard.ChartBarDTO;
import sptech.school.v2.cleanarch.core.dtos.internal.dashboard.ChartLineDTO;
import sptech.school.v2.cleanarch.core.dtos.internal.dashboard.overview.OverviewStatsDTO;
import sptech.school.v2.cleanarch.core.dtos.internal.dashboard.overview.OverviewTableDTO;
import sptech.school.v2.cleanarch.core.dtos.out.dashboard.overview.OverviewDashResponseDTO;
import sptech.school.v2.cleanarch.infra.persistence.repository.dashboard.overview.OverviewDashRepository;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class OverviewDashQueryJpaAdapter implements OverviewDashQueryGateway {

    private final OverviewDashRepository repository;
    private final org.springframework.data.repository.CrudRepository<?, ?> teacherRepository;

    public OverviewDashQueryJpaAdapter(OverviewDashRepository repository,
                                       sptech.school.adapters.out.persistence.TeacherRepositoryJpa teacherRepositoryJpa) {
        this.repository = repository;
        this.teacherRepository = teacherRepositoryJpa;
    }

    @Override
    public OverviewDashResponseDTO getOverviewDashData(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        Double totalRevenueObj = repository.sumTotalByPaymentStatusBetween(PaymentStatus.PAID, startDateTime, endDateTime);
        double totalRevenue = totalRevenueObj == null ? 0.0 : totalRevenueObj;

        long totalTeachers = teacherRepository.count();

        Double pendingObj = repository.sumPendingAmountBetween(PaymentStatus.PENDING, startDateTime, endDateTime);
        double pendingAmount = pendingObj == null ? 0.0 : pendingObj;

        long totalAppointments = repository.countAppointmentsBetween(startDateTime, endDateTime);

        OverviewStatsDTO statsDTO = new OverviewStatsDTO(totalRevenue, totalTeachers, pendingAmount, (int) totalAppointments);

        List<ChartLineDTO> monthlyRevenue = repository.sumPaidByMonthBetween(PaymentStatus.PAID, startDateTime, endDateTime)
                .stream()
                .map(m -> {
                    Integer monthNum = m.getMonth();
                    String label = monthNum != null && monthNum >= 1 && monthNum <= 12 ? Month.of(monthNum).name() : "Mês " + monthNum;
                    Double total = m.getTotal() == null ? 0.0 : m.getTotal();
                    return new ChartLineDTO(label, total);
                })
                .collect(Collectors.toList());

        List<ChartBarDTO> lessonsPerDay = repository.countLessonsByDayNameBetween(startDateTime, endDateTime)
                .stream()
                .map(d -> new ChartBarDTO(d.getDayName(), d.getTotal().doubleValue()))
                .collect(Collectors.toList());

        int recentLimit = 5;
        List<OverviewTableDTO> recentPayments = repository.findRecentPaymentsByStatusBetween(PaymentStatus.PAID, startDateTime, endDateTime, PageRequest.of(0, recentLimit))
                .stream()
                .map(p -> new OverviewTableDTO(
                        p.getTeacherName(),
                        p.getSubjects(),
                        p.getHourlyRate(),
                        p.getLessonDuration(),
                        p.getPaymentStatus()
                ))
                .collect(Collectors.toList());

        return new OverviewDashResponseDTO(statsDTO, monthlyRevenue, lessonsPerDay, recentPayments);
    }
}
