package sptech.school.v2.cleanarch.core.dtos.out.teacher;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@DisplayName("WeekdayStatsDTO Tests")
class WeekdayStatsDTOTest {

    private WeekdayStatsDTO weekdayStatsDTO;

    @BeforeEach
    void setUp() {
        weekdayStatsDTO = new WeekdayStatsDTO(1, 15L);
    }

    @Test
    @DisplayName("Should create weekday stats DTO with wrapper types")
    void testCreateWeekdayStatsDTO() {
        assertEquals(1, weekdayStatsDTO.getWeekday());
        assertEquals(15L, weekdayStatsDTO.getCount());
    }

    @Test
    @DisplayName("Should create weekday stats DTO with primitive types")
    void testCreateWithPrimitives() {
        WeekdayStatsDTO dto = new WeekdayStatsDTO(2, 20L);
        assertEquals(2, dto.getWeekday());
        assertEquals(20L, dto.getCount());
    }

    @Test
    @DisplayName("Should set and get weekday")
    void testSetGetWeekday() {
        weekdayStatsDTO.setWeekday(3);
        assertEquals(3, weekdayStatsDTO.getWeekday());
    }

    @Test
    @DisplayName("Should set and get count")
    void testSetGetCount() {
        weekdayStatsDTO.setCount(30L);
        assertEquals(30L, weekdayStatsDTO.getCount());
    }

    @Test
    @DisplayName("Should create empty weekday stats DTO")
    void testEmptyConstructor() {
        WeekdayStatsDTO dto = new WeekdayStatsDTO();
        assertNull(dto.getWeekday());
        assertNull(dto.getCount());
    }

    @Test
    @DisplayName("Should create for all weekdays")
    void testAllWeekdays() {
        for (int i = 1; i <= 7; i++) {
            WeekdayStatsDTO dto = new WeekdayStatsDTO(i, (long) i * 10);
            assertEquals(i, dto.getWeekday());
            assertEquals((long) i * 10, dto.getCount());
        }
    }
}

