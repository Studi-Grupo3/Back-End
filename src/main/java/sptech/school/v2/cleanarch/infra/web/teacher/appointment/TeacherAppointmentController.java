package sptech.school.v2.cleanarch.infra.web.teacher.appointment;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sptech.school.v2.cleanarch.core.application.facades.teacher.appointment.TeacherAppointmentFacadeContract;
import sptech.school.v2.cleanarch.core.dtos.out.teacher.LessonHistoryDTO;
import sptech.school.v2.cleanarch.core.dtos.out.teacher.TeacherStatsDTO;
import sptech.school.v2.cleanarch.core.dtos.out.teacher.UpcomingLessonDTO;

import java.util.List;

@RestController
@RequestMapping("/teachers")
public class TeacherAppointmentController {

    private final TeacherAppointmentFacadeContract facade;

    public TeacherAppointmentController(TeacherAppointmentFacadeContract facade) {
        this.facade = facade;
    }

    @GetMapping("/{teacherId}/lessons/upcoming")
    @Operation(summary = "Lista as próximas aulas do professor")
    public ResponseEntity<List<UpcomingLessonDTO>> getUpcomingLessons(@PathVariable("teacherId") Integer teacherId) {
        List<UpcomingLessonDTO> dtos = facade.getUpcomingLessons(teacherId);
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{teacherId}/lessons-history")
    @Operation(summary = "Lista o histórico de aulas do professor")
    public ResponseEntity<List<LessonHistoryDTO>> getLessonsHistory(@PathVariable("teacherId") Integer teacherId) {
        List<LessonHistoryDTO> dtos = facade.getLessonsHistory(teacherId);
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{teacherId}/stats")
    @Operation(summary = "Estatísticas resumidas do professor")
    public ResponseEntity<TeacherStatsDTO> getStats(@PathVariable("teacherId") Integer teacherId) {
        TeacherStatsDTO stats = facade.getStats(teacherId);
        return ResponseEntity.ok(stats);
    }
}