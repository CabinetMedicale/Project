package ma.WhiteLab.repository.common;

import ma.WhiteLab.entities.BaseEntity;
import ma.WhiteLab.entities.patient.*;
import ma.WhiteLab.entities.enums.*;
import ma.WhiteLab.entities.user.*;
import ma.WhiteLab.entities.planning.*;
import ma.WhiteLab.entities.prescription.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class RowMappers {

    private RowMappers(){}

    // ============================================================
    //  MÉTHODE GLOBALE POUR MAPPER BaseEntity
    // ============================================================
    private static <T extends BaseEntity> T mapBaseEntity(ResultSet rs, T entity) throws SQLException {

        entity.setId(rs.getLong("id"));

        Timestamp dc = rs.getTimestamp("dateCreation");
        if (dc != null) entity.setDateCreation(dc.toLocalDateTime());

        Timestamp dm = rs.getTimestamp("dateMiseAJour");
        if (dm != null) entity.setDateMiseAJour(dm.toLocalDateTime());

        entity.setCreePar(rs.getString("creePar"));
        entity.setModifierPar(rs.getString("modifierPar"));

        return entity;
    }


    // ============================================================
    //  PATIENT
    // ============================================================
    public static Patient mapPatient(ResultSet rs) throws SQLException {

        Patient patientRow = mapBaseEntity(rs, new Patient());

        patientRow.setNom(rs.getString("nom"));
        patientRow.setPrenom(rs.getString("prenom"));
        patientRow.setAdresse(rs.getString("adresse"));
        patientRow.setTelephone(rs.getString("telephone"));
        patientRow.setEmail(rs.getString("email"));

        var dn = rs.getDate("dateNaissance");
        if (dn != null) patientRow.setDateNaissance(dn.toLocalDate());

        patientRow.setSexe(Sexe.valueOf(rs.getString("sexe")));
        patientRow.setAssurance(Assurance.valueOf(rs.getString("assurance")));

        // list vide : chargée dans le service
        patientRow.setAntecedents(new ArrayList<>());

        return patientRow;
    }


    // ============================================================
    //  ANTECEDENT
    // ============================================================
    public static Antecedent mapAntecedent(ResultSet rs) throws SQLException {

        Antecedent a = mapBaseEntity(rs, new Antecedent());

        a.setNom(rs.getString("nom"));
        a.setDescription(rs.getString("description"));

        String cat = rs.getString("categorie");
        if (cat != null) a.setCategorie(CategorieAntecedent.valueOf(cat));

        String niv = rs.getString("niveauRisque");
        if (niv != null) a.setNiveauDeRisk(NiveauDeRisk.valueOf(niv));

        return a;
    }


    // ============================================================
    //  CERTIFICAT
    // ============================================================
    public static Certificat mapCertificat(ResultSet rs) throws SQLException {

        Certificat c = mapBaseEntity(rs, new Certificat());

        Date dDebut = rs.getDate("dateDebut");
        if (dDebut != null) c.setDateDebut(dDebut.toLocalDate());

        Date dFin = rs.getDate("dateFin");
        if (dFin != null) c.setDateFin(dFin.toLocalDate());

        c.setDureeRepos(rs.getInt("dureeRepos"));
        c.setContenu(rs.getString("contenu"));

        return c;
    }


    // ============================================================
    //  DOSSIER MEDICAL
    // ============================================================
    public static DossierMedical mapDossierMedical(ResultSet rs) throws SQLException {

        DossierMedical d = mapBaseEntity(rs, new DossierMedical());

        d.setHistorique(rs.getString("historique"));

        return d;
    }


    // ============================================================
    //  NOTIFICATION
    // ============================================================
    public static Notification mapNotification(ResultSet rs) throws SQLException {

        Notification n = mapBaseEntity(rs, new Notification());

        String titre = rs.getString("titre");
        if (titre != null) n.setTitre(TitreNotification.valueOf(titre));

        n.setMessage(rs.getString("message"));

        Date d = rs.getDate("date");
        if (d != null) n.setDate(d.toLocalDate());

        Time t = rs.getTime("time");
        if (t != null) n.setTime(t.toLocalTime());

        String type = rs.getString("type");
        if (type != null) n.setType(TypeNotification.valueOf(type));

        String priorite = rs.getString("priorite");
        if (priorite != null) n.setPriorite(PrioriteNotification.valueOf(priorite));

        return n;
    }


    // ============================================================
    //  ROLE
    // ============================================================
    public static Role mapRole(ResultSet rs) throws SQLException {

        Role role = mapBaseEntity(rs, new Role());

        String libelle = rs.getString("libelle");
        if (libelle != null) role.setLibelle(RoleR.valueOf(libelle));

        String privs = rs.getString("privileges");
        if (privs != null && !privs.isBlank()) {
            role.setPrivileges(List.of(privs.split(",")));
        }

        return role;
    }


    // ============================================================
    //  UTILISATEUR (générique)
    // ============================================================
    public static <T extends Utilisateur> T mapUtilisateur(ResultSet rs, T utilisateur) throws SQLException {

        utilisateur = mapBaseEntity(rs, utilisateur);

        utilisateur.setNom(rs.getString("nom"));
        utilisateur.setPrenom(rs.getString("prenom"));
        utilisateur.setEmail(rs.getString("email"));
        utilisateur.setAdresse(rs.getString("adresse"));
        utilisateur.setCin(rs.getString("cin"));
        utilisateur.setTelephone(rs.getString("telephone"));

        var dn = rs.getDate("dateNaissance");
        if (dn != null) utilisateur.setDateNaissance(dn.toLocalDate());

        var lastLogin = rs.getTimestamp("lastLoginDate");
        if (lastLogin != null)
            utilisateur.setLastLoginDate(lastLogin.toLocalDateTime());

        utilisateur.setMotDePasse(rs.getString("motDePasse"));

        // relations via services

        return utilisateur;
    }


    // ============================================================
    //  AGENDA MENSUEL
    // ============================================================
    public static AgendaMensuel mapAgendaMensuel(ResultSet rs) throws SQLException {

        AgendaMensuel agenda = mapBaseEntity(rs, new AgendaMensuel());

        String moisStr = rs.getString("mois");
        if (moisStr != null) agenda.setMois(Mois.valueOf(moisStr));

        String joursNonDispo = rs.getString("joursNonDisponible");
        if (joursNonDispo != null && !joursNonDispo.isBlank()) {
            agenda.setJoursNonDisponible(Arrays.asList(joursNonDispo.split(",")));
        } else {
            agenda.setJoursNonDisponible(new ArrayList<>());
        }

        // medecin sera chargé via service si nécessaire
        agenda.setMedecin(null);

        return agenda;
    }


    // ============================================================
    //  RENDEZ-VOUS
    // ============================================================
    public static RendezVous mapRendezVous(ResultSet rs) throws SQLException {

        RendezVous rdv = mapBaseEntity(rs, new RendezVous());

        Timestamp dateTime = rs.getTimestamp("date");
        if (dateTime != null) rdv.setDate(dateTime.toLocalDateTime());

        Time time = rs.getTime("time");
        if (time != null) rdv.setTime(time.toLocalTime());

        rdv.setMotif(rs.getString("motif"));

        String statusStr = rs.getString("status");
        if (statusStr != null && !statusStr.isBlank()) {
            try {
                rdv.setStatus(Status.valueOf(statusStr));
            } catch (IllegalArgumentException e) {
                // Si le status n'existe pas dans l'enum, on laisse null
            }
        }

        rdv.setNoteMedecin(rs.getString("noteMedecin"));

        // dossierMed et consultation seront chargés via service si nécessaire
        rdv.setDossierMed(null);
        rdv.setConsultation(null);

        return rdv;
    }


    // ============================================================
    //  MEDICAMENT
    // ============================================================
    public static Medicament mapMedicament(ResultSet rs) throws SQLException {

        Medicament medicament = mapBaseEntity(rs, new Medicament());

        medicament.setNom(rs.getString("nom"));
        medicament.setLabo(rs.getString("labo"));
        medicament.setType(rs.getString("type"));
        
        String formeStr = rs.getString("forme");
        if (formeStr != null && !formeStr.isBlank()) {
            try {
                medicament.setForme(Forme.valueOf(formeStr));
            } catch (IllegalArgumentException e) {
                // Si la forme n'existe pas dans l'enum, on laisse null
            }
        }

        medicament.setRemboursable(rs.getBoolean("remboursable"));
        medicament.setPrixUnitaire(rs.getDouble("prixUnitaire"));
        medicament.setDescription(rs.getString("description"));

        // prescriptions sera chargée via service si nécessaire
        medicament.setPrescriptions(new ArrayList<>());

        return medicament;
    }


    // ============================================================
    //  ORDONNANCE
    // ============================================================
    public static Ordonnance mapOrdonnance(ResultSet rs) throws SQLException {

        Ordonnance ordonnance = mapBaseEntity(rs, new Ordonnance());

        Date dateOrd = rs.getDate("dateOrdonnance");
        if (dateOrd != null) ordonnance.setDateOrdonnance(dateOrd.toLocalDate());

        // consultation, prescriptions et dossierMedical seront chargés via service si nécessaire
        ordonnance.setConsultation(null);
        ordonnance.setPrescriptions(new ArrayList<>());
        ordonnance.setDossierMedical(null);

        return ordonnance;
    }


    // ============================================================
    //  PRESCRIPTION
    // ============================================================
    public static Prescription mapPrescription(ResultSet rs) throws SQLException {

        Prescription prescription = mapBaseEntity(rs, new Prescription());

        prescription.setQte(rs.getInt("qte"));
        prescription.setFrequence(rs.getString("frequence"));
        prescription.setDuree(rs.getInt("duree"));

        // ordonnance et medicament seront chargés via service si nécessaire
        prescription.setOrdonnance(null);
        prescription.setMedicament(null);

        return prescription;
    }
}
