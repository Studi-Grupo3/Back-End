//package sptech.school;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.ArgumentCaptor;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import sptech.school.adapters.out.persistence.AdminRepository;
//import sptech.school.application.service.AdminSettingsService;
//import sptech.school.domain.dto.request.AdminSettingsRequestDTO;
//import sptech.school.domain.dto.response.AdminSettingsResponseDTO;
//import sptech.school.domain.entity.Admin;
//
//import java.util.Collections;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//class AdminSettingsServiceTest {
//
//    @Mock
//    private AdminRepository repository;
//
//    @Mock
//    private BCryptPasswordEncoder encoder;
//
//    private AdminSettingsService service;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        service = new AdminSettingsService(repository, encoder);
//    }
//
//    @Test
//    void deveRetornarConfiguracoesQuandoAdminExiste() {
//        Admin admin = new Admin();
//        admin.setEmail("admin@teste.com");
//        admin.setNotifyPayments(true);
//        admin.setNotifyAppointments(false);
//        admin.setNotifyCancellations(true);
//
//        when(repository.findAll()).thenReturn(Collections.singletonList(admin));
//
//        AdminSettingsResponseDTO resp = service.getSettings();
//
//        assertEquals("admin@teste.com", resp.getEmail());
//        assertTrue(resp.getNotifyPayments());
//        assertFalse(resp.getNotifyAppointments());
//        assertTrue(resp.getNotifyCancellations());
//        verify(repository, never()).save(any());
//    }
//
//    @Test
//    void deveLancarExceptionQuandoNaoHaAdmin() {
//        when(repository.findAll()).thenReturn(Collections.emptyList());
//
//        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.getSettings());
//        assertEquals("Admin settings not found", ex.getMessage());
//    }
//
//    @Test
//    void deveAtualizarTodasAsConfiguracoesSemTrocaDeSenha() {
//        Admin admin = new Admin();
//        admin.setEmail("old@teste.com");
//        admin.setPassword("hashed");
//        admin.setNotifyPayments(false);
//        admin.setNotifyAppointments(false);
//        admin.setNotifyCancellations(false);
//
//        when(repository.findAll()).thenReturn(Collections.singletonList(admin));
//
//        AdminSettingsRequestDTO dto = new AdminSettingsRequestDTO();
//        dto.setEmail("new@teste.com");
//        dto.setPassword("");   // senha em branco não altera
//        dto.setNotifyPayments(true);
//        dto.setNotifyAppointments(true);
//        dto.setNotifyCancellations(false);
//
//        AdminSettingsResponseDTO resp = service.updateSettings(dto);
//
//        // capturamos o objeto salvo
//        ArgumentCaptor<Admin> captor = ArgumentCaptor.forClass(Admin.class);
//        verify(repository).save(captor.capture());
//        Admin saved = captor.getValue();
//
//        assertEquals("new@teste.com", saved.getEmail());
//        assertEquals("hashed", saved.getPassword()); // continua a mesma
//        assertTrue(saved.getNotifyPayments());
//        assertTrue(saved.getNotifyAppointments());
//        assertFalse(saved.getNotifyCancellations());
//
//        // valida também o retorno
//        assertEquals("new@teste.com", resp.getEmail());
//        assertTrue(resp.getNotifyPayments());
//        assertTrue(resp.getNotifyAppointments());
//        assertFalse(resp.getNotifyCancellations());
//    }
//
//    @Test
//    void deveAtualizarSenhaQuandoForInformada() {
//        Admin admin = new Admin();
//        admin.setPassword("old-hash");
//
//        when(repository.findAll()).thenReturn(Collections.singletonList(admin));
//        when(encoder.encode("novaSenha")).thenReturn("new-hash");
//
//        AdminSettingsRequestDTO dto = new AdminSettingsRequestDTO();
//        dto.setPassword("novaSenha");
//        dto.setEmail("x@x");
//        dto.setNotifyPayments(false);
//        dto.setNotifyAppointments(false);
//        dto.setNotifyCancellations(false);
//
//        service.updateSettings(dto);
//
//        ArgumentCaptor<Admin> captor = ArgumentCaptor.forClass(Admin.class);
//        verify(repository).save(captor.capture());
//        assertEquals("new-hash", captor.getValue().getPassword());
//    }
//}
