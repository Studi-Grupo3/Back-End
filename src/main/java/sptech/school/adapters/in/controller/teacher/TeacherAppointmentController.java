package sptech.school.adapters.in.controller.teacher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.application.service.teacher.TeacherAppointmentService;
import sptech.school.domain.dto.response.teacher.UpcomingLessonDTO;
import sptech.school.domain.dto.response.teacher.LessonHistoryDTO;
import sptech.school.domain.dto.response.teacher.TeacherStatsDTO;

import java.util.List;

@RestController
@RequestMapping("/teachers")
public class TeacherAppointmentController {

    @Autowired
    private TeacherAppointmentService teacherAppointmentService;

    @GetMapping("/{teacherId}/proximas-aulas")
    public ResponseEntity<List<UpcomingLessonDTO>> getUpcomingLessons(
            @PathVariable("teacherId") Integer teacherId) {
        List<UpcomingLessonDTO> dtos = teacherAppointmentService.getUpcomingLessonsForTeacher(teacherId);
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{teacherId}/lessons-history")
    public ResponseEntity<List<LessonHistoryDTO>> getLessonsHistory(
            @PathVariable("teacherId") Integer teacherId) {
        List<LessonHistoryDTO> dtos = teacherAppointmentService.getLessonsHistoryForTeacher(teacherId);
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{teacherId}/stats")
    public ResponseEntity<TeacherStatsDTO> getStats(
            @PathVariable("teacherId") Integer teacherId) {
        TeacherStatsDTO stats = teacherAppointmentService.getStatsForTeacher(teacherId);
        return ResponseEntity.ok(stats);
    }

}
