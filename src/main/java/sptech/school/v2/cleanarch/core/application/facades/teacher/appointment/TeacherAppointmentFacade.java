package sptech.school.v2.cleanarch.core.application.facades.teacher.appointment;

import org.springframework.stereotype.Service;
import sptech.school.v2.cleanarch.core.application.usecases.query.teacher.appointment.TeacherAppointmentQueryUseCase;
import sptech.school.v2.cleanarch.core.dtos.out.teacher.LessonHistoryDTO;
import sptech.school.v2.cleanarch.core.dtos.out.teacher.TeacherDashboardDTO;
import sptech.school.v2.cleanarch.core.dtos.out.teacher.TeacherStatsDTO;
import sptech.school.v2.cleanarch.core.dtos.out.teacher.UpcomingLessonDTO;

import java.util.List;

@Service
public class TeacherAppointmentFacade implements TeacherAppointmentFacadeContract {

    private final TeacherAppointmentQueryUseCase useCase;

    public TeacherAppointmentFacade(TeacherAppointmentQueryUseCase useCase) {
        this.useCase = useCase;
    }

    @Override
    public List<UpcomingLessonDTO> getUpcomingLessons(Integer teacherId) {
        return useCase.getUpcomingLessons(teacherId);
    }

    @Override
    public List<LessonHistoryDTO> getLessonsHistory(Integer teacherId) {
        return useCase.getLessonsHistory(teacherId);
    }

    @Override
    public TeacherDashboardDTO getDashboard(Integer teacherId) {
        return useCase.getDashboard(teacherId);
    }

    @Override
    public TeacherStatsDTO getStats(Integer teacherId) {
        return useCase.getTeacherStats(teacherId);
    }
}