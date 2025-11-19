package ma.WhiteLab.mvc.controllers.modules.consultation.swing_implementation;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.WhiteLab.mvc.controllers.modules.consultation.api.ConsultationController;
import ma.WhiteLab.mvc.dto.ConsultationDTO;
import ma.WhiteLab.mvc.ui.modules.consultation.ConsultationView;
import ma.WhiteLab.service.modules.consultation.api.ConsultationService;

@Data @AllArgsConstructor @NoArgsConstructor
public class ConsultationControllerImpl implements ConsultationController {

    private ConsultationService service;

    @Override
    public void showRecentConsultations() {
        List<ConsultationDTO> dtos = service.getTodayConsultationsAsDTO();
        ConsultationView.showAsync(dtos);
    }
}

