
package sptech.school;

import org.junit.jupiter.api.*;
import sptech.school.application.service.JwtService;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Dado o uso da JwtService")
class JwtServiceTest {

    private JwtService jwtService;
    private String tokenValido;

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();
        tokenValido = jwtService.generateToken("user@example.com", "UsuarioTeste", "admin");
    }

    @Test
    @DisplayName("[1] - Deve gerar token não nulo")
    void deveGerarToken() {
        String token = jwtService.generateToken("teste@teste.com", "teste", "user");

        assertNotNull(token);
    }

    @Test
    @DisplayName("[2] - Deve validar token válido com sucesso")
    void deveValidarToken() {
        assertTrue(jwtService.validateToken(tokenValido));
    }

    @Test
    @DisplayName("[3] - Deve invalidar token malformado")
    void deveInvalidarToken() {
        assertFalse(jwtService.validateToken("token.invalido.aqui"));
    }

    @Test
    @DisplayName("[4] - Deve extrair e-mail do token")
    void deveExtrairEmail() {
        String email = jwtService.extractEmail(tokenValido);

        assertEquals("user@example.com", email);
    }

    @Test
    @DisplayName("[5] - Deve extrair role do token")
    void deveExtrairRole() {
        String role = jwtService.extractRole(tokenValido);

        assertEquals("ADMIN", role);
    }

    @Test
    @DisplayName("[6] - Extração de e-mail deve lançar exceção com token inválido")
    void erroAoExtrairEmail() {
        assertThrows(Exception.class, () -> jwtService.extractEmail("erro.token"));
    }

    @Test
    @DisplayName("[7] - Extração de role deve lançar exceção com token inválido")
    void erroAoExtrairRole() {
        assertThrows(Exception.class, () -> jwtService.extractRole("erro.token"));
    }

    @Test
    @DisplayName("[8] - Token gerado deve conter dados codificados")
    void tokenContemDados() {
        assertTrue(tokenValido.split("\\.").length == 3);
    }

    @Test
    @DisplayName("[9] - Deve validar múltiplos tokens válidos")
    void validarMultiplosTokens() {
        String t1 = jwtService.generateToken("a@a.com", "a", "admin");
        String t2 = jwtService.generateToken("b@b.com", "b", "user");

        assertTrue(jwtService.validateToken(t1));
        assertTrue(jwtService.validateToken(t2));
    }

    @Test
    @DisplayName("[10] - Deve gerar token com role em maiúsculas")
    void roleEmMaiusculas() {
        String token = jwtService.generateToken("x@y.com", "x", "user");
        String role = jwtService.extractRole(token);

        assertEquals("USER", role);
    }
}
