# 🎓 Système Avancé de Gestion des Étudiants (Student Management)
### *Spring Boot 3.3.4 • Spring Data JPA • REST API • Springdoc OpenAPI 3 / Swagger • JUnit 5 & Mockito*

---

## 🌟 Ce qui distingue ce projet (Points forts académiques & professionnels)

Contrairement aux réalisations classiques d'étudiants se limitant aux exemples bruts, ce projet intègre les meilleures pratiques de développement logiciel d'entreprise :

1. **Architecture Propre & Découplée** :
   * **Injection par constructeur** (conformément aux recommandations officielles de Spring) garantissant l'immutabilité et une testabilité exemplaire.
   * Gestion transactionnelle fine avec `@Transactional` et `@Transactional(readOnly = true)`.
2. **Documentation Interactive OpenAPI 3 / Swagger enrichie** :
   * Bean de configuration centralisé [`OpenApiConfig`](src/main/java/com/example/studentmanagement/config/OpenApiConfig.java) avec métadonnées complètes (titre, description pédagogique, licences, contacts).
   * Annotations riches (`@Tag`, `@Operation`, `@ApiResponse`, `@Parameter`, `@Schema`) fournissant des descriptions claires et des exemples JSON dans le Swagger UI.
3. **Robustesse & Validation des données** :
   * Validation automatique des requêtes entrantes avec Jakarta Validation (`@NotBlank`, `@Past`).
   * [`GlobalExceptionHandler`](src/main/java/com/example/studentmanagement/exception/GlobalExceptionHandler.java) (`@RestControllerAdvice`) interceptant les erreurs de validation ou exceptions pour renvoyer des réponses JSON claires et normalisées.
4. **Initialisation automatique intelligente (Seed Data)** :
   * [`DataLoader`](src/main/java/com/example/studentmanagement/config/DataLoader.java) pré-remplit la base avec les données d'exemple du TP (notamment Mohamed LACHGAR né en 1985) si la base est vide. Ainsi, Swagger et l'API sont testables immédiatement sans saisie manuelle.
5. **Couverture de Tests Rigoureuse** :
   * Tests du contrôleur avec JUnit 5 et Mockito ([`StudentControllerTest`](src/test/java/com/example/studentmanagement/StudentControllerTest.java)) couvrant le succès et les cas d'erreur (404 Not Found, 204 No Content, 201 Created).
   * Tests unitaires dédiés pour la couche métier ([`StudentServiceTest`](src/test/java/com/example/studentmanagement/StudentServiceTest.java)).
   * Base de données en mémoire H2 configurée pour les tests afin de pouvoir exécuter `mvn test` sans dépendance externe.
6. **Support Docker Compose** :
   * Fichier [`docker-compose.yml`](docker-compose.yml) permettant de lancer une instance MySQL 8.0 prête à l'emploi en une commande.

---

## 🏗️ Architecture du Projet

```text
student-management/
├── pom.xml                                      # Configuration Maven (Java 21, Spring Boot 3.3.4, SpringDoc, MySQL, H2)
├── docker-compose.yml                           # Conteneur MySQL 8.0 préconfiguré
├── src/
│   ├── main/
│   │   ├── java/com/example/studentmanagement/
│   │   │   ├── StudentManagementApplication.java# Point d'entrée Spring Boot
│   │   │   ├── config/
│   │   │   │   ├── OpenApiConfig.java           # Configuration avancée Swagger / OpenAPI
│   │   │   │   └── DataLoader.java              # CommandLineRunner pour injection des données de démo
│   │   │   ├── controller/
│   │   │   │   └── StudentController.java       # Contrôleur REST annoté OpenAPI
│   │   │   ├── entity/
│   │   │   │   └── Student.java                 # Entité JPA avec contraintes de validation & schémas
│   │   │   ├── exception/
│   │   │   │   └── GlobalExceptionHandler.java  # Gestionnaire global d'exceptions (@RestControllerAdvice)
│   │   │   ├── repository/
│   │   │   │   └── StudentRepository.java       # JpaRepository avec requête JPQL personnalisée
│   │   │   └── service/
│   │   │       └── StudentService.java          # Service métier transactionnel
│   │   └── resources/
│   │       └── application.properties           # Configuration MySQL (studentdb) & Swagger
│   └── test/
│       ├── java/com/example/studentmanagement/
│       │   ├── StudentControllerTest.java       # Tests unitaires du contrôleur (Mockito/JUnit 5)
│       │   └── StudentServiceTest.java          # Tests unitaires de la logique métier
│       └── resources/
│           ├── application.properties           # Configuration H2 pour les tests automatisés
│           └── META-INF/mockito-extensions/
│               └── org.mockito.plugins.MockMaker# Configuration Subclass MockMaker (compatible Java 21)
```

---

## 🚀 Démarrage Rapide

### 1. Base de données MySQL

Vous pouvez utiliser MySQL local (XAMPP / WampServer / MySQL Workbench) :
```sql
CREATE DATABASE IF NOT EXISTS studentdb;
```
*Ou* démarrer instantanément le conteneur Docker fourni :
```bash
docker compose up -d
```

### 2. Lancement de l'Application

Via Maven :
```bash
mvn spring-boot:run
```

Ou en exécutant directement le fichier JAR généré :
```bash
java -jar target/student-management-0.0.1-SNAPSHOT.jar
```

---

## 📖 Documentation Interactive Swagger UI

Une fois l'application démarrée, ouvrez votre navigateur à l'adresse :
👉 **http://localhost:8080/swagger-ui.html**
*(ou http://localhost:8080/swagger-ui/index.html)*

Pour consulter la spécification OpenAPI brute (JSON) :
👉 **http://localhost:8080/v3/api-docs**

---

## 📡 Endpoints REST de l'API

| Méthode | Endpoint | Description | Code HTTP Attendu |
| :--- | :--- | :--- | :--- |
| **POST** | `/students/save` | Créer ou modifier un étudiant | `201 Created` |
| **GET** | `/students/all` | Récupérer la liste de tous les étudiants | `200 OK` |
| **DELETE** | `/students/delete/{id}` | Supprimer un étudiant par son ID | `204 No Content` / `404 Not Found` |
| **GET** | `/students/count` | Obtenir le nombre total d'étudiants | `200 OK` |
| **GET** | `/students/byYear` | Nombre d'étudiants regroupés par année de naissance | `200 OK` |

---

## 🧪 Exécution des Tests Unitaires

Pour exécuter l'ensemble de la suite de tests automatisée (12 tests) :
```bash
mvn test
```

### Résumé des tests exécutés :
* **StudentControllerTest** :
  1. `testSaveStudent` : vérifie la création et le code `201 Created`.
  2. `testDeleteStudent` : vérifie la suppression avec succès et le code `204 No Content`.
  3. `testDeleteStudentNotFound` : vérifie le comportement d'erreur `404 Not Found`.
  4. `testFindAllStudents` : vérifie la récupération de la liste et le code `200 OK`.
  5. `testCountStudents` : vérifie le comptage et le code `200 OK`.
  6. `testFindByYear` : vérifie la récupération des données statistiques.
* **StudentServiceTest** :
  1. `testSave` : persistance de l'entité.
  2. `testDeleteWhenStudentExists` : suppression et vérification d'appel repository.
  3. `testDeleteWhenStudentNotFound` : gestion du cas inexistant.
  4. `testFindAll` : vérification du listing.
  5. `testCountStudents` : vérification du décompte.
  6. `testFindNbrStudentByYear` : vérification de l'agrégation JPQL.
