//package sptech.school.adapters.in.controller.teacher;
//
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import sptech.school.application.service.teacher.TeacherDashboardService;
//import sptech.school.domain.dto.response.teacher.TeacherDashboardDTO;
//
//@RestController
//@RequestMapping("/teachers/{teacherId}/dashboard")
//public class TeacherDashboardController {
//
//    private final TeacherDashboardService dashboardService;
//
//    public TeacherDashboardController(TeacherDashboardService dashboardService) {
//        this.dashboardService = dashboardService;
//    }
//
//    @GetMapping
//    public TeacherDashboardDTO dashboard(@PathVariable Integer teacherId) {
//        return dashboardService.getDashboard(teacherId);
//    }
//}
