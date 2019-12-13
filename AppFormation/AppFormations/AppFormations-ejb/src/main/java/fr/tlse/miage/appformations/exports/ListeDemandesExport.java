/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.tlse.miage.appformations.exports;

import java.util.ArrayList;

/**
 *
 * @author SALLABERRYMarion
 */
public class ListeDemandesExport {
    private ArrayList<DemandeExport> demandesTraitees ; //Liste des demandes traitées
    
    public ListeDemandesExport(ArrayList<DemandeExport> demandesTraitees){
        this.demandesTraitees = demandesTraitees;
    }

    public ArrayList<DemandeExport> getDemandesTraitees() {
        return demandesTraitees;
    }

    public void setDemandesTraitees(ArrayList<DemandeExport> demandesTraitees) {
        this.demandesTraitees = demandesTraitees;
    }
    
    
}
