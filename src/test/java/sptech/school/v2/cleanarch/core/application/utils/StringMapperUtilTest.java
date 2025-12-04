package sptech.school.v2.cleanarch.core.application.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("StringMapperUtil Tests")
class StringMapperUtilTest {

    private StringMapperUtil stringMapperUtil;

    @BeforeEach
    void setUp() {
        stringMapperUtil = new StringMapperUtil();
    }

    @Test
    @DisplayName("Should return null when value is null")
    void testMapStringWithNullValue() {
        String result = stringMapperUtil.mapString(null);
        assertNull(result);
    }

    @Test
    @DisplayName("Should return null when value is blank")
    void testMapStringWithBlankValue() {
        String result = stringMapperUtil.mapString("   ");
        assertNull(result);
    }

    @Test
    @DisplayName("Should return null when value is empty")
    void testMapStringWithEmptyValue() {
        String result = stringMapperUtil.mapString("");
        assertNull(result);
    }

    @Test
    @DisplayName("Should return trimmed value when value has leading and trailing spaces")
    void testMapStringWithSpaces() {
        String result = stringMapperUtil.mapString("  hello world  ");
        assertEquals("hello world", result);
    }

    @Test
    @DisplayName("Should return same value when value has no spaces")
    void testMapStringWithoutSpaces() {
        String result = stringMapperUtil.mapString("helloworld");
        assertEquals("helloworld", result);
    }

    @Test
    @DisplayName("Should return trimmed value with only internal spaces")
    void testMapStringWithInternalSpaces() {
        String result = stringMapperUtil.mapString("hello    world");
        assertEquals("hello    world", result);
    }

    @Test
    @DisplayName("Should return null when trimmed value becomes blank")
    void testMapStringTrimmedToBlank() {
        String result = stringMapperUtil.mapString("\t\n");
        assertNull(result);
    }
}

