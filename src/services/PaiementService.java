package services;

import dao.AbonnementDAO;
import dao.PaiementDAO;
import model.Abonnement;
import model.Paiement;
import model.StatutPaiement;
import java.time.LocalDate;
import java.util.List;


    private PaiementDAO paiementDAO;
    private PaiementDAO paiementDAO;
    private AbonnementDAO abonnementDAO;

    public PaiementService(PaiementDAO paiementDAO, AbonnementDAO abonnementDAO) {
        this.paiementDAO = paiementDAO;
        this.abonnementDAO = abonnementDAO;
    }
    public void enregistrerPaiement(String idPaiement) {
        paiementDAO.findById(idPaiement).ifPresent(p -> {
            p.setStatut(StatutPaiement.PAYE);
            p.setDatePaiement(LocalDate.now());
            paiementDAO.update(p);
        });
    }
    public void modifierPaiement(Paiement paiement) {
        paiementDAO.update(paiement);
    }

    public void supprimerPaiement(String id) {
        paiementDAO.delete(id);
    }
    public void detecterImpayes() {
        paiementDAO.findAll().stream()
                .filter(p -> p.getStatut() == StatutPaiement.NON_PAYE)
                .filter(p -> p.getDateEcheance().isBefore(LocalDate.now()))
                .forEach(p -> {
                    p.setStatut(StatutPaiement.EN_RETARD);
                    paiementDAO.update(p);
                });
    }
    public double calculeSommePayee(String idAbonnement) {
        Abonnement abonnement = abonnementDAO.findById(idAbonnement).orElse(null);
        if (abonnement == null) {
            return 0.0;
        }
        return paiementDAO.findByAbonnement(idAbonnement).stream()
                .filter(p -> p.getStatut() == StatutPaiement.PAYE)
                .mapToDouble(p -> abonnement.getMontantMensuel())
                .sum();
    }
    public List<Paiement> getCinqDerniersPaiements() {
        return paiementDAO.findLastPayments(5);
    }

    public double genererRapportMensuel(int annee, int mois) {
        return paiementDAO.findAll().stream()
                .filter(p -> p.getStatut() == StatutPaiement.PAYE)
                .filter(p -> p.getDatePaiement() != null && p.getDatePaiement().getYear() == annee && p.getDatePaiement().getMonthValue() == mois)
                .mapToDouble(p -> abonnementDAO.findById(p.getIdAbonnement())
                        .map(Abonnement::getMontantMensuel)
                        .orElse(0.0))
                .sum();
    }
    public double genererRapportAnnuel(int annee) {
        return paiementDAO.findAll().stream()
                .filter(p -> p.getStatut() == StatutPaiement.PAYE)
                .filter(p -> p.getDatePaiement() != null && p.getDatePaiement().getYear() == annee)
                .mapToDouble(p -> abonnementDAO.findById(p.getIdAbonnement())
                        .map(Abonnement::getMontantMensuel)
                        .orElse(0.0))
                .sum();
    }
}
