package ma.WhiteLab.repository.modules.consultation.inMemDB_implementation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import ma.WhiteLab.entities.consultation.Consultation;
import ma.WhiteLab.entities.enums.StatusConsultation;
import ma.WhiteLab.repository.modules.consultation.api.ConsultationRepository;

public class ConsultationRepositoryImpl implements ConsultationRepository {

    private final List<Consultation> data = new ArrayList<>();

    public ConsultationRepositoryImpl() {
        // Données d'exemple
        LocalDateTime now = LocalDateTime.now();
        data.add(Consultation.builder()
                .id(1L)
                .date(now.minusHours(2))
                .status(StatusConsultation.TERMINEE)
                .notes("Consultation de routine")
                .observationsMedecin("Patient en bonne santé dentaire")
                .dateCreation(now.minusDays(1))
                .build());

        data.add(Consultation.builder()
                .id(2L)
                .date(now.minusHours(1))
                .status(StatusConsultation.EN_COURS)
                .notes("Détartrage")
                .dateCreation(now.minusHours(2))
                .build());

        data.add(Consultation.builder()
                .id(3L)
                .date(now.plusHours(2))
                .status(StatusConsultation.PLANIFIEE)
                .notes("Contrôle annuel")
                .dateCreation(now.minusDays(2))
                .build());

        // Tri stable par id pour cohérence
        data.sort(Comparator.comparing(Consultation::getId));
    }

    @Override
    public List<Consultation> findAll() { return List.copyOf(data); }

    @Override
    public Consultation findById(Long id) {
        return data.stream().filter(c -> c.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public void create(Consultation consultation) { data.add(consultation); }

    @Override
    public void update(Consultation consultation) {
        deleteById(consultation.getId());
        data.add(consultation);
    }

    @Override
    public void delete(Consultation consultation) { data.removeIf(c -> c.getId().equals(consultation.getId())); }

    @Override
    public void deleteById(Long id) { data.removeIf(c -> c.getId().equals(id)); }
}

