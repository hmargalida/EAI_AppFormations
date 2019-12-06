/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.tlse.miage.appformations.exports;

/**
 *
 * @author SALLABERRYMarion
 */
public class FormateurDispoExport {
    private Long idFormation;           //Identifiant de la formation associée
    private Long idFormateur;           //Identifiant du formateur
    private int[] listeSemainesDispo;   //Liste des semaines disponibles
    
    public FormateurDispoExport(Long idFormation, Long idFormateur, int[] listeSemainesDispo){
        this.idFormation = idFormation;
        this.idFormateur = idFormateur;
        this.listeSemainesDispo = listeSemainesDispo;
    }

    public Long getIdFormation() {
        return idFormation;
    }

    public void setIdFormation(Long idFormation) {
        this.idFormation = idFormation;
    }

    public Long getIdFormateur() {
        return idFormateur;
    }

    public void setIdFormateur(Long idFormateur) {
        this.idFormateur = idFormateur;
    }

    public int[] getListeSemainesDispo() {
        return listeSemainesDispo;
    }

    public void setListeSemainesDispo(int[] listeSemainesDispo) {
        this.listeSemainesDispo = listeSemainesDispo;
    }
    
    
}
