package sptech.school.v2.cleanarch.core.dtos.in.teacher;

public record TeacherAvailabilitySlotDTO(
        String dayOfWeek,
        String startTime,
        String endTime
) {
}
