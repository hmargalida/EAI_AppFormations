/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.tlse.miage.appformations.business;

import fr.tlse.miage.appformations.entities.Demande;
import fr.tlse.miage.appformations.enumerations.StatutDemande;
import fr.tlse.miage.appformations.entities.Session;
import fr.tlse.miage.appformations.enumerations.StatutSession;
import fr.tlse.miage.appformations.exceptions.FormationNotFoundException;
import fr.tlse.miage.appformations.exceptions.SessionInexistanteException;
import fr.tlse.miage.appformations.repositories.DemandeFacadeLocal;
import fr.tlse.miage.appformations.repositories.SessionFacadeLocal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author SALLABERRYMarion
 */
@Stateless
public class GestionFormations implements GestionFormationsLocal {

    @EJB
    private SessionFacadeLocal sessionFacade;

    @EJB
    private DemandeFacadeLocal demandeFacade;

    @Override
    public void annulerSession(long idSession) throws SessionInexistanteException {

        Session s = this.sessionFacade.find(idSession);

        if (s != null) {
            List<Demande> listeDemandes = s.getListeDemandes();

            for (Demande d : listeDemandes) {
                d.setStatut(StatutDemande.Annulee);
            }

            s.setStatut(StatutSession.Annulee);

            //Envoi session annulee dans JMS
        } else {
            throw new SessionInexistanteException("La session " + idSession + " n'existe pas.");
        }

    }
    
    @Override
    public String annulerSession(String content){
        return "OK annulerSession";
    }

    @Override
    public void traiterDemandes(long codeFormation) throws FormationNotFoundException {
        //Récupération liste demandes validées
        List<Demande> demandesValidees = new ArrayList<Demande>();
        List<Session> listeSessions = this.sessionFacade.findByCodeFormation(codeFormation);
        List<Demande> demandesTraitees = new ArrayList<Demande>();

        for (Demande d : demandesValidees) {
            if (d.getIdFormation() != null) {
                if (d.getIdFormation() == codeFormation) {
                    int nbParticipants = d.getNbParticipants();
                    for (Session s : listeSessions) {
                        int capaciteRestante = s.getCapaciteMax() - s.getNbParticipants();
                        if (nbParticipants <= capaciteRestante) {
                            d.setStatut(StatutDemande.Traitee);
                            s.setNbParticipants(s.getNbParticipants() + nbParticipants);
                            s.addDemande(d);
                            demandesTraitees.add(d);

                            if ((nbParticipants >= s.getCapaciteMin()) && (s.getStatut() != StatutSession.Planifiee)) {
                                s.setStatut(StatutSession.Planifiee);
                                //Envoi dans JMS Session Planifiée
                            }

                        } else {
                            if (nbParticipants > capaciteRestante && nbParticipants <= s.getCapaciteMax()) {
                                Session newSession = new Session();
                                this.sessionFacade.create(newSession);
                                newSession.setStatut(StatutSession.En_projet);
                                //Recuperer listeFormateursDispo dans JMS
                                //RecupererListeSallesDispo dans JMS
                                Map<Long, Map<Date, Long>> listeFormateursDispo = new HashMap<Long, Map<Date, Long>>();
                                Map<Long, Map<Date, Long>> listeSallesDispo = new HashMap<Long, Map<Date, Long>>();
                                Date date = determinerMeilleurePeriode(codeFormation, listeFormateursDispo, listeSallesDispo, newSession.getDuree());
                                if (date != null) {
                                    newSession.setDate(date);
                                    newSession.setNbParticipants(d.getNbParticipants());
                                    newSession.addDemande(d);
                                    demandesTraitees.add(d);

                                    if ((nbParticipants >= s.getCapaciteMin()) && (s.getStatut() != StatutSession.Planifiee)) {
                                        newSession.setStatut(StatutSession.Planifiee);
                                        //Envoi dans JMS Session Planifiée
                                    }

                                    this.sessionFacade.edit(newSession);
                                }
                            }
                        }

                        this.sessionFacade.edit(s);
                    }
                }
            } else {
                throw new FormationNotFoundException("La demande " + d.getIdDemande() + " n'a pas de formation associée.");
            }

        }
        //Envoi listeDemandesTraitees dans JMS
    }
    
    @Override
    public String traiterDemandes(String content){
        return "OK traiterDemandes";
    }

    private static Date determinerMeilleurePeriode(long codeFormation, Map<Long, Map<Date, Long>> listeFormateursDispo, Map<Long, Map<Date, Long>> listeSallesDispo, int duree) {
        HashMap<Date,Long> formateursDispo = (HashMap<Date,Long>) listeFormateursDispo.get(codeFormation);
        HashMap<Date,Long> sallesDispo = (HashMap<Date,Long>) listeSallesDispo.get(codeFormation);
        Set<Date> datesFormateursDispo = formateursDispo.keySet();
        Set<Date> datesSallesDispo = sallesDispo.keySet();
        for (Date dF : datesFormateursDispo) {
            for (Date dS : datesSallesDispo) {
                if (dF == dS) {
                    return dF;
                }
            }
        }
        return null;
    }

}
