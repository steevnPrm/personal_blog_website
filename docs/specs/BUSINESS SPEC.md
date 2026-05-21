---

project: personal blog web site
module: business-rules
type: portfolio
status: draft
-------------

# BUSINESS RULES — STUDY CASE MODULE

---

# 1. CREATION D’UNE STUDY CASE

## Endpoint associé

`POST /api/studycases/create`

## Règle métier

Lors de l’appel à l’endpoint de création :

* Une nouvelle StudyCase est créée immédiatement en base
* Un identifiant unique (UUID) est généré automatiquement
* La création nécessite un utilisateur authentifié
* Le champ `title` est obligatoire dès la création

---

## Résultat attendu

```text
StudyCase
- id: UUID (generated)
- title: String (required)
- createdAt: timestamp
```

---

# 2. ACCÈS À UNE STUDY CASE

## Endpoint associé

`GET /api/studycases/{studyCaseId}`

## Règle métier

* L’accès nécessite uniquement une authentification valide
* Si la StudyCase n’existe pas → 404
* Si l’utilisateur n’est pas authentifié → 403

---

# 3. GESTION DU TITRE

## Règle métier

Le titre étant obligatoire dès la création de la `StudyCase`, aucun endpoint dédié à l’initialisation tardive du titre n’est nécessaire.text
IF StudyCase.title == null
→ autoriser création du titre
ELSE
→ refuser (title already set)

````

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
- authentification obligatoire

---

## Comportement

```text
1. Vérifier existence StudyCase
2. Vérifier authentification utilisateur
3. Valider subtitle + content
4. Créer Section liée à StudyCase
5. Retourner Section créée
````

---

# 5. FLUX GLOBAL UTILISATEUR

```text
1. User crée une StudyCase
      ↓
2. StudyCase est persistée (UUID généré)
      ↓
3. User accède à la StudyCase
      ↓
4. User ajoute plusieurs sections
      ↓
5. Le serveur assemble les sections par ordre de position
      ↓
6. StudyCase devient un document structuré
```

---

# 6. RÈGLES D’INTÉGRITÉ

* Une Section ne peut exister sans StudyCase
* Une StudyCase nécessite une authentification valide pour être manipulée
* Le titre est obligatoire dès la création
* Subtitle + Content sont obligatoires pour une Section

---

# 7. CODES D’ERREUR ATTENDUS

| Cas                   | Code |
| --------------------- | ---- |
| StudyCase inexistante | 404  |
| Accès interdit        | 403  |
| Validation invalide   | 400  |

---

# 8. OBJECTIF MÉTIER

Permettre la construction progressive d’un document structuré :

* création immédiate d’une StudyCase avec titre
* enrichissement progressif via les sections
* assemblage ordonné des sections par position
* contenu modulaire et extensible
