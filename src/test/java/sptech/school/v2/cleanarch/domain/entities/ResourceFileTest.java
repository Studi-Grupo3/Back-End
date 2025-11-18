package sptech.school.v2.cleanarch.domain.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ResourceFile Entity Tests")
class ResourceFileTest {

    private ResourceFile resourceFile;

    @BeforeEach
    void setUp() {
        resourceFile = new ResourceFile();
    }

    @Test
    @DisplayName("Should create resource file with all parameters")
    void testResourceFileConstructor() {
        ResourceFile file = new ResourceFile("document.pdf", "application/pdf", "/files/document.pdf", 2048L);

        assertEquals("document.pdf", file.getFileName());
        assertEquals("application/pdf", file.getFileType());
        assertEquals("/files/document.pdf", file.getFileLocation());
        assertEquals(2048L, file.getFileSize());
    }

    @Test
    @DisplayName("Should set and get ID")
    void testSetGetId() {
        resourceFile.setId(1L);
        assertEquals(1L, resourceFile.getId());
    }

    @Test
    @DisplayName("Should set and get file name")
    void testSetGetFileName() {
        resourceFile.setFileName("image.jpg");
        assertEquals("image.jpg", resourceFile.getFileName());
    }

    @Test
    @DisplayName("Should set and get file type")
    void testSetGetFileType() {
        resourceFile.setFileType("image/jpeg");
        assertEquals("image/jpeg", resourceFile.getFileType());
    }

    @Test
    @DisplayName("Should set and get file location")
    void testSetGetFileLocation() {
        resourceFile.setFileLocation("/uploads/image.jpg");
        assertEquals("/uploads/image.jpg", resourceFile.getFileLocation());
    }

    @Test
    @DisplayName("Should set and get file size")
    void testSetGetFileSize() {
        resourceFile.setFileSize(5120L);
        assertEquals(5120L, resourceFile.getFileSize());
    }

    @Test
    @DisplayName("Should set and get input stream")
    void testSetGetInputStream() {
        byte[] data = "test data".getBytes();
        InputStream inputStream = new ByteArrayInputStream(data);
        resourceFile.setInputStream(inputStream);

        assertNotNull(resourceFile.getInputStream());
        assertEquals(inputStream, resourceFile.getInputStream());
    }

    @Test
    @DisplayName("Should create empty resource file")
    void testEmptyConstructor() {
        ResourceFile file = new ResourceFile();
        assertNull(file.getId());
        assertNull(file.getFileName());
    }

    @Test
    @DisplayName("Should set multiple fields")
    void testSetMultipleFields() {
        resourceFile.setId(2L);
        resourceFile.setFileName("video.mp4");
        resourceFile.setFileType("video/mp4");
        resourceFile.setFileLocation("/videos/video.mp4");
        resourceFile.setFileSize(10240L);

        assertEquals(2L, resourceFile.getId());
        assertEquals("video.mp4", resourceFile.getFileName());
        assertEquals("video/mp4", resourceFile.getFileType());
        assertEquals("/videos/video.mp4", resourceFile.getFileLocation());
        assertEquals(10240L, resourceFile.getFileSize());
    }
}

