# 🏢 Jakarta EE WebApp - Enterprise Java

[![Java](https://img.shields.io/badge/Java-17+-red)](https://www.oracle.com/java/)
[![Jakarta EE](https://img.shields.io/badge/Jakarta%20EE-10-blue)](https://jakarta.ee/)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-blue)](https://www.postgresql.org/)

Aplicação web enterprise usando **Jakarta EE puro** (sem Spring), demonstrando domínio das especificações Java EE/Jakarta EE.

---

## 🎯 Por que Jakarta EE?

Jakarta EE é a evolução do Java EE e é amplamente usado em grandes corporações e sistemas legados. Conhecer Jakarta EE demonstra:

- ✅ Conhecimento de **especificações Java padrão**
- ✅ Capacidade de trabalhar com **sistemas legados**
- ✅ Entendimento de **application servers** (WildFly, Payara, TomEE)
- ✅ Experiência com **enterprise patterns**

---

## 📚 Especificações Jakarta EE Implementadas

### Core Specs
- ✅ **JAX-RS** - RESTful Web Services
- ✅ **JPA** - Java Persistence API
- ✅ **CDI** - Contexts and Dependency Injection
- ✅ **Bean Validation** - Validação declarativa
- ✅ **JSON-B** - JSON Binding

---

## 🏗️ Arquitetura

```
jakarta-ee-webapp/
├── src/
│   ├── main/
│   │   ├── java/com/tharsis/jakartaapp/
│   │   │   ├── config/
│   │   │   │   ├── JaxRsConfig.java          # JAX-RS Application
│   │   │   │   └── CorsFilter.java           # CORS Configuration
│   │   │   ├── resource/                     # JAX-RS Resources (Controllers)
│   │   │   │   ├── CustomerResource.java
│   │   │   │   └── InvoiceResource.java
│   │   │   ├── service/                      # Business Logic
│   │   │   │   ├── CustomerService.java
│   │   │   │   └── InvoiceService.java
│   │   │   ├── repository/                   # Data Access
│   │   │   │   ├── CustomerRepository.java
│   │   │   │   └── InvoiceRepository.java
│   │   │   ├── entity/                       # JPA Entities
│   │   │   │   ├── Customer.java
│   │   │   │   └── Invoice.java
│   │   │   ├── dto/                          # Data Transfer Objects
│   │   │   │   ├── CustomerDTO.java
│   │   │   │   └── InvoiceDTO.java
│   │   │   └── exception/                    # Exception Handling
│   │   │       ├── BusinessException.java
│   │   │       └── ExceptionMapper.java
│   │   ├── resources/
│   │   │   └── META-INF/
│   │   │       └── persistence.xml           # JPA Configuration
│   │   └── webapp/
│   │       └── WEB-INF/
│   │           └── web.xml                   # Deployment Descriptor
│   └── test/
│       └── java/com/tharsis/jakartaapp/
├── docker-compose.yml
├── pom.xml
└── README.md
```

---

## 🚀 Como Executar

### Pré-requisitos
- Java 17+
- Maven 3.8+
- Application Server: WildFly, Payara, ou TomEE
- PostgreSQL 15+

### Opção 1: WildFly

```bash
# Download WildFly
wget https://github.com/wildfly/wildfly/releases/download/27.0.0.Final/wildfly-27.0.0.Final.zip
unzip wildfly-27.0.0.Final.zip

# Start PostgreSQL
docker run -d --name postgres \
  -e POSTGRES_DB=invoicedb \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_PASSWORD=postgres \
  -p 5432:5432 \
  postgres:15-alpine

# Build application
mvn clean package

# Deploy
cp target/jakarta-ee-webapp.war wildfly-27.0.0.Final/standalone/deployments/

# Start WildFly
./wildfly-27.0.0.Final/bin/standalone.sh
```

### Opção 2: Docker Compose

```bash
docker-compose up -d
```

---

## 📡 API Endpoints

### Base URL
```
http://localhost:8080/jakarta-ee-webapp/api
```

### Customers
```bash
GET    /api/customers           # List all
GET    /api/customers/{id}      # Get by ID
POST   /api/customers           # Create
PUT    /api/customers/{id}      # Update
DELETE /api/customers/{id}      # Delete
```

### Invoices
```bash
GET    /api/invoices            # List all
GET    /api/invoices/{id}       # Get by ID
POST   /api/invoices            # Create
PUT    /api/invoices/{id}       # Update
DELETE /api/invoices/{id}       # Delete
```

---

## 💡 Exemplo de Uso

### Criar Customer

```bash
curl -X POST http://localhost:8080/jakarta-ee-webapp/api/customers \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Acme Corp",
    "email": "contact@acme.com",
    "taxId": "12.345.678/0001-90"
  }'
```

### Criar Invoice

```bash
curl -X POST http://localhost:8080/jakarta-ee-webapp/api/invoices \
  -H "Content-Type: application/json" \
  -d '{
    "customerId": 1,
    "amount": 1500.00,
    "description": "Professional Services",
    "dueDate": "2026-02-15"
  }'
```

---

## 🔧 Configuração

### persistence.xml

```xml
<persistence xmlns="https://jakarta.ee/xml/ns/persistence" version="3.0">
    <persistence-unit name="invoicePU" transaction-type="JTA">
        <jta-data-source>java:jboss/datasources/PostgresDS</jta-data-source>
        <properties>
            <property name="hibernate.dialect" 
                     value="org.hibernate.dialect.PostgreSQLDialect"/>
            <property name="hibernate.hbm2ddl.auto" value="update"/>
            <property name="hibernate.show_sql" value="true"/>
        </properties>
    </persistence-unit>
</persistence>
```

---

## 🎯 Jakarta EE vs Spring Boot

| Feature | Jakarta EE | Spring Boot |
|---------|-----------|-------------|
| **Dependency Injection** | CDI (`@Inject`) | Spring DI (`@Autowired`) |
| **REST API** | JAX-RS (`@Path`) | Spring MVC (`@RestController`) |
| **Persistence** | JPA (standard) | Spring Data JPA |
| **Transactions** | JTA (`@Transactional`) | Spring TX |
| **Application Server** | Required (WildFly, Payara) | Embedded (Tomcat) |
| **Lightweight** | ❌ Heavier | ✅ Lighter |
| **Enterprise Features** | ✅ Built-in | Requires libraries |

---

## 🏢 Quando Usar Jakarta EE?

### ✅ Use Jakarta EE quando:
- Trabalhar com **sistemas legados** Java EE
- Empresa já usa **application servers**
- Necessita de **especificações padrão** (portabilidade)
- Projetos **governamentais** ou **grandes corporações**
- Sistemas que rodam em **IBM WebSphere**, **Oracle WebLogic**

### ❌ Use Spring Boot quando:
- Novo projeto greenfield
- Microserviços leves
- Deploy rápido e simples
- Equipe já familiar com Spring

---

## 📊 Domínio: Sistema de Faturamento

### Entidades

**Customer** (Cliente)
- ID, Name, Email, TaxID
- Relacionamento: 1-N com Invoices

**Invoice** (Fatura)
- ID, Number, Amount, DueDate, Status
- Relacionamento: N-1 com Customer

---

## 🧪 Testing

```bash
# Unit tests
mvn test

# Integration tests
mvn verify
```

---

## 🐳 Docker Support

```yaml
# docker-compose.yml
version: '3.8'
services:
  wildfly:
    image: quay.io/wildfly/wildfly:27.0.0.Final-jdk17
    ports:
      - "8080:8080"
      - "9990:9990"
    environment:
      - WILDFLY_USER=admin
      - WILDFLY_PASS=admin
    volumes:
      - ./target/jakarta-ee-webapp.war:/opt/jboss/wildfly/standalone/deployments/

  postgres:
    image: postgres:15-alpine
    environment:
      POSTGRES_DB: invoicedb
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: postgres
    ports:
      - "5432:5432"
```

---

## 📚 Especificações Jakarta EE

- [JAX-RS 3.1](https://jakarta.ee/specifications/restful-ws/)
- [JPA 3.1](https://jakarta.ee/specifications/persistence/)
- [CDI 4.0](https://jakarta.ee/specifications/cdi/)
- [Bean Validation 3.0](https://jakarta.ee/specifications/bean-validation/)
- [JSON-B 3.0](https://jakarta.ee/specifications/jsonb/)

---

## 🤝 Contribuições

Contribuições são bem-vindas! Especialmente:
- Exemplos de outras especificações Jakarta EE
- Testes de integração
- Deploy em outros application servers

---

## 👤 Autor

**Tharsis Soares**
- GitHub: [@tharsis-soares](https://github.com/tharsis-soares)
- LinkedIn: [linkedin.com/in/tharsis-soares](https://linkedin.com/in/tharsis-soares)
- Email: tharsissoares@hotmail.com

---

**Demonstrando domínio das especificações Jakarta EE para sistemas enterprise** 🏢
