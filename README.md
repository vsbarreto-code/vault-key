# 🔐 VaultKey — Token Generation & Validation API

API REST para geração e validação de tokens de acesso seguro, desenvolvida como desafio técnico backend com Spring Boot.

---

## 📋 Sobre o Projeto

O **VaultKey** é um serviço fictício de segurança que expõe endpoints para criação e validação de tokens de acesso. Cada token carrega informações de destinatário, nível de acesso e tempo de vida (TTL), podendo ser configurado para uso único ou múltiplo.

---

## 🚀 Tecnologias

| Tecnologia | Versão |
|---|---|
| Java | 21 |
| Spring Boot | 4.1.0 |
| Spring Data JPA | — |
| H2 Database | — |
| MapStruct | 1.5.5.Final |
| Lombok | — |
| JUnit 5 + Mockito | — |

---

## 📐 Arquitetura

```
src/
└── main/
    └── java/com/vb/vault_key/
        ├── controller/        # Camada de entrada HTTP
        ├── database/
        │   ├── entity/        # Entidade JPA + Enum NivelAcesso
        │   └── repository/    # Interface de acesso a dados
        ├── dto/               # Request, Response e DTOs auxiliares
        ├── exception/         # Exceptions customizadas + GlobalExceptionHandler
        ├── mapper/            # Mapeamento DTO ↔ Entity via MapStruct
        └── service/           # Regras de negócio
```

---

## 📡 Endpoints

### `POST /v1/api/tokens` — Gerar Token

Cria um novo token com base nos dados do destinatário.

**Request Body:**
```json
{
  "destinatario": "sistema-pagamentos",
  "nivelAcesso": "ADMIN",
  "duracaoMinutos": 30,
  "usoUnico": true
}
```

**Response `201 Created`:**
```json
{
  "id": "a3f1c2d4-...",
  "destinatario": "sistema-pagamentos",
  "nivelAcesso": "ADMIN",
  "duracaoMinutos": 30,
  "usoUnico": true,
  "token": "VK-a3f1c2d4-xxxx-xxxx-xxxx-xxxxxxxxxxxx-ADMIN",
  "geradoEm": "2025-06-01T10:00:00",
  "usos": 0,
  "expiraEm": "2025-06-01T10:30:00"
}
```

---

### `POST /v1/api/tokens/validar` — Validar Token

Valida um token existente, aplicando as regras de expiração e uso único.

**Request Body:**
```json
{
  "token": "VK-a3f1c2d4-xxxx-xxxx-xxxx-xxxxxxxxxxxx-ADMIN"
}
```

**Response `200 OK`:**
```
Token validado com sucesso!
```

---

## ⚠️ Regras de Negócio

| Situação | HTTP Status | Descrição |
|---|---|---|
| Token não encontrado | `404 Not Found` | Token não existe na base |
| Token expirado | `410 Gone` | `LocalDateTime.now()` ultrapassou `expiraEm` |
| Token de uso único já utilizado | `409 Conflict` | `usoUnico = true` e `usos >= 1` |

### Formato do Token

```
VK-{UUID}-{nivelAcesso}
```

Exemplo: `VK-550e8400-e29b-41d4-a716-446655440000-ADMIN`

---

## 🏃 Como Executar

**Pré-requisitos:** Java 21, Maven 3.9+

```bash
# Clonar o repositório
git clone https://github.com/seu-usuario/vault-key.git
cd vault-key

# Executar
./mvnw spring-boot:run
```

A API sobe em `http://localhost:8080`.

**Console H2** (banco em memória): `http://localhost:8080/h2-console`

---

## 🧪 Testes

```bash
./mvnw test
```

Cobertura atual inclui testes unitários do `TokenService` com Mockito:

- ✅ Geração de token com sucesso
- ✅ Validação de token com sucesso
- ✅ Incremento de uso após validação

---

## 📦 Estrutura do Token Model

| Campo | Tipo | Descrição |
|---|---|---|
| `id` | UUID | Identificador único gerado automaticamente |
| `destinatario` | String | Sistema ou usuário que receberá o token |
| `nivelAcesso` | Enum | `ADMIN`, `USER`, etc. |
| `duracaoMinutos` | Integer | Tempo de vida do token em minutos |
| `usoUnico` | Boolean | Se `true`, invalida após o primeiro uso |
| `token` | String | String no formato `VK-{UUID}-{nivelAcesso}` |
| `geradoEm` | LocalDateTime | Timestamp de criação |
| `usos` | Integer | Contador de validações realizadas |
| `expiraEm` | LocalDateTime | Timestamp de expiração |
