package ma.WhiteLab.mvc.controllers.modules.cabinet.api;

public interface CabinetController {

    /**
     * Méthode qui affiche les cabinets ajoutés aujourd'hui au système
     * trié par date d'ajout au système
     * Chaque cabinet est affiché seulement avec son nom, email, téléphone et sa date d'ajout formatée (selon un CabinetDTO)
     */
    void showRecentCabinets();

}

