# ADR 002: Rotation des Refresh Tokens

## Statut
Accepté

## Contexte
Comme l'Access Token expire rapidement (15 min) pour limiter les risques, je veux que l'application renouvelle la session de manière transparente. Même en étant seul sur le projet, je veux appliquer les bonnes pratiques de sécurité d'une API de production.

## Décision
J'implémente un mécanisme de **Refresh Token Rotation** sur l'endpoint `POST /api/v1/auth/refresh` :
- Chaque demande de rafraîchissement invalide le Refresh Token actuel et en génère un nouveau en même temps que le nouvel Access Token.
- L'endpoint `/logout` invalide immédiatement le Refresh Token actif.

## Conséquences
- **Avantages :** - Sécurité robuste : si un token est intercepté, sa réutilisation par un tiers provoquera un conflit qui bloquera l'accès, protégeant ainsi l'application.
- **Inconvénients :** - Cela demande un peu plus de logique de code dans mon service d'authentification et une gestion propre du stockage du nouveau token côté frontend à chaque appel.
