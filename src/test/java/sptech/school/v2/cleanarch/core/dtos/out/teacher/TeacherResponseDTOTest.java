package sptech.school.v2.cleanarch.core.dtos.out.teacher;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import sptech.school.v2.cleanarch.domain.enumerated.Subject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("TeacherResponseDTO Tests")
class TeacherResponseDTOTest {

    @Test
    @DisplayName("Should create teacher response DTO")
    void testCreateTeacherResponseDTO() {
        List<Subject> subjects = new ArrayList<>();
        subjects.add(Subject.MATHEMATICS);

        LocalDate birthDate = LocalDate.of(1985, 5, 15);

        TeacherResponseDTO dto = new TeacherResponseDTO(
            1,
            "Prof. Silva",
            "silva@email.com",
            "12345678901",
            subjects,
            "11987654321",
            birthDate,
            "Experienced teacher",
            "10",
            "Licenciatura em Matemática",
            "150.00",
            null,
            null
        );

        assertNotNull(dto);
        assertEquals(1, dto.id());
        assertEquals("Prof. Silva", dto.name());
        assertEquals("silva@email.com", dto.email());
        assertEquals("11987654321", dto.cellphoneNumber());
    }

    @Test
    @DisplayName("Should access all teacher response fields")
    void testAccessAllFields() {
        List<Subject> subjects = new ArrayList<>();
        subjects.add(Subject.PHYSICS);
        subjects.add(Subject.ENGLISH);

        TeacherResponseDTO dto = new TeacherResponseDTO(
            2,
            "Prof. João",
            "joao@email.com",
            "98765432101",
            subjects,
            "11988776655",
            LocalDate.of(1990, 3, 20),
            "Physics expert",
            "5",
            "Mestrado em Física",
            "200.00",
            "image/jpeg",
            new byte[]{1, 2, 3}
        );

        assertNotNull(dto.id());
        assertNotNull(dto.name());
        assertNotNull(dto.email());
        assertNotNull(dto.subjects());
        assertNotNull(dto.dateBirth());
        assertEquals(2, dto.subjects().size());
    }
}

