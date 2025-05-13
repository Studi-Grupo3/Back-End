package sptech.school.adapters.in.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sptech.school.application.service.dashboard.TeacherDashService;
import sptech.school.domain.dto.response.dashboard.teacher.TeacherDashboardDTO;

@RestController
@RequestMapping("/dashboards")
public class DashboardController {

    private final TeacherDashService teacherDashService;

    public DashboardController(TeacherDashService teacherDashService) {
        this.teacherDashService = teacherDashService;
    }

    @GetMapping
    public ResponseEntity<TeacherDashboardDTO> getTeacherDashboard() {
        TeacherDashboardDTO dashboard = teacherDashService.getDashboardData();
        return ResponseEntity.ok(dashboard);
    }
}
