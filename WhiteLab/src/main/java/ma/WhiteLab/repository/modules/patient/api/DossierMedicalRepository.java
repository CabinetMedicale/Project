package ma.WhiteLab.repository.modules.patient.api;

import ma.WhiteLab.entities.patient.DossierMedical;
import ma.WhiteLab.repository.common.CrudRepository;

import java.util.List;
import java.util.Optional;
//auteur : Aymane Akarbich

public interface DossierMedicalRepository extends CrudRepository<DossierMedical, Long> {

    Optional<DossierMedical> findByPatientId(Long patientId);

    List<DossierMedical> findByMedecinId(Long medecinId);

    long count();
}
