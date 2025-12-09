package sptech.school.v2.cleanarch.infra.persistence.adapter.content;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sptech.school.v2.cleanarch.domain.entities.Content;
import sptech.school.v2.cleanarch.domain.entities.Student;
import sptech.school.v2.cleanarch.infra.persistence.repository.ContentJpaRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("ContentJpaAdapter Tests")
@ExtendWith(MockitoExtension.class)
class ContentJpaAdapterTest {

    private ContentJpaAdapter contentJpaAdapter;

    @Mock
    private ContentJpaRepository contentJpaRepository;

    private Content content;
    private Student student;

    @BeforeEach
    void setUp() {
        contentJpaAdapter = new ContentJpaAdapter(contentJpaRepository);

        student = new Student("João", "joao@email.com", "12345678901", "password");
        student.setId(1);

        content = new Content("material.pdf", "application/pdf", "/files/material.pdf", 2048L);
        content.setId(1L);
        content.setStudent(student);
    }

    @Test
    @DisplayName("Should save content")
    void testSaveContent() {
        Content newContent = new Content("exercise.pdf", "application/pdf", "/files/exercise.pdf", 1024L);
        newContent.setStudent(student);

        when(contentJpaRepository.save(any(Content.class))).thenReturn(newContent);

        Content result = contentJpaAdapter.save(newContent);

        assertNotNull(result);
        assertEquals("exercise.pdf", result.getFileName());
        assertNull(newContent.getId());
        verify(contentJpaRepository, times(1)).save(any(Content.class));
    }

    @Test
    @DisplayName("Should set ID to null before saving")
    void testSaveContentSetsIdToNull() {
        Content newContent = new Content("test.pdf", "application/pdf", "/files/test.pdf", 512L);
        newContent.setId(999L);
        newContent.setStudent(student);

        when(contentJpaRepository.save(newContent)).thenReturn(newContent);

        contentJpaAdapter.save(newContent);

        assertNull(newContent.getId());
    }

    @Test
    @DisplayName("Should find content by ID")
    void testFindById() {
        when(contentJpaRepository.findById(1L)).thenReturn(Optional.of(content));

        Optional<Content> result = contentJpaAdapter.findById(1L);

        assertTrue(result.isPresent());
        assertEquals("material.pdf", result.get().getFileName());
        verify(contentJpaRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Should return empty optional when content not found by ID")
    void testFindByIdNotFound() {
        when(contentJpaRepository.findById(999L)).thenReturn(Optional.empty());

        Optional<Content> result = contentJpaAdapter.findById(999L);

        assertFalse(result.isPresent());
        verify(contentJpaRepository, times(1)).findById(999L);
    }

    @Test
    @DisplayName("Should save content with different file types")
    void testSaveContentDifferentFileTypes() {
        Content imageContent = new Content("image.jpg", "image/jpeg", "/files/image.jpg", 4096L);
        imageContent.setStudent(student);

        when(contentJpaRepository.save(any(Content.class))).thenReturn(imageContent);

        Content result = contentJpaAdapter.save(imageContent);

        assertNotNull(result);
        assertEquals("image/jpeg", result.getFileType());
    }

    @Test
    @DisplayName("Should handle null content in save")
    @SuppressWarnings("ConstantConditions")
    void testSaveNullContent() {
        assertThrows(IllegalArgumentException.class, () -> contentJpaAdapter.save(null));
    }

    @Test
    @DisplayName("Should find multiple contents")
    void testFindMultipleContents() {
        Content content1 = new Content("file1.pdf", "application/pdf", "/files/file1.pdf", 1024L);
        content1.setId(1L);

        Content content2 = new Content("file2.pdf", "application/pdf", "/files/file2.pdf", 2048L);
        content2.setId(2L);

        when(contentJpaRepository.findById(1L)).thenReturn(Optional.of(content1));
        when(contentJpaRepository.findById(2L)).thenReturn(Optional.of(content2));

        Optional<Content> result1 = contentJpaAdapter.findById(1L);
        Optional<Content> result2 = contentJpaAdapter.findById(2L);

        assertTrue(result1.isPresent());
        assertTrue(result2.isPresent());
        assertEquals("file1.pdf", result1.get().getFileName());
        assertEquals("file2.pdf", result2.get().getFileName());
    }
}

