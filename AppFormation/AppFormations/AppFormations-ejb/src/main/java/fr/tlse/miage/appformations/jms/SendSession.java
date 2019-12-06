/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.tlse.miage.appformations.jms;

import com.google.gson.Gson;
import fr.tlse.miage.appformations.exports.SessionExport;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.TextMessage;
import javax.jms.Topic;

/**
 *
 * @author SALLABERRYMarion
 */
@Singleton
public class SendSession implements SendSessionLocal {

    /**
     * Nom du Topic
     */
    @Resource(mappedName = "Session")
    private Topic Session;
    /**
     * contexte JMS
     */
    @Inject
    @JMSConnectionFactory("ConnectionFactory")
    private JMSContext context;

    private Gson gson;      //Objet permettant d'effectuer des conversions depuis/vers du json

    public SendSession() {
        this.gson = new Gson();
    }

    /**
     * Envoi de messages dans le topic Session
     *
     * @param session - session à envoyer
     */
    @Override
    public void sendSession(SessionExport session) {
        try {
            JMSProducer producer = context.createProducer();
            TextMessage mess = context.createTextMessage();
            //Conversion de l'objet session en json
            mess.setText(this.gson.toJson(session));
            mess.setJMSType("DemandeExport");
            //Envoi du message
            context.createProducer().send(Session, mess);
            System.out.println(session + " envoyée.");

        } catch (JMSException ex) {
            Logger.getLogger(SendSession.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
