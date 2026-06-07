package sptech.school.v2.cleanarch.core.dtos.out.teacher;

public record TeacherAvailabilityResponseDTO(
        Integer id,
        String dayOfWeek,
        String startTime,
        String endTime
) {
}
