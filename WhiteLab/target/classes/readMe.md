# 🦷 WhiteLab 

---
### Application de Gestion d’un Cabinet Dentaire

---

> Projet Java – EMSI Rabat  
> Réalisé par une équipe de 4 étudiants sous la supervision de **Pr. Omar EL MIDAOUI**

---

## 📖 Sommaire
1. [Contexte du projet](#-contexte-du-projet)
2. [Objectifs du projet](#-objectifs-du-projet)
3. [Technologies utilisées](#-technologies-utilisées)
4. [Architecture du projet](#-architecture-du-projet)
5. [Organisation du travail (Scrum / Modules)](#-organisation-du-travail)
6. [Fonctionnalités principales](#-fonctionnalités-principales)
7. [Base de données](#-base-de-données)
8. [Structure du code source](#-structure-du-code-source)
9. [Charte graphique & UI/UX](#-charte-graphique--uiux)
10. [Procédure d’installation et d’exécution](#-procédure-dinstallation-et-dexécution)
11. [Scénario de démonstration](#-scénario-de-démo)
12. [Conclusion et perspectives](#-conclusion-et-perspectives)

---

## 🩺 Contexte du projet
La digitalisation des cabinets médicaux devient une nécessité pour améliorer la gestion des dossiers patients, la planification des rendez-vous et le suivi des interventions.  
Dans ce contexte, **DentalTech** vise à fournir une **application de bureau (Java Swing)** permettant au médecin dentiste et à sa secrétaire de gérer efficacement les aspects administratifs, médicaux et financiers du cabinet.

---

## 🎯 Objectifs du projet
- **Automatiser** la gestion des patients et de leurs dossiers médicaux.  
- **Optimiser** la planification des rendez-vous et la facturation.  
- **Assurer** un suivi complet du parcours médical du patient (consultations, ordonnances, certificats).  
- **Offrir** une interface ergonomique, intuitive et moderne via Swing.  
- **Appliquer** les principes SOLID et la conception en couches (MVC + DAO + Services).  

---

## 🧰 Technologies utilisées
| Catégorie | Technologies |
|------------|---------------|
| Langage principal | Java SE 23 |
| Framework graphique | Java Swing |
| Base de données | MySQL 8.0 |
| ORM / DAO | JDBC |
| Outils de build | Maven |
| Librairies | Lombok, JFreeChart, iTextPDF |
| IDE | IntelliJ IDEA Ultimate 2025 |
| Méthodologie | Agile (Scrum) |

---

## 🏗️ Architecture du projet
L’application respecte une architecture **multi-couche MVC** :

```
DentalTech/
├─ config/          → Configuration et injection de dépendances
├─ entities/        → Entités métiers (Patient, Dossier, RendezVous, etc.)
├─ repository/      → Accès aux données (DAO / JDBC)
├─ service/         → Logique métier (PatientService, FactureService…)
├─ mvc/
│  ├─ controllers/  → Contrôleurs des modules UI
│  ├─ dto/          → Objets de transfert de données (DTO)
│  └─ ui/           → Interface utilisateur (Swing)
│     ├─ common/        → palette : Composants réutilisables
│     ├─ rdv/           → Vues module Rendez-vous
│     ├─ caisse/        → Vues module Caisse
│     ├─ dashboard/     → Vues tableau de bord
│     ├─ .../           → Vues d'autres modules
│     └─ patient/       → Vues module Patient
└─ common/          → Exceptions, utilitaires, validateurs
```

Chaque couche communique uniquement avec la couche inférieure (respect du principe **DIP – Dependency Inversion Principle**).

---


##  Fonctionnalités principales
###  Espace Médecin :
- Accès complet au dossier patient
- Ajout et consultation des interventions
- Édition d’ordonnances et certificats
- ...

###  Espace Secrétaire :
- Gestion des rendez-vous
- Gestion des paiements / factures
- Consultation du planning global
- ...

###  Fonctions transverses :
- Authentification / rôles
- Statistiques (patients / revenus / interventions)
- Export PDF des rapports
- Sauvegarde et restauration de la base
- ...

---

## Base de données
- **Script SQL** : `schema.sql` → création du schéma complet (utilisateur, staff, patient, cabinet_medicale, consultation, dossier_medical, etc.).  
- **Jeu de données** : `seed.sql` → initialisation (patients, cabinets, rendez-vous, factures ...etc).  
- **Connexion JDBC** : paramètres stockés dans `db.properties`.

**Exemple (db.properties)** :
```properties
db.url=jdbc:mysql://localhost:3306/DentalTech
db.user=root
db.password=
```

---

## 📁 Structure du code source
Chaque package contient :
- `entities` → POJOs (avec Lombok)
- `repository` → Interfaces DAO + impl JDBC/file-based/in-memory
- `service` → Logique métier
- `mvc` → Présentation (Swing)
- `common` → Outils, exceptions, validateurs
- `config` → Fabriques et contexte applicatif

**Modules disponibles :**
- ✅ Patient (gestion des patients)
- ✅ Cabinet (gestion des cabinets médicaux)
- ✅ Consultation (gestion des consultations)

---

## 🎨 Charte graphique & UI/UX
- **Palette de couleurs :**
  - Bleu clair (#4DB6AC)
  - Blanc (#FFFFFF)
  - Gris clair (#E0E0E0)
  - Vert validation (#81C784)
  - Rouge alerte (#E57373)

- **Polices :**
  - `Poppins` pour les titres
  - `Roboto` pour les textes

- **Règles UI :**
  - Fenêtres centrées
  - Champs bien espacés
  - Icônes cohérentes (dossier *static/icones/*)
  - Respect de la hiérarchie visuelle

---

## ⚙️ Procédure d’installation et d’exécution

1. **Cloner le projet :**
   ```bash
   git clone https://github.com/nom-utilisateur/DentalTech.git
   cd DentalTech
   ```

2. **Configurer la base de données :**
   - Importer le fichier `schema.sql` dans MySQL
   - Modifier `src/main/resources/db.properties`

3. **Compiler et exécuter :**
   ```bash
   mvn clean install
   java -jar target/DentalTech-1.0-SNAPSHOT-shaded.jar
   ```

4. **Connexion par défaut (test) :**
   ```
   Utilisateur : admin@DentalTech.ma
   Mot de passe : admin123
   ```

---

## 🧪 Scénario de démo

1. **Connexion** avec un compte secrétaire  
2. **Ajout d’un nouveau patient**  
3. **Création d’un rendez-vous**  
4. **Saisie d’une consultation + ordonnance**  
5. **Génération d’une facture PDF**  
6. **Affichage du tableau de bord** (statistiques patients / revenus)

---
---

## 👨‍💻 Équipe projet
| Étudiant | Rôle | Email |
|-----------|------|------|
| Étudiant 1 | ...  | ... |
| Étudiant 2 | ...  | ... |
| Étudiant 3 | ...  | ... |
| Étudiant 4 | ...  | ... |

---

## 🆕 Développements récents

### Modules Cabinet et Consultation
**Auteur : Amine Charro**

Ajout de deux nouveaux modules complets suivant l'architecture MVC existante :

- **Module Cabinet** : Gestion complète des cabinets médicaux
  - Repository (file-based et in-memory)
  - Service layer avec DTO
  - Contrôleurs (batch et Swing)
  - Interface utilisateur Swing
  - Schéma MySQL et données de test

- **Module Consultation** : Gestion des consultations médicales
  - Repository (file-based et in-memory)
  - Service layer avec DTO
  - Contrôleurs (batch et Swing)
  - Interface utilisateur Swing
  - Schéma MySQL avec relations aux dossiers médicaux

Les deux modules respectent la même structure que le module Patient, avec support pour le stockage fichier (PSV) et base de données MySQL.

---

© 2025 – **DentalTech | EMSI Rabat**  
Encadré par **Pr. Omar El Midaoui**
