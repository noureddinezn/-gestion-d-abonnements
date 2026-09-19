package model;

import java.time.LocalDate;

public class AbonnementAvecEngagement extends Abonnement {
    
    private int dureeEngagementMois;

    public AbonnementAvecEngagement(String nomService, double montantMensuel, LocalDate dateDebut, LocalDate dateFin, StatutAbonnement statut, int dureeEngagementMois) {
        super(nomService, montantMensuel, dateDebut, dateFin, statut);
        this.dureeEngagementMois = dureeEngagementMois;
    }

    public int getDureeEngagementMois() {
        return this.dureeEngagementMois;
    }

    public void setDureeEngagementMois(int dureeEngagementMois) {
        this.dureeEngagementMois = dureeEngagementMois;
    }
}