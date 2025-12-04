package sptech.school.v2.cleanarch.domain.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@DisplayName("Content Entity Tests")
class ContentTest {

    private Content content;
    private Student student;

    @BeforeEach
    void setUp() {
        content = new Content();
        student = new Student("João", "joao@email.com", "12345678901", "password");
        student.setId(1);
    }

    @Test
    @DisplayName("Should create content with all parameters")
    void testContentConstructor() {
        Content c = new Content("material.pdf", "application/pdf", "/files/material.pdf", 2048L);

        assertEquals("material.pdf", c.getFileName());
        assertEquals("application/pdf", c.getFileType());
        assertEquals("/files/material.pdf", c.getFileLocation());
        assertEquals(2048L, c.getFileSize());
    }

    @Test
    @DisplayName("Should set and get student")
    void testSetGetStudent() {
        content.setStudent(student);
        assertEquals(student, content.getStudent());
    }

    @Test
    @DisplayName("Should set and get file name")
    void testSetGetFileName() {
        content.setFileName("exercise.pdf");
        assertEquals("exercise.pdf", content.getFileName());
    }

    @Test
    @DisplayName("Should set and get file type")
    void testSetGetFileType() {
        content.setFileType("application/pdf");
        assertEquals("application/pdf", content.getFileType());
    }

    @Test
    @DisplayName("Should set and get file location")
    void testSetGetFileLocation() {
        content.setFileLocation("/content/exercise.pdf");
        assertEquals("/content/exercise.pdf", content.getFileLocation());
    }

    @Test
    @DisplayName("Should set and get file size")
    void testSetGetFileSize() {
        content.setFileSize(3096L);
        assertEquals(3096L, content.getFileSize());
    }

    @Test
    @DisplayName("Should create empty content")
    void testEmptyConstructor() {
        Content emptyContent = new Content();
        assertNull(emptyContent.getId());
        assertNull(emptyContent.getStudent());
    }
}

