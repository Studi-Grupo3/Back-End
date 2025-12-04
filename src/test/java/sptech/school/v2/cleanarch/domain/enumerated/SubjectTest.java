package sptech.school.v2.cleanarch.domain.enumerated;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("Subject Enum Tests")
class SubjectTest {

    @Test
    @DisplayName("Should have PORTUGUESE subject")
    void testPortugueseSubject() {
        Subject subject = Subject.PORTUGUESE;
        assertNotNull(subject);
        assertEquals("Português", subject.getDescription());
    }

    @Test
    @DisplayName("Should have MATHEMATICS subject")
    void testMathematicsSubject() {
        Subject subject = Subject.MATHEMATICS;
        assertNotNull(subject);
        assertEquals("Matemática", subject.getDescription());
    }

    @Test
    @DisplayName("Should have ENGLISH subject")
    void testEnglishSubject() {
        Subject subject = Subject.ENGLISH;
        assertNotNull(subject);
        assertEquals("Inglês", subject.getDescription());
    }

    @Test
    @DisplayName("Should have BIOLOGY subject")
    void testBiologySubject() {
        Subject subject = Subject.BIOLOGY;
        assertNotNull(subject);
        assertEquals("Biologia", subject.getDescription());
    }

    @Test
    @DisplayName("Should have exactly 14 subjects")
    void testSubjectCount() {
        Subject[] subjects = Subject.values();
        assertEquals(14, subjects.length);
    }

    @Test
    @DisplayName("Should get subject by name")
    void testValueOf() {
        Subject subject = Subject.valueOf("MATHEMATICS");
        assertEquals(Subject.MATHEMATICS, subject);
    }

    @Test
    @DisplayName("Should return correct description for GEOGRAPHY")
    void testGeographyDescription() {
        assertEquals("Geografia", Subject.GEOGRAPHY.getDescription());
    }

    @Test
    @DisplayName("Should return correct description for PHYSICS")
    void testPhysicsDescription() {
        assertEquals("Física", Subject.PHYSICS.getDescription());
    }

    @Test
    @DisplayName("Should return correct description for LITERACY")
    void testLiteracyDescription() {
        assertEquals("Alfabetização", Subject.LITERACY.getDescription());
    }
}

