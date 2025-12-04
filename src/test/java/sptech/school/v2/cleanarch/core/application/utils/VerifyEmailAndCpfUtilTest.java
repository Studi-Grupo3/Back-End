package sptech.school.v2.cleanarch.core.application.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sptech.school.v2.cleanarch.core.application.gateways.student.StudentQueryGateway;
import sptech.school.v2.cleanarch.core.application.gateways.teacher.TeacherQueryGateway;
import sptech.school.v2.cleanarch.domain.entities.User;
import sptech.school.v2.cleanarch.domain.exception.CpfAlreadyExistsException;
import sptech.school.v2.cleanarch.domain.exception.EmailAlreadyExistsException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("VerifyEmailAndCpfUtil Tests")
@ExtendWith(MockitoExtension.class)
class VerifyEmailAndCpfUtilTest {

    private VerifyEmailAndCpfUtil verifyEmailAndCpfUtil;

    @Mock
    private TeacherQueryGateway teacherQueryGateway;

    @Mock
    private StudentQueryGateway studentQueryGateway;

    @BeforeEach
    void setUp() {
        verifyEmailAndCpfUtil = new VerifyEmailAndCpfUtil(teacherQueryGateway, studentQueryGateway);
    }

    @Test
    @DisplayName("Should throw exception when user is null")
    void testVerifyWithNullUser() {
        assertThrows(NullPointerException.class, () -> {
            verifyEmailAndCpfUtil.verify(null);
        });
    }

    @Test
    @DisplayName("Should throw EmailAlreadyExistsException when email exists in student")
    void testVerifyEmailExistsInStudent() {
        User user = new User("John", "john@email.com", "12345678901", "password") {};
        when(studentQueryGateway.findIdByEmail("john@email.com")).thenReturn(Optional.of(1));
        when(teacherQueryGateway.findIdByEmail("john@email.com")).thenReturn(Optional.empty());

        assertThrows(EmailAlreadyExistsException.class, () -> {
            verifyEmailAndCpfUtil.verify(user);
        });
    }

    @Test
    @DisplayName("Should throw EmailAlreadyExistsException when email exists in teacher")
    void testVerifyEmailExistsInTeacher() {
        User user = new User("John", "john@email.com", "12345678901", "password") {};
        when(studentQueryGateway.findIdByEmail("john@email.com")).thenReturn(Optional.empty());
        when(teacherQueryGateway.findIdByEmail("john@email.com")).thenReturn(Optional.of(1));

        assertThrows(EmailAlreadyExistsException.class, () -> {
            verifyEmailAndCpfUtil.verify(user);
        });
    }

    @Test
    @DisplayName("Should throw CpfAlreadyExistsException when CPF exists in student")
    void testVerifyCpfExistsInStudent() {
        User user = new User("John", "john@email.com", "12345678901", "password") {};
        when(studentQueryGateway.findIdByEmail("john@email.com")).thenReturn(Optional.empty());
        when(teacherQueryGateway.findIdByEmail("john@email.com")).thenReturn(Optional.empty());
        when(studentQueryGateway.findByCpf("12345678901")).thenReturn(Optional.of(1));
        when(teacherQueryGateway.findByCpf("12345678901")).thenReturn(Optional.empty());

        assertThrows(CpfAlreadyExistsException.class, () -> {
            verifyEmailAndCpfUtil.verify(user);
        });
    }

    @Test
    @DisplayName("Should throw CpfAlreadyExistsException when CPF exists in teacher")
    void testVerifyCpfExistsInTeacher() {
        User user = new User("John", "john@email.com", "12345678901", "password") {};
        when(studentQueryGateway.findIdByEmail("john@email.com")).thenReturn(Optional.empty());
        when(teacherQueryGateway.findIdByEmail("john@email.com")).thenReturn(Optional.empty());
        when(studentQueryGateway.findByCpf("12345678901")).thenReturn(Optional.empty());
        when(teacherQueryGateway.findByCpf("12345678901")).thenReturn(Optional.of(1));

        assertThrows(CpfAlreadyExistsException.class, () -> {
            verifyEmailAndCpfUtil.verify(user);
        });
    }

    @Test
    @DisplayName("Should not throw exception when email and CPF are unique")
    void testVerifyWithUniqueEmailAndCpf() {
        User user = new User("John", "john@email.com", "12345678901", "password") {};
        when(studentQueryGateway.findIdByEmail("john@email.com")).thenReturn(Optional.empty());
        when(teacherQueryGateway.findIdByEmail("john@email.com")).thenReturn(Optional.empty());
        when(studentQueryGateway.findByCpf("12345678901")).thenReturn(Optional.empty());
        when(teacherQueryGateway.findByCpf("12345678901")).thenReturn(Optional.empty());

        assertDoesNotThrow(() -> {
            verifyEmailAndCpfUtil.verify(user);
        });
    }

    @Test
    @DisplayName("Should skip email verification when email is null")
    void testVerifyWithNullEmail() {
        User user = new User("John", null, "12345678901", "password") {};
        when(studentQueryGateway.findByCpf("12345678901")).thenReturn(Optional.empty());
        when(teacherQueryGateway.findByCpf("12345678901")).thenReturn(Optional.empty());

        assertDoesNotThrow(() -> {
            verifyEmailAndCpfUtil.verify(user);
        });
        verify(studentQueryGateway, never()).findIdByEmail(any());
    }

    @Test
    @DisplayName("Should skip CPF verification when CPF is null")
    void testVerifyWithNullCpf() {
        User user = new User("John", "john@email.com", null, "password") {};
        when(studentQueryGateway.findIdByEmail("john@email.com")).thenReturn(Optional.empty());
        when(teacherQueryGateway.findIdByEmail("john@email.com")).thenReturn(Optional.empty());

        assertDoesNotThrow(() -> {
            verifyEmailAndCpfUtil.verify(user);
        });
        verify(studentQueryGateway, never()).findByCpf(any());
    }

    @Test
    @DisplayName("Should allow same email when updating with same user ID")
    void testVerifyWithIdAllowsSameEmail() {
        User user = new User("John", "john@email.com", null, "password") {};
        when(studentQueryGateway.findIdByEmail("john@email.com")).thenReturn(Optional.of(1));
        when(teacherQueryGateway.findIdByEmail("john@email.com")).thenReturn(Optional.empty());

        assertDoesNotThrow(() -> {
            verifyEmailAndCpfUtil.verify(user, 1);
        });
    }

    @Test
    @DisplayName("Should throw exception when updating with different email from different user")
    void testVerifyWithIdThrowsExceptionForDifferentUser() {
        User user = new User("John", "john@email.com", null, "password") {};
        when(studentQueryGateway.findIdByEmail("john@email.com")).thenReturn(Optional.of(2));
        when(teacherQueryGateway.findIdByEmail("john@email.com")).thenReturn(Optional.empty());

        assertThrows(EmailAlreadyExistsException.class, () -> {
            verifyEmailAndCpfUtil.verify(user, 1);
        });
    }

    @Test
    @DisplayName("Should allow same CPF when updating with same user ID")
    void testVerifyWithIdAllowsSameCpf() {
        User user = new User("John", null, "12345678901", "password") {};
        when(studentQueryGateway.findByCpf("12345678901")).thenReturn(Optional.of(1));
        when(teacherQueryGateway.findByCpf("12345678901")).thenReturn(Optional.empty());

        assertDoesNotThrow(() -> {
            verifyEmailAndCpfUtil.verify(user, 1);
        });
    }
}

