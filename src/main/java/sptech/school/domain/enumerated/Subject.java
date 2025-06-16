package sptech.school.domain.enumerated;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Subject {
    PORTUGUESE("Português"),
    MATHEMATICS("Matemática"),
    GEOGRAPHY("Geografia"),
    HISTORY("História"),
    SOCIOLOGY("Sociologia"),
    PHILOSOPHY("Filosofia"),
    ART("Arte"),
    ENGLISH("Inglês"),
    SPANISH("Espanhol"),
    SCIENCE("Ciências"),
    BIOLOGY("Biologia"),
    CHEMISTRY("Química"),
    PHYSICS("Física"),
    LITERACY("Alfabetização");

    private final String description;

    Subject(String description) {
        this.description = description;
    }

    @JsonValue
    public String getDescription() {
        return description;
    }

    @JsonCreator
    public static Subject fromDescription(String description) {
        for (Subject subject : Subject.values()) {
            if (subject.getDescription().equalsIgnoreCase(description)) {
                return subject;
            }
        }
        throw new IllegalArgumentException("Descrição inválida: " + description);
    }

}