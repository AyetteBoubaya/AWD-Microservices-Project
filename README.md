# AWD-Microservices-Project
Mini-projet AWD 2026-2027 - Architecture microservices
# AgriSmart – Gestion intelligente des exploitations agricoles

**Mini-projet AWD – Année universitaire 2026–2027**

## 1. Présentation du projet

AgriSmart est une plateforme intelligente destinée à faciliter la gestion des exploitations agricoles. Elle centralise les informations relatives aux exploitations, aux cultures, aux stocks, aux commandes et aux équipements agricoles.

La plateforme prévoit également un système de surveillance IoT et un service intelligent permettant de prédire les besoins en irrigation à partir des données agricoles collectées.

## 2. Objectifs

- Faciliter la gestion des exploitations et des parcelles agricoles.
- Suivre les cultures et leurs cycles de production.
- Gérer les stocks de produits agricoles.
- Assurer le suivi des commandes et des fournisseurs.
- Collecter et surveiller les données des capteurs IoT.
- Prédire les besoins en irrigation grâce à l'intelligence artificielle.

## 3. Architecture microservices

Le backend est organisé en services indépendants, chacun responsable d'un domaine fonctionnel et de ses propres données.

| Microservice | Responsabilité |
|---|---|
| Farm Management | Gestion des exploitations, parcelles et sols |
| Crop Management | Gestion des cultures, plantations et récoltes |
| Inventory Management | Gestion des produits, stocks et mouvements |
| Order Management | Gestion des commandes et fournisseurs |
| IoT Monitoring | Collecte des mesures et surveillance des capteurs |
| Smart Irrigation AI | Prédiction des besoins en irrigation et recommandations |

Chaque microservice possède sa propre base de données afin de respecter le principe Database per Service.

## 4. Technologies envisagées

- **Backend :** Java, Spring Boot, Python, FastAPI.
- **Frontend :** Angular.
- **Bases de données :** PostgreSQL, MySQL et MongoDB.
- **Communication :** API REST et communication interservices.
- **Architecture :** API Gateway, Eureka Discovery Server, Config Server.
- **Sécurité :** Keycloak.
- **Conteneurisation :** Docker.
- **Intelligence artificielle :** Python, Scikit-learn.

*Les technologies et composants seront intégrés progressivement au cours du développement.*

## 5. Structure du dépôt

```text
AWD-Microservices-Project/
│
├── Backend/
│   ├── farm-service/
│   ├── crop-service/
│   ├── inventory-service/
│   ├── order-service/
│   ├── iot-service/
│   └── irrigation-ai-service/
│
├── Frontend/
│   └── agrismart-web/
│
├── Documentation/
│   ├── Diagrams/
│   ├── Rapport
