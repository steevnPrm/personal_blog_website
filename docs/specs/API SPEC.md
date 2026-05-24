# Personal Blog Web Site — Backend API

## API v1 — Spécification Technique (Mono-Administrateur)

| Champ           | Valeur                   |
| --------------- | ------------------------ |
| **Project**     | `personal blog web site` |
| **Type**        | `portfolio`              |
| **API Version** | `v1`                     |
| **Domain**      | `backend-api`            |
| **Modules**     | `auth`, `studycases`     |
| **Status**      | `specification`          |

---

# Standards Globaux

| Standard | Usage                         |
| -------- | ----------------------------- |
| RFC 7231 | HTTP Semantics                |
| RFC 6750 | Bearer Token Header           |
| RFC 7807 | Problem Details for HTTP APIs |
| JWT      | Stateless Authentication      |
| REST     | Resource-based API            |
| JSON     | Data Serialization Format     |

---

# AUTH SERVICE

**Base path:** `/api/v1/auth`

Pour un portfolio à administrateur unique, aucune inscription publique (`/register`) n'est implémentée.

Le compte d'administration est configuré de manière sécurisée côté serveur via des variables d'environnement.

---

## Login

Authentifie l'unique administrateur et génère un jeton d'accès.

### Endpoint

```http
POST /api/v1/auth/login
```

### Request

```json
{
  "username": "admin_secure_prod",
  "password": "A9x$kL2m!vP9_zQW4r*sT7b"
}
```

### Response — `200 OK`

```json
{
  "accessToken": "eyJhbGciOiJIUzUxMiJ9..."
}
```

---

# STUDY CASE SERVICE

**Base path:** `/api/v1/studycases`

---

## Create Study Case

### Endpoint

```http
POST /api/v1/studycases
```

### Request

```json
{
  "title": "Analyse fintech"
}
```

### Response — `201 Created`

```json
{
  "id": "a9f8b7c6-e5d4-4c3b-b2a1-0f9e8d7c6b5a",
  "title": "Analyse fintech",
  "createdAt": "2026-05-18T10:00:00Z"
}
```

---

# RFC 7807 — Error Format (Global)

Toutes les erreurs de l'API utilisent :

```http
Content-Type: application/problem+json
```

## Exemple — Erreur de Validation (`400 Bad Request`)

```json
{
  "type": "https://api.personalwebsite.com/probs/validation-error",
  "title": "Validation Error",
  "status": 400,
  "detail": "Title must be between 3 and 120 characters.",
  "instance": "/api/v1/studycases"
}
```

---

# Docker / Environment Configuration (Bash)

Avant de lancer l'application, configurez l'environnement avec le script suivant :

```bash
# Environment Configuration Mock Data

export JWT_SECRET=Mjg4ZDBmNjE0Y2M2YzVjOGJlYTM4M2VjOTU1YmI3MmQ3Yzg3NTg5NWRlNTg3MWUwOTg0M2M1Y2I4ZDYwMmEzYw==
export JWT_EXPIRATION=86400000
export JWT_REFRESH_EXPIRATION=604800000

# Default Admin Credentials

export ADMIN_USERNAME=admin_secure_prod
export ADMIN_PASSWORD='A9x$kL2m!vP9_zQW4r*sT7b'
export ADMIN_EMAIL=admin@personalwebsite.com

# Database & Infrastructure

export DB_URL=jdbc:postgresql://localhost:5432/blog_db
export ALLOWED_ORIGIN=http://localhost:3000
```

---

# DoD (Definition of Done)

* [x] Initialisation de la chaîne de filtres Spring Security fonctionnelle et stateless.
* [x] Middleware d'interception et de validation JWT (`JwtAuthFilter`) opérationnel.
* [x] Extraction de l'unique compte administrateur hors du code source via variables d'environnement.
* [x] Modèle d'erreur RFC 7807 appliqué de manière centralisée via `@ControllerAdvice`.
* [x] Exposition versionnée de l'API sous le préfixe global `/api/v1`.
