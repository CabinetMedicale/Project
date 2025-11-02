package ma.WhiteLab.entities.finance;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ma.WhiteLab.entities.BaseEntity;
import ma.WhiteLab.entities.cabinet.CabinetMedicale;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class Revenus extends BaseEntity {
    private String titre;
    private String description;
    private Double montant;
    private LocalDateTime date;

    private CabinetMedicale cabinetMedicale;
}
