package ma.WhiteLab.entities.patient;

import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ma.WhiteLab.entities.BaseEntity;
import ma.WhiteLab.entities.consultation.Consultation;
import ma.WhiteLab.entities.finance.SuiviFinancier;
import ma.WhiteLab.entities.personnel.Medecin;
import ma.WhiteLab.entities.planning.RendezVous;
import ma.WhiteLab.entities.prescription.Ordonnance;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class DossierMedical extends BaseEntity {
    private String historique;
    private Date dateCreation;

    private Patient pat;
    private Medecin medcine;
    private List<RendezVous> rendezVous;
    private List<Consultation> consultations;
    private List<Ordonnance> ordonnance;
    private List<Certificat> certificat;
    private SuiviFinancier suiviFinancier;
}
