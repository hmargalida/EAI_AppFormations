/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.tlse.miage.appformations.exports;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author SALLABERRYMarion
 */
public class SalleDispoExport {
    private Long idFormation;           //Identifiant de la formation associée
    private Long idSalle;               //Identifiant de la salle
    private List<Integer> listeSemainesDispo;   //Liste des semaines disponibles
    
    public SalleDispoExport(Long idFormation, Long idSalle, ArrayList<Integer> listeSemainesDispo){
        this.idFormation = idFormation;
        this.idSalle = idSalle;
        this.listeSemainesDispo = listeSemainesDispo;
    }

    public Long getIdFormation() {
        return idFormation;
    }

    public void setIdFormation(Long idFormation) {
        this.idFormation = idFormation;
    }

    public Long getIdSalle() {
        return idSalle;
    }

    public void setIdSalle(Long idSalle) {
        this.idSalle = idSalle;
    }

    public List<Integer> getListeSemainesDispo() {
        return listeSemainesDispo;
    }

    public void setListeSemainesDispo(List<Integer> listeSemainesDispo) {
        this.listeSemainesDispo = listeSemainesDispo;
    }
}
