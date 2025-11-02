package ma.WhiteLab.entities.planning;

import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ma.WhiteLab.entities.BaseEntity;
import ma.WhiteLab.entities.consultation.Consultation;
import ma.WhiteLab.entities.enums.Status;
import ma.WhiteLab.entities.patient.DossierMedical;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class RendezVous extends BaseEntity {
    private LocalDateTime date;
    private LocalTime time;
    private String motif;
    private Status status;
    private String noteMedecin;

    private DossierMedical dossierMed;
    private Consultation consultation;
}
