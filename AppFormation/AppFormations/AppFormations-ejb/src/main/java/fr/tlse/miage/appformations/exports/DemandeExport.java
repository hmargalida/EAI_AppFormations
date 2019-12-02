/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.tlse.miage.appformations.exports;

import fr.tlse.miage.appformations.enumerations.StatutDemande;

/**
 *
 * @author SALLABERRYMarion
 */
public class DemandeExport {
    private Long idDemande;
    private StatutDemande statut;
    private Long idFormation;
    private int nbParticipants;
    private Long idClient;
    private String nomClient;

    public DemandeExport(Long idDemande, StatutDemande statut, Long idFormation, int nbParticipants, Long idClient, String nomClient) {
        this.idDemande = idDemande;
        this.statut = statut;
        this.idFormation = idFormation;
        this.nbParticipants = nbParticipants;
        this.idClient = idClient;
        this.nomClient = nomClient;
    }

    public Long getIdDemande() {
        return idDemande;
    }

    public void setIdDemande(Long idDemande) {
        this.idDemande = idDemande;
    }

    public StatutDemande getStatut() {
        return statut;
    }

    public void setStatut(StatutDemande statut) {
        this.statut = statut;
    }

    public Long getIdFormation() {
        return idFormation;
    }

    public void setIdFormation(Long idFormation) {
        this.idFormation = idFormation;
    }

    public int getNbParticipants() {
        return nbParticipants;
    }

    public void setNbParticipants(int nbParticipants) {
        this.nbParticipants = nbParticipants;
    }

    public Long getIdClient() {
        return idClient;
    }

    public void setIdClient(Long idClient) {
        this.idClient = idClient;
    }

    public String getNomClient() {
        return nomClient;
    }

    public void setNomClient(String nomClient) {
        this.nomClient = nomClient;
    }    
}
