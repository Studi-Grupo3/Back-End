# 📚 Studi - Back-End

Projeto backend de uma plataforma educacional desenvolvida com Spring Boot, implementando arquitetura limpa e boas práticas de desenvolvimento.

## 📋 Sumário

- [Visão Geral](#visão-geral)
- [Tecnologias](#tecnologias)
- [Pré-requisitos](#pré-requisitos)
- [Instalação e Configuração](#instalação-e-configuração)
- [Estrutura do Projeto](#estrutura-do-projeto)
- [Variáveis de Ambiente](#variáveis-de-ambiente)
- [Como Executar](#como-executar)
- [Documentação da API](#documentação-da-api)
- [Testes](#testes)
- [Contribuição](#contribuição)
- [Contato](#contato)

## 🎯 Visão Geral

**Studi** é uma plataforma educacional com um backend robusto desenvolvido em Java com Spring Boot 3.4.2. O projeto utiliza **Clean Architecture** para manter uma separação clara de responsabilidades e facilitar a manutenção e escalabilidade.

### Principais Características:
- ✅ Arquitetura Limpa (Clean Architecture)
- ✅ Autenticação e Autorização com JWT
- ✅ Integração com RabbitMQ para mensageria
- ✅ Upload de arquivos com AWS S3
- ✅ Integração com Mercado Pago
- ✅ Suporte a Email
- ✅ Documentação automática com Swagger/OpenAPI
- ✅ Banco de dados MySQL
- ✅ Configuração com Docker Compose

## 🛠️ Tecnologias

### Core
- **Java 21**
- **Spring Boot 3.4.2**
- **Spring Data JPA**
- **Spring Security**
- **Spring Mail**
- **Spring WebFlux**
- **Spring AMQP (RabbitMQ)**

### Integração e Serviços
- **AWS SDK S3** - Armazenamento de arquivos
- **Mercado Pago SDK** - Processamento de pagamentos
- **JWT (JJWT)** - Token-based authentication
- **MapStruct** - Mapeamento de objetos (DTO)

### Banco de Dados
- **MySQL** - Banco de dados principal
- **H2** - Banco de dados em memória (desenvolvimento)
- **Hibernate/JPA** - ORM

### Ferramentas e Utilitários
- **Swagger/SpringDoc OpenAPI** - Documentação automática da API
- **Hibernate Validator** - Validação de dados
- **Maven** - Build e gerenciamento de dependências
- **Docker & Docker Compose** - Containerização

### Qualidade e Testes
- **SonarCloud** - Análise de código
- **JUnit & Spring Boot Test** - Testes automatizados
- **Apache JMeter** - Testes de performance

## 📦 Pré-requisitos

Certifique-se de ter os seguintes itens instalados:

- **Java 21** ou superior
- **Maven 3.8+**
- **Docker & Docker Compose**
- **MySQL 8.0+** (ou use o Docker)
- **RabbitMQ** (ou use o Docker)
- **Git**

### Verificar Instalação:
```bash
java -version
mvn -version
docker --version
docker-compose --version
```

## 🚀 Instalação e Configuração

### 1. Clonar o Repositório
```bash
git clone <URL_DO_REPOSITORIO>
cd Studi/Back-End
```

### 2. Iniciar Dependências com Docker
```bash
docker-compose up -d
```

Isso iniciará:
- **RabbitMQ** na porta `5672` (AMQP) e `15672` (Management UI)
- **MySQL** na porta `3306`

### 3. Configurar Variáveis de Ambiente
Crie um arquivo `.env` na raiz do projeto:

```env
# Perfil da Aplicação
SPRING_PROFILES_ACTIVE=dev

# Banco de Dados
DATABASE_URL=jdbc:mysql://localhost:3306/db_studi?createDatabaseIfNotExist=true
DB_USERNAME=root
DB_PASSWORD=password

# RabbitMQ
RABBITMQ_HOST=localhost
RABBITMQ_PORT=5672
RABBITMQ_USERNAME=guest
RABBITMQ_PASSWORD=guest

# JWT
JWT_SECRET_KEY=sua_chave_secreta_muito_segura_aqui
JWT_EXPIRATION_TIME=3600000

# AWS S3
AWS_ACCESS_KEY_ID=sua_access_key
AWS_SECRET_ACCESS_KEY=sua_secret_key
AWS_S3_BUCKET=seu_bucket_name
AWS_S3_REGION=us-east-1

# Mercado Pago
MERCADO_PAGO_ACCESS_TOKEN=seu_token_aqui

# Email
MAIL_HOST=smtp.gmail.com
MAIL_PORT=587
MAIL_USERNAME=seu_email@gmail.com
MAIL_PASSWORD=sua_senha_de_app

# Upload
FILE_UPLOAD_DIR=/app/uploads
APP_NAME=back-studi
```

### 4. Build do Projeto
```bash
mvn clean install
```

## 📁 Estrutura do Projeto

```
back-studi/
├── src/
│   ├── main/
│   │   ├── java/sptech/school/
│   │   │   ├── StudiApplication.java (Ponto de entrada)
│   │   │   └── v2/cleanarch/
│   │   │       ├── config/              (Configurações)
│   │   │       │   ├── CorsConfig.java
│   │   │       │   ├── SecurityConfig.java
│   │   │       │   ├── SwaggerConfig.java
│   │   │       │   └── DataLoader.java
│   │   │       ├── core/                (Lógica de negócio)
│   │   │       │   ├── application/     (Use Cases)
│   │   │       │   └── dtos/            (Data Transfer Objects)
│   │   │       ├── domain/              (Entidades e Regras de Negócio)
│   │   │       │   ├── entities/
│   │   │       │   ├── enumerated/
│   │   │       │   └── exception/
│   │   │       └── infra/               (Implementações Externas)
│   │   │           ├── persistence/     (Repositórios JPA)
│   │   │           ├── email/           (Serviços de Email)
│   │   │           └── web/             (Controladores REST)
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── application-dev.properties
│   │       └── application-prod.properties
│   └── test/                           (Testes Unitários e de Integração)
├── docker-compose.yml                  (Configuração Docker)
├── pom.xml                             (Dependências Maven)
└── README.md                           (Este arquivo)
```

### Estrutura de Camadas (Clean Architecture):

```
User Interface Layer (Controladores REST)
         ↓
Application Layer (Use Cases / DTOs)
         ↓
Business Logic Layer (Entidades / Regras)
         ↓
Persistence Layer (Repositórios / BD)
```

## 🌍 Variáveis de Ambiente

A aplicação aceita as seguintes variáveis de ambiente:

| Variável | Descrição | Padrão |
|----------|-----------|--------|
| `SPRING_PROFILES_ACTIVE` | Perfil ativo (dev/prod) | `dev` |
| `DATABASE_URL` | URL de conexão do MySQL | `jdbc:mysql://localhost:3306/db_studi` |
| `JWT_SECRET_KEY` | Chave secreta para assinar JWT | ⚠️ Obrigatório em produção |
| `AWS_ACCESS_KEY_ID` | Access key da AWS | ⚠️ Obrigatório se usar S3 |
| `AWS_SECRET_ACCESS_KEY` | Secret key da AWS | ⚠️ Obrigatório se usar S3 |
| `RABBITMQ_HOST` | Host do RabbitMQ | `localhost` |
| `FILE_UPLOAD_DIR` | Diretório para upload de arquivos | `/app/uploads` |

## 💻 Como Executar

### Desenvolvimento Local

#### 1. Com IDE (IntelliJ, VSCode, Eclipse)
```bash
# A classe StudiApplication.java como ponto de entrada
# Clique em "Run" na classe StudiApplication
```

#### 2. Via Maven
```bash
mvn spring-boot:run
```

#### 3. Via Linha de Comando (JAR compilado)
```bash
mvn clean package
java -jar target/back-studi-0.0.1-SNAPSHOT.jar
```

### Produção

```bash
# Build otimizado
mvn clean package -DskipTests

# Executar com perfil de produção
java -jar target/back-studi-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod
```

### Verificar se está Rodando
A aplicação estará disponível em: `http://localhost:8080`

## 📖 Documentação da API

A documentação interativa da API está disponível em:

- **Swagger UI**: `http://localhost:8080/swagger-ui.html`
- **OpenAPI JSON**: `http://localhost:8080/v3/api-docs`

### Acesso ao RabbitMQ Management UI
- URL: `http://localhost:15672`
- Usuário: `guest`
- Senha: `guest`

## 🧪 Testes

### Executar Todos os Testes
```bash
mvn test
```

### Executar Testes de Integração
```bash
mvn verify
```

### Testes Específicos
```bash
# Teste da classe ResponsibleTest
mvn test -Dtest=ResponsibleTest

# Executar todos os testes de um pacote
mvn test -Dtest=sptech.school.v2.cleanarch.domain.**
```

### Cobertura de Código
```bash
mvn clean test jacoco:report
# Relatório em: target/site/jacoco/index.html
```

## 🔐 Segurança

### Autenticação JWT
A aplicação implementa autenticação baseada em tokens JWT. Para usar endpoints protegidos:

1. **Login**: Envie credenciais para obter o token
2. **Token**: Inclua o token no header `Authorization: Bearer <token>`

### CORS
CORS está configurado para aceitar requisições de:
- `http://localhost:3000` (desenvolvimento frontend)
- `https://seu-dominio.com` (produção)

## 📊 Monitoramento

### SonarCloud
Análise de qualidade de código configurada. Acesse:
- Organização: `studi-grupo3`
- Projeto: `Studi-Grupo3_Back-End`

## 🐛 Troubleshooting

### Problema: "Connection refused" no MySQL
```bash
# Verifique se o MySQL está rodando
docker-compose ps

# Reinicie os containers
docker-compose restart
```

### Problema: Porta 8080 já em uso
```bash
# Altere a porta em application.properties
server.port=8081
```

### Problema: Erro de autenticação RabbitMQ
```bash
# Verifique as credenciais em docker-compose.yml
# Padrão: guest/guest
```

### Padrões de Código
- Siga o padrão Java/Spring Boot
- Use Clean Code principles
- Escreva testes para novas funcionalidades
- Mantenha a cobertura de testes acima de 50%

## 📧 Contato

Para dúvidas, sugestões ou reportar problemas:

- **Email**: matheus.fischer@sptech.school
---

<p align="center">
  Desenvolvido com ❤️ pelo 4ADSB - Grupo 3 - Studi
</p>

