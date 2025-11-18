# Guia de Expansão de Testes - Studi Back-End

## Objetivo
Este documento fornece orientações para expandir a cobertura de testes de 50% para 100%.

---

## 🎯 Fases de Expansão

### Fase 1: Testes de Aplicação (70-75% cobertura)

#### 1.1 Testes de Use Cases
```java
// Exemplo: TeacherRegisterUseCaseTest
@ExtendWith(MockitoExtension.class)
class TeacherRegisterUseCaseTest {
    @Mock TeacherCommandGateway teacherCommandGateway;
    @Mock VerifyEmailAndCpfUtil verifyUtil;
    
    private TeacherRegisterUseCase useCase;
    
    @BeforeEach
    void setUp() {
        useCase = new TeacherRegisterUseCase(teacherCommandGateway, verifyUtil);
    }
    
    @Test
    void shouldRegisterTeacherSuccessfully() {
        // Arrange
        Teacher teacher = new Teacher(...);
        when(teacherCommandGateway.save(any())).thenReturn(teacher);
        
        // Act
        Teacher result = useCase.execute(teacher);
        
        // Assert
        assertNotNull(result);
        verify(verifyUtil).verify(teacher);
    }
}
```

#### 1.2 Testes de Facades
```java
// Exemplo: ContentFacadeTest
@ExtendWith(MockitoExtension.class)
class ContentFacadeTest {
    @Mock ContentUseCase contentUseCase;
    @Mock FileStorageAdapter fileStorageAdapter;
    
    private ContentFacade facade;
    
    @Test
    void shouldUploadContentSuccessfully() {
        // Implementação similar ao UseCase
    }
}
```

#### 1.3 Testes de Mappers Completos
```java
// Exemplo: StudentMapperTest
@ExtendWith(MockitoExtension.class)
class StudentMapperTest {
    @InjectMocks StudentMapper mapper;
    
    @Test
    void shouldMapStudentRequestToEntity() {
        StudentRequestDTO dto = new StudentRequestDTO(...);
        Student student = mapper.toEntity(dto);
        
        assertEquals(dto.name(), student.getName());
        // ... validar outros campos
    }
}
```

---

### Fase 2: Testes de Web (80-85% cobertura)

#### 2.1 Testes de Controllers com @WebMvcTest
```java
@WebMvcTest(TeacherController.class)
class TeacherControllerTest {
    @MockBean TeacherRegisterUseCase teacherRegisterUseCase;
    @Autowired MockMvc mockMvc;
    
    @Test
    void shouldRegisterTeacherWithValidData() throws Exception {
        TeacherRequestDTO request = new TeacherRequestDTO(...);
        
        mockMvc.perform(post("/teachers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists());
    }
    
    @Test
    void shouldReturnBadRequestWithInvalidEmail() throws Exception {
        // Validação de email inválido
        mockMvc.perform(post("/teachers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidRequestJson))
                .andExpect(status().isBadRequest());
    }
}
```

#### 2.2 Testes de Validação
```java
@WebMvcTest(TeacherController.class)
class TeacherControllerValidationTest {
    @Test
    void shouldValidateRequiredFields() throws Exception {
        // Testar validação de campos obrigatórios
    }
    
    @Test
    void shouldValidateEmailFormat() throws Exception {
        // Testar validação de email
    }
    
    @Test
    void shouldValidateCpfFormat() throws Exception {
        // Testar validação de CPF
    }
}
```

#### 2.3 Testes de Segurança
```java
@WebMvcTest(TeacherController.class)
@WithMockUser(authorities = "TEACHER")
class TeacherControllerSecurityTest {
    @Test
    void shouldAllowTeacherAccessToOwnData() throws Exception {
        // Testar acesso autorizado
    }
    
    @Test
    @WithMockUser(authorities = "STUDENT")
    void shouldDenyStudentAccessToTeacherData() throws Exception {
        // Testar acesso negado
    }
}
```

---

### Fase 3: Testes de Integração (90-95% cobertura)

#### 3.1 Testes com @SpringBootTest
```java
@SpringBootTest
@Transactional
class TeacherIntegrationTest {
    @Autowired TeacherRepository repository;
    @Autowired TeacherRegisterUseCase useCase;
    @Autowired MockMvc mockMvc;
    
    @Test
    void shouldRegisterAndRetrieveTeacher() {
        // Integração completa: registro + recuperação
    }
    
    @Test
    void shouldNotRegisterDuplicateEmail() {
        // Validação de duplicação em BD real
    }
}
```

