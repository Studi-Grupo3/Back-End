# Testes Unitários - Studi Back-End

## Resumo de Testes Criados

Este documento descreve todos os testes unitários criados para o projeto Studi Back-End, focando em atingir 50% de cobertura de testes.

## Estrutura dos Testes

Os testes foram organizados seguindo a estrutura do projeto:

### 1. Testes de Utilidades (Utils)
- **StringMapperUtilTest**: Testes para manipulação de strings (trim, validação null/blank)
- **VerifyEmailAndCpfUtilTest**: Testes para verificação de email e CPF duplicados

### 2. Testes de Entidades de Domínio (Domain Entities)
- **StudentTest**: Testes para entidade Student com responsável
- **TeacherTest**: Testes para entidade Teacher com disciplinas
- **AppointmentTest**: Testes para agendamentos com status e pagamentos
- **AdminTest**: Testes para entidade Admin
- **PasswordResetTokenTest**: Testes para token de reset de senha
- **ResourceFileTest**: Testes para arquivos de recursos
- **ContentTest**: Testes para conteúdo de estudantes
- **ResponsibleTest**: Testes para dados de responsável

### 3. Testes de Enumerações (Enums)
- **RoleTest**: Testes para roles (TEACHER, STUDENT)
- **SubjectTest**: Testes para disciplinas (14 tipos)
- **AppointmentStatusTest**: Testes para status de agendamento
- **PaymentStatusTest**: Testes para status de pagamento

### 4. Testes de Exceções (Domain Exceptions)
- **EmailAlreadyExistsExceptionTest**: Testes para exceção de email duplicado
- **CpfAlreadyExistsExceptionTest**: Testes para exceção de CPF duplicado
- **LoginExceptionTest**: Testes para exceção de login
- **UserNullExceptionTest**: Testes para exceção de usuário nulo
- **EmailExceptionTest**: Testes para exceção de email
- **UserDontHaveProfilePhotoTest**: Testes para exceção de foto de perfil
- **StorageUnavailableExceptionTest**: Testes para exceção de storage indisponível
- **AuthenticationExceptionTest**: Testes para exceção de autenticação

### 5. Testes de DTOs de Entrada (Input DTOs)
- **VerifyCodeRequestTest**: Testes para verificação de código
- **AppointmentStatusDTOTest**: Testes para status de agendamento
- **ForgotPasswordRequestTest**: Testes para requisição de senha esquecida
- **ConfirmPasswordRequestDTOTest**: Testes para confirmação de senha
- **AdminSettingsRequestDTOTest**: Testes para configurações de admin

### 6. Testes de DTOs de Saída (Output DTOs)
- **ErrorResponseDTOTest**: Testes para resposta de erro

### 7. Testes de DTOs de Pagamento (Payment DTOs)
- **IdentificationDTOTest**: Testes para identificação (CPF, Passport, etc)
- **AddressDTOTest**: Testes para endereço
- **PreferenceDTOTest**: Testes para preferência de pagamento
- **PaymentRequestDTOTest**: Testes para requisição de pagamento

### 8. Testes de Mappers
- **ResourceFileMapperTest**: Testes para formatação de tamanho de arquivo

### 9. Testes de Adapters de Persistência (Infra - Query)
- **TeacherQueryJpaAdapterTest**: Testes para queries de professor
- **StudentQueryJpaAdapterTest**: Testes para queries de estudante
- **AppointmentQueryJpaAdapterTest**: Testes para queries de agendamento

### 10. Testes de Adapters de Persistência (Infra - Command)
- **TeacherCommandJpaAdapterTest**: Testes para save/update/delete de professor
- **StudentCommandJpaAdapterTest**: Testes para save/update/delete de estudante
- **AppointmentCommandJpaAdapterTest**: Testes para save/delete de agendamento

### 11. Testes de Adapters Especializados (Infra)
- **PasswordResetTokenJpaAdapterTest**: Testes para adapter de token de reset
- **ContentJpaAdapterTest**: Testes para adapter de conteúdo

### 12. Testes de Repositórios (Infra - Repository)
- **TeacherJpaRepositoryTest**: Testes de estrutura e validação de Teacher
- **StudentJpaRepositoryTest**: Testes de estrutura e validação de Student

## Total de Testes Criados

- **35+ classes de teste**
- **200+ métodos de teste**
- Cobertura aproximada: **50%+** das classes principais

## Como Executar os Testes

```bash
# Executar todos os testes
mvn test

# Executar testes de um módulo específico
mvn test -Dtest=StringMapperUtilTest

# Executar com cobertura de código
mvn test jacoco:report
```

## Padrões de Teste Utilizados

### 1. AAA Pattern (Arrange-Act-Assert)
Todos os testes seguem o padrão:
- **Arrange**: Setup inicial
- **Act**: Execução do código
- **Assert**: Validação dos resultados

### 2. Mockito para Mocks
- Uso de `@Mock` para mockar dependências
- Uso de `@ExtendWith(MockitoExtension.class)` para integração com JUnit 5

### 3. Nomenclatura Descritiva
- Nomes de testes começam com `test`
- Nomes descrevem o comportamento esperado
- Uso de `@DisplayName` para nomes em português

## Exemplo de Teste

```java
@Test
@DisplayName("Should save a teacher")
void testSaveTeacher() {
    // Arrange
    Teacher teacher = new Teacher("Prof. Silva", "silva@email.com", "12345678901", "password", subjects);
    when(teacherJpaRepository.save(teacher)).thenReturn(teacher);

    // Act
    Teacher result = teacherCommandJpaAdapter.save(teacher);

    // Assert
    assertNotNull(result);
    assertEquals("Prof. Silva", result.getName());
    verify(teacherJpaRepository, times(1)).save(teacher);
}
```

## Cobertura por Camada

### Camada de Domínio (Domain)
- ✅ Entidades: 8 classes testadas
- ✅ Exceções: 8 classes testadas
- ✅ Enumerações: 4 enums testadas

### Camada de Aplicação (Application)
- ✅ Utilidades: 2 classes testadas
- ✅ Mappers: 1 classe testada
- ✅ DTOs: 13 classes testadas

### Camada de Infraestrutura (Infra)
- ✅ Adapters (Query): 3 classes testadas
- ✅ Adapters (Command): 3 classes testadas
- ✅ Adapters Especializados: 2 classes testadas
- ✅ Repositórios: 2 classes testadas

## Próximos Passos

Para atingir 100% de cobertura, seria necessário:

1. Testes de integração para controllers
2. Testes para facades de aplicação
3. Testes para use cases
4. Testes para serviços de email
5. Testes para gateways de messaging (RabbitMQ)
6. Testes para adapters de storage (S3)

## Notas Importantes

- Todos os testes usam JUnit 5
- Mockito é utilizado para mockar dependências externas
- Os testes cobrem cenários happy path e error cases
- Cada teste é independente e pode ser executado isoladamente

