---

project: personal blog web site
module: infrastructure
type: portfolio
status: draft
-------------

# SPÉCIFICATION INFRASTRUCTURE — POSTGRESQL LOCAL DOCKER

---

# 1. PÉRIMÈTRE

Cette infrastructure fournit un environnement de développement local basé sur Docker pour l’API backend du portfolio.

Elle contient un seul service : PostgreSQL.

---

# 2. STRUCTURE

```
infrastructure/
└── compose.yml

.docker/
└── db_data/   (runtime, non versionné)
```

---

# 3. DOCKER COMPOSE

## Version

```yaml
version: "3.9"
```

---

# 4. SERVICES

## postgres

Service PostgreSQL utilisé comme base de données locale.

```yaml
services:
  postgres:
    image: postgres:16-alpine
    container_name: portfolio-postgres
    restart: unless-stopped
```

---

# 5. CONFIGURATION ENVIRONNEMENT

Les variables sont injectées via `.env` :

```env
DB_USER=
DB_PASSWORD=
DB_NAME=
```

Utilisation :

```yaml
environment:
  POSTGRES_USER: ${DB_USER}
  POSTGRES_PASSWORD: ${DB_PASSWORD}
  POSTGRES_DB: ${DB_NAME}
```

---

# 6. RÉSEAU

Réseau Docker interne :

```yaml
networks:
  backend:
    driver: bridge
```

Attaché au service :

```yaml
networks:
  - backend
```

---

# 7. PORTS

Exposition locale :

```yaml
ports:
  - "5432:5432"
```

---

# 8. PERSISTANCE DES DONNÉES

Bind mount pour la persistance locale :

```yaml
volumes:
  - ../.docker/db_data:/var/lib/postgresql/data
```

---

# 9. HEALTHCHECK

Vérification de disponibilité PostgreSQL :

```yaml
healthcheck:
  test: ["CMD-SHELL", "pg_isready -U ${DB_USER}"]
  interval: 10s
  timeout: 5s
  retries: 5
```

---

# 10. COMPORTEMENT GLOBAL

* Un seul conteneur PostgreSQL est exécuté en local
* Les données sont persistées sur l’hôte via `.docker/db_data`
* La configuration est externalisée via `.env`
* Le service est isolé dans un réseau Docker dédié (`backend`)
* PostgreSQL est exposé sur la machine hôte

---

# 11. HORS PÉRIMÈTRE

Cette infrastructure ne couvre pas :

* API backend (Spring Boot)
* Reverse proxy
* Pipeline CI/CD
* Services supplémentaires (Redis, cache, broker de messages)
* Environnement de production

---

# 12. OBJECTIF

Fournir un environnement minimal, reproductible et isolé permettant d’exécuter PostgreSQL en local via Docker dans un contexte de développement backend.
