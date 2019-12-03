/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.tlse.miage.appformations.jms;

import fr.tlse.miage.appformations.exports.DemandeExport;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.ObjectMessage;
import javax.jms.Topic;

/**
 *
 * @author SALLABERRYMarion
 */
@Singleton
public class SendDemandeTraitee implements SendDemandeTraiteeLocal {

    /**
     * Nom du Topic recherché.
     */
    @Resource(mappedName = "DemandesTraitees")
    private Topic DemandesTraitees;
    /**
     * contexte JMS. Injection auto par serveur d'appli.
     */
    @Inject
    @JMSConnectionFactory("ConnectionFactory")
    private JMSContext context;

    public SendDemandeTraitee() {

    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Override
    public void sendDemandeTraitee(DemandeExport demande) {
        try {
            JMSProducer producer = context.createProducer();
            ObjectMessage mess = context.createObjectMessage();
            mess.setJMSType("DemandeExport");
            mess.setObject((Serializable) demande);
            context.createProducer().send(DemandesTraitees, mess);
            System.out.println(demande + " envoyée.");

        } catch (JMSException ex) {
            Logger.getLogger(SendDemandeTraitee.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
