/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.tlse.miage.appformations.exports;

import fr.tlse.miage.appformations.entities.Demande;
import fr.tlse.miage.appformations.enumerations.StatutDemande;
import java.io.Serializable;

/**
 *
 * @author SALLABERRYMarion
 */
public class DemandeExport implements Serializable {

    private Long idDemande;             //Identifiant de la demande
    private StatutDemande statut;       //Statut de la demande
    private Long idFormation;           //Identifiant de la formation associée
    private int nbParticipants;         //Nombre de participants à la formation
    private Long idClient;              //Identifiant du client effectuant la demande

    public DemandeExport(StatutDemande statut, Long idFormation, int nbParticipants, Long idClient) {
        this.statut = statut;
        this.idFormation = idFormation;
        this.nbParticipants = nbParticipants;
        this.idClient = idClient;
    }

    public DemandeExport(Long idDemande, StatutDemande statut, Long idFormation, int nbParticipants, Long idClient) {
        this.idDemande = idDemande;
        this.statut = statut;
        this.idFormation = idFormation;
        this.nbParticipants = nbParticipants;
        this.idClient = idClient;
    }

    public DemandeExport(Demande d) {
        this.idDemande = d.getIdDemande();
        this.statut = d.getStatut();
        this.idFormation = d.getIdFormation();
        this.nbParticipants = d.getNbParticipants();
        this.idClient = d.getIdClient();
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
}
