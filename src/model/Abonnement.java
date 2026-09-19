package model;

import java.time.LocalDate;
import java.util.UUID;

public abstract class Abonnement {
    protected String id;
    protected String nomService;
    protected double montantMensuel;
    protected LocalDate dateDebut;
    protected LocalDate dateFin;
    protected StatutAbonnement statut;

    public Abonnement(String nomService, double montantMensuel, LocalDate dateDebut, LocalDate dateFin, StatutAbonnement statut) {
        this.id = UUID.randomUUID().toString();
        this.nomService = nomService;
        this.montantMensuel = montantMensuel;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.statut = statut;
    }

    public String getId() {
        return this.id;
    }

    public String getNomService() {
        return this.nomService;
    }

    public double getMontantMensuel() {
        return this.montantMensuel;
    }

    public LocalDate getDateDebut() {
        return this.dateDebut;
    }

    public LocalDate getDateFin() {
        return this.dateFin;
    }

    public StatutAbonnement getStatut() {
        return this.statut;
    }

    public void setStatut(StatutAbonnement statut) {
        this.statut = statut;
    }
}
