/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.tlse.miage.appformations.jms;

import com.google.gson.Gson;
import fr.tlse.miage.appformations.business.GestionFormationsLocal;
import fr.tlse.miage.appformations.entities.Demande;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 *
 * @author SALLABERRYMarion
 */
@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "DemandesFormationsValidees")
    ,
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})
public class ReceiveDemandesValidees implements MessageListener{

    @EJB
    private GestionFormationsLocal gestionFormations;
    private Gson gson;
    

    public ReceiveDemandesValidees() {
        this.gson = new Gson();
    }
    @Override
    public void onMessage(Message message) {
                if (message instanceof TextMessage) {
            try {
                String json = ((TextMessage) message).getText();
                Demande demande = this.gson.fromJson(json, Demande.class);
                this.gestionFormations.addDemandeValidee(demande);
                System.out.println("Demande ajoutée à la liste des demandes à traiter !");
            } catch (JMSException ex) {
                Logger.getLogger(ReceiveDemandesValidees.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (message != null) {
            System.out.println("Echec de réception du message");
        }
    }
    
}
