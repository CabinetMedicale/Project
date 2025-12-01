package ma.WhiteLab.repository.modules.user.api;

import ma.WhiteLab.entities.user.Utilisateur;
import ma.WhiteLab.repository.common.CrudRepository;

import java.util.Optional;
//auteur : Aymane Akarbich

public interface UtilisateurRepository<T extends Utilisateur> extends CrudRepository<T, Long> {

    Optional<T> findByEmail(String email);

    Optional<T> findByTelephone(String telephone);

    boolean existsByEmail(String email);

    boolean existsByTelephone(String telephone);

    long count();
}
