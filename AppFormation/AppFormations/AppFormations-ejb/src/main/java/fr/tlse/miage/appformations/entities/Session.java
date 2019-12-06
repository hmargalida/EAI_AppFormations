/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.tlse.miage.appformations.entities;

import fr.tlse.miage.appformations.enumerations.StatutSession;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;

/**
 *
 * @author SALLABERRYMarion
 */
@Entity
public class Session implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idSession;                 //Identifiant de la session - l'attribut est autogénéré
    private int date;                       //Numéro de la semaine pendant laquelle se déroule la session
    private StatutSession statut;           //Statut de la session
    private int nbParticipants;             //Nombre de participants de la session
    private int duree;                      //Duree de la session (3 ou 5 jours)
    private int capaciteMin;                //Capacité minimale de la session
    private int capaciteMax;                //Capacité maximale de la session
    private Long idFormateur;               //Identifiant du formateur associé
    private Long idSalle;                   //Identifiant de la salle associée
    private Long idFormation;               //Identifiant de la formation associée
    @Temporal(javax.persistence.TemporalType.DATE)
    private Calendar createdDate;           //Date de création de la session
    
    @OneToMany
    private List<Demande> listeDemandes;    //Liste des demandes associées à cette session

    public Session() {
        this.listeDemandes = new ArrayList<Demande>();
        this.createdDate = Calendar.getInstance();  //Récupération de la date courante
    }

    public Session(int date, int nbParticipants, int duree, int capaciteMin, int capaciteMax, Long idFormation) {
        this.date = date;
        this.nbParticipants = nbParticipants;
        this.duree = duree;
        this.capaciteMin = capaciteMin;
        this.capaciteMax = capaciteMax;
        this.statut = StatutSession.En_projet;
        this.idFormation = idFormation;
        this.listeDemandes = new ArrayList<Demande>();
        this.createdDate = Calendar.getInstance();
    }

    public Long getIdSession() {
        return idSession;
    }

    public void setIdSession(Long idSession) {
        this.idSession = idSession;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public StatutSession getStatut() {
        return statut;
    }

    public void setStatut(StatutSession statut) {
        this.statut = statut;
    }

    public Long getIdFormateur() {
        return idFormateur;
    }

    public void setIdFormateur(Long idFormateur) {
        this.idFormateur = idFormateur;
    }

    public Long getIdSalle() {
        return idSalle;
    }

    public void setIdSalle(Long idSalle) {
        this.idSalle = idSalle;
    }

    public int getNbParticipants() {
        return nbParticipants;
    }

    public void setNbParticipants(int nbParticipants) {
        this.nbParticipants = nbParticipants;
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public int getCapaciteMin() {
        return capaciteMin;
    }

    public void setCapaciteMin(int capaciteMin) {
        this.capaciteMin = capaciteMin;
    }

    public int getCapaciteMax() {
        return capaciteMax;
    }

    public void setCapaciteMax(int capaciteMax) {
        this.capaciteMax = capaciteMax;
    }

    public Long getIdFormation() {
        return idFormation;
    }

    public void setIdFormation(Long idFormation) {
        this.idFormation = idFormation;
    }

    public List<Demande> getListeDemandes() {
        return listeDemandes;
    }

    public void setListeDemandes(List<Demande> listeDemandes) {
        this.listeDemandes = listeDemandes;
    }

    public void addDemande(Demande d) {
        this.listeDemandes.add(d);
    }

    public void removeDemande(Demande d) {
        this.listeDemandes.remove(d);
    }

    public Calendar getCreatedDate() {
        return createdDate;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSession != null ? idSession.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the idSession fields are not set
        if (!(object instanceof Session)) {
            return false;
        }
        Session other = (Session) object;
        if ((this.idSession == null && other.idSession != null) || (this.idSession != null && !this.idSession.equals(other.idSession))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tps.maven.sallab.entities.Session[ id=" + idSession + " ]";
    }

}
