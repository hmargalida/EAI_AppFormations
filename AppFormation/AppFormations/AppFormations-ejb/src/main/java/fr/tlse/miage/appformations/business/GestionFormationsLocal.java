/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.tlse.miage.appformations.business;

import fr.tlse.miage.appformations.exceptions.FormationNotFoundException;
import fr.tlse.miage.appformations.exceptions.SessionInexistanteException;
import javax.ejb.Local;

/**
 *
 * @author SALLABERRYMarion
 */
@Local
public interface GestionFormationsLocal {

    void annulerSession(long idSession) throws SessionInexistanteException;
    
    String annulerSession(String content) throws SessionInexistanteException;

    void traiterDemandes(long codeFormation) throws FormationNotFoundException;
    
    String traiterDemandes(String content) throws FormationNotFoundException;
}
