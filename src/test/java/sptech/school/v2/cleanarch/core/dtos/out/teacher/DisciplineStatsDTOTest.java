package sptech.school.v2.cleanarch.core.dtos.out.teacher;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import sptech.school.v2.cleanarch.domain.enumerated.Subject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("DisciplineStatsDTO Tests")
class DisciplineStatsDTOTest {

    private DisciplineStatsDTO disciplineStatsDTO;

    @BeforeEach
    void setUp() {
        disciplineStatsDTO = new DisciplineStatsDTO(Subject.MATHEMATICS, 10L);
    }

    @Test
    @DisplayName("Should create discipline stats DTO")
    void testCreateDisciplineStatsDTO() {
        assertNotNull(disciplineStatsDTO);
        assertEquals(Subject.MATHEMATICS, disciplineStatsDTO.getSubject());
        assertEquals(10L, disciplineStatsDTO.getCount());
    }

    @Test
    @DisplayName("Should set and get subject")
    void testSetGetSubject() {
        disciplineStatsDTO.setSubject(Subject.PHYSICS);
        assertEquals(Subject.PHYSICS, disciplineStatsDTO.getSubject());
    }

    @Test
    @DisplayName("Should set and get count")
    void testSetGetCount() {
        disciplineStatsDTO.setCount(25L);
        assertEquals(25L, disciplineStatsDTO.getCount());
    }

    @Test
    @DisplayName("Should create with different subjects")
    void testDifferentSubjects() {
        DisciplineStatsDTO english = new DisciplineStatsDTO(Subject.ENGLISH, 5L);
        DisciplineStatsDTO portuguese = new DisciplineStatsDTO(Subject.PORTUGUESE, 8L);

        assertEquals(Subject.ENGLISH, english.getSubject());
        assertEquals(Subject.PORTUGUESE, portuguese.getSubject());
        assertEquals(5L, english.getCount());
        assertEquals(8L, portuguese.getCount());
    }

    @Test
    @DisplayName("Should handle zero count")
    void testZeroCount() {
        DisciplineStatsDTO dto = new DisciplineStatsDTO(Subject.HISTORY, 0L);
        assertEquals(0L, dto.getCount());
    }
}

