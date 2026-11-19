# Order Management Service

**Rôle** : gestion des fournisseurs, des commandes et de leurs statuts.
**Base de données** : MySQL (order-db), dédiée à ce service.
**Port** : 8084
**Technologies** : Spring Boot 4, Spring Data JPA, OpenFeign, RabbitMQ

## Lancement
    .\mvnw.cmd spring-boot:run
    Invoke-RestMethod http://localhost:8084/actuator/health

## Endpoints prévus (préfixe /api/v1)
| Méthode | URI | Description |
|---|---|---|
| POST | /suppliers | Créer un fournisseur |
| GET | /suppliers | Lister les fournisseurs actifs |
| GET | /suppliers/{id} | Consulter un fournisseur |
| PUT | /suppliers/{id} | Modifier ou désactiver |
| POST | /orders | Créer une commande |
| GET | /orders | Lister les commandes |
| GET | /orders/{id} | Consulter une commande |
| PATCH | /orders/{id}/status | Changer le statut |

## Interactions
- I-06 : appelle Inventory (OpenFeign, synchrone)
- I-07 : publie l'événement `order.received` (RabbitMQ, asynchrone)