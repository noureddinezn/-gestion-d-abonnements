package dao;

import model.Abonnement;
import model.StatutAbonnement;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AbonnementDAO {

    private List<Abonnement> abonnements = new ArrayList<>();

    public void create(Abonnement abonnement) {
        abonnements.add(abonnement);
    }

    public List<Abonnement> findAll() {
        return abonnements;
    }

    public Optional<Abonnement> findById(String id) {
        return abonnements.stream()
                .filter(abo -> abo.getId().equals(id))
                .findFirst();
    }

    public boolean delete(String id) {
        return abonnements.removeIf(abo -> abo.getId().equals(id));
    }

    public void update(Abonnement abonnementModifie) {
        abonnements.replaceAll(abo -> 
            abo.getId().equals(abonnementModifie.getId()) ? abonnementModifie : abo
        );
    }

    public List<Abonnement> findActiveSubscriptions() {
        return abonnements.stream()
                .filter(abo -> abo.getStatut() == StatutAbonnement.ACTIVE)
                .collect(Collectors.toList());
    }
}