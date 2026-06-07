package sptech.school.v2.cleanarch.core.dtos.out.appointment;

import sptech.school.v2.cleanarch.domain.enumerated.Subject;

import java.time.LocalDateTime;

public record AppointmentResponseDTO(
        Integer id,
        Subject subject,
        String professorName,
        String professorTitle,
        String professorImageUrl,
        String professorPhone,
        LocalDateTime dateTime,
        Double duration,
        String location,
        String status,
        Double totalValue,
        boolean online,
        LocalDateTime createdAt,
        String phase
) {}