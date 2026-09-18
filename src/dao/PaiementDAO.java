package dao;

import model.Paiement;
import model.StatutPaiement;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PaiementDAO {

    private List<Paiement> paiements = new ArrayList<>();

    public void create(Paiement paiement) {
        paiements.add(paiement);
    }

    public List<Paiement> findAll() {
        return paiements;
    }

    public Optional<Paiement> findById(String id) {
        return paiements.stream()
                .filter(p -> p.getIdPaiement().equals(id))
                .findFirst();
    }

    public List<Paiement> findByAbonnement(String idAbonnement) {
        return paiements.stream()
                .filter(p -> p.getIdAbonnement().equals(idAbonnement))
                .collect(Collectors.toList());
    }

    public boolean delete(String id) {
        return paiements.removeIf(p -> p.getIdPaiement().equals(id));
    }

    public void update(Paiement paiementModifie) {
        paiements.replaceAll(p -> 
            p.getIdPaiement().equals(paiementModifie.getIdPaiement()) ? paiementModifie : p
        );
    }

    public List<Paiement> findUnpaidByAbonnement(String idAbonnement) {
        return paiements.stream()
                .filter(p -> p.getIdAbonnement().equals(idAbonnement))
                .filter(p -> p.getStatut() == StatutPaiement.NON_PAYE || p.getStatut() == StatutPaiement.EN_RETARD)
                .collect(Collectors.toList());
    }

    public List<Paiement> findLastPayments(int limit) {
        return paiements.stream()
                .sorted(Comparator.comparing(Paiement::getDateEcheance).reversed())
                .limit(limit)
                .collect(Collectors.toList());
    }
}