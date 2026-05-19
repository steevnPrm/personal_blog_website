# ADR 006: Utilisation de Docker pour PostgreSQL en environnement de développement local

## Statut

Accepté

---

## Contexte

Dans le cadre du développement de l’API backend du portfolio, une base de données PostgreSQL est nécessaire pour persister les entités métier.

Plusieurs approches sont possibles pour fournir cet environnement de données en local :

* installation native de PostgreSQL sur chaque machine de développement
* utilisation d’une base de données cloud dédiée
* utilisation de Docker pour exécuter PostgreSQL de manière isolée et reproductible

L’objectif est de garantir un environnement de développement simple à initialiser, cohérent entre les machines, et aligné avec une approche DevOps moderne.

---

## Décision

J’opte pour l’utilisation de Docker Compose afin de fournir une instance PostgreSQL locale :

* La base de données est exécutée dans un conteneur Docker (`postgres:16-alpine`)
* La configuration est centralisée dans `infrastructure/compose.yml`
* Les paramètres de connexion sont externalisés via un fichier `.env`
* Les données sont persistées via un volume Docker (bind mount ou volume local)

---

## Conséquences

### Avantages

* Environnement reproductible sur toutes les machines sans installation locale de PostgreSQL
* Isolation complète de la base de données par rapport au système hôte
* Facilité de reset de l’environnement (suppression des volumes)
* Mise en place rapide pour tout nouveau développeur
* Alignement avec les pratiques DevOps modernes et conteneurisées
* Possibilité d’étendre facilement l’infrastructure (Redis, cache, queue, etc.)

### Inconvénients

* Dépendance à Docker pour le développement local
* Complexité initiale légèrement supérieure à une installation native
* Nécessité de maîtriser les notions de volumes, réseaux et variables d’environnement
* Risque de conflits de ports avec des services locaux existants
