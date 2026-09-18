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
}
