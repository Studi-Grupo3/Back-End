package sptech.school.domain.dto.response;

import sptech.school.v2.cleanarch.domain.enumerated.Subject;

import java.time.LocalDateTime;
import java.util.List;

public record AppointmentResponseDTO(
        Integer id,
        List<Subject> subjects,
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