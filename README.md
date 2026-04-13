# parcel-service — SunuDelivery

Microservice de gestion des colis pour la plateforme SunuDelivery.  
Développé avec **Spring Boot 4.0.2** · **Java 21** · **MySQL** · **Kafka**

---

## Structure du projet

```
src/main/java/sn/edu/ept/parcel/
├── ParcelServiceApplication.java
├── controller/
│   └── ParcelController.java
├── service/
│   ├── ParcelService.java
│   └── ParcelServiceImpl.java
├── repository/
│   └── ParcelRepository.java
├── entities/
│   ├── Parcel.java
│   └── Address.java
├── enums/
│   ├── ParcelStatus.java
│   └── ParcelType.java
├── dto/
│   ├── ParcelRequest.java
│   ├── ParcelResponse.java
│   └── AddressDto.java
├── mapper/
│   └── ParcelMapper.java
├── kafka/
│   ├── KafkaProducerConfig.java
│   ├── KafkaTopics.java
│   ├── ParcelEventProducer.java
│   └── events/
│       ├── ParcelCreatedEvent.java
│       └── ParcelStatusUpdatedEvent.java
└── exception/
    ├── ParcelNotFoundException.java
    ├── InvalidStatusTransitionException.java
    └── GlobalExceptionHandler.java
```

---

## Endpoints REST

| Méthode | URL | Description |
|---------|-----|-------------|
| POST | `/api/parcels` | Créer un colis |
| GET | `/api/parcels/{id}` | Récupérer un colis |
| GET | `/api/parcels` | Tous les colis (admin) |
| GET | `/api/parcels/client/{clientId}` | Colis d'un client |
| PATCH | `/api/parcels/{id}/statut?nouveauStatut=RECUPERE` | Changer le statut |
| DELETE | `/api/parcels/{id}` | Supprimer (si EN_ATTENTE) |

---

## Cycle de vie d'un colis

```
EN_ATTENTE → RECUPERE → EN_TRANSIT → LIVRE
                     ↘ EN_GROUPAGE ↗
                                  ↘ ECHEC_LIVRAISON → EN_TRANSIT
                                                    ↘ RETOURNE
EN_ATTENTE → ANNULE
```

---

## Topics Kafka publiés

| Topic | Déclencheur | Consommateurs |
|-------|-------------|---------------|
| `parcel.created` | Création d'un colis | order-service, grouping-service |
| `parcel.status.updated` | Changement de statut | notification-service, tracking-service |

---

## Lancer le projet en local

### 1. Démarrer Kafka + MySQL
```bash
docker-compose up -d
```

### 2. Variables d'environnement (profil dev)
```bash
export PARCEL_DB_USER=root
export PARCEL_DB_PWD=root
```

### 3. Lancer le service
```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

### 4. Swagger UI
```
http://localhost:8083/swagger-ui.html
```

### 5. Kafka UI (visualiser les topics)
```
http://localhost:8090
```

---

## Variables d'environnement

| Variable | Description | Défaut |
|----------|-------------|--------|
| `PARCEL_PROFILE` | Profil Spring actif | `dev` |
| `PARCEL_PORT` | Port du service | `8083` |
| `PARCEL_DB_URL` | URL JDBC MySQL | — |
| `PARCEL_DB_USER` | Utilisateur MySQL | `root` |
| `PARCEL_DB_PWD` | Mot de passe MySQL | `root` |
| `KAFKA_BOOTSTRAP_SERVERS` | Adresse Kafka | `localhost:9092` |
| `EUREKA_URL` | URL Eureka | `http://localhost:8761/eureka` |
| `CONFIG_SERVER_URL` | URL Config Server | `http://localhost:8888` |

---

*SunuDelivery — Projet Services Web · École Polytechnique de Thiès · DIC2*
