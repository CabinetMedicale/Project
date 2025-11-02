package ma.WhiteLab.entities.finance;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ma.WhiteLab.entities.BaseEntity;
import ma.WhiteLab.entities.enums.PromoStatus;
import ma.WhiteLab.entities.enums.Status;
import ma.WhiteLab.entities.patient.DossierMedical;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class SuiviFinancier extends BaseEntity {
    private float totalDesActes;
    private float totalPaye;
    private float credit;
    private float debit;
    private PromoStatus enPromo;
    private Status status;

    private List<Facture> listeFactures;
    private DossierMedical dossierMedical;
}
