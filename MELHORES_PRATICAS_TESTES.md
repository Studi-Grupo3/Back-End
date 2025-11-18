# Melhores Práticas para Testes Unitários - Studi Back-End

## 📚 Princípios Fundamentais

### 1. FIRST Principle
- **F**ast: Testes rápidos (< 100ms)
- **I**solated: Testes independentes
- **R**epeatable: Resultados consistentes
- **S**elf-checking: Passam ou falham claramente
- **T**imely: Escritos junto com o código

### 2. Arrange-Act-Assert (AAA)
```java
@Test
void testExample() {
    // Arrange - Setup
    User user = new User("João", "joao@email.com");
    
    // Act - Execução
    boolean isValid = userValidator.validate(user);
    
    // Assert - Validação
    assertTrue(isValid);
}
```

### 3. One Assertion Per Test (Quando Possível)
```java
// ❌ Evitar
@Test
void testUserCreation() {
    User user = createUser("João");
    assertNotNull(user);
    assertEquals("João", user.getName());
    assertEquals("joao@email.com", user.getEmail());
}

// ✅ Preferir
@Test
void shouldCreateUserWithName() {
    User user = createUser("João");
    assertEquals("João", user.getName());
}

@Test
void shouldCreateUserWithEmail() {
    User user = createUser("João");
    assertEquals("joao@email.com", user.getEmail());
}
```

---

## 🎯 Padrões de Teste

### 1. Test Doubles (Mocks, Stubs, Fakes)

#### Stub - Retorna valor pré-definido
```java
@Test
void testWithStub() {
    StudentRepository stub = new StudentRepository() {
        @Override
        public Optional<Student> findById(Integer id) {
            return Optional.of(new Student("João", "joao@email.com", "12345678901", "pass"));
        }
    };
}
```

#### Mock - Verifica chamadas
```java
@Test
void testWithMock() {
    @Mock StudentRepository repository;
    
    when(repository.save(any())).thenReturn(student);
    service.save(student);
    verify(repository, times(1)).save(any());
}
```

#### Fake - Implementação simplificada
```java
public class FakeStudentRepository implements StudentRepository {
    private List<Student> students = new ArrayList<>();
    
    @Override
    public Student save(Student student) {
        students.add(student);
        return student;
    }
}
```

### 2. Test Fixtures
```java
@ExtendWith(MockitoExtension.class)
class TeacherServiceTest {
    private Teacher defaultTeacher;
    private List<Subject> defaultSubjects;
    
    @BeforeEach
    void setUp() {
        defaultSubjects = Arrays.asList(Subject.MATHEMATICS, Subject.PHYSICS);
        defaultTeacher = new Teacher("Prof. Silva", "silva@email.com", "12345678901", "pass", defaultSubjects);
    }
    
    @Test
    void testTeacherWithDefaultFixture() {
        assertNotNull(defaultTeacher);
        assertEquals(2, defaultTeacher.getSubjects().size());
    }
}
```

### 3. Test Builders
```java
public class StudentTestBuilder {
    private String name = "João Silva";
    private String email = "joao@email.com";
    private String cpf = "12345678901";
    private String password = "password123";
    
    public StudentTestBuilder withName(String name) {
        this.name = name;
        return this;
    }
    
    public Student build() {
        return new Student(name, email, cpf, password);
    }
}

// Uso
@Test
void testWithBuilder() {
    Student student = new StudentTestBuilder()
        .withName("Maria")
        .build();
    
    assertEquals("Maria", student.getName());
}
```

---

## 🔍 Técnicas de Teste

### 1. Boundary Testing
```java
@Test
void testMinimumBoundary() {
    // Teste com valor mínimo válido
    assertEquals(0, calculator.divide(0, 1));
}

@Test
void testMaximumBoundary() {
    // Teste com valor máximo válido
    assertEquals(MAX_VALUE, processor.process(MAX_VALUE));
}

@Test
void shouldThrowExceptionOnZeroDivisor() {
    // Teste fora dos limites
    assertThrows(ArithmeticException.class, () -> {
        calculator.divide(10, 0);
    });
}
```

### 2. Equivalence Partitioning
```java
@Test
void testValidEmails() {
    String[] validEmails = {
        "user@example.com",
        "user.name@example.com",
        "user+tag@example.co.uk"
    };
    
    for (String email : validEmails) {
        assertTrue(emailValidator.isValid(email));
    }
}

@Test
void testInvalidEmails() {
    String[] invalidEmails = {
        "user@",
        "@example.com",
        "user @example.com"
    };
    
    for (String email : invalidEmails) {
        assertFalse(emailValidator.isValid(email));
    }
}
```

### 3. State Transition Testing
```java
@Test
void shouldTransitionFromScheduledToCompleted() {
    Appointment appointment = new Appointment();
    appointment.setStatus(AppointmentStatus.SCHEDULED);
    
    // Verificar estado inicial
    assertEquals(AppointmentStatus.SCHEDULED, appointment.getStatus());
    
    // Transicionar
    appointment.setStatus(AppointmentStatus.COMPLETED);
    
    // Verificar novo estado
    assertEquals(AppointmentStatus.COMPLETED, appointment.getStatus());
}
```

---

## 📝 Padrões de Nomenclatura

### Padrão 1: Should-When (Recomendado)
```java
class StudentServiceTest {
    @Test
    void shouldCreateStudentWhenValidDataProvided() { }
    
    @Test
    void shouldThrowExceptionWhenEmailAlreadyExists() { }
    
    @Test
    void shouldReturnEmptyListWhenNoStudentsFound() { }
}
```

### Padrão 2: Given-When-Then
```java
class StudentServiceTest {
    @Test
    void givenValidStudent_whenSave_thenReturnSavedStudent() { }
    
    @Test
    void givenDuplicateEmail_whenSave_thenThrowException() { }
}
```

