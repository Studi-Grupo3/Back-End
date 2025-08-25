package sptech.school.domain.dto.response.teacher;

import java.util.List;

public class TeacherDashboardDTO {

    private Long totalLessons;
    private Double cancellationPercentage;
    private Double totalHours;
    private List<DisciplineStatsDTO> lessonsByDiscipline;
    private List<WeekdayStatsDTO> lessonsByWeekday;

    public TeacherDashboardDTO(Long totalLessons,
                               Double cancellationPercentage,
                               Double totalHours,
                               List<DisciplineStatsDTO> lessonsByDiscipline,
                               List<WeekdayStatsDTO> lessonsByWeekday) {
        this.totalLessons = totalLessons;
        this.cancellationPercentage = cancellationPercentage;
        this.totalHours = totalHours;
        this.lessonsByDiscipline = lessonsByDiscipline;
        this.lessonsByWeekday = lessonsByWeekday;
    }

    public Long getTotalLessons() {
        return totalLessons;
    }

    public void setTotalLessons(Long totalLessons) {
        this.totalLessons = totalLessons;
    }

    public Double getCancellationPercentage() {
        return cancellationPercentage;
    }

    public void setCancellationPercentage(Double cancellationPercentage) {
        this.cancellationPercentage = cancellationPercentage;
    }

    public Double getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(Double totalHours) {
        this.totalHours = totalHours;
    }

    public List<DisciplineStatsDTO> getLessonsByDiscipline() {
        return lessonsByDiscipline;
    }

    public void setLessonsByDiscipline(List<DisciplineStatsDTO> lessonsByDiscipline) {
        this.lessonsByDiscipline = lessonsByDiscipline;
    }

    public List<WeekdayStatsDTO> getLessonsByWeekday() {
        return lessonsByWeekday;
    }

    public void setLessonsByWeekday(List<WeekdayStatsDTO> lessonsByWeekday) {
        this.lessonsByWeekday = lessonsByWeekday;
    }
}
