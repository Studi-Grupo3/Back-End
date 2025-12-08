package sptech.school.v2.cleanarch.core.application.gateways.teacher.appointment;

import sptech.school.v2.cleanarch.core.dtos.out.teacher.LessonHistoryDTO;
import sptech.school.v2.cleanarch.core.dtos.out.teacher.TeacherDashboardDTO;
import sptech.school.v2.cleanarch.core.dtos.out.teacher.TeacherStatsDTO;
import sptech.school.v2.cleanarch.core.dtos.out.teacher.UpcomingLessonDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface TeacherAppointmentQueryGateway {
    List<UpcomingLessonDTO> findUpcomingLessons(Integer teacherId, LocalDateTime fromDate);
    List<LessonHistoryDTO> findLessonsHistory(Integer teacherId);
    TeacherStatsDTO calculateStats(Integer teacherId, LocalDateTime now);
    TeacherDashboardDTO getDashboardData(Integer teacherId);
}