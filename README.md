# ⚙️ Freelance Platform — Backend

Backend da plataforma de freelancers desenvolvida como projeto de TCC, inspirado em plataformas como Upwork e Workana.

A API foi construída utilizando Spring Boot seguindo arquitetura REST, autenticação JWT e separação em camadas para simular uma aplicação profissional de marketplace de freelancers.

---

# 🚀 Tecnologias Utilizadas

## Backend

- Java 17
- Spring Boot
- Spring Security
- JWT Authentication
- Spring Data JPA
- Hibernate
- MySQL
- Maven

---

# 🧠 Objetivo do Projeto

O objetivo deste projeto é desenvolver uma API REST profissional para gerenciamento de uma plataforma de freelancers, demonstrando conhecimentos em:

- arquitetura backend
- autenticação JWT
- modelagem de domínio
- boas práticas REST
- segurança com Spring Security
- integração com frontend
- organização em camadas
- persistência com JPA/Hibernate

---

# 🏗️ Arquitetura da Aplicação

A aplicação segue arquitetura em camadas:

```text
Controller → Service → Repository → Database
```

Separação clara de responsabilidades:

- Controllers → recebem requisições HTTP
- Services → regras de negócio
- Repositories → acesso ao banco de dados
- Entities → modelagem do domínio

---

# 📦 Domínios Principais

## 👤 User

Responsável pelos usuários da plataforma.

Tipos de usuário:

- FREELANCER
- EMPLOYER

---

## 💼 Job

Responsável pelas vagas publicadas.

Status possíveis:

- OPEN
- IN_PROGRESS
- COMPLETED
- CANCELLED

---

## 📄 Application

Responsável pelas candidaturas enviadas para jobs.

Status possíveis:

- PENDING
- ACCEPTED
- REFUSED
- CANCELLED

---

# 🗂️ Estrutura de Entidades

## User

```java
id
name
email
password
userType
```

---

## Job

```java
id
title
description
budget
status
employer
```

---

## Application

```java
id
freelancer
job
status
createdAt
```

---

# 🔐 Autenticação

A API utiliza autenticação JWT com Spring Security.

Após o login, o usuário recebe um token JWT que deve ser enviado nas requisições protegidas:

```http
Authorization: Bearer TOKEN
```

---

# 🔗 Endpoints da API

# 🔑 Auth

## Login

```http
POST /api/auth/login
```

### Request

```json
{
  "email": "user@email.com",
  "password": "123456"
}
```

### Response

```json
{
  "token": "jwt-token",
  "type": "Bearer",
  "userId": 1,
  "name": "User",
  "role": "FREELANCER"
}
```

---

# 👤 Users

## Registrar usuário

```http
POST /api/users/register
```

### Request

```json
{
  "name": "John Doe",
  "email": "john@email.com",
  "password": "123456",
  "userType": "FREELANCER"
}
```

---

## Buscar usuário por ID

```http
GET /api/users/{id}
```

---

## Deletar usuário

```http
DELETE /api/users/{id}
```

---

## Listar freelancers

```http
GET /api/users/freelancers
```

---

# 💼 Jobs

## Criar vaga

```http
POST /api/jobs
```

### Request

```json
{
  "title": "Desenvolvedor Frontend",
  "description": "Projeto em React",
  "budget": 2500
}
```

---

## Buscar vaga por ID

```http
GET /api/jobs/{id}
```

---

## Deletar vaga

```http
DELETE /api/jobs/{id}
```

---

## Atualizar status da vaga

```http
PATCH /api/jobs/{id}/status
```

---

## Listar vagas abertas

```http
GET /api/jobs/open
```

---

## Buscar vagas do empregador

```http
GET /api/jobs/employer/{employerId}
```

---

# 📄 Applications

## Criar candidatura

```http
POST /api/applications
```

---

## Atualizar status da candidatura

```http
PATCH /api/applications/{id}/status
```

---

## Listar candidaturas de uma vaga

```http
GET /api/applications/job/{jobId}
```

---

## Listar candidaturas do freelancer

```http
GET /api/applications/freelancer/{freelancerId}
```

---

## Cancelar candidatura

```http
DELETE /api/applications/{id}
```

---

# 🛡️ Segurança

A aplicação utiliza:

- Spring Security
- JWT Authentication Filter
- Rotas protegidas
- Controle de autenticação
- Validação de acesso

---

# ⚠️ Tratamento de Exceções

Exceções tratadas na API:

- ResourceNotFoundException
- ResourceAlreadyExistsException
- BusinessException

---

# 📁 Estrutura do Projeto

```bash
src/main/java
├── controller
├── service
├── repository
├── entity
├── dto
├── config
├── security
├── exception
└── enums
```

---

# 🖥️ Como Executar o Projeto

## 1. Clone o repositório

```bash
git clone <URL_DO_REPOSITORIO>
```

---

## 2. Entre na pasta do projeto

```bash
cd backend-freelance-platform
```

---

## 3. Configure o banco de dados

Crie um banco MySQL e configure as credenciais no:

```properties
application.properties
```

Exemplo:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/freelance_platform
spring.datasource.username=root
spring.datasource.password=senha
```

---

## 4. Execute o projeto

```bash
./mvnw spring-boot:run
```

Ou:

```bash
mvn spring-boot:run
```

---

# 🌐 API

A aplicação será iniciada em:

```bash
http://localhost:8080
```

---

# 📦 Build do Projeto

## Gerar build

```bash
mvn clean install
```

---

# 🧪 Testes

## Executar testes

```bash
mvn test
```

---

# 📚 Conceitos Demonstrados

Este projeto demonstra conhecimentos em:

- Java moderno
- Spring Boot
- APIs REST
- JWT Authentication
- Spring Security
- JPA/Hibernate
- Modelagem de domínio
- Arquitetura em camadas
- Integração Full Stack
- Boas práticas backend

---

# 🚧 Status do Projeto

🚀 Em desenvolvimento

Novas funcionalidades serão adicionadas futuramente.

---

# 📄 Licença

Este projeto está sob a licença MIT.

---
