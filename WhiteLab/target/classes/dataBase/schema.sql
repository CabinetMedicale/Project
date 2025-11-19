-- FULL SCHEMA (users/staff/medecin/secretaire + roles ...
CREATE TABLE IF NOT EXISTS utilisateur (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  last_modification_date TIMESTAMP NULL,
  created_by VARCHAR(64),
  updated_by VARCHAR(64),
  nom VARCHAR(120) NOT NULL,
  prenom VARCHAR(120) NOT NULL,
  email VARCHAR(160) NOT NULL UNIQUE,
  tel VARCHAR(40),
  adresse VARCHAR(255),
  cin VARCHAR(32) UNIQUE,
  sexe ENUM('HOMME','FEMME','AUTRE') DEFAULT 'AUTRE',
  login VARCHAR(64) NOT NULL UNIQUE,
  password_hash VARCHAR(120) NOT NULL,
  last_login_date TIMESTAMP NULL,
  date_naissance DATE NULL,
  actif BOOLEAN NOT NULL DEFAULT TRUE
);
CREATE TABLE IF NOT EXISTS staff (
  id BIGINT PRIMARY KEY,
  salaire DECIMAL(12,2) DEFAULT 0,
  prime DECIMAL(12,2) DEFAULT 0,
  date_recrutement DATE,
  solde_conge INT DEFAULT 0,
  CONSTRAINT fk_staff_user FOREIGN KEY (id) REFERENCES utilisateur(id) ON DELETE CASCADE
);
CREATE TABLE IF NOT EXISTS medecin (
  id BIGINT PRIMARY KEY,
  specialite VARCHAR(120),
  agenda_medecin TEXT,
  CONSTRAINT fk_med_staff FOREIGN KEY (id) REFERENCES staff(id) ON DELETE CASCADE
);
CREATE TABLE IF NOT EXISTS secretaire (
  id BIGINT PRIMARY KEY,
  num_cnss VARCHAR(64),
  commission DECIMAL(12,2) DEFAULT 0,
  CONSTRAINT fk_sec_staff FOREIGN KEY (id) REFERENCES staff(id) ON DELETE CASCADE
);
CREATE TABLE IF NOT EXISTS role (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  libelle VARCHAR(80) NOT NULL UNIQUE
);
CREATE TABLE IF NOT EXISTS utilisateur_role (
  utilisateur_id BIGINT NOT NULL,
  role_id BIGINT NOT NULL,
  PRIMARY KEY (utilisateur_id, role_id),
  CONSTRAINT fk_ur_user FOREIGN KEY (utilisateur_id) REFERENCES utilisateur(id) ON DELETE CASCADE,
  CONSTRAINT fk_ur_role FOREIGN KEY (role_id) REFERENCES role(id) ON DELETE CASCADE
);

-- Table Patient (must be created before dossier_medical)
CREATE TABLE IF NOT EXISTS patient (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  last_modification_date TIMESTAMP NULL,
  created_by VARCHAR(64),
  updated_by VARCHAR(64),
  nom VARCHAR(120) NOT NULL,
  prenom VARCHAR(120) NOT NULL,
  sexe ENUM('HOMME','FEMME','AUTRE') DEFAULT 'AUTRE',
  email VARCHAR(160),
  date_naissance DATE,
  adresse VARCHAR(255),
  telephone VARCHAR(40),
  assurance ENUM('CNSS','CNOPS','Autre','Aucune') DEFAULT 'Aucune'
);

-- Table Dossier Medical (must be created before consultation)
CREATE TABLE IF NOT EXISTS dossier_medical (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  last_modification_date TIMESTAMP NULL,
  created_by VARCHAR(64),
  updated_by VARCHAR(64),
  historique TEXT,
  date_creation_dossier DATE,
  patient_id BIGINT NOT NULL,
  medecin_id BIGINT,
  CONSTRAINT fk_dossier_patient FOREIGN KEY (patient_id) REFERENCES patient(id) ON DELETE CASCADE,
  CONSTRAINT fk_dossier_medecin FOREIGN KEY (medecin_id) REFERENCES medecin(id) ON DELETE SET NULL
);

-- Table Cabinet Medical
CREATE TABLE IF NOT EXISTS cabinet_medicale (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  last_modification_date TIMESTAMP NULL,
  created_by VARCHAR(64),
  updated_by VARCHAR(64),
  nom VARCHAR(255) NOT NULL,
  email VARCHAR(160),
  logo VARCHAR(255),
  categorie VARCHAR(120),
  tel1 VARCHAR(40),
  tel2 VARCHAR(40),
  site_web VARCHAR(255),
  instagram VARCHAR(255),
  facebook VARCHAR(255),
  description TEXT,
  proprietaire_id BIGINT,
  CONSTRAINT fk_cabinet_proprietaire FOREIGN KEY (proprietaire_id) REFERENCES utilisateur(id) ON DELETE SET NULL
);

-- Table Consultation (must be created after dossier_medical)
CREATE TABLE IF NOT EXISTS consultation (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  last_modification_date TIMESTAMP NULL,
  created_by VARCHAR(64),
  updated_by VARCHAR(64),
  date_consultation TIMESTAMP NOT NULL,
  status ENUM('PLANIFIEE', 'EN_COURS', 'TERMINEE', 'ANNULEE') DEFAULT 'PLANIFIEE',
  notes TEXT,
  observations_medecin TEXT,
  dossier_medical_id BIGINT,
  CONSTRAINT fk_consultation_dossier FOREIGN KEY (dossier_medical_id) REFERENCES dossier_medical(id) ON DELETE SET NULL
);

-- ...etc