### Padrão 3: Descritivo
```java
class StudentServiceTest {
    @Test
    void testCreateValidStudent() { }
    
    @Test
    void testRejectDuplicateEmail() { }
}
```

---

## 🛡️ Tratamento de Exceções

### Teste de Exceção
```java
@Test
void shouldThrowEmailAlreadyExistsException() {
    // Arrange
    StudentRepository repository = mock(StudentRepository.class);
    when(repository.findByEmail("joao@email.com"))
        .thenReturn(Optional.of(new Student()));
    
    // Act & Assert
    assertThrows(EmailAlreadyExistsException.class, () -> {
        service.registerStudent(validStudent);
    });
}
```

### Teste de Mensagem de Erro
```java
@Test
void shouldProvideDetailedErrorMessage() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
        validator.validate(null);
    });
    
    assertEquals("User cannot be null", exception.getMessage());
}
```

---

## 🔄 Testes com Estado

### Teste Stateful
```java
@Test
void shouldMaintainStateTransitions() {
    Student student = new Student("João", "joao@email.com", "12345678901", "pass");
    
    // Estado inicial
    assertTrue(student.isActive());
    
    // Alterar estado
    student.deactivate();
    assertFalse(student.isActive());
    
    // Restaurar estado
    student.activate();
    assertTrue(student.isActive());
}
```

---

## 📊 Cobertura de Código

### Tipos de Cobertura
```java
// Line Coverage - Linhas executadas
public void process(int value) {
    if (value > 0) {          // Cobertura: linha testada
        System.out.println("Positivo");
    } else {                  // Cobertura: linha testada
        System.out.println("Negativo");
    }
}

// Branch Coverage - Caminhos do if/else
@Test
void testPositivePath() {
    process(1);  // Testa branch if (value > 0)
}

@Test
void testNegativePath() {
    process(-1); // Testa branch else
}
```

---

## ⚡ Performance de Testes

### Teste Rápido (< 100ms)
```java
@Test
void quickTest() {
    // Setup simples
    User user = new User("João", "joao@email.com");
    
    // Execução rápida
    boolean valid = emailValidator.isValid(user.getEmail());
    
    // Asserts simples
    assertTrue(valid);
}
```

### Teste Lento (Evitar)
```java
@Test
void slowTest() throws InterruptedException {
    // ❌ Evitar sleeps
    Thread.sleep(5000);
    
    // ❌ Evitar I/O real
    FileWriter writer = new FileWriter("test.txt");
    writer.close();
    
    // ❌ Evitar banco de dados real
    repository.save(student);
}
```

---

## 🎓 Anti-Patterns a Evitar

### 1. Testes Frágeis
```java
// ❌ Ruim - Depende de ordem e timing
@Test
void testUserListOrder() {
    List<User> users = service.listAll();
    assertEquals("Ana", users.get(0).getName());  // Pode falhar aleatoriamente
}

// ✅ Bom - Verificar conteúdo sem ordem
@Test
void testUserListContains() {
    List<User> users = service.listAll();
    assertTrue(users.stream().anyMatch(u -> u.getName().equals("Ana")));
}
```

### 2. Testes Lentos
```java
// ❌ Ruim
@Test
void testWithRealDatabase() {
    database.connect();  // Lento
    Student student = database.save(new Student(...));
    assertTrue(student.getId() > 0);
}

// ✅ Bom
@Test
void testWithMockedRepository() {
    StudentRepository repository = mock(StudentRepository.class);
    when(repository.save(any())).thenReturn(student);
    assertTrue(student.getId() > 0);
}
```

### 3. Testes Interdependentes
```java
// ❌ Ruim - Ordem importa
@Test
void test1_Create() { service.save(user); }

@Test
void test2_Update() { service.update(user); }  // Depende de test1

// ✅ Bom - Cada teste é independente
@Test
void testCreate() {
    User user = new User(...);
    service.save(user);
}

@Test
void testUpdate() {
    User user = new User(...);
    service.update(user);
}
```

---

## 🚀 Boas Práticas

### 1. Organizar Testes
```
src/test/java/
└── sptech/school/
    ├── domain/
    │   ├── entities/
    │   ├── exception/
    │   └── enumerated/
    ├── application/
    │   ├── utils/
    │   ├── mappers/
    │   └── dtos/
    └── infra/
        ├── persistence/
        └── adapter/
```

### 2. Nomenclatura Consistente
```java
// Padrão: [Classe]Test
class StudentServiceTest { }
class TeacherMapperTest { }
class EmailValidatorTest { }
```

### 3. Usar Builders para Dados
```java
Student student = new StudentBuilder()
    .withName("João")
    .withEmail("joao@email.com")
    .build();
```

### 4. Documentar Testes Complexos
```java
/**
 * Testa a verificação de email duplicado entre professores e alunos.
 * 
 * Cenário:
 * 1. Criar um professor com email joao@email.com
 * 2. Tentar criar um aluno com o mesmo email
 * 3. Deve lançar EmailAlreadyExistsException
 */
@Test
void shouldNotAllowDuplicateEmailAcrossUserTypes() {
    // Implementação
}
```

---

## ✅ Checklist de Qualidade

- [ ] Todos os testes passam
- [ ] Nenhum teste é flaky
- [ ] Testes executam em < 10 segundos
- [ ] Cobertura >= 80%
- [ ] Nomes descritivos
- [ ] Sem duplicação de código
- [ ] Usa mocks apropriadamente
- [ ] Sem sleeps ou waits
- [ ] Independentes entre si
- [ ] Documentados quando necessário

---

**Última Atualização**: Novembro 2025
**Versão**: 1.0

