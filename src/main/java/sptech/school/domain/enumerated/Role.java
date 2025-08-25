package sptech.school.domain.enumerated;

public enum Role {
    TEACHER("Teacher"),
    STUDENT("Student");

    private final String description;

    Role(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
