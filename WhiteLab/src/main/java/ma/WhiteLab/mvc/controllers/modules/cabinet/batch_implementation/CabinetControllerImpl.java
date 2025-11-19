package ma.WhiteLab.mvc.controllers.modules.cabinet.batch_implementation;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.WhiteLab.mvc.controllers.modules.cabinet.api.CabinetController;
import ma.WhiteLab.mvc.dto.CabinetDTO;
import ma.WhiteLab.service.modules.cabinet.api.CabinetService;

@Data @AllArgsConstructor @NoArgsConstructor
public class CabinetControllerImpl implements CabinetController {

    private CabinetService service;

    @Override
    public void showRecentCabinets() {
        List<CabinetDTO> dtos = service.getTodayCabinetsAsDTO();
        if (dtos.isEmpty()) {
            System.out.println("Aucun cabinet ajouté aujourd'hui.");
            return;
        }
        System.out.println("=== Cabinets ajoutés aujourd'hui ===");
        dtos.forEach(dto -> System.out.printf("- %s | %s | %s | ajouté le %s%n",
                dto.getNom(), dto.getEmail(), dto.getTelephone(), dto.getDateCreationFormatee()));
    }
}

