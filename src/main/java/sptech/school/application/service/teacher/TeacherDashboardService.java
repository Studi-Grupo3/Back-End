package sptech.school.application.service.teacher;

import org.springframework.stereotype.Service;
import sptech.school.adapters.out.persistence.AppointmentRepository;
import sptech.school.domain.dto.response.teacher.DisciplineStatsDTO;
import sptech.school.domain.dto.response.teacher.TeacherDashboardDTO;
import sptech.school.domain.dto.response.teacher.WeekdayStatsDTO;
import sptech.school.domain.enumerated.AppointmentStatus;

import java.util.List;

@Service
public class TeacherDashboardService {

    private final AppointmentRepository appointmentRepository;

    public TeacherDashboardService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    public TeacherDashboardDTO getDashboard(Integer teacherId) {
        Long total     = appointmentRepository.countByTeacherId(teacherId);
        Long cancelled = appointmentRepository.countByTeacherIdAndStatus(
                teacherId, AppointmentStatus.CANCELLED);
        Double hours   = appointmentRepository.sumLessonDurationByTeacherId(teacherId);

        double pctCancel = (total == 0)
                ? 0.0
                : (cancelled.doubleValue() / total.doubleValue()) * 100.0;

        List<DisciplineStatsDTO> byDisc   =
                appointmentRepository.countByTeacherGroupBySubject(teacherId);

        List<WeekdayStatsDTO> byWeekday   =
                appointmentRepository.countByTeacherGroupByWeekday(teacherId);

        return new TeacherDashboardDTO(
                total,
                pctCancel,
                hours,
                byDisc,
                byWeekday
        );
    }
}
