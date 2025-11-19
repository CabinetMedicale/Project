package ma.WhiteLab.mvc.controllers.modules.cabinet.swing_implementation;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.WhiteLab.mvc.controllers.modules.cabinet.api.CabinetController;
import ma.WhiteLab.mvc.dto.CabinetDTO;
import ma.WhiteLab.mvc.ui.modules.cabinet.CabinetView;
import ma.WhiteLab.service.modules.cabinet.api.CabinetService;

@Data @AllArgsConstructor @NoArgsConstructor
public class CabinetControllerImpl implements CabinetController {

    private CabinetService service;

    @Override
    public void showRecentCabinets() {
        List<CabinetDTO> dtos = service.getTodayCabinetsAsDTO();
        CabinetView.showAsync(dtos);
    }
}

