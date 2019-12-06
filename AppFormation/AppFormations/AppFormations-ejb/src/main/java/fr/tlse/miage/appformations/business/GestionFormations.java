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
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author SALLABERRYMarion
 */
@Stateless
public class GestionFormations implements GestionFormationsLocal {

    /**
     * Provider JMS servant à envoyer la demande traitée à l'AppCommerciale
     */
    @EJB
    private SendDemandeTraiteeLocal sendDemandeTraitee;

    /**
     * Provider JMS servant à envoyer la session planifiée ou annulée à l'AppRH
     * et l'AppPatrimoine
     */
    @EJB
    private SendSessionLocal sendSession;

    /**
     * Objet permettant de manipuler des objets de type Session
     */
    @EJB
    private SessionFacadeLocal sessionFacade;

    /**
     * Objet permettant de manipuler des objets de type Demande
     */
    @EJB
    private DemandeFacadeLocal demandeFacade;

    private ArrayList<Demande> demandesValidees;                            //Liste des demandes validées à traiter
    private ArrayList<ListeFormateursDisposExport> listeFormateursDispos;   //Liste des disponibilités des formateurs
    private ArrayList<ListeSallesDisposExport> listeSallesDispos;           //Liste des disponiblités des salles

    public GestionFormations() {
        this.demandesValidees = new ArrayList<Demande>();
        this.listeFormateursDispos = new ArrayList<ListeFormateursDisposExport>();
        this.listeSallesDispos = new ArrayList<ListeSallesDisposExport>();
    }

    /**
     * Ajout d'une demande validée à la liste des demandes à traiter et création
     * dans la base de données
     *
     * @param demande - demande à ajouter
     */
    @Override
    public void addDemandeValidee(Demande demande) {
        this.demandeFacade.creerDemande(demande.getIdFormation(), demande.getNbParticipants(), demande.getIdClient());
        this.demandesValidees.add(demande);
    }

    /**
     * Suppression d'une demande de la liste des demandes à traiter
     *
     * @param demande - demande à supprimer
     */
    @Override
    public void removeDemandeValidee(Demande demande) {
        this.demandesValidees.remove(demande);
    }

    /**
     * Ajout d'une liste de formateurs disponibles à la liste des disponibilités
     *
     * @param liste - liste à ajouter
     */
    @Override
    public void addListeFormateursDispos(ListeFormateursDisposExport liste) {
        this.listeFormateursDispos.add(liste);
    }

    /**
     * Suppression d'une liste de formateurs disponibles de la liste des
     * disponibilités
     *
     * @param liste - liste à supprimer
     */
    @Override
    public void removeListeFormateursDispos(ListeFormateursDisposExport liste) {
        this.listeFormateursDispos.remove(liste);
    }

    /**
     * Ajout d'une liste de salles disponibles à la liste des disponibilités
     *
     * @param liste - liste à ajouter
     */
    @Override
    public void addListeSallesDispos(ListeSallesDisposExport liste) {
        this.listeSallesDispos.add(liste);
    }

    /**
     * Suppression d'une liste de salles disponibles de la liste des
     * disponibilités
     *
     * @param liste - liste à supprimer
     */
    @Override
    public void removeListeSallesDispos(ListeSallesDisposExport liste) {
        this.listeSallesDispos.remove(liste);
    }

    /**
     * Annulation d'une session de formation
     *
     * @param idSession - identifiant de la session à annuler
     * @return - message de confirmation de l'annulation de la session
     * @throws SessionInexistanteException - la session à annuler n'existe pas
     */
    @Override
    public String annulerSession(long idSession) throws SessionInexistanteException {
        //Récupération de la session dans la base de données
        Session s = this.sessionFacade.find(idSession);

        if (s != null) {
            //Récupération de la liste des demandes associées à cette session
            List<Demande> listeDemandes = s.getListeDemandes();

            //Annulation des demandes
            for (Demande d : listeDemandes) {
                d.setStatut(StatutDemande.Annulee);
            }

            //Mise à jour du statut de la session
            s.setStatut(StatutSession.Annulee);

            //Envoi de la session annulée à l'AppRH et l'AppPatrimoine via le JMS Provider
            SessionExport se = new SessionExport(s);
            this.sendSession.sendSession(se);

            return "La session a été annulée !";

        } else {
            throw new SessionInexistanteException("La session " + idSession + " n'existe pas.");
        }

    }

