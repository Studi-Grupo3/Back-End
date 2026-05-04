package sptech.school.v2.cleanarch.infra.persistence.query.dashboard.payment;

import org.springframework.stereotype.Component;
import sptech.school.v2.cleanarch.core.application.gateways.dashboard.payment.PaymentDashQueryGateway;
import sptech.school.v2.cleanarch.core.dtos.internal.dashboard.payment.PaymentStatsDTO;
import sptech.school.v2.cleanarch.core.dtos.internal.dashboard.payment.PaymentTableDTO;
import sptech.school.v2.cleanarch.core.dtos.out.dashboard.payment.PaymentDashResponseDTO;
import sptech.school.v2.cleanarch.domain.enumerated.PaymentStatus;
import sptech.school.v2.cleanarch.infra.persistence.repository.dashboard.payment.PaymentDashJpaRepository;
import sptech.school.v2.cleanarch.infra.persistence.repository.dashboard.payment.projections.PaymentAppointmentProjection;
import sptech.school.v2.cleanarch.infra.persistence.repository.teacher.TeacherRepository;
import sptech.school.v2.cleanarch.infra.persistence.repository.payment.PaymentTeacherPeriodRepository;
import sptech.school.v2.cleanarch.domain.entities.Teacher;
import sptech.school.v2.cleanarch.domain.entities.PaymentTeacherPeriod;
import sptech.school.v2.cleanarch.infra.persistence.repository.appointment.AppointmentJpaRepository;
import sptech.school.v2.cleanarch.domain.entities.Appointment;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PaymentDashQueryJpaAdapter implements PaymentDashQueryGateway {

    private final PaymentDashJpaRepository repository;
    private final TeacherRepository teacherRepository;
    private final AppointmentJpaRepository appointmentJpaRepository;
    private final PaymentTeacherPeriodRepository paymentTeacherPeriodRepository;

    public PaymentDashQueryJpaAdapter(PaymentDashJpaRepository repository,
            TeacherRepository teacherRepository,
            AppointmentJpaRepository appointmentJpaRepository,
            PaymentTeacherPeriodRepository paymentTeacherPeriodRepository) {
        this.repository = repository;
        this.teacherRepository = teacherRepository;
        this.appointmentJpaRepository = appointmentJpaRepository;
        this.paymentTeacherPeriodRepository = paymentTeacherPeriodRepository;
    }

    @Override
    public PaymentDashResponseDTO getPaymentDashData(LocalDateTime start, LocalDateTime end) {
        int month = start.getMonthValue();
        int year = start.getYear();
        List<Teacher> teachers = teacherRepository.findAll();
        double totalAmount = 0;
        double pendingAmount = 0;
        double realizedAmount = 0;
        int totalTeachers = 0;
        int pendingTeachers = 0;
        int realizedTeachers = 0;
        List<PaymentTableDTO> recent = new java.util.ArrayList<>();

        for (Teacher teacher : teachers) {
            List<Appointment> appointments = appointmentJpaRepository.findByTeacherIdAndMonthAndYear(teacher.getId(),
                    month, year);
            int totalMinutes = appointments.stream()
                    .mapToInt(a -> a.getLessonDuration() != null ? a.getLessonDuration().intValue() : 0).sum();
            int hours = totalMinutes / 60;
            double valuePerHour = teacher.getHourlyRate() != null ? teacher.getHourlyRate() : 0.0;
            double total = valuePerHour * hours;

            PaymentTeacherPeriod paymentPeriod = paymentTeacherPeriodRepository
                    .findByTeacherIdAndMonthAndYear(teacher.getId(), month, year).orElse(null);
            String status = paymentPeriod == null ? "pending" : paymentPeriod.getStatus().name().toLowerCase();

            if ("cancelled".equals(status)) {
                recent.add(new PaymentTableDTO(
                        teacher.getId(),
                        teacher.getName(),
                        teacher.getSubjects() != null ? teacher.getSubjects().toString() : "",
                        valuePerHour,
                        hours,
                        total,
                        status));
                continue;
            }

            recent.add(new PaymentTableDTO(
                    teacher.getId(),
                    teacher.getName(),
                    teacher.getSubjects() != null ? teacher.getSubjects().toString() : "",
                    valuePerHour,
                    hours,
                    total,
                    status));

            // Only count teachers with actual hours worked for KPI calculations
            if (hours == 0) continue;

            totalTeachers++;
            totalAmount += total;

            if ("pending".equals(status)) {
                pendingAmount += total;
                pendingTeachers++;
            } else if ("paid".equals(status)) {
                realizedAmount += total;
                realizedTeachers++;
            }
        }

        double averageAmountPerTeacher = totalTeachers == 0 ? 0.0 : totalAmount / totalTeachers;
        PaymentStatsDTO stats = new PaymentStatsDTO(
                totalAmount,
                totalTeachers,
                pendingAmount,
                pendingTeachers,
                realizedAmount,
                realizedTeachers,
                averageAmountPerTeacher);

        return new PaymentDashResponseDTO(stats, recent);
    }
}