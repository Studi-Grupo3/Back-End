//package sptech.school.v2.cleanarch.domain.entities;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import sptech.school.v2.cleanarch.domain.enumerated.AppointmentStatus;
//import sptech.school.v2.cleanarch.domain.enumerated.PaymentStatus;
//
//import java.time.LocalDateTime;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@DisplayName("Appointment Entity Tests")
//class AppointmentTest {
//
//    private Appointment appointment;
//    private Student student;
//    private Teacher teacher;
//
//    @BeforeEach
//    void setUp() {
//        appointment = new Appointment();
//        student = new Student("João", "joao@email.com", "12345678901", "password");
//        student.setId(1);
//        teacher = new Teacher("Prof. Silva", "silva@email.com", "98765432101", "password", null);
//        teacher.setId(1);
//    }
//
//    @Test
//    @DisplayName("Should create appointment with all parameters")
//    void testAppointmentConstructor() {
//        LocalDateTime dateTime = LocalDateTime.of(2025, 5, 15, 10, 0);
//        Appointment appt = new Appointment(1, student, teacher, dateTime, 1.5,
//                AppointmentStatus.SCHEDULED, "Online", 100.0, PaymentStatus.PENDING);
//
//        assertEquals(1, appt.getId());
//        assertEquals(student, appt.getStudent());
//        assertEquals(teacher, appt.getTeacher());
//        assertEquals(dateTime, appt.getDateTime());
//        assertEquals(1.5, appt.getLessonDuration());
//        assertEquals(AppointmentStatus.SCHEDULED, appt.getStatus());
//        assertEquals("Online", appt.getLocation());
//        assertEquals(100.0, appt.getTotalValue());
//        assertEquals(PaymentStatus.PENDING, appt.getPaymentStatus());
//    }
//
//    @Test
//    @DisplayName("Should set and get ID")
//    void testSetGetId() {
//        appointment.setId(1);
//        assertEquals(1, appointment.getId());
//    }
//
//    @Test
//    @DisplayName("Should set and get student")
//    void testSetGetStudent() {
//        appointment.setStudent(student);
//        assertEquals(student, appointment.getStudent());
//    }
//
//    @Test
//    @DisplayName("Should set and get teacher")
//    void testSetGetTeacher() {
//        appointment.setTeacher(teacher);
//        assertEquals(teacher, appointment.getTeacher());
//    }
//
//    @Test
//    @DisplayName("Should set and get date time")
//    void testSetGetDateTime() {
//        LocalDateTime dateTime = LocalDateTime.of(2025, 6, 20, 14, 30);
//        appointment.setDateTime(dateTime);
//        assertEquals(dateTime, appointment.getDateTime());
//    }
//
//    @Test
//    @DisplayName("Should set and get lesson duration")
//    void testSetGetLessonDuration() {
//        appointment.setLessonDuration(2.0);
//        assertEquals(2.0, appointment.getLessonDuration());
//    }
//
//    @Test
//    @DisplayName("Should set and get status")
//    void testSetGetStatus() {
//        appointment.setStatus(AppointmentStatus.COMPLETED);
//        assertEquals(AppointmentStatus.COMPLETED, appointment.getStatus());
//    }
//
//    @Test
//    @DisplayName("Should set and get location")
//    void testSetGetLocation() {
//        appointment.setLocation("Sala 101");
//        assertEquals("Sala 101", appointment.getLocation());
//    }
//
//    @Test
//    @DisplayName("Should set and get total value")
//    void testSetGetTotalValue() {
//        appointment.setTotalValue(150.75);
//        assertEquals(150.75, appointment.getTotalValue());
//    }
//
//    @Test
//    @DisplayName("Should set and get payment status")
//    void testSetGetPaymentStatus() {
//        appointment.setPaymentStatus(PaymentStatus.PAID);
//        assertEquals(PaymentStatus.PAID, appointment.getPaymentStatus());
//    }
//
//    @Test
//    @DisplayName("Should create empty appointment")
//    void testEmptyConstructor() {
//        Appointment emptyAppt = new Appointment();
//        assertNull(emptyAppt.getId());
//        assertNull(emptyAppt.getStudent());
//        assertNull(emptyAppt.getTeacher());
//    }
//}
//
