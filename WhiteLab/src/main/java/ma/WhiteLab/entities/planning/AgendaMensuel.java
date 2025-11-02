package ma.WhiteLab.entities.planning;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ma.WhiteLab.entities.BaseEntity;
import ma.WhiteLab.entities.enums.Mois;
import ma.WhiteLab.entities.personnel.Medecin;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class AgendaMensuel extends BaseEntity {
    private Mois mois;
    private List<String> joursNonDisponible;

    private Medecin medecin;
}
