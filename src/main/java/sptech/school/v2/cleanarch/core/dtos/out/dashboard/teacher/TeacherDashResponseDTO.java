package sptech.school.v2.cleanarch.core.dtos.out.dashboard.teacher;

import sptech.school.v2.cleanarch.core.dtos.internal.dashboard.ChartBarDTO;
import sptech.school.v2.cleanarch.core.dtos.internal.dashboard.ChartPieDTO;
import sptech.school.v2.cleanarch.core.dtos.internal.dashboard.teacher.TeacherStatsDTO;
import sptech.school.v2.cleanarch.core.dtos.internal.dashboard.teacher.TeacherTableDTO;

import java.util.List;

public record TeacherDashResponseDTO(
        TeacherStatsDTO stats,
        List<ChartBarDTO> topTeachers,
        List<ChartPieDTO> subjectChartValues,
        List<TeacherTableDTO> teacherTableValues
) {}
