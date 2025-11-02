package ma.WhiteLab.entities.patient;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ma.WhiteLab.entities.BaseEntity;
import ma.WhiteLab.entities.consultation.Consultation;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class Certificat extends BaseEntity {
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private int dureeRepos;
    private String contenu;

    private Consultation consultation;
    private DossierMedical dossierMedical;
}
