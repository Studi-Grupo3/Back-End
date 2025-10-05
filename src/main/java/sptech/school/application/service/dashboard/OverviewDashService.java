package sptech.school.application.service.dashboard;

import org.springframework.stereotype.Service;
import sptech.school.adapters.out.persistence.AppointmentRepository;
import sptech.school.adapters.out.persistence.TeacherRepositoryJpa;
import sptech.school.domain.dto.response.dashboard.ChartBarDTO;
import sptech.school.domain.dto.response.dashboard.overview.ChartLineDTO;
import sptech.school.domain.dto.response.dashboard.overview.OverviewDashDTO;
import sptech.school.domain.dto.response.dashboard.overview.OverviewStatsDTO;
import sptech.school.domain.dto.response.dashboard.overview.OverviewTableDTO;
import sptech.school.domain.entity.Appointment;
import sptech.school.domain.enumerated.PaymentStatus;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OverviewDashService {

    private final AppointmentRepository appointmentRepository;
    private final TeacherRepositoryJpa teacherRepository;

    public OverviewDashService(AppointmentRepository appointmentRepository, TeacherRepositoryJpa teacherRepository) {
        this.appointmentRepository = appointmentRepository;
        this.teacherRepository = teacherRepository;
    }

    public OverviewDashDTO getDashboardData() {
        List<Appointment> allAppointments = appointmentRepository.findAll();

        double totalRevenue = allAppointments.stream()
                .filter(a -> a.getPaymentStatus() == PaymentStatus.PAID)
                .mapToDouble(Appointment::getTotalValue)
                .sum();

        long totalTeachers = teacherRepository.count();

        List<Appointment> pendentes = appointmentRepository
                .findByPaymentStatus(PaymentStatus.PENDING);

        double pendingAmount = pendentes.stream()
                .map(a -> {
                    double hours = a.getLessonDuration() == null
                            ? 0
                            : a.getLessonDuration();
                    double rate  = a.getTeacher().getHourlyRate() == null
                            ? 0
                            : a.getTeacher().getHourlyRate();
                    return rate * hours;
                })
                .mapToDouble(Double::doubleValue)
                .sum();

        int totalAppointments = allAppointments.size();

        OverviewStatsDTO statsDTO = new OverviewStatsDTO(
                totalRevenue,
                totalTeachers,
                pendingAmount,
                totalAppointments
        );

        List<ChartLineDTO> monthlyRevenue = allAppointments.stream()
                .filter(a -> a.getPaymentStatus() == PaymentStatus.PAID)
                .collect(Collectors.groupingBy(
                        a -> a.getDateTime().getMonth(),
                        TreeMap::new,
                        Collectors.summingDouble(Appointment::getTotalValue)
                ))
                .entrySet().stream()
                .map(entry -> new ChartLineDTO(entry.getKey().toString(), entry.getValue()))
                .toList();

        List<ChartBarDTO> lessonsPerDay = allAppointments.stream()
                .collect(Collectors.groupingBy(
                        a -> a.getDateTime().getDayOfWeek(),
                        TreeMap::new,
                        Collectors.counting()
                ))
                .entrySet().stream()
                .map(entry -> new ChartBarDTO(entry.getKey().toString(), entry.getValue()))
                .toList();

        int recentPaymentsLimit = 5;
      List<OverviewTableDTO> recentPayments = allAppointments.stream()
                .filter(a -> a.getPaymentStatus() == PaymentStatus.PAID)
            .map(a -> new OverviewTableDTO(
                      a.getTeacher().getName(),
                       a.getTeacher().getSubjects().toString(),
                        a.getTeacher().getHourlyRate(),
                      a.getLessonDuration(),
                       a.getPaymentStatus().toString()
               ))
              .toList();

        return new OverviewDashDTO(statsDTO, monthlyRevenue, lessonsPerDay, recentPayments);
    }
}