    /**
     * Traitement des demandes associées à une formation
     *
     * @param codeFormation - identifiant de la formation pour laquelle on
     * souhaite traiter les demandes
     * @return - message de confirmation de succès du traitement
     */
    @Override
    public String traiterDemandes(long codeFormation) {
        //Pour chaque demande contenue dans la liste des demandes à traiter
        for (Demande d : demandesValidees) {
            //Récupération de la liste des sessions présentes dans la base de donnée
            List<Session> listeSessions = this.sessionFacade.findByCodeFormation(codeFormation);
            System.out.println("code formation : " + codeFormation);
            //S'il existe au moins une session
            if (!listeSessions.isEmpty()) {

                System.out.println("Demande validée : " + d);
                if (d.getIdFormation() != null) {
                    System.out.println(d.getIdFormation());
                    //Si l'identifiant de la formation de la demande courante correspond à la formation souhaitée
                    if (d.getIdFormation() == codeFormation) {
                        System.out.println("true");

                        //Récupération du nombre de participants à inscrire
                        int nbParticipants = d.getNbParticipants();

                        //Pour chaque session trouvée
                        for (Session s : listeSessions) {
                            //Calcul du nombre de places restantes dans la session
                            int capaciteRestante = s.getCapaciteMax() - s.getNbParticipants();

                            //S'il reste suffisamment de place, on ajoute la demande
                            if (nbParticipants <= capaciteRestante) {
                                d.setStatut(StatutDemande.Traitee);
                                //Suppression de la liste des demandes à traiter
                                removeDemandeValidee(d);

                                //Mise à jour des informations de la session
                                s.setNbParticipants(s.getNbParticipants() + nbParticipants);
                                s.addDemande(d);

                                //Envoi de la demande traitée à l'AppCommerciale
                                DemandeExport de = new DemandeExport(d);
                                this.sendDemandeTraitee.sendDemandeTraitee(de);

                                //Si l'organisation de la session est confirmée (seuil de participant atteint)
                                if ((nbParticipants >= s.getCapaciteMin()) && (s.getStatut() != StatutSession.Planifiee)) {
                                    s.setStatut(StatutSession.Planifiee);

                                    //Envoi de la session à l'AppRH et l'AppCommerciale
                                    SessionExport se = new SessionExport(s);
                                    this.sendSession.sendSession(se);
                                }
                                //Le nombre de places restant est insuffisant
                            } else {
                                //Si la capacité maximale de la formation n'est pas dépassée
                                if (nbParticipants > capaciteRestante && nbParticipants <= s.getCapaciteMax()) {
                                    //Création d'une nouvelle session de formation
                                    creerNouvelleSession(d, codeFormation);
                                }
                            }
                            //Mise à jour de la session dans la base de données
                            this.sessionFacade.edit(s);
                        }
                    }
                }
            //Aucune session existante pour cette formation
            } else {
                System.out.println("Demande validée : " + d);
                if (d.getIdFormation() != null) {
                    System.out.println(d.getIdFormation());
                    //Si l'identifiant de la formation de la demande courante correspond à la formation souhaitée
                    if (d.getIdFormation() == codeFormation) {
                        //Création d'une nouvelle session de formation
                        creerNouvelleSession(d, codeFormation);
                    }
                }

            }
        }
        return "Les demandes ont été traitées !";
    }

