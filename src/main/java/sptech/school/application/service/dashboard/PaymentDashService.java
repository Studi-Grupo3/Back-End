package sptech.school.application.service.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sptech.school.adapters.out.persistence.AppointmentRepository;
import sptech.school.domain.dto.response.dashboard.payment.PaymentDashDTO;
import sptech.school.domain.dto.response.dashboard.payment.PaymentStatsDTO;
import sptech.school.domain.dto.response.dashboard.payment.PaymentTableDTO;
import sptech.school.domain.entity.Appointment;
import sptech.school.domain.enumerated.PaymentStatus;

import java.time.YearMonth;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentDashService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    public PaymentDashDTO getDashboardData(int month, int year) {
        YearMonth ym = YearMonth.of(year, month);
        LocalDateTime start = ym.atDay(1).atStartOfDay();
        LocalDateTime end   = ym.atEndOfMonth().atTime(23, 59, 59);

        List<Appointment> appointments = appointmentRepository
                .findByDateTimeBetween(start, end);

        List<Double> totals = appointments.stream()
                .map(a -> {
                    double hours = a.getLessonDuration() == null ? 0 : a.getLessonDuration();
                    double rate  = a.getTeacher().getHourlyRate() == null ? 0 : a.getTeacher().getHourlyRate();
                    return rate * hours;
                })
                .collect(Collectors.toList());

        double totalAmount = totals.stream().mapToDouble(Double::doubleValue).sum();
        long totalTeachers = appointments.stream()
                .map(a -> a.getTeacher().getId())
                .distinct()
                .count();

        double pendingAmount = appointments.stream()
                .filter(a -> a.getPaymentStatus() == PaymentStatus.PENDING)
                .mapToDouble(a -> {
                    double hours = a.getLessonDuration() == null ? 0 : a.getLessonDuration();
                    double rate  = a.getTeacher().getHourlyRate() == null ? 0 : a.getTeacher().getHourlyRate();
                    return rate * hours;
                })
                .sum();
        long pendingTeachers = appointments.stream()
                .filter(a -> a.getPaymentStatus() == PaymentStatus.PENDING)
                .map(a -> a.getTeacher().getId())
                .distinct()
                .count();

        double realizedAmount = totalAmount - pendingAmount;
        long realizedTeachers = totalTeachers - pendingTeachers;

        double averagePerTeacher = totalTeachers == 0
                ? 0
                : totalAmount / totalTeachers;

        PaymentStatsDTO stats = new PaymentStatsDTO(
                totalAmount,
                totalTeachers,
                pendingAmount,
                pendingTeachers,
                realizedAmount,
                realizedTeachers,
                averagePerTeacher
        );

        List<PaymentTableDTO> recent = appointments.stream()
                .sorted(Comparator.comparing(Appointment::getDateTime).reversed())
                .map(a -> {
                    PaymentTableDTO dto = new PaymentTableDTO();
                    double hours = a.getLessonDuration() == null ? 0 : a.getLessonDuration();
                    double rate  = a.getTeacher().getHourlyRate() == null ? 0 : a.getTeacher().getHourlyRate();

                    dto.setId(a.getId());
                    dto.setName(a.getTeacher().getName());
                    dto.setSubject(a.getTeacher().getSubject().name());
                    dto.setValuePerHour(rate);
                    dto.setHours((int) hours);
                    dto.setTotal(rate * hours);
                    dto.setStatus(a.getPaymentStatus().name().toLowerCase());
                    return dto;
                })
                .collect(Collectors.toList());

        return new PaymentDashDTO(stats, recent);
    }

    public PaymentStatus togglePaymentStatus(Integer id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Appointment não encontrado para id: " + id
                        )
                );

        PaymentStatus current = appointment.getPaymentStatus();
        PaymentStatus next = current == PaymentStatus.PENDING
                ? PaymentStatus.PAID
                : PaymentStatus.PENDING;

        appointment.setPaymentStatus(next);
        appointmentRepository.save(appointment);

        return next;
    }
}
