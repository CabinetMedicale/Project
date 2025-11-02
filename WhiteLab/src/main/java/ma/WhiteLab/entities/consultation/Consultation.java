package ma.WhiteLab.entities.consultation;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ma.WhiteLab.entities.BaseEntity;
import ma.WhiteLab.entities.enums.StatusConsultation;
import ma.WhiteLab.entities.finance.Facture;
import ma.WhiteLab.entities.patient.Certificat;
import ma.WhiteLab.entities.patient.DossierMedical;
import ma.WhiteLab.entities.planning.RendezVous;
import ma.WhiteLab.entities.prescription.Ordonnance;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class Consultation extends BaseEntity {
    private LocalDateTime date;
    private StatusConsultation status;
    private String notes;
    private String observationsMedecin;

    private List<Ordonnance> ordonnances;
    private Certificat certificats;
    private List<InterventionMedecin> ims;
    private List<Facture> factures;
    private List<RendezVous> rendezVouses;
    private DossierMedical dossierMedical;
}
