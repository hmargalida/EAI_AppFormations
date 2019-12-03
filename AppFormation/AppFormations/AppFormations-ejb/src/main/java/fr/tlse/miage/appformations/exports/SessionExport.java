/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.tlse.miage.appformations.exports;

import fr.tlse.miage.appformations.entities.Session;
import fr.tlse.miage.appformations.enumerations.StatutSession;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author SALLABERRYMarion
 */
public class SessionExport {

    private Long idSession;
    private int date;
    private StatutSession statut;
    private Long idFormateur;
    private Long idSalle;
    private Long idFormation;
    private Calendar createdDate;

    public SessionExport(Long idSession, int date, StatutSession statut, Long idFormation, Calendar createdDate) {
        this.idSession = idSession;
        this.date = date;
        this.statut = statut;
        this.idFormation = idFormation;
        this.createdDate = createdDate;
    }

    public SessionExport(Long idSession, int date, StatutSession statut, Long idFormateur, Long idSalle, Long idFormation, Calendar createdDate) {
        this.idSession = idSession;
        this.date = date;
        this.statut = statut;
        this.idFormateur = idFormateur;
        this.idSalle = idSalle;
        this.idFormation = idFormation;
        this.createdDate = createdDate;
    }

    public SessionExport(Session s) {
        this.idSession = s.getIdSession();
        this.date = s.getDate();
        this.statut = s.getStatut();
        this.idFormateur = s.getIdFormateur();
        this.idSalle = s.getIdSalle();
        this.idFormation = s.getIdFormation();
        this.createdDate = s.getCreatedDate();
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

    public Long getIdFormation() {
        return idFormation;
    }

    public void setIdFormation(Long idFormation) {
        this.idFormation = idFormation;
    }

    public Calendar getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Calendar createdDate) {
        this.createdDate = createdDate;
    }

}
