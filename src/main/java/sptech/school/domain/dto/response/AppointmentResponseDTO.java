package sptech.school.domain.dto.response;

import java.time.LocalDateTime;

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