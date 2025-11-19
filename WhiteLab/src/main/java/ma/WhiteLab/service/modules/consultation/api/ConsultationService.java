package ma.WhiteLab.service.modules.consultation.api;

import java.util.List;
import ma.WhiteLab.mvc.dto.ConsultationDTO;

public interface ConsultationService {

    /**
     * Récupère les consultations ajoutées aujourd'hui,
     * triées par ordre de création (plus récent -> plus ancien),
     * et les expose sous forme de ConsultationDTO (date, status, notes, date formatée).
     */
    List<ConsultationDTO> getTodayConsultationsAsDTO();
}

