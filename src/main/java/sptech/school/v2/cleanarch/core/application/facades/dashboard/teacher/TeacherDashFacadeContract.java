package sptech.school.v2.cleanarch.core.application.facades.dashboard.teacher;

import sptech.school.v2.cleanarch.core.dtos.out.dashboard.overview.OverviewDashResponseDTO;
import sptech.school.v2.cleanarch.core.dtos.out.dashboard.teacher.TeacherDashResponseDTO;

public interface TeacherDashFacadeContract {
    TeacherDashResponseDTO getTeacherDashData();
}
