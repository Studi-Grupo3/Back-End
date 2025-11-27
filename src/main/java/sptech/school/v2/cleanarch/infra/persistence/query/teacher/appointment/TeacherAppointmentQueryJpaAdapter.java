package sptech.school.v2.cleanarch.infra.persistence.query.teacher.appointment;

import org.springframework.stereotype.Component;
import sptech.school.v2.cleanarch.core.application.gateways.teacher.appointment.TeacherAppointmentQueryGateway;
import sptech.school.v2.cleanarch.core.dtos.out.teacher.LessonHistoryDTO;
import sptech.school.v2.cleanarch.core.dtos.out.teacher.TeacherStatsDTO;
import sptech.school.v2.cleanarch.core.dtos.out.teacher.UpcomingLessonDTO;
import sptech.school.v2.cleanarch.domain.enumerated.AppointmentStatus;
import sptech.school.v2.cleanarch.infra.persistence.repository.teacher.appointment.TeacherAppointmentJpaRepository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TeacherAppointmentQueryJpaAdapter implements TeacherAppointmentQueryGateway {

    private final TeacherAppointmentJpaRepository repository;

    public TeacherAppointmentQueryJpaAdapter(TeacherAppointmentJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<UpcomingLessonDTO> findUpcomingLessons(Integer teacherId, LocalDateTime fromDate) {
        return repository.findUpcomingByTeacher(teacherId, fromDate).stream()
                .map(a -> new UpcomingLessonDTO(
                        a.getId(),
                        a.getSubject(),
                        a.getStudent().getId(),
                        a.getStudent().getName(),
                        a.getDateTime().toLocalDate(),
                        a.getDateTime().toLocalTime(),
                        a.getLessonDuration(),
                        "Presencial",
                        a.getStatus().toString()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public List<LessonHistoryDTO> findLessonsHistory(Integer teacherId) {
        return repository.findAllByTeacherIdOrderByDateDesc(teacherId).stream()
                .map(a -> new LessonHistoryDTO(
                        a.getId(),
                        a.getSubject(),
                        a.getStudent().getId(),
                        a.getStudent().getName(),
                        a.getDateTime().toLocalDate(),
                        a.getDateTime().toLocalTime(),
                        a.getLessonDuration(),
                        "Presencial",
                        false,
                        a.getStatus().toString(),
                        a.getTotalValue(),
                        null,
                        null
                ))
                .collect(Collectors.toList());
    }

    @Override
    public TeacherStatsDTO calculateStats(Integer teacherId, LocalDateTime now) {
        LocalDate today = now.toLocalDate();

        Long aulasHoje = repository.countByTeacherAndStatusAndDate(teacherId, AppointmentStatus.SCHEDULED, today);

        LocalDateTime startWeek = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)).atStartOfDay();
        LocalDateTime endWeek = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY)).atTime(23,59,59);
        Long aulasSemana = repository.countByTeacherAndStatusBetween(teacherId, AppointmentStatus.SCHEDULED, startWeek, endWeek);

        Double totalMinutes = repository.sumDurationByTeacherAndStatus(teacherId, AppointmentStatus.COMPLETED);
        String horasFormatadas = formatMinutes(totalMinutes);

        return new TeacherStatsDTO(
                aulasHoje, "Agendadas para hoje",
                aulasSemana, "Nesta semana",
                horasFormatadas, "Total ministrado"
        );
    }

    private String formatMinutes(Double minutes) {
        if (minutes == null) return "0h";
        int total = minutes.intValue();
        int h = total / 60;
        int m = total % 60;
        if (m == 0) return h + "h";
        return String.format("%dh %02dm", h, m);
    }
}