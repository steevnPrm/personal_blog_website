# ADR 003: Standardisation des Erreurs via la RFC 7807

## Statut
Accepté

## Contexte
Pour éviter de construire des formats de réponses d'erreur différents à chaque fois que je crée un nouveau contrôleur ou une nouvelle règle métier, j'ai besoin d'une structure de retour d'erreur unique, prévisible et simple à intercepter côté frontend.

## Décision
J'adopte le standard **RFC 7807 (Problem Details for HTTP APIs)** pour toutes les exceptions de l'application :
- Format JSON imposé avec le Content-Type `application/problem+json`.
- Structure fixe pour chaque erreur : `type`, `title`, `status`, `detail`, et `instance`.
- Centralisation de la gestion via un `RestControllerAdvice` global dans Spring Boot.

## Conséquences
- **Avantages :** - Un gain de temps énorme côté frontend : je crée un seul intercepteur/handler d'erreur générique pour afficher mes notifications ou messages d'alerte.
  - L'API est propre, documentée et professionnelle.
- **Inconvénients :** - Demande un petit effort d'implémentation initial pour intercepter les erreurs natives de Spring Security (401, 403) et les formater selon ce standard.
