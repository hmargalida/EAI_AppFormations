/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.tlse.miage.appformations.jms;

import com.google.gson.Gson;
import fr.tlse.miage.appformations.exports.DemandeExport;
import fr.tlse.miage.appformations.exports.ListeDemandesExport;
import java.util.List;
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
public class SendDemandeTraitee implements SendDemandeTraiteeLocal {

    /**
     * Nom du Topic
     */
    @Resource(mappedName = "DemandesTraitees")
    private Topic DemandesTraitees;
    /**
     * contexte JMS
     */
    @Inject
    @JMSConnectionFactory("ConnectionFactory")
    private JMSContext context;
    
    private Gson gson;      //Objet permettant d'effectuer des conversions depuis/vers du json

    public SendDemandeTraitee() {
        this.gson = new Gson();
    }

    /**
     * Envoi de messages dans le topic DemandesTraitees
     *
     * @param demandes - demande à envoyer
     */
    @Override
    public void sendDemandeTraitee(ListeDemandesExport demandes) {
        try {
            JMSProducer producer = context.createProducer();
            TextMessage mess = context.createTextMessage();
            //Conversion de l'objet demande en json
            mess.setText(this.gson.toJson(demandes));
            mess.setJMSType("ListeDemandesExport");
            //Envoi du message
            context.createProducer().send(DemandesTraitees, mess);
            System.out.println(demandes + " traitées envoyées.");


        } catch (JMSException ex) {
            Logger.getLogger(SendDemandeTraitee.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
