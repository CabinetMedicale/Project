package ma.WhiteLab.repository.modules.patient.api;

import ma.WhiteLab.entities.patient.Antecedent;
import ma.WhiteLab.entities.enums.CategorieAntecedent;
import ma.WhiteLab.entities.enums.NiveauDeRisk;
import ma.WhiteLab.entities.patient.Patient;
import ma.WhiteLab.repository.common.CrudRepository;

import java.util.List;
import java.util.Optional;
//auteur : Aymane Akarbich

public interface AntecedentRepository extends CrudRepository<Antecedent, Long> {

    Optional<Antecedent> findByNom(String nom);
    List<Antecedent> findByCategorie(CategorieAntecedent categorie);
    List<Antecedent> findByNiveauRisque(NiveauDeRisk niveau);
    boolean existsById(Long id);
    long count();
    List<Antecedent> findPage(int limit, int offset);

    // ---- Navigation inverse ----
    List<Patient> getPatientsHavingAntecedent(Long antecedentId);
}
