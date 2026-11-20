# AgriSmart – inventory-service

Microservice **Gestion de l'inventaire** (produits, stocks, mouvements de stock) du projet AgriSmart.

- Spring Boot 3.3.5 · Java 17 · Maven
- PostgreSQL · Spring Data JPA · Eureka Client · Lombok · Validation · Actuator
- Port : **8083**

## 1. Ouvrir dans IntelliJ IDEA

1. Décompresser le zip.
2. IntelliJ → **File > Open** → choisir le dossier `inventory-service` (celui qui contient `pom.xml`).
3. Choisir **Open as Project** / **Trust Project**. IntelliJ télécharge les dépendances Maven automatiquement.
4. Vérifier le JDK : **File > Project Structure > Project SDK = 17** (ou plus).
5. Activer Lombok : **Settings > Build, Execution, Deployment > Compiler > Annotation Processors > Enable annotation processing** (IntelliJ le propose souvent tout seul).

## 2. Créer la base de données

Dans pgAdmin ou `psql` :

```sql
CREATE DATABASE inventory_db;
```

Utilisateur / mot de passe par défaut : `postgres` / `postgres` (modifiable dans `src/main/resources/application.yml`).
Les tables sont créées automatiquement (`ddl-auto: update`).

## 3. Lancer

- Dans IntelliJ : ouvrir `InventoryServiceApplication.java` → bouton ▶ **Run**.
- Ou en ligne de commande : `./mvnw spring-boot:run` (Windows : `mvnw.cmd spring-boot:run`).

Vérifier : http://localhost:8083/actuator/health → `{"status":"UP"}`

> Eureka (`http://localhost:8761/eureka/`) : si le serveur Eureka n'est pas lancé, le service démarre quand même (il affiche juste des erreurs de connexion dans la console). Lancez le discovery-server pour qu'il s'enregistre.

## 4. Modèle

| Entité | Attributs | Relation |
|---|---|---|
| `Produit` | id, nom, categorie, unite | 1 → 0..1 `Stock`, 1 → 0..* `MouvementStock` |
| `Stock` | id, quantite, quantiteMinimale, dateDerniereMiseAJour | OneToOne `Produit` |
| `MouvementStock` | id, type (`ENTREE`/`SORTIE`), quantite, dateMouvement, motif | ManyToOne `Produit` |

**Règle métier** : un mouvement `ENTREE` ajoute la quantité au stock (le stock est créé s'il n'existe pas), un mouvement `SORTIE` la retire et est **refusé** (400) si le stock est insuffisant. `dateDerniereMiseAJour` est mise à jour à chaque mouvement.

## 5. Endpoints

| Méthode | URL | Description |
|---|---|---|
| GET | `/api/produits` (option `?categorie=Engrais`) | Liste des produits |
| GET | `/api/produits/{id}` | Un produit |
| POST | `/api/produits` | Créer |
| PUT | `/api/produits/{id}` | Modifier |
| DELETE | `/api/produits/{id}` | Supprimer (supprime aussi son stock et ses mouvements) |
| GET | `/api/stocks` | Liste des stocks |
| GET | `/api/stocks/{id}` | Un stock |
| GET | `/api/stocks/produit/{produitId}` | Stock d'un produit |
| GET | `/api/stocks/alertes` | Stocks sous la quantité minimale |
| POST | `/api/stocks` | Créer un stock |
| PUT | `/api/stocks/{id}` | Modifier quantité / seuil |
| DELETE | `/api/stocks/{id}` | Supprimer |
| GET | `/api/mouvements` | Historique des mouvements |
| GET | `/api/mouvements/{id}` | Un mouvement |
| GET | `/api/mouvements/produit/{produitId}` | Mouvements d'un produit |
| POST | `/api/mouvements` | Créer un mouvement (met à jour le stock) |
| DELETE | `/api/mouvements/{id}` | Supprimer de l'historique |

## 6. Exemples Postman (Body → raw → JSON)

**Créer un produit** – `POST http://localhost:8083/api/produits`
```json
{ "nom": "Engrais NPK", "categorie": "Engrais", "unite": "kg" }
```

**Créer le stock** – `POST http://localhost:8083/api/stocks`
```json
{ "produitId": 1, "quantite": 50, "quantiteMinimale": 20 }
```

**Sortie de stock** – `POST http://localhost:8083/api/mouvements`
```json
{ "produitId": 1, "type": "SORTIE", "quantite": 40, "motif": "Fertilisation parcelle A" }
```

**Entrée de stock** – `POST http://localhost:8083/api/mouvements`
```json
{ "produitId": 1, "type": "ENTREE", "quantite": 100, "motif": "Livraison fournisseur" }
```

**Erreur si stock insuffisant** (réponse 400) :
```json
{ "status": 400, "erreur": "Bad Request", "message": "Stock insuffisant pour le produit 'Engrais NPK' : disponible = 10.0, demandé = 40.0" }
```

**Stocks en alerte** – `GET http://localhost:8083/api/stocks/alertes`

## 7. Structure

```
src/main/java/com/agrismart/inventory
├── InventoryServiceApplication.java
├── controller/   ProduitController, StockController, MouvementStockController
├── dto/          StockRequest, MouvementRequest
├── entity/       Produit, Stock, MouvementStock, TypeMouvement
├── exception/    GlobalExceptionHandler, ResourceNotFoundException, BusinessException
├── repository/   ProduitRepository, StockRepository, MouvementStockRepository
└── service/      ProduitService, StockService, MouvementStockService
```
