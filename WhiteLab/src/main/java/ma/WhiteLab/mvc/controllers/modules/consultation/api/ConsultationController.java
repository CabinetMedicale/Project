package ma.WhiteLab.mvc.controllers.modules.consultation.api;

public interface ConsultationController {

    /**
     * Méthode qui affiche les consultations ajoutées aujourd'hui au système
     * trié par date d'ajout au système
     * Chaque consultation est affichée seulement avec sa date, status, notes et sa date d'ajout formatée (selon un ConsultationDTO)
     */
    void showRecentConsultations();

}

