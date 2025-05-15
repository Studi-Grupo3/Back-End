package sptech.school;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import sptech.school.adapters.out.persistence.AppointmentRepository;
import sptech.school.adapters.out.persistence.TeacherRepositoryJpa;
import sptech.school.application.service.dashboard.OverviewDashService;
import sptech.school.domain.dto.response.dashboard.overview.OverviewDashDTO;
import sptech.school.domain.entity.Appointment;
import sptech.school.domain.entity.Teacher;
import sptech.school.domain.enumerated.AppointmentStatus;
import sptech.school.domain.enumerated.PaymentStatus;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OverviewDashServiceTest {

    private AppointmentRepository appointmentRepository;
    private TeacherRepositoryJpa teacherRepository;
    private OverviewDashService service;

    @BeforeEach
    void setUp() {
        appointmentRepository = mock(AppointmentRepository.class);
        teacherRepository = mock(TeacherRepositoryJpa.class);
        service = new OverviewDashService(appointmentRepository, teacherRepository);
    }

    @Test
    void testGetDashboardData_ReturnsCorrectOverview() {
        // Arrange
        Teacher teacher1 = new Teacher();
        teacher1.setName("Prof. João");

        Teacher teacher2 = new Teacher();
        teacher2.setName("Prof. Ana");

        Appointment a1 = new Appointment();
        a1.setTeacher(teacher1);
        a1.setDateTime(LocalDateTime.of(2025, 5, 10, 14, 0));
        a1.setPaymentStatus(PaymentStatus.PAID);
        a1.setStatus(AppointmentStatus.SCHEDULED);
        a1.setLessonDuration(1.5);
        a1.setTotalValue(100.0);

        Appointment a2 = new Appointment();
        a2.setTeacher(teacher2);
        a2.setDateTime(LocalDateTime.of(2025, 5, 11, 16, 0));
        a2.setPaymentStatus(PaymentStatus.PENDING);
        a2.setStatus(AppointmentStatus.SCHEDULED);
        a2.setLessonDuration(2.0);
        a2.setTotalValue(150.0);

        Appointment a3 = new Appointment();
        a3.setTeacher(teacher1);
        a3.setDateTime(LocalDateTime.of(2025, 4, 5, 10, 0));
        a3.setPaymentStatus(PaymentStatus.PAID);
        a3.setStatus(AppointmentStatus.SCHEDULED);
        a3.setLessonDuration(1.0);
        a3.setTotalValue(120.0);

        List<Appointment> mockAppointments = Arrays.asList(a1, a2, a3);

        when(appointmentRepository.findAll()).thenReturn(mockAppointments);
        when(teacherRepository.count()).thenReturn(2L);

        // Act
        OverviewDashDTO result = service.getDashboardData();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.getStats().getTotalTeachers());
        assertEquals(3, result.getStats().getTotalAppointments());
        assertEquals(220.0, result.getStats().getTotalRevenue(), 0.001);
        assertEquals(4.5, result.getStats().getTotalHours(), 0.001);

        assertEquals(2, result.getMonthlyRevenue().size());
        assertTrue(result.getMonthlyRevenue().stream().anyMatch(m -> m.getMonth().equals("MAY")));
        assertTrue(result.getMonthlyRevenue().stream().anyMatch(m -> m.getMonth().equals("APRIL")));

        assertEquals(2, result.getRecentPayments().size());
        assertEquals("Prof. João", result.getRecentPayments().get(0).getProfessor());
        assertEquals("Prof. João", result.getRecentPayments().get(1).getProfessor());

        assertEquals(2, result.getLessonsPerDay().size());
        assertTrue(result.getLessonsPerDay().stream().anyMatch(d -> d.getLabel().equals(DayOfWeek.SATURDAY.toString())));
    }
}
