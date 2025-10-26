package sptech.school.v2.cleanarch.core.dtos.out.dashboard.teacher;

import sptech.school.v2.cleanarch.core.dtos.out.dashboard.ChartBarDTO;
import sptech.school.v2.cleanarch.core.dtos.out.dashboard.ChartPieDTO;

import java.util.List;

public class TeacherDashboardDTO {
    private TeacherStatsDTO stats;
    private List<ChartBarDTO> topTeachers;
    private List<ChartPieDTO> subsjectChartValues;
    private List<TeacherTableDTO> teacherTableValues;

    public TeacherDashboardDTO() {
    }

    public TeacherDashboardDTO(
            TeacherStatsDTO stats,
            List<ChartBarDTO> topTeachers,
            List<ChartPieDTO> subsjectChartValues,
            List<TeacherTableDTO> teacherTableValues) {
        this.stats = stats;
        this.topTeachers = topTeachers;
        this.subsjectChartValues = subsjectChartValues;
        this.teacherTableValues = teacherTableValues;
    }

    public TeacherStatsDTO getStats() {
        return stats;
    }

    public List<ChartBarDTO> getTopTeachers() {
        return topTeachers;
    }

    public List<ChartPieDTO> getSubsjectChartValues() {
        return subsjectChartValues;
    }

    public List<TeacherTableDTO> getTeacherTableValues() {
        return teacherTableValues;
    }

}
