
package sptech.school;

import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.mock.web.MockMultipartFile;
import sptech.school.application.service.ContentService;
import sptech.school.application.usecase.ContentRepositoryUseCase;
import sptech.school.application.usecase.StorageServiceUseCase;
import sptech.school.domain.entity.Content;
import sptech.school.domain.entity.Student;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("Dado o uso real de ContentService")
class ContentServiceTest {

    @Mock private ContentRepositoryUseCase repository;
    @Mock private StorageServiceUseCase storageService;

    @InjectMocks
    private ContentService contentService;

    private AutoCloseable mocks;

    private MockMultipartFile mockFile;
    private Content content;
    private Student student;

    @BeforeEach
    void setUp() {
        mocks = MockitoAnnotations.openMocks(this);
        mockFile = new MockMultipartFile("file", "arquivo.txt", "text/plain", "conteudo".getBytes());
        content = new Content("arquivo.txt", "text/plain", "/uploads/arquivo.txt", mockFile.getSize());
        student = new Student(
                "Nome Teste",
                "email@exemplo.com",
                "123.456.789-09",
                "senhaSegura",
                "11999999999"
        );
    }

    @Test
    @DisplayName("[1] - Deve salvar arquivo e retornar Content com sucesso")
    void salvarArquivoComSucesso() throws IOException {
        when(storageService.saveFile(mockFile)).thenReturn("/uploads/arquivo.txt");
        when(repository.save(any())).thenReturn(content);

        Content salvo = contentService.saveFile(mockFile, student);

        assertNotNull(salvo);
        assertEquals("arquivo.txt", salvo.getFileName());
        assertEquals("text/plain", salvo.getFileType());
    }

    @Test
    @DisplayName("[2] - Deve obter metadados com sucesso por ID")
    void obterMetadataPorId() {
        when(repository.findById(1L)).thenReturn(Optional.of(content));

        Optional<Content> resultado = contentService.getMetadataById(1L);

        assertTrue(resultado.isPresent());
        assertEquals("arquivo.txt", resultado.get().getFileName());
    }

    @Test
    @DisplayName("[3] - Deve retornar Optional.empty() se metadado não existir")
    void metadataNaoEncontrado() {
        when(repository.findById(42L)).thenReturn(Optional.empty());

        Optional<Content> resultado = contentService.getMetadataById(42L);

        assertTrue(resultado.isEmpty());
    }

    @Test
    @DisplayName("[4] - Deve buscar arquivo e retornar InputStream se existir")
    void buscarArquivoStreamComSucesso() throws IOException {
        InputStream stream = new ByteArrayInputStream("conteudo".getBytes());

        when(repository.findById(1L)).thenReturn(Optional.of(content));
        when(storageService.findFile("/uploads/arquivo.txt")).thenReturn(Optional.of(stream));

        Optional<InputStream> resultado = contentService.findFileById(1L);

        assertTrue(resultado.isPresent());
    }

    @Test
    @DisplayName("[5] - Deve retornar Optional.empty() se arquivo não for encontrado")
    void streamNaoEncontrado() throws IOException {
        when(repository.findById(1L)).thenReturn(Optional.of(content));
        when(storageService.findFile(any())).thenReturn(Optional.empty());

        Optional<InputStream> resultado = contentService.findFileById(1L);

        assertTrue(resultado.isEmpty());
    }

    @Test
    @DisplayName("[6] - Deve retornar Optional.empty() se metadado não existir ao buscar stream")
    void buscarStreamSemMetadata() throws IOException {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        Optional<InputStream> resultado = contentService.findFileById(99L);

        assertTrue(resultado.isEmpty());
    }
}
