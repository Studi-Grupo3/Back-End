//package sptech.school;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import sptech.school.adapters.out.persistence.AppointmentRepository;
//import sptech.school.application.service.dashboard.PaymentDashService;
//import sptech.school.domain.dto.response.dashboard.payment.PaymentDashDTO;
//import sptech.school.domain.dto.response.dashboard.payment.PaymentStatsDTO;
//import sptech.school.domain.dto.response.dashboard.payment.PaymentTableDTO;
//import sptech.school.domain.entity.Appointment;
//import sptech.school.v2.cleanarch.domain.entities.Teacher;
//import sptech.school.domain.enumerated.PaymentStatus;
//import sptech.school.domain.enumerated.Subject;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//class PaymentDashServiceTest {
//
//    @Mock
//    private AppointmentRepository appointmentRepository;
//
//    @InjectMocks
//    private PaymentDashService service;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void testGetDashboardData() {
//        Teacher t1 = new Teacher();
//        t1.setId(1);
//        t1.setName("Alice");
//        t1.setSubject(Subject.MATHEMATICS);
//
//        Teacher t2 = new Teacher();
//        t2.setId(2);
//        t2.setName("Bob");
//        t2.setSubject(Subject.SCIENCE);
//
//        Appointment a1 = new Appointment();
//        a1.setId(100);
//        a1.setTeacher(t1);
//        a1.setDateTime(LocalDateTime.of(2025,5,10,10,0));
//        a1.setLessonDuration(2.0);
//        a1.setTotalValue(200.0);
//        a1.setPaymentStatus(PaymentStatus.PENDING);
//
//        Appointment a2 = new Appointment();
//        a2.setId(101);
//        a2.setTeacher(t2);
//        a2.setDateTime(LocalDateTime.of(2025,5,11,12,0));
//        a2.setLessonDuration(1.5);
//        a2.setTotalValue(150.0);
//        a2.setPaymentStatus(PaymentStatus.PAID);
//
//        when(appointmentRepository.findByDateTimeBetween(any(), any()))
//                .thenReturn(List.of(a1, a2));
//
//        PaymentDashDTO dto = service.getDashboardData(5, 2025);
//
//        PaymentStatsDTO stats = dto.getStats();
//        assertEquals(350.0, stats.getTotalAmount());
//        assertEquals(2, stats.getTotalTeachers());
//        assertEquals(200.0, stats.getPendingAmount());
//        assertEquals(1, stats.getPendingTeachers());
//        assertEquals(150.0, stats.getRealizedAmount());
//        assertEquals(1, stats.getRealizedTeachers());
//        assertEquals(175.0, stats.getAverageAmountPerTeacher());
//
//        List<PaymentTableDTO> recent = dto.getRecent();
//        assertEquals(2, recent.size());
//
//        PaymentTableDTO first = recent.get(0);
//        assertEquals(101, first.getId());
//        assertEquals("Bob", first.getName());
//        assertEquals("SCIENCE", first.getSubject());
//        assertEquals(150.0/1.5, first.getValuePerHour());
//        assertEquals(1, first.getHours());
//        assertEquals(150.0, first.getTotal());
//        assertEquals("paid", first.getStatus());
//
//        PaymentTableDTO second = recent.get(1);
//        assertEquals(100, second.getId());
//        assertEquals("Alice", second.getName());
//    }
//}
