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
public class ListeFormateursDisposExport {
    private ArrayList<FormateurDispoExport> formateursDispos ; //Liste des disponiblités des formateurs
    
    public ListeFormateursDisposExport(ArrayList<FormateurDispoExport> formateursDispos){
        this.formateursDispos = formateursDispos;
    }

    public ArrayList<FormateurDispoExport> getFormateursDispos() {
        return formateursDispos;
    }

    public void setFormateursDispos(ArrayList<FormateurDispoExport> formateursDispos) {
        this.formateursDispos = formateursDispos;
    }
    
    
}
