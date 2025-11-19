-- Jeu de données de Test
INSERT INTO role(libelle) VALUES ('ADMIN'), ('MEDECIN'), ('SECRETAIRE')
ON DUPLICATE KEY UPDATE libelle=VALUES(libelle);

-- Sample Cabinet Data
INSERT INTO cabinet_medicale (nom, email, tel1, tel2, categorie, description, creation_date) VALUES
('Cabinet Dentaire WhiteLab', 'contact@whitelab.ma', '0522-123456', '0522-123457', 'Dentaire', 'Cabinet dentaire moderne', NOW()),
('Clinique Dentaire Rabat', 'info@clinique-rabat.ma', '0537-789012', NULL, 'Dentaire', 'Clinique dentaire spécialisée', NOW())
ON DUPLICATE KEY UPDATE nom=VALUES(nom);

-- Sample Patient Data
INSERT INTO patient (nom, prenom, sexe, email, date_naissance, telephone, adresse, assurance, creation_date) VALUES
('Amal', 'Z.', 'FEMME', 'amal@example.com', '1995-05-12', '0611-111111', 'Rabat', 'CNSS', NOW()),
('Omar', 'B.', 'HOMME', 'hassan@example.com', '1989-09-23', '0622-222222', 'Salé', 'CNOPS', NOW()),
('Nour', 'C.', 'FEMME', 'nour@example.com', '2000-02-02', '0633-333333', 'Témara', 'Autre', NOW()),
('Youssef', 'D.', 'HOMME', 'youssef@example.com', '1992-11-01', '0644-444444', 'Kénitra', 'Aucune', NOW())
ON DUPLICATE KEY UPDATE nom=VALUES(nom);

-- Sample Consultation Data (requires dossier_medical to exist first)
-- Note: Insert these after creating dossier_medical records
-- Example (uncomment and adjust IDs after creating dossiers):
-- INSERT INTO consultation (date_consultation, status, notes, observations_medecin, dossier_medical_id, creation_date) VALUES
-- (NOW(), 'TERMINEE', 'Consultation de routine', 'Patient en bonne santé dentaire', 1, NOW()),
-- (NOW(), 'EN_COURS', 'Détartrage', 'En cours de traitement', 1, NOW()),
-- (DATE_ADD(NOW(), INTERVAL 2 HOUR), 'PLANIFIEE', 'Contrôle annuel', NULL, 2, NOW())
-- ON DUPLICATE KEY UPDATE notes=VALUES(notes);
-- etc
