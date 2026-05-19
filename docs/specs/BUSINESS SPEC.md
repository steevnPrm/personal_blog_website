---
project: personal blog web site
module: business-rules
type: portfolio
status: draft
---
# BUSINESS RULES — STUDY CASE MODULE

---

# 1. CREATION D’UNE STUDY CASE

## Endpoint associé
`POST /api/studycases/create`

## Règle métier

Lors de l’appel à l’endpoint de création :

- Une nouvelle StudyCase est créée immédiatement en base
- Un identifiant unique (UUID) est généré automatiquement
- La StudyCase est associée à l’utilisateur authentifié
- Le champ `title` peut être vide ou initialisé (selon UX)

---

## Résultat attendu

```text
StudyCase
- id: UUID (generated)
- userId: UUID (owner)
- title: String (optional at creation)
- createdAt: timestamp
```

---

# 2. ACCÈS À UNE STUDY CASE

## Endpoint associé
`GET /api/studycases/{studyCaseId}`

## Règle métier

- L’utilisateur ne peut accéder qu’à ses propres StudyCases
- Si la StudyCase n’existe pas ou n’appartient pas à l’utilisateur → 404 ou 403

---

# 3. GESTION DU TITRE (UNE SEULE FOIS)

## Endpoint associé
`POST /api/studycases/{studyCaseId}/title`

## Règle métier

- Une StudyCase peut recevoir un titre **une seule fois**
- Si un titre existe déjà :
  - la requête est refusée (409 CONFLICT)

---

## Contraintes

- title obligatoire
- longueur : 3 à 120 caractères

---

## Comportement

```text
IF StudyCase.title == null
    → autoriser création du titre
ELSE
    → refuser (title already set)
```

---

# 4. AJOUT D’UNE SECTION

## Endpoint associé
`POST /api/studycases/{studyCaseId}/sections`

---

## Règle métier

L’utilisateur peut ajouter plusieurs sections à une StudyCase.

Chaque section doit contenir :

- subtitle (obligatoire)
- content (obligatoire)

---

## Contraintes

- subtitle : 3 à 120 caractères
- content : non vide
- studyCaseId doit exister
- ownership obligatoire

---

## Comportement

```text
1. Vérifier existence StudyCase
2. Vérifier ownership utilisateur
3. Valider subtitle + content
4. Créer Section liée à StudyCase
5. Retourner Section créée
```

---

# 5. FLUX GLOBAL UTILISATEUR

```text
1. User crée une StudyCase
      ↓
2. StudyCase est persistée (UUID généré)
      ↓
3. User accède à la StudyCase
      ↓
4. User ajoute un titre (une seule fois)
      ↓
5. User ajoute plusieurs sections
      ↓
6. StudyCase devient un document structuré
```

---

# 6. RÈGLES D’INTÉGRITÉ

- Une Section ne peut exister sans StudyCase
- Une StudyCase appartient à un seul User
- Un titre ne peut être défini qu’une seule fois
- Subtitle + Content sont obligatoires pour une Section

---

# 7. CODES D’ERREUR ATTENDUS

| Cas | Code |
|---|---|
| StudyCase inexistante | 404 |
| Accès interdit | 403 |
| Titre déjà défini | 409 |
| Validation invalide | 400 |

---

# 8. OBJECTIF MÉTIER

Permettre à un utilisateur de construire progressivement un document structuré :

- création rapide (StudyCase)
- enrichissement progressif (title + sections)
- contenu modulaire et extensible