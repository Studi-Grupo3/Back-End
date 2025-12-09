//package sptech.school.v2.cleanarch.infra.persistence.command.appointment;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import sptech.school.v2.cleanarch.domain.entities.Appointment;
//import sptech.school.v2.cleanarch.domain.entities.Student;
//import sptech.school.v2.cleanarch.domain.entities.Teacher;
//import sptech.school.v2.cleanarch.domain.enumerated.AppointmentStatus;
//import sptech.school.v2.cleanarch.domain.enumerated.PaymentStatus;
//import sptech.school.v2.cleanarch.infra.persistence.repository.appointment.AppointmentJpaRepository;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@DisplayName("AppointmentCommandJpaAdapter Tests")
//@ExtendWith(MockitoExtension.class)
//class AppointmentCommandJpaAdapterTest {
//
//    private AppointmentCommandJpaAdapter appointmentCommandJpaAdapter;
//
//    @Mock
//    private AppointmentJpaRepository appointmentJpaRepository;
//
//    private Appointment appointment;
//    private Student student;
//    private Teacher teacher;
//
//    @BeforeEach
//    void setUp() {
//        appointmentCommandJpaAdapter = new AppointmentCommandJpaAdapter(appointmentJpaRepository);
//
//        student = new Student("João", "joao@email.com", "12345678901", "password");
//        student.setId(1);
//
//        teacher = new Teacher("Prof. Silva", "silva@email.com", "98765432101", "password", new ArrayList<>());
//        teacher.setId(1);
//
//        LocalDateTime dateTime = LocalDateTime.of(2025, 5, 15, 10, 0);
//        appointment = new Appointment(1, student, teacher, dateTime, 1.5,
//                AppointmentStatus.SCHEDULED, "Online", 100.0, PaymentStatus.PENDING);
//    }
//
//    @Test
//    @DisplayName("Should save an appointment")
//    void testSaveAppointment() {
//        when(appointmentJpaRepository.save(appointment)).thenReturn(appointment);
//
//        Appointment result = appointmentCommandJpaAdapter.save(appointment);
//
//        assertNotNull(result);
//        assertEquals(1, result.getId());
//        assertEquals(AppointmentStatus.SCHEDULED, result.getStatus());
//        verify(appointmentJpaRepository, times(1)).save(appointment);
//    }
//
//    @Test
//    @DisplayName("Should save appointment with different status")
//    void testSaveAppointmentCompleted() {
//        appointment.setStatus(AppointmentStatus.COMPLETED);
//        appointment.setPaymentStatus(PaymentStatus.PAID);
//
//        when(appointmentJpaRepository.save(appointment)).thenReturn(appointment);
//
//        Appointment result = appointmentCommandJpaAdapter.save(appointment);
//
//        assertNotNull(result);
//        assertEquals(AppointmentStatus.COMPLETED, result.getStatus());
//        assertEquals(PaymentStatus.PAID, result.getPaymentStatus());
//        verify(appointmentJpaRepository, times(1)).save(appointment);
//    }
//
//    @Test
//    @DisplayName("Should delete an appointment by ID")
//    void testDeleteAppointment() {
//        Integer appointmentId = 1;
//
//        appointmentCommandJpaAdapter.deleteById(appointmentId);
//
//        verify(appointmentJpaRepository, times(1)).deleteById(appointmentId);
//    }
//
//    @Test
//    @DisplayName("Should save appointment with updated values")
//    void testSaveAppointmentWithUpdatedValues() {
//        appointment.setTotalValue(150.0);
//        appointment.setLessonDuration(2.0);
//
//        when(appointmentJpaRepository.save(appointment)).thenReturn(appointment);
//
//        Appointment result = appointmentCommandJpaAdapter.save(appointment);
//
//        assertNotNull(result);
//        assertEquals(150.0, result.getTotalValue());
//        assertEquals(2.0, result.getLessonDuration());
//        verify(appointmentJpaRepository, times(1)).save(appointment);
//    }
//
//    @Test
//    @DisplayName("Should handle null appointment in save")
//    void testSaveNullAppointment() {
//        when(appointmentJpaRepository.save(null)).thenThrow(new IllegalArgumentException("Appointment cannot be null"));
//
//        assertThrows(IllegalArgumentException.class, () -> {
//            appointmentCommandJpaAdapter.save(null);
//        });
//    }
//
//    @Test
//    @DisplayName("Should delete multiple appointments sequentially")
//    void testDeleteMultipleAppointments() {
//        appointmentCommandJpaAdapter.deleteById(1);
//        appointmentCommandJpaAdapter.deleteById(2);
//        appointmentCommandJpaAdapter.deleteById(3);
//
//        verify(appointmentJpaRepository, times(3)).deleteById(any());
//    }
//}
//
