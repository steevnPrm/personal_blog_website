# ADR 001: Authentification Stateless (JWT) pour API Découplée

## Statut
Accepté

## Contexte
Pour ce projet solo (portfolio), je dois sécuriser l'accès aux ressources (`StudyCase`, `Section`) avec une API backend totalement indépendante du frontend. Le mécanisme de sécurité doit être léger pour le serveur tout en restant standard.

## Décision
J'opte pour une authentification **Stateless** via **JWT (JSON Web Tokens)** :
- Utilisation de **Spring Security** configuré sans session HTTP (`SessionCreationPolicy.STATELESS`).
- Validation des tokens via un filtre unique `JwtAuthenticationFilter` placé dans la chaîne de sécurité.
- Stockage des mots de passe utilisateurs uniquement sous forme de hash via **BCrypt**.

## Conséquences
- **Avantages :** - Côté serveur, c'est ultra-léger : pas de base de données ou de session en mémoire à requêter juste pour vérifier si je suis connecté.
  - Pas besoin de gérer les problématiques de CSRF complexes liées aux cookies sur des domaines différents.
- **Inconvénients :** - Si un token est généré, il est valide jusqu'à son expiration. Je dois donc implémenter un système de Refresh Token pour que la session utilisateur reste fluide sans être trop risquée.