    /**
     * Détermination de la meilleure période pour organiser la session
     * @param listeFormateursDispo - liste des disponibilités des formateurs
     * @param listeSallesDispo - liste des disponiblités des salles
     * @return - numéro de semaine optimal pour organiser la formation
     */
    private static int determinerMeilleurePeriode(ArrayList<FormateurDispoExport> listeFormateursDispo, ArrayList<SalleDispoExport> listeSallesDispo) {
        HashMap<Integer, Long> disposFormateurs = new HashMap<Integer, Long>();
        //Récupération de la liste des formateurs dispos en fonction de la date
        for (FormateurDispoExport f : listeFormateursDispo) {
            for (int date : f.getListeSemainesDispo()) {
                disposFormateurs.put(date, f.getIdFormateur());
            }
        }

        System.out.println("DisposFormateurs : " + disposFormateurs);

        HashMap<Integer, Long> disposSalles = new HashMap<Integer, Long>();
        //Récupération de la liste des salles dispos en fonction de la date
        for (SalleDispoExport f : listeSallesDispo) {
            for (int date : f.getListeSemainesDispo()) {
                disposSalles.put(date, f.getIdSalle());
            }
        }

        System.out.println("DisposSalles : " + disposSalles);

        //Récupération de la liste des dates où des formateurs/salles sont disponibles
        Set<Integer> datesFormateurs = disposFormateurs.keySet();
        Set<Integer> datesSalles = disposSalles.keySet();

        //Comparaison des dates
        for (int dF : datesFormateurs) {
            for (int dS : datesSalles) {
                //Si une date corresond, on la récupère
                if (dF == dS) {
                    System.out.println("dateFormation : " + dF);
                    return dF;
                }
            }
        }
        return 0;
    }

    /**
     * Création d'une nouvelle session
     *
     * @param d - demande à ajouter à la session
     * @param codeFormation - identifiant de la formation pour laquelle on crée
     * la session
     */
    private void creerNouvelleSession(Demande d, long codeFormation) {
        //Création d'une nouvelle session de formation
        Session newSession = new Session();
        this.sessionFacade.create(newSession);
        newSession.setStatut(StatutSession.En_projet);

        //Récupération de la liste des formateurs disponibles pour cette formation
        ArrayList<FormateurDispoExport> formateurs = new ArrayList<FormateurDispoExport>();
        for (ListeFormateursDisposExport liste : this.listeFormateursDispos) {
            FormateurDispoExport form = liste.getFormateursDispos().get(0);
            if (form.getIdFormation() == codeFormation) {
                formateurs.addAll(liste.getFormateursDispos());
            }
        }

        //Récupération de la liste des salles disponibles pour cette formation
        ArrayList<SalleDispoExport> salles = new ArrayList<SalleDispoExport>();
        for (ListeSallesDisposExport liste : this.listeSallesDispos) {
            SalleDispoExport salle = liste.getSallesDispos().get(0);
            if (salle.getIdFormation() == codeFormation) {
                salles.addAll(liste.getSallesDispos());
            }
        }

        //Calcul de la date optimale pour l'organisation de la session
        int date = determinerMeilleurePeriode(formateurs, salles);
        /**
         * Récupérer premier formateur et première salle dispo
         */

        //Si une date a été trouvée
        if (date != 0) {
            //Mise à jour des informations de la session
            newSession.setDate(date);
            newSession.setNbParticipants(d.getNbParticipants());

            d.setStatut(StatutDemande.Traitee);
            newSession.addDemande(d);

            //Suppression de la liste des demandes à traiter
            removeDemandeValidee(d);

            //Envoi de la demande traitée à l'AppCommerciale
            DemandeExport de = new DemandeExport(d);
            this.sendDemandeTraitee.sendDemandeTraitee(de);

            //Si l'organisation de la session est confirmée (seuil de participant atteint)
            if ((d.getNbParticipants() >= newSession.getCapaciteMin()) && (newSession.getStatut() != StatutSession.Planifiee)) {
                newSession.setStatut(StatutSession.Planifiee);

                //Envoi de la session à l'AppRH et l'AppCommerciale
                SessionExport se = new SessionExport(newSession);
                this.sendSession.sendSession(se);
            }
            //Mise à jour de la session dans la base de données
            this.sessionFacade.edit(newSession);
        } else {
            //Suppression de la session créée
            this.sessionFacade.remove(newSession);
        }

    }
}
