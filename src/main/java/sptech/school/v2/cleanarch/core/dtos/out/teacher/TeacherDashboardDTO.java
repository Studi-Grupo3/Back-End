package sptech.school.v2.cleanarch.core.dtos.out.teacher;

import java.util.List;

public class TeacherDashboardDTO {

    private Long totalLessons;
    private Double cancellationPercentage;
    private Double totalHours;
    private Double monthlyEarnings;
    private Long completedLessons;
    private Long upcomingLessons;
    private Long totalStudents;
    private List<DisciplineStatsDTO> lessonsByDiscipline;
    private List<WeekdayStatsDTO> lessonsByWeekday;

    public TeacherDashboardDTO(Long totalLessons,
                               Double cancellationPercentage,
                               Double totalHours,
                               Double monthlyEarnings,
                               Long completedLessons,
                               Long upcomingLessons,
                               Long totalStudents,
                               List<DisciplineStatsDTO> lessonsByDiscipline,
                               List<WeekdayStatsDTO> lessonsByWeekday) {
        this.totalLessons = totalLessons;
        this.cancellationPercentage = cancellationPercentage;
        this.totalHours = totalHours;
        this.monthlyEarnings = monthlyEarnings;
        this.completedLessons = completedLessons;
        this.upcomingLessons = upcomingLessons;
        this.totalStudents = totalStudents;
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

    public Double getMonthlyEarnings() {
        return monthlyEarnings;
    }

    public void setMonthlyEarnings(Double monthlyEarnings) {
        this.monthlyEarnings = monthlyEarnings;
    }

    public List<WeekdayStatsDTO> getLessonsByWeekday() {
        return lessonsByWeekday;
    }

    public void setLessonsByWeekday(List<WeekdayStatsDTO> lessonsByWeekday) {
        this.lessonsByWeekday = lessonsByWeekday;
    }

    public Long getCompletedLessons() {
        return completedLessons;
    }

    public void setCompletedLessons(Long completedLessons) {
        this.completedLessons = completedLessons;
    }

    public Long getUpcomingLessons() {
        return upcomingLessons;
    }

    public void setUpcomingLessons(Long upcomingLessons) {
        this.upcomingLessons = upcomingLessons;
    }

    public Long getTotalStudents() {
        return totalStudents;
    }

    public void setTotalStudents(Long totalStudents) {
        this.totalStudents = totalStudents;
    }
}
