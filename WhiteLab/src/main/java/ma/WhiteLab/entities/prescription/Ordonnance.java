package ma.WhiteLab.entities.prescription;

import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ma.WhiteLab.entities.BaseEntity;
import ma.WhiteLab.entities.consultation.Consultation;
import ma.WhiteLab.entities.patient.DossierMedical;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class Ordonnance extends BaseEntity {
    private LocalDate dateOrdonnance;

    private Consultation consultation;
    private List<Prescription> prescriptions;
    private DossierMedical dossierMedical;
}
