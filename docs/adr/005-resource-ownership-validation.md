# ADR 005: Validation Systématique de l'Ownership des Ressources

## Statut
Accepté

## Contexte
Même si je suis le principal utilisateur et développeur du projet, l'application est conçue pour être multi-utilisateur dans sa structure de données. Je dois garantire qu'un utilisateur authentifié ne puisse en aucun cas manipuler les études de cas ou les sections d'un autre utilisateur.

## Décision
J'intègre une vérification systématique de la propriété (**Ownership Check**) dans la couche service avant chaque action sur le module `studycases` :
- Extraction du `userId` depuis le token JWT (contexte de sécurité).
- Comparaison obligatoire avec le `userId` de la `StudyCase` demandée en base de données.
- En cas de non-correspondance, rejet immédiat via une erreur `403 Forbidden` ou `404 Not Found`.
- Application d'une suppression en cascade : supprimer une `StudyCase` supprime toutes ses `Sections`.

## Conséquences
- **Avantages :** - Protection native contre les failles d'accès direct aux objets (IDOR/BOLA). 
  - Base de données saine et cloisonnée.
- **Inconvénients :** - Obligation de faire une lecture en base de données pour valider le propriétaire avant d'exécuter l'action (écriture/suppression/lecture), ce qui ajoute une petite requête SQL intermédiaire.
