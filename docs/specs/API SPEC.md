# Personal Blog Web Site — Backend API

## API v1 — Spécification Technique (Mono-Administrateur)

* **Project**: personal blog web site
* **Type**: portfolio
* **API Version**: v1
* **Domain**: backend-api
* **Modules**:

  * auth
  * studycases
* **Standards**:

  * RFC 7231
  * RFC 6750
  * RFC 7807
  * JWT
  * REST
  * JSON
* **Status**: specification

---

# Standards globaux

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

Aucune notion d'ownership métier n'est implémentée : les accès sont contrôlés uniquement par authentification et rôle.

---

## Login

Authentifie l'unique administrateur et génère un jeton d'accès à vie courte.

### Endpoint

```http
POST /login
```

### Request

```json
{
  "username": "admin@test.com",
  "password": "StrongPassword123"
}
```

### Response — 200 OK

```json
{
  "accessToken": "eyJhbGciOiJIUzM4NCIsInR5cCI6IkpXVCJ9..."
}
```

---

## Logout

Déconnecte l'administrateur en détruisant le contexte de sécurité de la requête courante.

### Endpoint

```http
POST /logout
```

### Headers

```http
Authorization: Bearer <accessToken>
```

### Response — 200 OK

```json
{
  "message": "Logged out"
}
```

> **Note** : Le jeton JWT étant par nature stateless, le client front-end (Angular, Vue, etc.) doit impérativement détruire sa copie locale de l'accessToken lors de cette opération.

---

# STUDY CASE SERVICE

**Base path:** `/api/v1/studycases`

---

## Get all study cases

Récupère la liste de toutes les études de cas disponibles.

### Endpoint

```http
GET /
```

### Access

Public (Aucun token requis)

### Response — 200 OK

```json
[
  {
    "id": "a9f8b7c6-e5d4-4c3b-b2a1-0f9e8d7c6b5a",
    "title": "Analyse fintech",
    "createdAt": "2026-05-18T10:00:00Z"
  }
]
```

---

## Create study case

Crée une nouvelle étude de cas.

### Endpoint

```http
POST /
```

### Access

Restreint (Rôle ADMIN requis)

### Headers

```http
Authorization: Bearer <accessToken>
```

### Request

```json
{
  "title": "Analyse fintech"
}
```

### Validation Rules

* `title` obligatoire
* Longueur minimale : 3 caractères
* Longueur maximale : 120 caractères
* Les espaces en début et fin de chaîne doivent être ignorés côté serveur

### Response — 201 Created

````json
{
  "id": "a9f8b7c6-e5d4-4c3b-b2a1-0f9e8d7c6b5a",
  "title": "Analyse fintech",
  "createdAt": "2026-05-18T10:00:00Z"
}
```json
{
  "id": "a9f8b7c6-e5d4-4c3b-b2a1-0f9e8d7c6b5a",
  "title": null,
  "createdAt": "2026-05-18T10:00:00Z"
}
````

---

## Get study case by ID

Récupère les détails complets d'une étude de cas spécifique.

### Endpoint

```http
GET /{studyCaseId}
```

### Access

Public (Aucun token requis)

### Response — 200 OK

```json
{
  "id": "a9f8b7c6-e5d4-4c3b-b2a1-0f9e8d7c6b5a",
  "title": "Analyse fintech",
  "createdAt": "2026-05-18T10:00:00Z",
  "sections": []
}
```

> **Note** : Les sections associées à la StudyCase sont assemblées dynamiquement côté serveur puis triées par `position ASC` avant retour au client.

---

## Add section

Ajoute une section de contenu textuel ordonnée à une étude de cas.

La position est calculée automatiquement côté serveur selon l'ordre actuel des sections existantes.

### Endpoint

```http
POST /{studyCaseId}/sections
```

### Access

Restreint (Rôle ADMIN requis)

### Headers

```http
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

Toutes les erreurs de l'API doivent utiliser le type de contenu standardisé suivant :

```http
Content-Type: application/problem+json
```

---

## Exemple : Erreur de Validation (400)

````json
{
  "type": "https://api.example.com/problems/validation-error",
  "title": "Validation Error",
  "status": 400,
  "detail": "Title is required and must be between 3 and 120 characters.",
  "instance": "/api/v1/studycases"
}
```json
{
  "type": "https://api.example.com/problems/validation-error",
  "title": "Validation Error",
  "status": 400,
  "detail": "Title must be between 3 and 120 characters.",
  "instance": "/api/v1/studycases/a9f8b7c6-e5d4-4c3b-b2a1-0f9e8d7c6b5a/title"
}
````

---

## Exemple : Accès Refusé / Non Autorisé (403)

```json
{
  "type": "https://api.example.com/problems/forbidden",
  "title": "Forbidden",
  "status": 403,
  "detail": "Full authentication is required to access this resource.",
  "instance": "/api/v1/studycases"
}
```

---

---

# CONTRAINTES TECHNIQUES

* Authentification par en-tête : utilisation exclusive du format RFC 6750 (`Authorization: Bearer <accessToken>`).
* UUID obligatoires : tous les identifiants générés (`StudyCase`, `Section`) doivent utiliser UUID v4.
* HTTPS obligatoire sur les environnements de staging, pré-production et production.
* Sécurisation par autorisations : accès basé uniquement sur l'authentification et le rôle `ADMIN` pour les opérations d'écriture.
* Architecture stateless sans stockage de session côté serveur.

---

# DoD (Definition of Done)

* [x] Initialisation de la chaîne de filtres Spring Security fonctionnelle et stateless.
* [x] Middleware d'interception et de validation JWT (`JwtAuthFilter`) opérationnel et débloqué des références circulaires.
* [x] Détection automatique du plus haut niveau de cryptage JWT selon la longueur de `JWT_SECRET`.
* [x] Extraction de l'unique compte administrateur hors du code source via variables d'environnement et BCrypt.
* [ ] Modèle d'erreur RFC 7807 appliqué de manière centralisée via `@ControllerAdvice`.
* [ ] Tests unitaires et tests d'intégration au statut `PASS`.
* [x] Exposition versionnée de l'API sous le préfixe global `/api/v1`.
