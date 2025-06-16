package sptech.school.domain.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public record AppointmentResponseDTO(
        Integer id,
        List<String> subjects,
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