package ma.WhiteLab.repository.modules.cabinet.inMemDB_implementation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import ma.WhiteLab.entities.cabinet.CabinetMedicale;
import ma.WhiteLab.repository.modules.cabinet.api.CabinetRepository;

public class CabinetRepositoryImpl implements CabinetRepository {

    private final List<CabinetMedicale> data = new ArrayList<>();

    public CabinetRepositoryImpl() {
        // Données d'exemple
        LocalDateTime now = LocalDateTime.now();
        data.add(CabinetMedicale.builder()
                .id(1L)
                .nom("Cabinet Dentaire WhiteLab")
                .email("contact@whitelab.ma")
                .tel1("0522-123456")
                .tel2("0522-123457")
                .categorie("Dentaire")
                .dateCreation(now.minusDays(1))
                .build());

        data.add(CabinetMedicale.builder()
                .id(2L)
                .nom("Clinique Dentaire Rabat")
                .email("info@clinique-rabat.ma")
                .tel1("0537-789012")
                .categorie("Dentaire")
                .dateCreation(now.minusHours(5))
                .build());

        // Tri stable par id pour cohérence
        data.sort(Comparator.comparing(CabinetMedicale::getId));
    }

    @Override
    public List<CabinetMedicale> findAll() { return List.copyOf(data); }

    @Override
    public CabinetMedicale findById(Long id) {
        return data.stream().filter(c -> c.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public void create(CabinetMedicale cabinet) { data.add(cabinet); }

    @Override
    public void update(CabinetMedicale cabinet) {
        deleteById(cabinet.getId());
        data.add(cabinet);
    }

    @Override
    public void delete(CabinetMedicale cabinet) { data.removeIf(c -> c.getId().equals(cabinet.getId())); }

    @Override
    public void deleteById(Long id) { data.removeIf(c -> c.getId().equals(id)); }
}

