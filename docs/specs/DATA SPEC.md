---

aliases: []
project: "personal blog web site"
type: "portfolio"
scope: "Data Structure"
stack:

* Spring Boot
* PostgreSQL
  status: "specification"

---

# DATA STRUCTURE SPECIFICATION

## 1. OBJECTIF

Définir la structure de données persistante minimale nécessaire au fonctionnement du portfolio.

Cette spécification couvre uniquement :

* les entités persistées en base de données,
* leurs relations,
* les contraintes d’intégrité,
* les règles de validation,
* les comportements de persistance.

L’authentification et les droits d’administration étant gérés via configuration sécurisée applicative (variables d’environnement), aucune entité `User` persistante n’est requise.

---

# 2. ENTITÉS PERSISTANTES

## 2.1 STUDY CASE

Représente une étude de cas ou un projet présenté sur le portfolio.

```text
StudyCase
- id: UUID (Primary Key)
- title: String
- createdAt: Instant
- updatedAt: Instant
```

### Contraintes

* `id`

  * UUID v4 auto-généré

* `title`

  * obligatoire dès la création
  * longueur valide comprise entre `3` et `120` caractères

* `createdAt`

  * généré automatiquement

* `updatedAt`

  * mis à jour automatiquement lors des modifications

---

## 2.2 SECTION

Représente une section de contenu ordonnée appartenant à une étude de cas.

```text
Section
- id: UUID (Primary Key)
- studyCaseId: UUID (Foreign Key → StudyCase)
- subtitle: String
- content: Text
- position: Integer
- createdAt: Instant
- updatedAt: Instant
```

### Contraintes

* `id`

  * UUID v4 auto-généré

* `studyCaseId`

  * obligatoire
  * référence valide vers `StudyCase`

* `subtitle`

  * obligatoire
  * longueur comprise entre `3` et `120` caractères

* `content`

  * obligatoire
  * non vide

* `position`

  * entier positif
  * utilisé pour l’ordre d’affichage

* `createdAt`

  * généré automatiquement

* `updatedAt`

  * mis à jour automatiquement

---

# 3. RELATIONS

```text
StudyCase (1) ───── (1) Section
```

## Cardinalités

### StudyCase → Section

* une `StudyCase` possède une unique `Section`

### Section → StudyCase

* une `Section` appartient obligatoirement à une unique `StudyCase`
* la relation est directe et exclusive

---

# 4. INTÉGRITÉ DES DONNÉES

## 4.1 Cascade de suppression

La suppression d’une `StudyCase` entraîne automatiquement :

* la suppression de la `Section` associée.

---

## 4.2 Interdiction d’orphelinage

Une `Section` ne peut jamais exister :

* sans `StudyCase`,
* avec une clé étrangère invalide.

---

# 5. VALIDATION MÉTIER

## StudyCase

### title

* obligatoire dès la création
* entre `3` et `120` caractères

---

## Section

### subtitle

* obligatoire
* entre `3` et `120` caractères

### content

* obligatoire
* non vide

### position

* entier supérieur ou égal à `1`

---

# 6. MODÈLE LOGIQUE

```text
[PostgreSQL]

study_cases
    └── sections
```

---

# 7. STRATÉGIE DE PERSISTANCE

## Base de données

* PostgreSQL

## Génération des identifiants

* UUID v4

## Gestion des dates

* `Instant` (UTC)

## Ordonnancement

L’ordre de rendu est déterminé :

* par `position ASC`
* utilisé pour l’assemblage séquentiel du contenu

---

# 8. ARCHITECTURE DE DONNÉES

La structure de persistance suit une relation directe :

```text
StudyCase
    └── Section
```

Aucune gestion multi-utilisateur persistante n’est implémentée dans la base de données.
