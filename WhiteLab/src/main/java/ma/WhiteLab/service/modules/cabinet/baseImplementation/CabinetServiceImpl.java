package ma.WhiteLab.service.modules.cabinet.baseImplementation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.WhiteLab.entities.cabinet.CabinetMedicale;
import ma.WhiteLab.mvc.dto.CabinetDTO;
import ma.WhiteLab.repository.modules.cabinet.api.CabinetRepository;
import ma.WhiteLab.service.modules.cabinet.api.CabinetService;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CabinetServiceImpl implements CabinetService {

    private CabinetRepository repository;

    /**
     * Formattage de date
     * @param dt : date Non Formatée
     * @return  date formatée
     */
    private static String formatDate(java.time.LocalDateTime dt) {
        return dt.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }

    @Override
    public List<CabinetDTO> getTodayCabinetsAsDTO() {
        LocalDate today = LocalDate.now();
        return repository.findAll().stream()
                .filter(c -> c.getDateCreation() != null && c.getDateCreation().toLocalDate().equals(today))
                .sorted(Comparator.comparing(CabinetMedicale::getDateCreation).reversed())
                .map(c -> CabinetDTO.builder()
                        .nom(c.getNom() == null ? "" : c.getNom().trim())
                        .email(c.getEmail() == null ? "" : c.getEmail().trim())
                        .telephone(c.getTel1() != null ? c.getTel1().trim() : (c.getTel2() != null ? c.getTel2().trim() : ""))
                        .dateCreationFormatee(formatDate(c.getDateCreation()))
                        .build())
                .collect(Collectors.toList());
    }
}

