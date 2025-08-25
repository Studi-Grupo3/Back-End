//
//package sptech.school.adapters.in.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.*;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.core.io.InputStreamResource;
//import org.springframework.http.MediaType;
//import org.springframework.mock.web.MockMultipartFile;
//import org.springframework.test.web.servlet.MockMvc;
//import sptech.school.application.mappers.ContentMapper;
//import sptech.school.application.service.ContentService;
//import sptech.school.domain.dto.ContentDTO;
//import sptech.school.domain.entity.Content;
//
//import java.io.ByteArrayInputStream;
//import java.io.InputStream;
//import java.util.Optional;
//
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@WebMvcTest(ContentController.class)
//@DisplayName("Dado o uso do ContentController")
//class ContentControllerTest {
//
//    @Autowired private MockMvc mockMvc;
//
//    @MockBean private ContentService service;
//    @MockBean private ContentMapper mapper;
//
//    @Autowired private ObjectMapper objectMapper;
//
//    private MockMultipartFile mockFile;
//    private Content content;
//    private ContentDTO dto;
//
//    @BeforeEach
//    void setup() {
//        mockFile = new MockMultipartFile("file", "teste.txt", "text/plain", "conteudo".getBytes());
//        content = new Content();
//        content.setFileName("teste.txt");
//        content.setFileSize(8L);
//        dto = new ContentDTO(1L, "arquivo.txt", "text/plain", 1024L, "1 KB");
//    }
//
//    @Test
//    @DisplayName("[1] - Deve realizar upload de arquivo com sucesso")
//    void uploadArquivoComSucesso() throws Exception {
//        when(service.saveFile(mockFile)).thenReturn(content);
//        when(mapper.toResponse(content)).thenReturn(dto);
//
//        mockMvc.perform(multipart("/files").file(mockFile))
//            .andExpect(status().isOk())
//            .andExpect(jsonPath("$.fileName").value("teste.txt"));
//    }
//
//    @Test
//    @DisplayName("[2] - Deve obter metadados por ID")
//    void getMetadata() throws Exception {
//        when(service.getMetadataById(1L)).thenReturn(Optional.of(content));
//        when(mapper.toResponse(content)).thenReturn(dto);
//
//        mockMvc.perform(get("/files/1/info"))
//            .andExpect(status().isOk())
//            .andExpect(jsonPath("$.fileName").value("teste.txt"));
//    }
//
//    @Test
//    @DisplayName("[3] - Deve retornar 404 se metadata não encontrado")
//    void metadataNaoEncontrado() throws Exception {
//        when(service.getMetadataById(99L)).thenReturn(Optional.empty());
//
//        mockMvc.perform(get("/files/99/info"))
//            .andExpect(status().isNotFound());
//    }
//
//    @Test
//    @DisplayName("[4] - Deve fazer download do arquivo existente")
//    void downloadArquivo() throws Exception {
//        InputStream stream = new ByteArrayInputStream("conteudo".getBytes());
//        when(service.getMetadataById(1L)).thenReturn(Optional.of(content));
//        when(service.findFileById(1L)).thenReturn(Optional.of(stream));
//
//        mockMvc.perform(get("/files/1"))
//            .andExpect(status().isOk())
//            .andExpect(header().string("Content-Disposition", "attachment; filename=\"teste.txt\""));
//    }
//
//    @Test
//    @DisplayName("[5] - Deve retornar 404 se arquivo não existir")
//    void arquivoNaoEncontrado() throws Exception {
//        when(service.getMetadataById(1L)).thenReturn(Optional.empty());
//
//        mockMvc.perform(get("/files/1"))
//            .andExpect(status().isNotFound());
//    }
//
//    @Test
//    @DisplayName("[6] - Deve retornar 404 se metadata existir mas stream não")
//    void streamNaoEncontrado() throws Exception {
//        when(service.getMetadataById(1L)).thenReturn(Optional.of(content));
//        when(service.findFileById(1L)).thenReturn(Optional.empty());
//
//        mockMvc.perform(get("/files/1"))
//            .andExpect(status().isNotFound());
//    }
//
//    @Test
//    @DisplayName("[7] - Deve retornar o tamanho correto no download")
//    void validarTamanhoArquivo() throws Exception {
//        InputStream stream = new ByteArrayInputStream("conteudo".getBytes());
//        when(service.getMetadataById(1L)).thenReturn(Optional.of(content));
//        when(service.findFileById(1L)).thenReturn(Optional.of(stream));
//
//        mockMvc.perform(get("/files/1"))
//            .andExpect(header().longValue("Content-Length", 8L));
//    }
//
//    @Test
//    @DisplayName("[8] - Upload inválido deve retornar 400")
//    void uploadInvalido() throws Exception {
//        MockMultipartFile fileVazio = new MockMultipartFile("file", "", "text/plain", new byte[0]);
//
//        mockMvc.perform(multipart("/files").file(fileVazio))
//            .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    @DisplayName("[9] - Upload deve retornar ContentDTO com nome")
//    void uploadRetornaDTO() throws Exception {
//        when(service.saveFile(mockFile)).thenReturn(content);
//        when(mapper.toResponse(content)).thenReturn(dto);
//
//        mockMvc.perform(multipart("/files").file(mockFile))
//            .andExpect(jsonPath("$.fileName").value("teste.txt"));
//    }
//
//    @Test
//    @DisplayName("[10] - Upload de arquivo grande ainda deve funcionar")
//    void uploadArquivoGrande() throws Exception {
//        byte[] grande = new byte[1024 * 1024]; // 1MB
//        MockMultipartFile grandeFile = new MockMultipartFile("file", "grande.txt", "text/plain", grande);
//
//        content = new Content("grande.txt", "text/plain", "/uploads/grande.txt", (long) grande.length);
//
//        ContentDTO dtoGrande = new ContentDTO(
//                1L,
//                "grande.txt",
//                "text/plain",
//                (long) grande.length,
//                "1 MB"
//        );
//
//        when(service.saveFile(grandeFile)).thenReturn(content);
//        when(mapper.toResponse(content)).thenReturn(dtoGrande);
//
//        mockMvc.perform(multipart("/files").file(grandeFile))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.fileName").value("grande.txt"));
//    }
//}
