package sptech.school.v2.cleanarch.core.dtos.out;

import sptech.school.v2.cleanarch.domain.enumerated.Subject;

import java.time.LocalDateTime;
import java.util.List;

public record AppointmentResponseDTO(
        Integer id,
        String subject,
        String professorName,
        String professorTitle,
        String professorImageUrl,
        LocalDateTime dateTime,
        Double duration,
        String location,
        String status,
        Double totalValue,
        boolean online
) {}