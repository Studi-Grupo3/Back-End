package sptech.school.v2.cleanarch.core.application.facades.dashboard.teacher;

import org.springframework.stereotype.Service;
import sptech.school.v2.cleanarch.core.application.usecases.query.dashboard.teacher.TeacherDashQueryUseCase;
import sptech.school.v2.cleanarch.core.dtos.out.dashboard.teacher.TeacherDashResponseDTO;

@Service
public class TeacherDashFacade implements TeacherDashFacadeContract {

    private final TeacherDashQueryUseCase teacherDashQueryUseCase;

    public TeacherDashFacade(TeacherDashQueryUseCase teacherDashQueryUseCase) {
        this.teacherDashQueryUseCase = teacherDashQueryUseCase;
    }

    @Override
    public TeacherDashResponseDTO getTeacherDashData() {
        return teacherDashQueryUseCase.getAllDashboardData();
    }
}
