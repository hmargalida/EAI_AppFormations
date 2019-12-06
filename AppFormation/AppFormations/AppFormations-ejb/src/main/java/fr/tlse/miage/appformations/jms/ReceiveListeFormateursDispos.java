/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.tlse.miage.appformations.jms;

import com.google.gson.Gson;
import fr.tlse.miage.appformations.business.GestionFormationsLocal;
import fr.tlse.miage.appformations.exports.ListeFormateursDisposExport;
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
    @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "ListeFormateursDispos")
    ,
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic")
})
public class ReceiveListeFormateursDispos implements MessageListener {

    @EJB
    private GestionFormationsLocal gestionFormations;   //Objet permettant d'appeler le code métier

    private Gson gson;        //Objet permettant d'effectuer des conversions depuis/vers du json

    public ReceiveListeFormateursDispos() {
        this.gson = new Gson();
    }

    /**
     * Réception des messages contenus dans le topic ListeFormateursDispos
     *
     * @param message - message reçu
     */
    @Override
    public void onMessage(Message message) {
        if (message instanceof TextMessage) {
            try {
                //Récupération du contenu du message
                String json = ((TextMessage) message).getText();
                //Conversion du message en objet ListeFormateursDisposExport
                ListeFormateursDisposExport listeFormateurs = this.gson.fromJson(json, ListeFormateursDisposExport.class);
                //Ajout de la liste à la liste des disponibilités
                this.gestionFormations.addListeFormateursDispos(listeFormateurs);
                System.out.println("Formateurs ajoutés à la liste des formateurs disponibles !");
            } catch (JMSException ex) {
                Logger.getLogger(ReceiveListeFormateursDispos.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (message != null) {
            System.out.println("Echec de réception du message");
        }
    }

}
