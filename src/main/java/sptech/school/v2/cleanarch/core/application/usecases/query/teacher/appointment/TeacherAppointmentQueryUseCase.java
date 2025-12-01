package sptech.school.v2.cleanarch.core.application.usecases.query.teacher.appointment;

import org.springframework.stereotype.Service;
import sptech.school.v2.cleanarch.core.application.gateways.teacher.appointment.TeacherAppointmentQueryGateway;
import sptech.school.v2.cleanarch.core.dtos.out.teacher.LessonHistoryDTO;
import sptech.school.v2.cleanarch.core.dtos.out.teacher.TeacherDashboardDTO;
import sptech.school.v2.cleanarch.core.dtos.out.teacher.TeacherStatsDTO;
import sptech.school.v2.cleanarch.core.dtos.out.teacher.UpcomingLessonDTO;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
public class TeacherAppointmentQueryUseCase {

    private final TeacherAppointmentQueryGateway gateway;
    private final ZoneId ZONE_SP = ZoneId.of("America/Sao_Paulo");

    public TeacherAppointmentQueryUseCase(TeacherAppointmentQueryGateway gateway) {
        this.gateway = gateway;
    }

    public List<UpcomingLessonDTO> getUpcomingLessons(Integer teacherId) {
        LocalDateTime now = LocalDateTime.now(ZONE_SP);
        return gateway.findUpcomingLessons(teacherId, now);
    }

    public TeacherDashboardDTO getDashboard(Integer teacherId) {
        return gateway.getDashboardData(teacherId);
    }

    public List<LessonHistoryDTO> getLessonsHistory(Integer teacherId) {
        return gateway.findLessonsHistory(teacherId);
    }

    public TeacherStatsDTO getTeacherStats(Integer teacherId) {
        LocalDateTime now = LocalDateTime.now(ZONE_SP);
        return gateway.calculateStats(teacherId, now);
    }
}