#### 3.2 Testes de API Completa
```java
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TeacherApiIntegrationTest {
    @Autowired TestRestTemplate restTemplate;
    
    @Test
    void shouldCreateAndRetrieveTeacher() {
        // POST + GET completo
    }
    
    @Test
    void shouldUpdateTeacher() {
        // POST + PUT + GET
    }
    
    @Test
    void shouldDeleteTeacher() {
        // POST + DELETE + GET
    }
}
```

---

### Fase 4: Testes de Eventos e Messaging (95%+ cobertura)

#### 4.1 Testes de RabbitMQ
```java
@SpringBootTest
class RabbitEmailGatewayTest {
    @MockBean RabbitTemplate rabbitTemplate;
    @Autowired RabbitEmailGateway emailGateway;
    
    @Test
    void shouldSendEmailMessage() {
        emailGateway.sendResetPasswordEmail("user@email.com");
        
        verify(rabbitTemplate).convertAndSend(
            eq("email.queue"),
            any(EmailMessage.class)
        );
    }
}
```

#### 4.2 Testes de S3 Storage
```java
@ExtendWith(MockitoExtension.class)
class S3StorageFacadeTest {
    @Mock S3Client s3Client;
    @InjectMocks S3StorageFacade storageFacade;
    
    @Test
    void shouldUploadFileToS3() {
        // Mock de upload
    }
    
    @Test
    void shouldHandleS3Exception() {
        // Testes de erro
    }
}
```

---

## 📊 Matriz de Cobertura por Componente

| Componente | Atual | Meta | Testes Necessários |
|-----------|-------|------|-------------------|
| Domain | 80% | 95% | Use cases, validações |
| Application | 50% | 85% | Facades, mappers |
| Infra | 70% | 90% | Query/Command completo |
| Web | 0% | 85% | Controllers, validação |
| Config | 10% | 50% | Configurações, segurança |
| **Total** | **50%** | **85%** | ~100 novos testes |

---

## 🛠️ Ferramentas Recomendadas

### Para Medir Cobertura
```bash
# JaCoCo
mvn clean test jacoco:report

# SonarQube
mvn clean test sonar:sonar \
  -Dsonar.projectKey=Studi \
  -Dsonar.host.url=http://localhost:9000
```

### Para Gerar Dados de Teste
```java
// Usar builders para facilitar criação de dados
@AllArgsConstructor
public class TeacherBuilder {
    private Teacher teacher = new Teacher();
    
    public TeacherBuilder withName(String name) {
        teacher.setName(name);
        return this;
    }
    
    public Teacher build() {
        return teacher;
    }
}

// Uso
Teacher teacher = new TeacherBuilder()
    .withName("Prof. Silva")
    .withEmail("silva@email.com")
    .build();
```

---

## 📋 Checklist para 100% Cobertura

### Fase 1 (75%)
- [ ] Use cases testados (10 classes)
- [ ] Facades testadas (5 classes)
- [ ] Mappers completos (4 classes)
- [ ] Serviços testados (3 classes)

### Fase 2 (85%)
- [ ] Controllers testados (@WebMvcTest)
- [ ] Validações testadas
- [ ] Autorização testada
- [ ] Exceções mapeadas

### Fase 3 (95%)
- [ ] Testes de integração
- [ ] Testes E2E
- [ ] Testes de performance
- [ ] Testes de segurança

### Fase 4 (100%)
- [ ] Testes de eventos
- [ ] Testes de cache
- [ ] Testes de concorrência
- [ ] Documentação de testes

---

## 🎓 Exemplos de Padrões Avançados

### 1. Parametrized Tests
```java
@ParameterizedTest
@ValueSource(strings = {"user@email.com", "admin@email.com"})
void testValidEmails(String email) {
    assertTrue(isValidEmail(email));
}
```

### 2. Dynamic Tests
```java
@TestFactory
Collection<DynamicTest> dynamicTests() {
    return Arrays.asList(
        dynamicTest("test 1", () -> assertEquals(2, 1 + 1)),
        dynamicTest("test 2", () -> assertEquals(4, 2 + 2))
    );
}
```

### 3. Test Fixtures
```java
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TeacherFixtureTest {
    @BeforeAll
    void setupAll() {
        // Setup compartilhado
    }
    
    @BeforeEach
    void setupEach() {
        // Setup por teste
    }
}
```

---

## 🚀 Próximas Ações

1. **Criar testes de use cases** (prioridade: alta)
2. **Implementar testes de controller** (prioridade: alta)
3. **Adicionar testes de integração** (prioridade: média)
4. **Setup de SonarQube** (prioridade: média)
5. **Documentar casos de teste** (prioridade: baixa)

---

**Estimativa de Esforço**: 60-80 horas de desenvolvimento
**Benefício**: 100% de cobertura de código
**ROI**: Altíssimo - redução de bugs em produção

