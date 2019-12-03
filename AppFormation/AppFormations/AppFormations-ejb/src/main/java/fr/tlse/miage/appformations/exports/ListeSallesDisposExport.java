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
public class ListeSallesDisposExport {
    private ArrayList<SalleDispoExport> sallesDispos ;
    
    public ListeSallesDisposExport(ArrayList<SalleDispoExport> sallesDispos){
        this.sallesDispos = sallesDispos;
    }

    public ArrayList<SalleDispoExport> getSallesDispos() {
        return sallesDispos;
    }

    public void setSallesDispos(ArrayList<SalleDispoExport> sallesDispos) {
        this.sallesDispos = sallesDispos;
    }  
}
