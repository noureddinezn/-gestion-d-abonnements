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
    public void resilierAbonnement(String id) {
        abonnementDAO.findById(id).ifPresent(abo -> {
            abo.setStatut(StatutAbonnement.RESILIE);
            abonnementDAO.update(abo);
        });
    }
    public Optional<Abonnement> findById(String id) {
        return abonnementDAO.findById(id);
    }

    public List<Abonnement> ListerAbonnements() {
        return abonnementDAO.findAll();
    }
    private void genererEcheances(Abonnement abonnement) {
        LocalDate dateEcheance = abonnement.getDateDebut();

        while (!dateEcheance.isAfter(abonnement.getDateFin())) {
            Paiement paiement = new Paiement(
                    abonnement.getId(),
                    dateEcheance,
                    null,
                    "MENSUEL",
                    StatutPaiement.NON_PAYE
            );
            paiementDAO.create(paiement);
            dateEcheance = dateEcheance.plusMonths(1);
        }
    }
}
