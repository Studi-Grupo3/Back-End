package sptech.school.application.service.teacher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sptech.school.application.mappers.AppointmentTeacherMapper;
import sptech.school.adapters.out.persistence.AppointmentRepository;
import sptech.school.domain.dto.response.teacher.UpcomingLessonDTO;
import sptech.school.domain.dto.response.teacher.LessonHistoryDTO;
import sptech.school.domain.dto.response.teacher.TeacherStatsDTO;
import sptech.school.domain.entity.Appointment;
import sptech.school.v2.cleanarch.domain.enumerated.AppointmentStatus;

import java.time.*;
import java.util.Arrays;
import java.util.List;

@Service
public class TeacherAppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private AppointmentTeacherMapper teacherMapper;

    /**
     * Próximas aulas: status = SCHEDULED.
     */
    public List<UpcomingLessonDTO> getUpcomingLessonsForTeacher(Integer teacherId) {
        List<Appointment> appointments =
                appointmentRepository.findByTeacherIdAndStatusOrderByDateTime(teacherId, AppointmentStatus.SCHEDULED);
        return appointments.stream()
                .map(teacherMapper::toUpcomingLessonDto)
                .toList();
    }

    /**
     * Histórico de aulas: status COMPLETED ou CANCELLED.
     * Se search fornecido, faz busca no nome do aluno; caso contrário, lista todos.
     */
    public List<LessonHistoryDTO> getLessonsHistoryForTeacher(Integer teacherId) {
        List<AppointmentStatus> historyStatuses = Arrays.asList(
                AppointmentStatus.COMPLETED, AppointmentStatus.CANCELLED);

        List<Appointment> appointments = appointmentRepository
                .findByTeacherIdAndStatusInOrderByDateTimeDesc(teacherId, historyStatuses);

        return appointments.stream()
                .map(teacherMapper::toLessonHistoryDto)
                .toList();
    }

    /**
     * Estatísticas do professor:
     * - Aulas Hoje: conta SCHEDULED com dateTime no dia de hoje.
     * - Aulas Semana: conta SCHEDULED com dateTime na semana atual.
     * - Horas ministradas: soma duration (minutos) onde status = COMPLETED.
     */
    public TeacherStatsDTO getStatsForTeacher(Integer teacherId) {
        ZoneId zone = ZoneId.of("America/Sao_Paulo");
        LocalDate today = LocalDate.now(zone);
        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.atTime(LocalTime.MAX);

        // Buscar todos SCHEDULED e filtrar por data
        List<Appointment> scheduledList =
                appointmentRepository.findByTeacherIdAndStatusOrderByDateTime(teacherId, AppointmentStatus.SCHEDULED);

        long aulasHojeCount = scheduledList.stream()
                .filter(a -> {
                    LocalDateTime dt = a.getDateTime();
                    return dt != null && !dt.isBefore(startOfDay) && !dt.isAfter(endOfDay);
                })
                .count();
        String subtitleHoje = aulasHojeCount + " agendada(s) hoje";

        LocalDate startWeekDate = today.with(DayOfWeek.MONDAY);
        LocalDate endWeekDate = today.with(DayOfWeek.SUNDAY);
        LocalDateTime startOfWeek = startWeekDate.atStartOfDay();
        LocalDateTime endOfWeek = endWeekDate.atTime(LocalTime.MAX);

        long aulasSemanaCount = scheduledList.stream()
                .filter(a -> {
                    LocalDateTime dt = a.getDateTime();
                    return dt != null && !dt.isBefore(startOfWeek) && !dt.isAfter(endOfWeek);
                })
                .count();
        String subtitleSemana = aulasSemanaCount + " agendada(s) nesta semana";

        // Soma duração de aulas concluídas
        Double sumDuration = appointmentRepository.sumLessonDurationByTeacherIdAndStatus(teacherId, AppointmentStatus.COMPLETED);
        long totalMinutes = sumDuration != null ? sumDuration.longValue() : 0L;
        String horasFormatadas = formatMinutesToHourString(totalMinutes);
        String subtitleHoras = "Concluídas";

        TeacherStatsDTO dto = new TeacherStatsDTO();
        dto.setAulasHoje(aulasHojeCount);
        dto.setAulasHojeSubtitle(subtitleHoje);
        dto.setAulasSemana(aulasSemanaCount);
        dto.setAulasSemanaSubtitle(subtitleSemana);
        dto.setHorasMinistradas(horasFormatadas);
        dto.setHorasMinistradasSubtitle(subtitleHoras);
        return dto;
    }

    private String formatMinutesToHourString(long totalMinutes) {
        long hours = totalMinutes / 60;
        long minutes = totalMinutes % 60;
        if (hours > 0 && minutes > 0) {
            return String.format("%dh %02dm", hours, minutes);
        } else if (hours > 0) {
            return String.format("%dh", hours);
        } else {
            return String.format("%02dm", minutes);
        }
    }

    // Se tiver endpoint para detalhes de um appointment, pode adicionar método getAppointmentForTeacher(...)
    // public AppointmentResponseDTO getAppointmentForTeacher(Integer teacherId, Integer appointmentId) { ... }
}
