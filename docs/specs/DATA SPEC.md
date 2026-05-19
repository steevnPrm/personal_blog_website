---
aliases:
project: personal blog web site
type: portfolio
scope: Data Structure
stack:
  - Spring
---
## 1. OBJECTIF

Définir la structure de données minimale nécessaire au fonctionnement de l’application :

- Authentification utilisateur
- Gestion des études de cas
- Gestion des sections de contenu
- Respect des règles d’ownership

---

# 2. ENTITÉS PRINCIPALES

## 2.1 USER (Auth Module)

Représente un utilisateur de l’application.

```text
User
- id: UUID
- email: String (unique)
- password: String (hashed)
- createdAt: Instant
- updatedAt: Instant
````

### Contraintes

* email unique
* password stocké uniquement en hash (BCrypt)
* id généré automatiquement (UUID)

---

## 2.2 STUDY CASE

Représente une étude de cas créée par un utilisateur.

```text
StudyCase
- id: UUID
- userId: UUID (FK → User)
- title: String
- createdAt: Instant
- updatedAt: Instant
```

### Contraintes

* title: 3 à 120 caractères
* ownership obligatoire (userId requis)
* suppression liée à l’utilisateur propriétaire

---

## 2.3 SECTION

Représente une partie de contenu d’une étude de cas.

```text
Section
- id: UUID
- studyCaseId: UUID (FK → StudyCase)
- subtitle: String
- content: Text
- position: Integer (ordre d’affichage)
- createdAt: Instant
- updatedAt: Instant
```

### Contraintes

* subtitle: 3 à 120 caractères
* content: non vide
* position utilisée pour ordonner les sections

---

# 3. RELATIONS ENTRE ENTITÉS

```text
User (1) ───── (N) StudyCase
StudyCase (1) ───── (N) Section
```

---

# 4. RÈGLES MÉTIER

## 4.1 Ownership

* Un utilisateur ne peut accéder qu’à ses propres StudyCases
* Un StudyCase ne peut contenir que ses propres Sections

---

## 4.2 Intégrité des données

* Suppression d’un StudyCase → suppression des Sections associées (cascade)
* Section toujours liée à un StudyCase valide
* StudyCase toujours lié à un User valide

---

## 4.3 Validation métier

### User

* email valide
* password >= 8 caractères

### StudyCase

* title obligatoire
* title entre 3 et 120 caractères

### Section

* subtitle obligatoire
* content obligatoire
* position optionnelle mais recommandée

---

# 5. MODÈLE LOGIQUE (RÉSUMÉ)

```text
User
  └── StudyCase
          └── Section
```

---