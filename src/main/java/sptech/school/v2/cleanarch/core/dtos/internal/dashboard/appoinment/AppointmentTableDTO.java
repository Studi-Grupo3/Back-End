package sptech.school.v2.cleanarch.core.dtos.internal.dashboard.appoinment;

import java.time.LocalDate;
import java.time.LocalTime;

public record AppointmentTableDTO(
        String studentName,
        String teacherName,
        LocalDate date,
        LocalTime time,
        Double duration,
        String location,
        String status
) { }
