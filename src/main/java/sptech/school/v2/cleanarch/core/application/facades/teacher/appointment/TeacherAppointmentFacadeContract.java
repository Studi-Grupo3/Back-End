package sptech.school.v2.cleanarch.core.application.facades.teacher.appointment;

import sptech.school.v2.cleanarch.core.dtos.out.teacher.LessonHistoryDTO;
import sptech.school.v2.cleanarch.core.dtos.out.teacher.TeacherStatsDTO;
import sptech.school.v2.cleanarch.core.dtos.out.teacher.UpcomingLessonDTO;

import java.util.List;

public interface TeacherAppointmentFacadeContract {
    List<UpcomingLessonDTO> getUpcomingLessons(Integer teacherId);
    List<LessonHistoryDTO> getLessonsHistory(Integer teacherId);
    TeacherStatsDTO getStats(Integer teacherId);
}