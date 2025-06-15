package sptech.school.application.mappers.utils;

import org.springframework.stereotype.Component;

@Component
public class StringMapperUtil {
    public String mapString(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
}
