package ma.WhiteLab.service.modules.consultation.baseImplementation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.WhiteLab.entities.consultation.Consultation;
import ma.WhiteLab.mvc.dto.ConsultationDTO;
import ma.WhiteLab.repository.modules.consultation.api.ConsultationRepository;
import ma.WhiteLab.service.modules.consultation.api.ConsultationService;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConsultationServiceImpl implements ConsultationService {

    private ConsultationRepository repository;

    /**
     * Formattage de date
     * @param dt : date Non Formatée
     * @return  date formatée
     */
    private static String formatDate(java.time.LocalDateTime dt) {
        if (dt == null) return "";
        return dt.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }

    @Override
    public List<ConsultationDTO> getTodayConsultationsAsDTO() {
        LocalDate today = LocalDate.now();
        return repository.findAll().stream()
                .filter(c -> c.getDateCreation() != null && c.getDateCreation().toLocalDate().equals(today))
                .sorted(Comparator.comparing(Consultation::getDateCreation).reversed())
                .map(c -> ConsultationDTO.builder()
                        .dateFormatee(formatDate(c.getDate()))
                        .status(c.getStatus())
                        .notes(c.getNotes() == null ? "" : c.getNotes().trim())
                        .dateCreationFormatee(formatDate(c.getDateCreation()))
                        .build())
                .collect(Collectors.toList());
    }
}

