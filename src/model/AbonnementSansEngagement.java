package model;

import java.time.LocalDate;

public class AbonnementSansEngagement extends Abonnement {

    public AbonnementSansEngagement(String nomService, double montantMensuel, LocalDate dateDebut, LocalDate dateFin, StatutAbonnement statut) {
        super(nomService, montantMensuel, dateDebut, dateFin, statut);
    }
}
