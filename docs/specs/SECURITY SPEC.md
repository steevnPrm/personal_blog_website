---
project: "personal blog website"
type: "portfolio"
module: "security"
scope: "authentication, authorization, session management"
stack: ["Spring Security", "JWT", "BCrypt"]
status: "design"
---
# SECURITY MODULE — SPEC UPDATE

## Module
Security applicative — Spring Boot JWT stateless

---

# Objectif

Sécuriser l’API via :

- Authentication (JWT)
- Authorization (RBAC ready)
- Stateless session management
- Refresh token rotation
- Error handling RFC 7807

---

# Architecture de sécurité

## Flow global

```
Client
  ↓ (login)
AuthController
  ↓
AuthenticationManager (Spring Security)
  ↓
UserDetailsService (DB lookup)
  ↓
JWT Service (token generation)
  ↓
Client stores access + refresh token

Client → API request
  ↓
Authorization: Bearer <token>
  ↓
JwtAuthenticationFilter
  ↓
SecurityContext populated
  ↓
Controller access granted
```

---

# Spring Security Configuration

## Mode de session

- Stateless
- No HTTP session
- JWT-based authentication only

```java
SessionCreationPolicy.STATELESS
```

---

## Security Filter Chain

### Responsibilities

- Disable CSRF (API mode)
- Define public routes
- Protect all other endpoints
- Inject JWT filter before authentication

---

## Public routes

```
/api/v1/auth/**
```

Allowed without authentication:
- register
- login
- refresh

---

## Protected routes

```
/api/v1/studycases/**
```

Require:

```
Authorization: Bearer <accessToken>
```

---

# JWT AUTHENTICATION

## Token types

| Type | Duration | Usage |
|---|---|---|
| Access Token | 15 min | API access |
| Refresh Token | 7–30 days | regenerate access token |

---

## Access Token validation

Performed by:

```
JwtAuthenticationFilter
```

Steps:

1. Extract Authorization header
2. Validate "Bearer " prefix
3. Extract JWT
4. Parse username (email)
5. Load user from DB
6. Validate token signature + expiration
7. Set SecurityContext

---

## Security Context usage

Once authenticated:

```java
SecurityContextHolder.getContext().getAuthentication()
```

contains:

- principal = UserDetails
- authorities = roles (if enabled)

---

# AUTHORIZATION (RBAC READY)

## Current state

- Authentication only (user identity verified)

## Future extension (recommended)

Roles:

```text
ROLE_USER
ROLE_ADMIN
```

---

## Authorization rules (future-ready)

| Endpoint | Rule |
|---|---|
| /auth/** | public |
| /studycases/** | authenticated |
| /admin/** | ROLE_ADMIN |

---

## Example (Spring Security)

```java
.requestMatchers("/admin/**").hasRole("ADMIN")
```

---

# CSRF / CORS

## CSRF

Disabled because:

- Stateless API
- JWT used instead of cookies (except refresh option)

```java
csrf().disable()
```

---

## CORS policy

Allowed origins:

- frontend app (localhost or production domain)

Allowed methods:

- GET
- POST
- PUT
- DELETE

Allowed headers:

- Authorization
- Content-Type

---

# AUTHENTICATION ERROR HANDLING

## RFC 7807 format used globally

```http
Content-Type: application/problem+json
```

---

## Standard error types

### 401 Unauthorized

- invalid credentials
- missing token
- expired JWT

---

### 403 Forbidden

- access denied (role mismatch)
- locked account
- invalid permissions

---

### 500 Internal Error

- unexpected server failure

---

# SECURITY RULES

## Password

- BCrypt hashing
- never stored in plain text

---

## JWT

- HS256 or RS256 signing
- secret stored in environment variable
- expiration mandatory

---

## Refresh Token

- JWT-based (stateless)
- rotation mandatory
- must be invalidated on logout (optional blacklist if needed)

---

## Transport security

- HTTPS mandatory
- no token in URL
- Authorization header only

---

# LOGICAL RESPONSIBILITIES

## JwtAuthenticationFilter

✔ Extract token  
✔ Validate token  
✔ Load user  
✔ Set security context  

---

## AuthenticationManager

✔ Validate credentials  
✔ Delegate to UserDetailsService  

---

## UserDetailsService

✔ Load user from database  
✔ Provide Spring Security user model  

---

## JwtService

✔ Token generation  
✔ Token validation  
✔ Claims extraction  

---

# DOCKER / ENV READY (RECOMMENDED)

```
JWT_SECRET=
JWT_EXPIRATION=
DB_URL=
```

---

# DEFINITION OF DONE (SECURITY)

- JWT authentication working
- Stateless security enforced
- Filter chain correctly ordered
- Protected endpoints secured
- RFC 7807 errors implemented
- Refresh token flow functional
- Password encrypted (BCrypt)
- CORS configured
- Security context correctly populated
- Ready for RBAC extension