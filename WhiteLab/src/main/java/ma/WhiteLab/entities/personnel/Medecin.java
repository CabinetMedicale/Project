package ma.WhiteLab.entities.personnel;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ma.WhiteLab.entities.patient.DossierMedical;
import ma.WhiteLab.entities.planning.AgendaMensuel;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class Medecin extends Staff {
    private String specialite;
    private AgendaMensuel agendaDocteur;

    private List<DossierMedical> dossiersMed;
}
