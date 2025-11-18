package sptech.school.v2.cleanarch.core.application.mappers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("ResourceFileMapper Tests")
class ResourceFileMapperTest {

    // Método auxiliar para normalizar o resultado (remover vírgula e substituir por ponto)
    private boolean compareFileSize(String result, String expectedWithDot) {
        String normalized = result.replace(",", ".");
        return normalized.equals(expectedWithDot);
    }

    @Test
    @DisplayName("Should format size 0 bytes")
    void testFormatSizeZero() {
        String result = ResourceFileMapper.formatSize(0L);
        assertTrue(compareFileSize(result, "0.0 B"),
            "Expected '0.0 B' but got: " + result);
    }

    @Test
    @DisplayName("Should format size null")
    void testFormatSizeNull() {
        String result = ResourceFileMapper.formatSize(null);
        assertEquals("0 B", result);
    }

    @Test
    @DisplayName("Should format size in bytes")
    void testFormatSizeBytes() {
        String result = ResourceFileMapper.formatSize(512L);
        assertTrue(compareFileSize(result, "512.0 B"),
            "Expected '512.0 B' but got: " + result);
    }

    @Test
    @DisplayName("Should format size in kilobytes")
    void testFormatSizeKilobytes() {
        String result = ResourceFileMapper.formatSize(2048L);
        assertTrue(compareFileSize(result, "2.0 KB"),
            "Expected '2.0 KB' but got: " + result);
    }

    @Test
    @DisplayName("Should format size in megabytes")
    void testFormatSizeMegabytes() {
        String result = ResourceFileMapper.formatSize(1048576L);
        assertTrue(compareFileSize(result, "1.0 MB"),
            "Expected '1.0 MB' but got: " + result);
    }

    @Test
    @DisplayName("Should format size in gigabytes")
    void testFormatSizeGigabytes() {
        String result = ResourceFileMapper.formatSize(1073741824L);
        assertTrue(compareFileSize(result, "1.0 GB"),
            "Expected '1.0 GB' but got: " + result);
    }

    @Test
    @DisplayName("Should format size with decimal")
    void testFormatSizeDecimal() {
        String result = ResourceFileMapper.formatSize(1536L);
        assertTrue(compareFileSize(result, "1.5 KB"),
            "Expected '1.5 KB' but got: " + result);
    }

    @Test
    @DisplayName("Should format large size")
    void testFormatSizeLarge() {
        String result = ResourceFileMapper.formatSize(5368709120L);
        assertTrue(compareFileSize(result, "5.0 GB"),
            "Expected '5.0 GB' but got: " + result);
    }
}

