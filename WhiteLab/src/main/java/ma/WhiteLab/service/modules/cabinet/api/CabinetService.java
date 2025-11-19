package ma.WhiteLab.service.modules.cabinet.api;

import java.util.List;
import ma.WhiteLab.mvc.dto.CabinetDTO;

public interface CabinetService {

    /**
     * Récupère les cabinets ajoutés aujourd'hui,
     * triés par ordre de création (plus récent -> plus ancien),
     * et les expose sous forme de CabinetDTO (nom, email, téléphone, date formatée).
     */
    List<CabinetDTO> getTodayCabinetsAsDTO();
}

