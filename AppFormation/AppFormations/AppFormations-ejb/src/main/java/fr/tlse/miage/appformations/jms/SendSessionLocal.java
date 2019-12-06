/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.tlse.miage.appformations.jms;

import fr.tlse.miage.appformations.exports.SessionExport;
import javax.ejb.Local;

/**
 *
 * @author SALLABERRYMarion
 */
@Local
public interface SendSessionLocal {

    /**
     * Envoi de messages dans le topic Session
     *
     * @param session - session à envoyer
     */
    void sendSession(SessionExport session);
}
