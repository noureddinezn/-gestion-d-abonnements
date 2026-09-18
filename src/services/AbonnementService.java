package services;

import dao.AbonnementDAO;
import dao.PaiementDAO;
import model.Abonnement;
import model.Paiement;
import model.StatutAbonnement;
import model.StatutPaiement;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class AbonnementService {
    private AbonnementDAO abonnementDAO;
    private PaiementDAO paiementDAO;
    public AbonnementService(AbonnementDAO abonnementDAO, PaiementDAO paiementDAO) {
        this.abonnementDAO = abonnementDAO;
        this.paiementDAO = paiementDAO;
    }
    public void creerAbonnement(Abonnement abonnement) {
        abonnementDAO.create(abonnement);
        genererEcheances(abonnement);
    }
    public void modifierAbonnement(Abonnement abonnement) {
        abonnementDAO.update(abonnement);
    }

    public void suprimerAbonnement(String id) {
        abonnementDAO.delete(id);
    }
}
