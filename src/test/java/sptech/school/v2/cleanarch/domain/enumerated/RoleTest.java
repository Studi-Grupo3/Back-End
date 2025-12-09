package sptech.school.v2.cleanarch.domain.enumerated;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("Role Enum Tests")
class RoleTest {

    @Test
    @DisplayName("Should have TEACHER role")
    void testTeacherRole() {
        Role role = Role.TEACHER;
        assertNotNull(role);
        assertEquals("Teacher", role.getDescription());
    }

    @Test
    @DisplayName("Should have STUDENT role")
    void testStudentRole() {
        Role role = Role.STUDENT;
        assertNotNull(role);
        assertEquals("Student", role.getDescription());
    }

    @Test
    @DisplayName("Should return correct description for TEACHER")
    void testTeacherDescription() {
        assertEquals("Teacher", Role.TEACHER.getDescription());
    }

    @Test
    @DisplayName("Should return correct description for STUDENT")
    void testStudentDescription() {
        assertEquals("Student", Role.STUDENT.getDescription());
    }

    @Test
    @DisplayName("Should have exactly 2 roles")
    void testRoleCount() {
        Role[] roles = Role.values();
        assertEquals(2, roles.length);
    }

    @Test
    @DisplayName("Should get role by name")
    void testValueOf() {
        Role role = Role.valueOf("TEACHER");
        assertEquals(Role.TEACHER, role);
    }
}

