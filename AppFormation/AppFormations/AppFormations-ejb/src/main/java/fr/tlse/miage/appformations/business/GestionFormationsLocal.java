/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.tlse.miage.appformations.business;

import fr.tlse.miage.appformations.entities.Demande;
import fr.tlse.miage.appformations.exceptions.FormationNotFoundException;
import fr.tlse.miage.appformations.exceptions.SessionInexistanteException;
import fr.tlse.miage.appformations.exports.ListeFormateursDisposExport;
import fr.tlse.miage.appformations.exports.ListeSallesDisposExport;
import javax.ejb.Local;

/**
 *
 * @author SALLABERRYMarion
 */
@Local
public interface GestionFormationsLocal {

    void addDemandeValidee(Demande demande);
    
    void removeDemandeValidee(Demande demande);
    
    void addListeFormateursDispos(ListeFormateursDisposExport liste);

    void removeListeFormateursDispos(ListeFormateursDisposExport liste);

    void addListeSallesDispos(ListeSallesDisposExport liste);

    void removeListeSallesDispos(ListeSallesDisposExport liste);
    
    String annulerSession(long idSession) throws SessionInexistanteException;

    String traiterDemandes(long codeFormation) throws FormationNotFoundException;

}
