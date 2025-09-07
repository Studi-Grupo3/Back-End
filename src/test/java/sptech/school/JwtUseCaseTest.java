
package sptech.school;

import org.junit.jupiter.api.*;
import sptech.school.v2.cleanarch.core.application.usecases.security.JwtUseCase;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Dado o uso da JwtUseCase")
class JwtUseCaseTest {

    private JwtUseCase jwtUseCase;
    private String tokenValido;

    @BeforeEach
    void setUp() {
        jwtUseCase = new JwtUseCase();
        tokenValido = jwtUseCase.generateToken("user@example.com", "UsuarioTeste", "admin");
    }

    @Test
    @DisplayName("[1] - Deve gerar token não nulo")
    void deveGerarToken() {
        String token = jwtUseCase.generateToken("teste@teste.com", "teste", "user");

        assertNotNull(token);
    }

    @Test
    @DisplayName("[2] - Deve validar token válido com sucesso")
    void deveValidarToken() {
        assertTrue(jwtUseCase.validateToken(tokenValido));
    }

    @Test
    @DisplayName("[3] - Deve invalidar token malformado")
    void deveInvalidarToken() {
        assertFalse(jwtUseCase.validateToken("token.invalido.aqui"));
    }

    @Test
    @DisplayName("[4] - Deve extrair e-mail do token")
    void deveExtrairEmail() {
        String email = jwtUseCase.extractEmail(tokenValido);

        assertEquals("user@example.com", email);
    }

    @Test
    @DisplayName("[5] - Deve extrair role do token")
    void deveExtrairRole() {
        String role = jwtUseCase.extractRole(tokenValido);

        assertEquals("ADMIN", role);
    }

    @Test
    @DisplayName("[6] - Extração de e-mail deve lançar exceção com token inválido")
    void erroAoExtrairEmail() {
        assertThrows(Exception.class, () -> jwtUseCase.extractEmail("erro.token"));
    }

    @Test
    @DisplayName("[7] - Extração de role deve lançar exceção com token inválido")
    void erroAoExtrairRole() {
        assertThrows(Exception.class, () -> jwtUseCase.extractRole("erro.token"));
    }

    @Test
    @DisplayName("[8] - Token gerado deve conter dados codificados")
    void tokenContemDados() {
        assertTrue(tokenValido.split("\\.").length == 3);
    }

    @Test
    @DisplayName("[9] - Deve validar múltiplos tokens válidos")
    void validarMultiplosTokens() {
        String t1 = jwtUseCase.generateToken("a@a.com", "a", "admin");
        String t2 = jwtUseCase.generateToken("b@b.com", "b", "user");

        assertTrue(jwtUseCase.validateToken(t1));
        assertTrue(jwtUseCase.validateToken(t2));
    }

    @Test
    @DisplayName("[10] - Deve gerar token com role em maiúsculas")
    void roleEmMaiusculas() {
        String token = jwtUseCase.generateToken("x@y.com", "x", "user");
        String role = jwtUseCase.extractRole(token);

        assertEquals("USER", role);
    }
}
