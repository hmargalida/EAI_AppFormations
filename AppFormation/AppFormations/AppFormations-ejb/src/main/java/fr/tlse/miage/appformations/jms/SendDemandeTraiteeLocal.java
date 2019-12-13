/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.tlse.miage.appformations.jms;

import fr.tlse.miage.appformations.exports.DemandeExport;
import fr.tlse.miage.appformations.exports.ListeDemandesExport;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author SALLABERRYMarion
 */
@Local
public interface SendDemandeTraiteeLocal {

    /**
     * Envoi de messages dans le topic DemandesTraitees
     *
     * @param demandes - demande à envoyer
     */
    void sendDemandeTraitee(ListeDemandesExport demandes);
}
