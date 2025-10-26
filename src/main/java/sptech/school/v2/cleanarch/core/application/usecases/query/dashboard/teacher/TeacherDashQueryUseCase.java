package sptech.school.v2.cleanarch.core.application.usecases.query.dashboard.teacher;

import org.springframework.stereotype.Service;
import sptech.school.v2.cleanarch.core.application.gateways.dashboard.teacher.TeacherDashQueryGateway;
import sptech.school.v2.cleanarch.core.dtos.out.dashboard.teacher.TeacherDashResponseDTO;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.ZoneId;

@Service
public class TeacherDashQueryUseCase {
    private final TeacherDashQueryGateway gateway;

    public TeacherDashQueryUseCase(TeacherDashQueryGateway gateway) {
        this.gateway = gateway;
    }

    public TeacherDashResponseDTO getAllDashboardData() {
        YearMonth now = YearMonth.now(ZoneId.of("America/Sao_Paulo"));
        LocalDateTime start = now.atDay(1).atStartOfDay();
        LocalDateTime end = now.atEndOfMonth().atTime(23, 59, 59);

        return gateway.getTeacherDashData(start, end);
    }
}
