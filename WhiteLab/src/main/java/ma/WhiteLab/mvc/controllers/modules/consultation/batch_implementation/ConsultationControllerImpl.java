package ma.WhiteLab.mvc.controllers.modules.consultation.batch_implementation;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.WhiteLab.mvc.controllers.modules.consultation.api.ConsultationController;
import ma.WhiteLab.mvc.dto.ConsultationDTO;
import ma.WhiteLab.service.modules.consultation.api.ConsultationService;

@Data @AllArgsConstructor @NoArgsConstructor
public class ConsultationControllerImpl implements ConsultationController {

    private ConsultationService service;

    @Override
    public void showRecentConsultations() {
        List<ConsultationDTO> dtos = service.getTodayConsultationsAsDTO();
        if (dtos.isEmpty()) {
            System.out.println("Aucune consultation ajoutée aujourd'hui.");
            return;
        }
        System.out.println("=== Consultations ajoutées aujourd'hui ===");
        dtos.forEach(dto -> System.out.printf("- Date: %s | Status: %s | Notes: %s | ajoutée le %s%n",
                dto.getDateFormatee(), dto.getStatus(), dto.getNotes(), dto.getDateCreationFormatee()));
    }
}

