package sptech.school.v2.cleanarch.domain.enumerated;

public enum Role {
    TEACHER("Teacher"),
    STUDENT("Student"),
    ADMIN("Admin");

    private final String description;

    Role(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
