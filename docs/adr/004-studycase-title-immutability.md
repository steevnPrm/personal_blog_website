# ADR 004: Verrouillage du Titre des StudyCases (Usage Unique)

## Statut
Accepté

## Contexte
Le besoin métier impose qu'une `StudyCase` puisse être initialisée sans titre (valeur à `null`), mais qu'une fois le titre défini par l'utilisateur, ce dernier ne puisse plus être modifié afin de figer le périmètre de l'étude.

## Décision
Je configure l'endpoint `POST /api/v1/studycases/{studyCaseId}/title` pour qu'il soit à **usage unique** :
- Si `title == null` : l'enregistrement est autorisé.
- Si `title != null` : l'API bloque la modification et renvoie une erreur `409 Conflict` (au format RFC 7807).

## Conséquences
- **Avantages :** - Code simple et logique métier stricte directement calquée sur les specs. Pas besoin de gérer des scénarios complexes de "mise à jour de titre" ou de logs de modifications.
- **Inconvénients :** - Aucune tolérance aux erreurs de frappe pour l'utilisateur lors de la validation. En tant qu'utilisateur unique/admin de mon portfolio, cela me force à valider le bon titre du premier coup, ou à recréer la ressource.
