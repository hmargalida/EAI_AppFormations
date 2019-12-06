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

    /**
     * Ajout d'une demande validée à la liste des demandes à traiter et création
     * dans la base de données
     *
     * @param demande - demande à ajouter
     */
    void addDemandeValidee(Demande demande);

    /**
     * Suppression d'une demande de la liste des demandes à traiter
     *
     * @param demande - demande à supprimer
     */
    void removeDemandeValidee(Demande demande);

    /**
     * Ajout d'une liste de formateurs disponibles à la liste des disponibilités
     *
     * @param liste - liste à ajouter
     */
    void addListeFormateursDispos(ListeFormateursDisposExport liste);

    /**
     * Suppression d'une liste de formateurs disponibles de la liste des
     * disponibilités
     *
     * @param liste - liste à supprimer
     */
    void removeListeFormateursDispos(ListeFormateursDisposExport liste);

    /**
     * Ajout d'une liste de salles disponibles à la liste des disponibilités
     *
     * @param liste - liste à ajouter
     */
    void addListeSallesDispos(ListeSallesDisposExport liste);

    /**
     * Suppression d'une liste de salles disponibles de la liste des
     * disponibilités
     *
     * @param liste - liste à supprimer
     */
    void removeListeSallesDispos(ListeSallesDisposExport liste);

    /**
     * Annulation d'une session de formation
     *
     * @param idSession - identifiant de la session à annuler
     * @return - message de confirmation de l'annulation de la session
     * @throws SessionInexistanteException - la session à annuler n'existe pas
     */
    String annulerSession(long idSession) throws SessionInexistanteException;

    /**
     * Traitement des demandes associées à une formation
     *
     * @param codeFormation - identifiant de la formation pour laquelle on
     * souhaite traiter les demandes
     * @return - message de confirmation de succès du traitement
     */
    String traiterDemandes(long codeFormation) throws FormationNotFoundException;

}
