package sptech.school.v2.cleanarch.core.dtos.in.appointment;

import java.time.LocalDateTime;

public record AppointmentRequestDTO(
        Integer idStudent,
        Integer idTeacher,
        LocalDateTime dateTime,
        Double lessonDuration,
        String location,
        Double totalValue,
        String status,
        String paymentStatus
) {}
