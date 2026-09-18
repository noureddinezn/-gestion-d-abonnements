package models;

import java.time.LocalDate;
import java.util.UUID;

public class Paiement {
    private String idPaiement;
    private String idAbonnement;
    private LocalDate dateEcheance;
    private LocalDate datePaiement;
    private String typePaiement;
    private StatutPaiement statut;

    public Paiement(String idAbonnement, LocalDate dateEcheance, LocalDate datePaiement, String typePaiement, StatutPaiement statut) {
        this.idPaiement = UUID.randomUUID().toString();
        this.idAbonnement = idAbonnement;
        this.dateEcheance = dateEcheance;
        this.datePaiement = datePaiement;
        this.typePaiement = typePaiement;
        this.statut = statut;
    }

    public String getIdPaiement() {
        return this.idPaiement;
    }

    public String getIdAbonnement() {
        return this.idAbonnement;
    }

    public LocalDate getDateEcheance() {
        return this.dateEcheance;
    }

    public LocalDate getDatePaiement() {
        return this.datePaiement;
    }

    public String getTypePaiement() {
        return this.typePaiement;
    }

    public StatutPaiement getStatut() {
        return this.statut;
    }

    public void setDatePaiement(LocalDate datePaiement) {
        this.datePaiement = datePaiement;
    }

    public void setStatut(StatutPaiement statut) {
        this.statut = statut;
    }
}