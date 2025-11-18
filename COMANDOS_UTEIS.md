# 🛠️ Comandos Úteis - Testes Studi Back-End

## 📋 Índice de Comandos

1. [Executar Testes](#executar-testes)
2. [Gerar Relatórios](#gerar-relatórios)
3. [Debugging](#debugging)
4. [Limpeza](#limpeza)
5. [CI/CD](#cicd)

---

## 🧪 Executar Testes

### Todos os Testes
```bash
mvn test
```

### Testes de um Pacote Específico
```bash
# Domain
mvn test -Dtest=sptech.school.v2.cleanarch.domain.**

# Application
mvn test -Dtest=sptech.school.v2.cleanarch.core.**

# Infrastructure
mvn test -Dtest=sptech.school.v2.cleanarch.infra.**
```

### Testes de Uma Classe Específica
```bash
mvn test -Dtest=StudentTest
mvn test -Dtest=TeacherTest
mvn test -Dtest=StringMapperUtilTest
```

### Testes que Contêm uma Palavra-Chave
```bash
mvn test -Dtest=*Mapper*
mvn test -Dtest=*Query*
mvn test -Dtest=*Command*
```

### Um Método de Teste Específico
```bash
mvn test -Dtest=StudentTest#testSetGetName
mvn test -Dtest=StudentTest#testSetGet*
```

### Testes Paralelos (Mais Rápido)
```bash
mvn test -DparallelTestClasses -DnumThreads=4
```

### Testes com Verbosidade Aumentada
```bash
mvn test -X
mvn test -X -DforkCount=1
```

---

## 📊 Gerar Relatórios

### JaCoCo (Cobertura de Código)
```bash
# Gerar relatório
mvn clean test jacoco:report

# Abrir no navegador (Windows)
start target/site/jacoco/index.html

# Abrir no navegador (Linux)
xdg-open target/site/jacoco/index.html

# Abrir no navegador (Mac)
open target/site/jacoco/index.html
```

### Verificar Limite Mínimo de Cobertura
```bash
mvn clean test jacoco:report jacoco:check -DminCoveragePct=50
```

### Relatório Detalhado
```bash
mvn clean test jacoco:report
# Verificar: target/site/jacoco/
```

### SonarQube (Análise Completa)
```bash
# Com SonarQube local
mvn clean test sonar:sonar \
  -Dsonar.projectKey=Studi \
  -Dsonar.host.url=http://localhost:9000 \
  -Dsonar.login=admin

# Com SonarCloud
mvn clean test sonar:sonar \
  -Dsonar.projectKey=Studi \
  -Dsonar.organization=seu-org \
  -Dsonar.host.url=https://sonarcloud.io \
  -Dsonar.login=seu-token
```

### Surefire Report (Relatório de Testes)
```bash
mvn surefire-report:report

# Abrir relatório
open target/site/surefire-report.html
```

---

## 🐛 Debugging

### Executar um Teste em Debug Mode
```bash
mvn -Dmaven.surefire.debug test -Dtest=StudentTest
```

### Executar com Saída Detalhada
```bash
mvn test -Dorg.slf4j.simpleLogger.defaultLogLevel=debug
```

### Executar Teste Específico com Debug
```bash
mvn -Dmaven.surefire.debug test -Dtest=StudentTest#testSetGetName
```

### Listar Todos os Testes sem Executar
```bash
mvn test -DdryRun
```

### Executar Testes Falhados
```bash
mvn test -Dsurefire.rerunFailingTestsCount=3
```

### Mostrar Stack Trace Completo
```bash
mvn test -Dmaven.surefire.debug -e -X
```

---

## 🧹 Limpeza

### Limpar Cache Maven
```bash
mvn clean
```

### Limpar e Reconstruir
```bash
mvn clean install -DskipTests
```

### Limpar Relatórios Antigos
```bash
rm -rf target/site/
rm -rf target/surefire-reports/
```

### Reiniciar Completamente
```bash
mvn clean
rm -rf ~/.m2/repository/sptech/
mvn test
```

---

## 🔄 CI/CD

### GitHub Actions
```yaml
# .github/workflows/tests.yml
name: Tests

on: [push, pull_request]

jobs:
  test:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v2
      - uses: actions/setup-java@v2
        with:
          java-version: '21'
      - run: mvn clean test
      - run: mvn test jacoco:report
      - uses: codecov/codecov-action@v2
```

### GitLab CI
```yaml
# .gitlab-ci.yml
test:
  image: maven:3.8.1-openjdk-21
  script:
    - mvn clean test
    - mvn test jacoco:report
  coverage: '/TOTAL.*?([0-9]{1,3})%/'
  artifacts:
    reports:
      coverage_report:
        coverage_format: cobertura
        path: target/site/cobertura/coverage.xml
```

### Jenkins
```groovy
pipeline {
    agent any
    
    stages {
        stage('Test') {
            steps {
                sh 'mvn clean test'
                sh 'mvn test jacoco:report'
            }
        }
        
        stage('Report') {
            steps {
                publishHTML([
                    reportDir: 'target/site/jacoco',
                    reportFiles: 'index.html',
                    reportName: 'JaCoCo Coverage'
                ])
            }
        }
    }
}
```

### GitLab CI com SonarQube
```yaml
test_and_sonar:
  image: maven:3.8.1-openjdk-21
  script:
    - mvn clean test
    - mvn sonar:sonar -Dsonar.projectKey=Studi
```

---

## 📈 Análise de Resultados

### Verificar Cobertura
```bash
# Gerar e ver cobertura
mvn clean test jacoco:report

# Grep para encontrar linhas cobertas
grep -A 1 "Overall" target/site/jacoco/index.html

# Com awk
mvn clean test jacoco:report | grep -i coverage
```

### Listar Testes Falhados
```bash
mvn test 2>&1 | grep -i "failure\|error" | head -20
```

### Contar Testes
```bash
find src/test -name "*.java" | wc -l
grep -c "@Test" src/test/java/**/*.java
```

---

## 🚀 Scripts Úteis

### Script para Executar Todos os Testes (bash)
```bash
#!/bin/bash
echo "Executando todos os testes..."
mvn clean test

echo "Gerando relatório de cobertura..."
mvn jacoco:report

echo "Abrindo relatório..."
open target/site/jacoco/index.html
```

### Script para Verificar Cobertura (bash)
```bash
#!/bin/bash
mvn clean test jacoco:report
coverage=$(grep -oP '(?<=missed=")[^"]*' target/site/jacoco/index.html | head -1)
echo "Cobertura: $coverage"
```

### Script para CI/CD Simples (bash)
```bash
#!/bin/bash
set -e

echo "🧪 Executando testes..."
mvn clean test

echo "📊 Gerando cobertura..."
mvn jacoco:report

echo "📈 Verificando limite..."
mvn jacoco:check -DminCoveragePct=50

echo "✅ Todos os testes passaram!"
```

---

## ⚙️ Configurações Úteis

### Maven Surefire Plugin
```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-surefire-plugin</artifactId>
    <version>3.5.2</version>
    <configuration>
        <parallel>methods</parallel>
        <threadCount>4</threadCount>
        <reuseForks>true</reuseForks>
        <argLine>-Xmx1024m -XX:MaxPermSize=256m</argLine>
    </configuration>
</plugin>
```

### JaCoCo
```xml
<plugin>
    <groupId>org.jacoco</groupId>
    <artifactId>jacoco-maven-plugin</artifactId>
    <version>0.8.8</version>
    <executions>
        <execution>
            <goals>
                <goal>prepare-agent</goal>
            </goals>
        </execution>
        <execution>
            <id>report</id>
            <phase>test</phase>
            <goals>
                <goal>report</goal>
            </goals>
        </execution>
        <execution>
            <id>jacoco-check</id>
            <goals>
                <goal>check</goal>
            </goals>
            <configuration>
                <rules>
                    <rule>
                        <element>PACKAGE</element>
                        <excludes>
                            <exclude>*Test</exclude>
                        </excludes>
                        <limits>
                            <limit>
                                <counter>LINE</counter>
                                <value>COVEREDRATIO</value>
                                <minimum>0.50</minimum>
                            </limit>
                        </limits>
                    </rule>
                </rules>
            </configuration>
        </execution>
    </executions>
</plugin>
```

---

## 📱 Atalhos Úteis

| Comando | O que faz |
|---------|-----------|
| `mvn test` | Executar todos os testes |
| `mvn test -Dtest=NomeTeste` | Executar teste específico |
| `mvn clean test` | Limpar e testar |
| `mvn test -X` | Debug mode |
| `mvn test jacoco:report` | Testar + cobertura |
| `mvn test -DskipTests` | Compilar sem testar |
| `mvn clean install` | Build completo |

---

## 🆘 Troubleshooting

### Erro: "Tests are flaky"
```bash
# Executar novamente
mvn test -Dsurefire.rerunFailingTestsCount=3
```

### Erro: "OutOfMemoryError"
```bash
# Aumentar memória
export MAVEN_OPTS="-Xmx2048m -XX:MaxPermSize=512m"
mvn test
```

### Erro: "Port already in use"
```bash
# Matar processo
lsof -ti:8080 | xargs kill -9
```

### Erro: "Cannot find symbol"
```bash
# Limpar e reconstruir
mvn clean
mvn compile
mvn test
```

---

**Última Atualização**: Novembro 2025
**Versão**: 1.0

