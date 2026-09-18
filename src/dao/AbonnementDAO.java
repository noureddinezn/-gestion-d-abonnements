package dao;

import model.Abonnement;
import model.StatutAbonnement;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AbonnementDAO {

    private List<PaiementDAO> abonnements = new ArrayList<>();

    public void create(PaiementDAO abonnement) {
        abonnements.add(abonnement);
    }

    public List<PaiementDAO> findAll() {
        return abonnements;
    }

    public Optional<PaiementDAO> findById(String id) {
        return abonnements.stream()
                .filter(abo -> abo.getId().equals(id))
                .findFirst();
    }

    public boolean delete(String id) {
        return abonnements.removeIf(abo -> abo.getId().equals(id));
    }

    public void update(PaiementDAO abonnementModifie) {
        abonnements.replaceAll(abo -> 
            abo.getId().equals(abonnementModifie.getId()) ? abonnementModifie : abo
        );
    }

    public List<PaiementDAO> findActiveSubscriptions() {
        return abonnements.stream()
                .filter(abo -> abo.getStatut() == StatutAbonnement.ACTIVE)
                .collect(Collectors.toList());
    }
}