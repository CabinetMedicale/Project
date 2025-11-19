package ma.WhiteLab.mvc.dto;

import lombok.*;
import ma.WhiteLab.entities.enums.StatusConsultation;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class ConsultationDTO {
    private String dateFormatee;
    private StatusConsultation status;
    private String notes;
    private String dateCreationFormatee;
}

