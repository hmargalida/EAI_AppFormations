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
import fr.tlse.miage.appformations.exports.DemandeExport;
import fr.tlse.miage.appformations.exports.FormateurDispoExport;
import fr.tlse.miage.appformations.exports.ListeFormateursDisposExport;
import fr.tlse.miage.appformations.exports.ListeSallesDisposExport;
import fr.tlse.miage.appformations.exports.SalleDispoExport;
import fr.tlse.miage.appformations.exports.SessionExport;
import fr.tlse.miage.appformations.jms.SendDemandeTraiteeLocal;
import fr.tlse.miage.appformations.jms.SendSessionLocal;
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
    private SendDemandeTraiteeLocal sendDemandeTraitee;

    @EJB
    private SendSessionLocal sendSession;

    @EJB
    private SessionFacadeLocal sessionFacade;

    @EJB
    private DemandeFacadeLocal demandeFacade;

    private ArrayList<Demande> demandesValidees;
    private ArrayList<ListeFormateursDisposExport> listeFormateursDispos;
    private ArrayList<ListeSallesDisposExport> listeSallesDispos;

    public GestionFormations() {
        this.demandesValidees = new ArrayList<Demande>();
        this.listeFormateursDispos = new ArrayList<ListeFormateursDisposExport>();
        this.listeSallesDispos = new ArrayList<ListeSallesDisposExport>();
    }

    public void addDemandeValidee(Demande demande) {
        this.demandesValidees.add(demande);
    }

    public void removeDemandeValidee(Demande demande) {
        this.demandesValidees.remove(demande);
    }

    public void addListeFormateursDispos(ListeFormateursDisposExport liste) {
        this.listeFormateursDispos.add(liste);
    }

    public void removeListeFormateursDispos(ListeFormateursDisposExport liste) {
        this.listeFormateursDispos.remove(liste);
    }

    public void addListeSallesDispos(ListeSallesDisposExport liste) {
        this.listeSallesDispos.add(liste);
    }

    public void removeListeSallesDispos(ListeSallesDisposExport liste) {
        this.listeSallesDispos.remove(liste);
    }

    @Override
    public String annulerSession(long idSession) throws SessionInexistanteException {

        Session s = this.sessionFacade.find(idSession);

        if (s != null) {
            List<Demande> listeDemandes = s.getListeDemandes();

            for (Demande d : listeDemandes) {
                d.setStatut(StatutDemande.Annulee);
            }

            s.setStatut(StatutSession.Annulee);

            SessionExport se = new SessionExport(s);
            this.sendSession.sendSession(se);
            
            return "La session a été annulée !";

        } else {
            throw new SessionInexistanteException("La session " + idSession + " n'existe pas.");
        }

    }

    @Override
    public String traiterDemandes(long codeFormation) throws FormationNotFoundException {
        List<Session> listeSessions = this.sessionFacade.findByCodeFormation(codeFormation);

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

                            DemandeExport de = new DemandeExport(d);
                            this.sendDemandeTraitee.sendDemandeTraitee(de);

                            if ((nbParticipants >= s.getCapaciteMin()) && (s.getStatut() != StatutSession.Planifiee)) {
                                s.setStatut(StatutSession.Planifiee);

                                SessionExport se = new SessionExport(s);
                                this.sendSession.sendSession(se);
                            }

                        } else {
                            if (nbParticipants > capaciteRestante && nbParticipants <= s.getCapaciteMax()) {
                                Session newSession = new Session();
                                this.sessionFacade.create(newSession);
                                newSession.setStatut(StatutSession.En_projet);

                                ArrayList<FormateurDispoExport> formateurs = new ArrayList<FormateurDispoExport>();
                                for (ListeFormateursDisposExport liste : this.listeFormateursDispos) {
                                    FormateurDispoExport form = liste.getFormateursDispos().get(0);
                                    if (form.getIdFormation() == codeFormation) {
                                        formateurs.addAll(liste.getFormateursDispos());
                                    }
                                }

                                ArrayList<SalleDispoExport> salles = new ArrayList<SalleDispoExport>();
                                for (ListeSallesDisposExport liste : this.listeSallesDispos) {
                                    SalleDispoExport salle = liste.getSallesDispos().get(0);
                                    if (salle.getIdFormation() == codeFormation) {
                                        salles.addAll(liste.getSallesDispos());
                                    }
                                }

                                int date = determinerMeilleurePeriode(codeFormation, formateurs, salles);
                                if (date != 0) {
                                    newSession.setDate(date);
                                    newSession.setNbParticipants(d.getNbParticipants());
                                    newSession.addDemande(d);

                                    DemandeExport de = new DemandeExport(d);
                                    this.sendDemandeTraitee.sendDemandeTraitee(de);

                                    if ((nbParticipants >= s.getCapaciteMin()) && (s.getStatut() != StatutSession.Planifiee)) {
                                        newSession.setStatut(StatutSession.Planifiee);

                                        SessionExport se = new SessionExport(s);
                                        this.sendSession.sendSession(se);
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
        return "Les demandes ont été traitées !";
    }

    private static int determinerMeilleurePeriode(long codeFormation, ArrayList<FormateurDispoExport> listeFormateursDispo, ArrayList<SalleDispoExport> listeSallesDispo) {
        HashMap<Integer, Long> disposFormateurs = new HashMap<Integer, Long>();
        for (FormateurDispoExport f : listeFormateursDispo) {
            for (int date : f.getListeSemainesDispo()) {
                disposFormateurs.put(date, f.getIdFormateur());
            }
        }

        HashMap<Integer, Long> disposSalles = new HashMap<Integer, Long>();
        for (SalleDispoExport f : listeSallesDispo) {
            for (int date : f.getListeSemainesDispo()) {
                disposSalles.put(date, f.getIdSalle());
            }
        }

        Set<Integer> datesFormateurs = disposFormateurs.keySet();
        Set<Integer> datesSalles = disposSalles.keySet();

        for (int dF : datesFormateurs) {
            for (int dS : datesSalles) {
                if (dF == dS) {
                    return dF;
                }
            }
        }
        return 0;
    }
}
