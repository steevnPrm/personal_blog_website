# Personal Blog Web Site — Backend API

## 📝 Présentation du Projet

Ce dépôt héberge le moteur de mon site web portfolio personnel.

L'approche traditionnelle des portfolios (un simple listing passif de compétences et de logos technologiques) manquant souvent de pertinence pour évaluer le niveau réel d'un développeur, ce projet propose une alternative : valoriser la conception logicielle et l'approche méthodique.

Le site expose et détaille mes propres réalisations sous forme d'études de cas structurées et modulaires, démontrant ainsi de manière transparente mes choix d'architecture, mes contraintes métiers et mes standards de développement.

---

# 🏛️ Les 3 Piliers de l'Architecture

Pour garantir un code de niveau production, robuste, évolutif et parfaitement documenté, le développement s'appuie sur trois méthodologies strictes.

---

## 1. Design-First — La conception avant le code

Aucune ligne de code n'est produite sans validation préalable des spécifications.

L'ensemble des règles fonctionnelles, des contrats d'API, de la structure des données et des choix de sécurité sont formalisés dans le dossier `/docs` sous forme de spécifications et de registres de décisions d'architecture (ADR).

---

## 2. API-Centric — Conformité aux standards du Web

Le backend fonctionne comme une API REST pure, totalement découplée du frontend.

Elle adhère aux standards industriels majeurs :

### REST & JSON

Manipulation uniforme des ressources.

### RFC 7231

Respect strict de la sémantique et des codes de statut HTTP.

### RFC 6750 & JWT

Authentification stateless sécurisée par porteur (*Bearer Token*).

### RFC 7807

Formatage universel et standardisé des réponses d'erreurs (`application/problem+json`).

---

## 3. Test-Driven — Garantie de non-régression

Chaque brique de l'application (validation d'entrée, contrôle d'accès, logique métier, intégrité transactionnelle) est couverte par un ensemble de tests unitaires et d'intégration afin de sécuriser les futurs refactorings et les pipelines de déploiement continu.

---

# ⚙️ Stack Technique

## Core Backend

| Domaine      | Technologie            |
| ------------ | ---------------------- |
| Langage      | Java 21                |
| Framework    | Spring Boot 4.0.6      |
| Sécurité     | Spring Security & JJWT |
| Validation   | Jakarta Validation     |
| Productivité | Lombok                 |

### Détails techniques

* **Java 21** : exploitation des fonctionnalités modernes (*records*, *pattern matching*, *virtual threads*).
* **Spring Boot 4.0.6** : support natif Jakarta EE et Spring Framework 6.
* **Spring Security & JJWT** : architecture stateless avec chiffrement BCrypt.
* **Jakarta Validation** : validation rigoureuse des payloads avant exécution métier.

---

## Persistance & Données

| Domaine             | Technologie                 |
| ------------------- | --------------------------- |
| Base de données     | PostgreSQL                  |
| ORM / Accès données | Spring Data JPA / Hibernate |

### Objectifs

* Gestion transactionnelle robuste.
* Contrôle fin des relations entre entités.
* Persistance adaptée aux environnements de développement et de production.

---

## DevOps, CI/CD & Cloud

| Domaine           | Technologie             |
| ----------------- | ----------------------- |
| Conteneurisation  | Docker & Docker Compose |
| CI/CD             | GitHub Actions          |
| Hébergement cible | AWS                     |

### Objectifs

* Isolation des services applicatifs.
* Validation automatisée des builds et des tests.
* Préparation à un déploiement cloud industrialisé.

---

# 📁 Organisation de la Documentation (`/docs`)

L'ensemble de l'ingénierie système est documenté afin d'assurer la transparence et la traçabilité du projet.

```text
docs/
├── adr/       # Architecture Decision Records
└── specs/     # Spécifications Business, API, Data & Security
```

---

## Synthèse des Décisions Majeures (ADR)

### ADR 001

Choix d'une architecture de sécurité entièrement stateless basée sur JWT.

### ADR 002

Implémentation d'une rotation obligatoire des Refresh Tokens pour limiter les risques de vol de session.

### ADR 003

Standardisation des retours d'erreurs via la spécification RFC 7807.

### ADR 004

Conception d'un endpoint à usage unique permettant de verrouiller définitivement le titre d'une étude de cas.

### ADR 005

Mise en place systématique d'un contrôle de propriété (*Ownership Check*) afin de prévenir les vulnérabilités de type BOLA / IDOR.

---

# 📦 Installation et Lancement Local

## Prérequis

Les outils suivants doivent être installés et configurés :

* Java 21
* Maven 3.9+
* Docker
* Docker Compose

---

## 1. Cloner le dépôt

```bash
git clone https://github.com/steevnPrm/personal_blog_website.git
cd personal_blog_website
```

---

## 2. Configuration de l'environnement

Configurer les variables d'environnement ou surcharger les propriétés dans :

```text
src/main/resources/application.yml
```

Exemples :

* Chaîne de connexion PostgreSQL
* Secret JWT
* Variables liées à l'environnement d'exécution

---

## 3. Compilation du projet

```bash
./mvnw clean compile
```

Cette étape valide l'ensemble du cycle de build Maven.

---

## 4. Exécution des tests

```bash
./mvnw test
```

Cette commande vérifie les règles métier, les contrôles de sécurité et les scénarios d'intégration.

---

## 5. Lancement du serveur API

```bash
./mvnw spring-boot:run
```

L'API sera accessible à l'adresse suivante :

```text
http://localhost:8080/api/
```

---

# 🚧 Note DevOps & Avancement

> ⚠️ La configuration complète des descripteurs de déploiement (`Dockerfile`, `Docker Compose` multi-environnements), ainsi que l'automatisation complète du pipeline GitHub Actions vers AWS, sont actuellement en cours d'intégration.

Le projet fonctionne actuellement sur un environnement de développement local standardisé.
