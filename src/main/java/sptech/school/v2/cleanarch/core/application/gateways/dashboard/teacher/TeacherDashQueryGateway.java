package sptech.school.v2.cleanarch.core.application.gateways.dashboard.teacher;

import sptech.school.v2.cleanarch.core.dtos.out.dashboard.teacher.TeacherDashResponseDTO;

import java.time.LocalDateTime;

public interface TeacherDashQueryGateway {
    TeacherDashResponseDTO getTeacherDashData(LocalDateTime start, LocalDateTime end);
}
