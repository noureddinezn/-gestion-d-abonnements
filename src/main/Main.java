package main;

import dao.AbonnementDAO;
import dao.PaiementDAO;
import model.AbonnementAvecEngagement;
import model.AbonnementSansEngagement;
import model.Paiement;
import model.StatutAbonnement;
import model.StatutPaiement;
import services.AbonnementService;
import services.PaiementService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        AbonnementDAO abonnementDAO = new AbonnementDAO();
        PaiementDAO paiementDAO = new PaiementDAO();
        AbonnementService abonnementService = new AbonnementService(abonnementDAO, paiementDAO);
        PaiementService paiementService = new PaiementService(paiementDAO, abonnementDAO);
        Scanner scanner = new Scanner(System.in);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        boolean mainRunning = true;
        
        while (mainRunning) {
            System.out.println("\n====== MENU PRINCIPAL ======");
            System.out.println("1. Gestion des Abonnements");
            System.out.println("2. Gestion des Paiements & Rapports");
            System.out.println("0. Quitter l'application");
            int mainChoix = lireEntier(scanner, "Choix : ");

            switch (mainChoix) {
                case 1:
                    boolean aboRunning = true;
                    while (aboRunning) {
                        System.out.println("\n--- GESTION DES ABONNEMENTS ---");
                        System.out.println("1. Creer un abonnement (avec/sans engagement)");
                        System.out.println("2. Consulter la liste des abonnements");
                        System.out.println("3. Resilier un abonnement");
                        System.out.println("4. Supprimer un abonnement");
                        System.out.println("0. Retour au menu principal");
                        int aboChoix = lireEntier(scanner, "Choix : ");
                        
                        switch (aboChoix) {
                            case 1:
                                String nom = lireTexte(scanner, "Nom du service (ex: Netflix) : ");
                                double montant = lireDouble(scanner, "Montant mensuel : ");
                                LocalDate debut = lireDate(scanner, formatter, "Date debut (yyyy-MM-dd) : ");
                                LocalDate fin = lireDate(scanner, formatter, "Date fin (yyyy-MM-dd) : ");
                                boolean engagement = lireBooleen(scanner, "Avec engagement ? (true/false) : ");
                                
                                if (engagement) {
                                    int duree = lireEntier(scanner, "Duree engagement (mois) : ");
                                    abonnementService.creerAbonnement(new AbonnementAvecEngagement(nom, montant, debut, fin, StatutAbonnement.ACTIVE, duree));
                                } else {
                                    abonnementService.creerAbonnement(new AbonnementSansEngagement(nom, montant, debut, fin, StatutAbonnement.ACTIVE));
                                }
                                System.out.println("Abonnement cree et echeances generees !");
                                break;
                            case 2:
                                abonnementService.ListerAbonnements().forEach(abo -> {
                                    System.out.println("ID: " + abo.getId() + " | Service: " + abo.getNomService() + " | Montant: " + abo.getMontantMensuel() + " | Statut: " + abo.getStatut());
                                });
                                break;
                            case 3:
                                String idModif = lireTexte(scanner, "ID de l'abonnement a resilier : ");
                                abonnementService.resilierAbonnement(idModif);
                                System.out.println("Abonnement resilie avec succes.");
                                break;
                            case 4:
                                String idSuppr = lireTexte(scanner, "ID de l'abonnement a supprimer : ");
                                abonnementService.suprimerAbonnement(idSuppr);
                                System.out.println("Abonnement supprime.");
                                break;
                            case 0:
                                aboRunning = false;
                                break;
                            default:
                                System.out.println("Choix invalide.");
                        }
                    }
                    break;
                    
                case 2:
                    boolean paieRunning = true;
                    while (paieRunning) {
                        System.out.println("\n--- GESTION DES PAIEMENTS & RAPPORTS ---");
                        System.out.println("1. Afficher les paiements d'un abonnement");
                        System.out.println("2. Enregistrer un paiement (Payer)");
                        System.out.println("3. Modifier un paiement");
                        System.out.println("4. Supprimer un paiement");
                        System.out.println("5. Consulter les impayes (Retards)");
                        System.out.println("6. Afficher la somme payee d'un abonnement");
                        System.out.println("7. Afficher les 5 derniers paiements");
                        System.out.println("8. Generer un rapport financier (Mensuel)");
                        System.out.println("9. Generer un rapport financier (Annuel)");
                        System.out.println("0. Retour au menu principal");
                        int paieChoix = lireEntier(scanner, "Choix : ");
                        
                        switch (paieChoix) {
                            case 1:
                                String idAbo = lireTexte(scanner, "ID de l'abonnement : ");
                                if (paiementDAO.findByAbonnement(idAbo).isEmpty()) {
                                    System.out.println("Aucun paiement trouve pour cet abonnement.");
                                } else {
                                    paiementDAO.findByAbonnement(idAbo).forEach(p ->
                                        System.out.println("Paiement ID: " + p.getIdPaiement() + " | Date Echeance: " + p.getDateEcheance() + " | Statut: " + p.getStatut())
                                    );
                                }
                                break;
                            case 2:
                                String idPaiement = lireTexte(scanner, "ID du paiement a regler : ");
                                if (paiementDAO.findById(idPaiement).isPresent()) {
                                    paiementService.enregistrerPaiement(idPaiement);
                                    System.out.println("Paiement valide !");
                                } else {
                                    System.out.println("Paiement introuvable.");
                                }
                                break;
                            case 3:
                                modifierPaiement(scanner, paiementDAO, paiementService, formatter);
                                break;
                            case 4:
                                String idPaieSuppr = lireTexte(scanner, "ID du paiement a supprimer : ");
                                if (paiementDAO.findById(idPaieSuppr).isPresent()) {
                                    paiementService.supprimerPaiement(idPaieSuppr);
                                    System.out.println("Paiement supprime.");
                                } else {
                                    System.out.println("Paiement introuvable.");
                                }
                                break;
                            case 5:
                                paiementService.detecterImpayes();
                                double totalImpaye = paiementDAO.findAll().stream()
                                    .filter(p -> p.getStatut() == StatutPaiement.EN_RETARD || p.getStatut() == StatutPaiement.NON_PAYE)
                                    .mapToDouble(p -> abonnementDAO.findById(p.getIdAbonnement()).map(a -> a.getMontantMensuel()).orElse(0.0))
                                    .sum();
                                System.out.println("Total des impayes : " + totalImpaye + " DH");
                                break;
                            case 6:
                                String idAboSomme = lireTexte(scanner, "ID de l'abonnement : ");
                                System.out.println("Somme totale payee : " + paiementService.calculeSommePayee(idAboSomme) + " DH");
                                break;
                            case 7:
                                paiementService.getCinqDerniersPaiements().forEach(p -> 
                                    System.out.println("Paiement ID: " + p.getIdPaiement() + " | Date: " + p.getDatePaiement())
                                );
                                break;
                            case 8:
                                int annee = lireEntier(scanner, "Annee : ");
                                int mois = lireEntier(scanner, "Mois (1-12) : ");
                                System.out.println("Total encaisse pour " + mois + "/" + annee + " : " + paiementService.genererRapportMensuel(annee, mois) + " DH");
                                break;
                            case 9:
                                int anneeRapport = lireEntier(scanner, "Annee : ");
                                System.out.println("Total encaisse pour " + anneeRapport + " : " + paiementService.genererRapportAnnuel(anneeRapport) + " DH");
                                break;
                            case 0:
                                paieRunning = false;
                                break;
                            default:
                                System.out.println("Choix invalide.");
                        }
                    }
                    break;
                    
                case 0:
                    mainRunning = false;
                    System.out.println("Au revoir !");
                    break;
                    
                default:
                    System.out.println("Choix invalide. Veuillez reessayer.");
            }
        }
        scanner.close();
    }

    private static void modifierPaiement(Scanner scanner, PaiementDAO paiementDAO,
            PaiementService paiementService, DateTimeFormatter formatter) {
        String idPaiement = lireTexte(scanner, "ID du paiement a modifier : ");
        Paiement paiement = paiementDAO.findById(idPaiement).orElse(null);
        if (paiement == null) {
            System.out.println("Paiement introuvable.");
            return;
        }

        System.out.println("1. Modifier le statut");
        System.out.println("2. Modifier la date de paiement");
        int choix = lireEntier(scanner, "Choix : ");
        if (choix == 1) {
            System.out.println("Statuts : PAYE, NON_PAYE, EN_RETARD");
            String statut = lireTexte(scanner, "Nouveau statut : ");
            try {
                paiement.setStatut(StatutPaiement.valueOf(statut.toUpperCase()));
            } catch (IllegalArgumentException exception) {
                System.out.println("Statut invalide.");
                return;
            }
        } else if (choix == 2) {
            paiement.setDatePaiement(lireDate(scanner, formatter, "Nouvelle date (yyyy-MM-dd) : "));
        } else {
            System.out.println("Choix invalide.");
            return;
        }
        paiementService.modifierPaiement(paiement);
        System.out.println("Paiement modifie avec succes.");
    }

    private static String lireTexte(Scanner scanner, String message) {
        System.out.print(message);
        return scanner.nextLine().trim();
    }

    private static int lireEntier(Scanner scanner, String message) {
        while (true) {
            String valeur = lireTexte(scanner, message);
            try {
                return Integer.parseInt(valeur);
            } catch (NumberFormatException exception) {
                System.out.println("Veuillez saisir un nombre entier valide.");
            }
        }
    }

    private static double lireDouble(Scanner scanner, String message) {
        while (true) {
            String valeur = lireTexte(scanner, message);
            try {
                return Double.parseDouble(valeur);
            } catch (NumberFormatException exception) {
                System.out.println("Veuillez saisir un montant valide.");
            }
        }
    }

    private static boolean lireBooleen(Scanner scanner, String message) {
        while (true) {
            String valeur = lireTexte(scanner, message);
            if ("true".equalsIgnoreCase(valeur) || "false".equalsIgnoreCase(valeur)) {
                return Boolean.parseBoolean(valeur);
            }
            System.out.println("Veuillez saisir true ou false.");
        }
    }

    private static LocalDate lireDate(Scanner scanner, DateTimeFormatter formatter, String message) {
        while (true) {
            String valeur = lireTexte(scanner, message);
            try {
                return LocalDate.parse(valeur, formatter);
            } catch (Exception exception) {
                System.out.println("Format invalide. Utilisez yyyy-MM-dd.");
            }
        }
    }
}