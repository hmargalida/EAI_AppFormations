/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.tlse.miage.appformations.jms;

import fr.tlse.miage.appformations.exports.SessionExport;
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
public class SendSession implements SendSessionLocal {
    /**
     * Nom du Topic recherché.
     */
    @Resource(mappedName = "Session")
    private Topic Session;
    /**
     * contexte JMS. Injection auto par serveur d'appli.
     */
    @Inject
    @JMSConnectionFactory("ConnectionFactory")
    private JMSContext context;

    public SendSession() {

    }
    
    @Override
    public void sendSession(SessionExport session) {
        try {
            JMSProducer producer = context.createProducer();
            ObjectMessage mess = context.createObjectMessage();
            mess.setJMSType("Session");
            mess.setObject((Serializable) session);
            context.createProducer().send(Session, mess);
            System.out.println(session + " envoyée.");

        } catch (JMSException ex) {
            Logger.getLogger(SendSession.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
