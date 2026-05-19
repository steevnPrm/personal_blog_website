---
project: "personal blog web site"
type: "portfolio"
api_version: "v1"
domain: "backend-api"
modules: ["auth", "studycases"]
standards: ["RFC7231", "RFC6750", "RFC7807", "JWT", "REST", "JSON"]
status: "specification"
---

# API v1

## Standards globaux

| Standard | Usage |
|---|---|
| RFC 7231 | HTTP semantics |
| RFC 6750 | Bearer token header |
| RFC 7807 | Error format |
| JWT | Stateless authentication |
| REST | Resource-based API |
| JSON | Data format |

---

# AUTH SERVICE

Base path: `/api/v1/auth`

## Register

POST `/register`

### Request
```json
{
  "email": "user@email.com",
  "password": "StrongPassword123"
}
````

### Response — 201 Created

```json
{
  "userId": "d3b07384-d113-49cd-a5d6-8ee4fc1dc3c7",
  "email": "user@email.com",
  "createdAt": "2026-05-18T10:00:00Z"
}
```

---

## Login

POST `/login`

### Request

```json
{
  "email": "user@email.com",
  "password": "StrongPassword123"
}
```

### Response — 200 OK

```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "expiresIn": 900
}
```

---

## Refresh token

POST `/refresh`

### Request

```json
{
  "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

### Response — 200 OK

```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "expiresIn": 900
}
```

---

## Logout

POST `/logout`

### Headers

```
Authorization: Bearer <accessToken>
```

### Request

```json
{
  "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

### Response — 200 OK

```json
{
  "message": "Logged out"
}
```

---

# STUDY CASE SERVICE

Base path: `/api/v1/studycases`

## Create study case

POST `/`

### Headers

```
Authorization: Bearer <accessToken>
```

### Request

```json
{
  "title": null 
}
```

_Note : Conformément aux règles métier, le titre peut être initialisé plus tard ou fourni vide à la création._

### Response — 201 Created

```json
{
  "id": "a9f8b7c6-e5d4-4c3b-b2a1-0f9e8d7c6b5a",
  "userId": "d3b07384-d113-49cd-a5d6-8ee4fc1dc3c7",
  "title": null,
  "createdAt": "2026-05-18T10:00:00Z"
}
```

---

## Get study case by ID

GET `/{studyCaseId}`

### Headers

```
Authorization: Bearer <accessToken>
```

### Response — 200 OK

```json
{
  "id": "a9f8b7c6-e5d4-4c3b-b2a1-0f9e8d7c6b5a",
  "userId": "d3b07384-d113-49cd-a5d6-8ee4fc1dc3c7",
  "title": "Analyse fintech",
  "createdAt": "2026-05-18T10:00:00Z",
  "sections": []
}
```

---

## Set title (Single Use)

POST `/{studyCaseId}/title`

### Headers

```
Authorization: Bearer <accessToken>
```

### Request

```json
{
  "title": "Analyse fintech"
}
```

### Response — 200 OK

```json
{
  "id": "a9f8b7c6-e5d4-4c3b-b2a1-0f9e8d7c6b5a",
  "title": "Analyse fintech",
  "updatedAt": "2026-05-18T10:02:00Z"
}
```

_Note : Si le titre est déjà défini pour cette StudyCase, cet endpoint retourne une erreur `409 Conflict` (voir format RFC 7807)._

---

## Add section

POST `/{studyCaseId}/sections`

### Headers

HTTP

```
Authorization: Bearer <accessToken>
```

### Request

```json
{
  "subtitle": "Contexte économique",
  "content": "Texte de l'analyse..."
}
```

### Response — 201 Created

```json
{
  "id": "e1f2g3h4-i5j6-4k7l-8m9n-0o1p2q3r4s5t",
  "studyCaseId": "a9f8b7c6-e5d4-4c3b-b2a1-0f9e8d7c6b5a",
  "subtitle": "Contexte économique",
  "content": "Texte de l'analyse...",
  "position": 1,
  "createdAt": "2026-05-18T10:05:00Z"
}
```

---

# RFC 7807 — ERROR FORMAT (GLOBAL)

### Content-Type

```
Content-Type: application/problem+json
```

### Exemple : Erreur de Validation (400)

```json
{
  "type": "[https://api.example.com/problems/validation-error](https://api.example.com/problems/validation-error)",
  "title": "Validation Error",
  "status": 400,
  "detail": "Title must be between 3 and 120 characters.",
  "instance": "/api/v1/studycases/a9f8b7c6-e5d4-4c3b-b2a1-0f9e8d7c6b5a/title"
}
```

### Exemple : Titre Déjà Défini (409)

```json
{
  "type": "[https://api.example.com/problems/conflict](https://api.example.com/problems/conflict)",
  "title": "Conflict",
  "status": 409,
  "detail": "The title for this StudyCase has already been set and cannot be modified.",
  "instance": "/api/v1/studycases/a9f8b7c6-e5d4-4c3b-b2a1-0f9e8d7c6b5a/title"
}
```

---

# CONTRAINTES

- JWT obligatoire pour toutes les routes `/api/v1/studycases/`.
    
- Password d'un utilisateur : minimum 8 caractères.
    
- UUID obligatoire pour toutes les ressources (`User`, `StudyCase`, `Section`).
    
- Rotation du Refresh token obligatoire lors de l'appel au rafraîchissement.
    
- HTTPS obligatoire partout.
    
- Ownership check systématique (`userId` du contexte de sécurité $\leftrightarrow$ ressource demandée) : renvoie `403` ou `404`.
    

---

# DoD (Definition of Done)

- Authentification Stateless JWT entièrement fonctionnelle.
    
- Rotation du Refresh token validée.
    
- Middleware de sécurité (`JwtAuthenticationFilter`) en place.
    
- CRUD et endpoints annexes de `StudyCase` entièrement sécurisés par propriété.
    
- Modèle d'erreur RFC 7807 appliqué sur l'ensemble des exceptions de l'API.
    
- Tests unitaires et tests d'intégration au statut "Pass".
    
- API versionnée correctement sous le préfixe `/v1`.