//package sptech.school.v2.cleanarch.infra.persistence.query.appointment;
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
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@DisplayName("AppointmentQueryJpaAdapter Tests")
//@ExtendWith(MockitoExtension.class)
//class AppointmentQueryJpaAdapterTest {
//
//    private AppointmentQueryJpaAdapter appointmentQueryJpaAdapter;
//
//    @Mock
//    private AppointmentJpaRepository appointmentJpaRepository;
//
//    private Student student;
//    private Teacher teacher;
//    private Appointment appointment;
//
//    @BeforeEach
//    void setUp() {
//        appointmentQueryJpaAdapter = new AppointmentQueryJpaAdapter(appointmentJpaRepository);
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
//    @DisplayName("Should find appointment by ID")
//    void testFindById() {
//        when(appointmentJpaRepository.findById(1)).thenReturn(Optional.of(appointment));
//
//        Optional<Appointment> result = appointmentQueryJpaAdapter.findById(1);
//
//        assertTrue(result.isPresent());
//        assertEquals(1, result.get().getId());
//        verify(appointmentJpaRepository, times(1)).findById(1);
//    }
//
//    @Test
//    @DisplayName("Should return empty optional when appointment not found by ID")
//    void testFindByIdNotFound() {
//        when(appointmentJpaRepository.findById(999)).thenReturn(Optional.empty());
//
//        Optional<Appointment> result = appointmentQueryJpaAdapter.findById(999);
//
//        assertFalse(result.isPresent());
//        verify(appointmentJpaRepository, times(1)).findById(999);
//    }
//
//    @Test
//    @DisplayName("Should find all appointments")
//    void testFindAll() {
//        List<Appointment> appointments = new ArrayList<>();
//        appointments.add(appointment);
//
//        when(appointmentJpaRepository.findAll()).thenReturn(appointments);
//
//        List<Appointment> result = appointmentQueryJpaAdapter.findAll();
//
//        assertNotNull(result);
//        assertEquals(1, result.size());
//        verify(appointmentJpaRepository, times(1)).findAll();
//    }
//
//    @Test
//    @DisplayName("Should find appointments by teacher ID")
//    void testFindByTeacherId() {
//        List<Appointment> appointments = new ArrayList<>();
//        appointments.add(appointment);
//
//        when(appointmentJpaRepository.findByTeacherId(1)).thenReturn(appointments);
//
//        List<Appointment> result = appointmentQueryJpaAdapter.findByTeacherId(1);
//
//        assertNotNull(result);
//        assertEquals(1, result.size());
//        verify(appointmentJpaRepository, times(1)).findByTeacherId(1);
//    }
//
//    @Test
//    @DisplayName("Should find appointments by teacher ID and status")
//    void testFindByTeacherIdAndStatus() {
//        List<Appointment> appointments = new ArrayList<>();
//        appointments.add(appointment);
//
//        when(appointmentJpaRepository.findByTeacherIdAndStatus(1, AppointmentStatus.SCHEDULED))
//                .thenReturn(appointments);
//
//        List<Appointment> result = appointmentQueryJpaAdapter.findByTeacherIdAndStatus(1, AppointmentStatus.SCHEDULED);
//
//        assertNotNull(result);
//        assertEquals(1, result.size());
//        verify(appointmentJpaRepository, times(1)).findByTeacherIdAndStatus(1, AppointmentStatus.SCHEDULED);
//    }
//
//    @Test
//    @DisplayName("Should return true when appointment exists by student, teacher and date time")
//    void testExistsByStudentIdAndTeacherIdAndDateTime() {
//        LocalDateTime dateTime = LocalDateTime.of(2025, 5, 15, 10, 0);
//
//        when(appointmentJpaRepository.existsByStudentIdAndTeacherIdAndDateTime(1, 1, dateTime))
//                .thenReturn(true);
//
//        boolean result = appointmentQueryJpaAdapter.existsByStudentIdAndTeacherIdAndDateTime(1, 1, dateTime);
//
//        assertTrue(result);
//        verify(appointmentJpaRepository, times(1)).existsByStudentIdAndTeacherIdAndDateTime(1, 1, dateTime);
//    }
//
//    @Test
//    @DisplayName("Should return false when appointment does not exist by student, teacher and date time")
//    void testExistsByStudentIdAndTeacherIdAndDateTimeNotFound() {
//        LocalDateTime dateTime = LocalDateTime.of(2025, 6, 20, 14, 30);
//
//        when(appointmentJpaRepository.existsByStudentIdAndTeacherIdAndDateTime(1, 1, dateTime))
//                .thenReturn(false);
//
//        boolean result = appointmentQueryJpaAdapter.existsByStudentIdAndTeacherIdAndDateTime(1, 1, dateTime);
//
//        assertFalse(result);
//        verify(appointmentJpaRepository, times(1)).existsByStudentIdAndTeacherIdAndDateTime(1, 1, dateTime);
//    }
//
//    @Test
//    @DisplayName("Should check existence except given ID")
//    void testExistsByStudentIdAndTeacherIdAndDateTimeExceptId() {
//        LocalDateTime dateTime = LocalDateTime.of(2025, 5, 15, 10, 0);
//
//        when(appointmentJpaRepository.existsByStudentIdAndTeacherIdAndDateTimeAndIdNot(1, 1, dateTime, 1))
//                .thenReturn(false);
//
//        boolean result = appointmentQueryJpaAdapter.existsByStudentIdAndTeacherIdAndDateTimeExceptId(1, 1, dateTime, 1);
//
//        assertFalse(result);
//        verify(appointmentJpaRepository, times(1)).existsByStudentIdAndTeacherIdAndDateTimeAndIdNot(1, 1, dateTime, 1);
//    }
//
//    @Test
//    @DisplayName("Should find empty list when no appointments exist")
//    void testFindByTeacherIdEmptyList() {
//        when(appointmentJpaRepository.findByTeacherId(999)).thenReturn(new ArrayList<>());
//
//        List<Appointment> result = appointmentQueryJpaAdapter.findByTeacherId(999);
//
//        assertNotNull(result);
//        assertEquals(0, result.size());
//        verify(appointmentJpaRepository, times(1)).findByTeacherId(999);
//    }
//}
//
