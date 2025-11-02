package ma.WhiteLab.entities.consultation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ma.WhiteLab.entities.BaseEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class InterventionMedecin extends BaseEntity {
    private double prixDePatient;
    private int numDent;

    private Consultation consultation;
    private ActeMedical acteMedical;
}
