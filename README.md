# Gestion des Dossiers de Mutuelle avec Spring Batch

## Description du projet

Ce projet est une application batch développée avec **Spring Batch**, qui automatise la gestion des dossiers de mutuelle. Il permet de lire, valider, traiter et calculer les remboursements pour les dossiers médicaux, avant d'écrire les résultats dans une base de données. Le projet suit une architecture modulaire pour une gestion claire et efficace des traitements par lots.

## Fonctionnalités principales

1. **Lecture des dossiers** :
   - Lecture des données JSON avec un `JsonItemReader`.
## Structure des données

### Exemple de fichier JSON d'entrée
```json
{
  "nomAssure": "Ibrahimi",
  "numeroAffiliation": "AFF123456",
  "immatriculation": "IMM098765",
  "lienParente": "fils",
  "montantTotalFrais": 150.0,
  "prixConsultation": 50.0,
  "nombrePiecesJointes": 3,
  "nomBeneficiaire": "Omar",
  "dateDepotDossier": "2024-11-10",
  "traitements": [
    {
      "codeBarre": "1234567890",
      "existe": true,
      "nomMedicament": "Paracétamol",
      "typeMedicament": "Antalgique",
      "prixMedicament": 5.0
    }
  ]
}

2. **Validation des données** :
   - Vérifie que les informations essentielles (nom de l'assuré, numéro d'affiliation, prix, etc.) sont valides et présentes.
   - Vérifie que le prix de la consultation et le montant total des frais sont positifs.

3. **Calcul des remboursements** :
   - Remboursement basé sur le prix de la consultation.
   - Calcul du remboursement des traitements en utilisant des médicaments référentiels.

4. **Écriture des données** :
   - Enregistrement des dossiers traités dans une base de données relationnelle.

5. **Enchaînement des processeurs** :
   - Validation des données (`ValidationProcessor`).
   - Calcul du remboursement (`CalculProcessor`), incluant :
     - `ConsultationProcessor` : Calcule le remboursement de la consultation.
     - `TraitementMappingProcessor` : Mappe les traitements aux médicaments référentiels.
     - `TraitementRemboursementProcessor` : Calcule les remboursements des médicaments.
     - `TotalRemboursementProcessor` : Additionne les remboursements pour calculer le total.

## Technologies utilisées

- **Spring Boot** : Framework principal pour le développement de l'application.
- **Spring Batch** : Gestion des traitements par lots.
- **Spring Data JPA** : Accès aux données et persistance dans la base de données.
- **PostgrSql Database** (ou une autre base de données relationnelle) : Base de données pour stocker les résultats.
- **Lombok** : Réduction du boilerplate avec annotations (@Getter, @Setter, etc.).
- **Maven** : Gestion des dépendances.

## Prérequis

- **JDK 17** ou une version compatible avec Spring Boot.
- **Maven** ou **Gradle** installé.
- Outil de gestion de base de données (H2 intégré ou MySQL/PostgreSQL).
- Un fichier JSON ou CSV contenant les données des dossiers.

## Installation et exécution

1. Clonez le dépôt :
   ```bash
   git clone https://github.com/votre-utili
   sateur/gestion-dossiers-mutuelle.git
   cd gestion-dossiers-mutuelle
2. Installer les dépendances Maven:
   ```bash
   mvn clean install
3. Modifications des fichiers de configuration:
   Selon votre environnement, vous pourriez avoir besoin de modifier certains fichiers de configuration :
   Le fichier application.properties pour configurer la base de données ou d'autres paramètres.
   Le chemin vers les fichiers JSON ou CSV dans le projet.
4. Exécuter l'application avec Maven:
   ```bash
   mvn spring-boot:run
## Auteur

Projet développé dans le cadre d'un mini-projet universitaire sur l'architecture logicielle (2024/2025).
