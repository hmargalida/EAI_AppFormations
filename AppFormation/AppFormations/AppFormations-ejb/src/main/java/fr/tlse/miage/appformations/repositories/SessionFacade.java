/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.tlse.miage.appformations.repositories;

import fr.tlse.miage.appformations.entities.Session;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author SALLABERRYMarion
 */
@Stateless
public class SessionFacade extends AbstractFacade<Session> implements SessionFacadeLocal {

    @PersistenceContext(unitName = "appFormationsPersistenceUnit")
    private EntityManager em;
    private Session session;

    /**
     * Récupération d'une instance de l'EntityManager pour agir sur la base de
     * données
     *
     * @return - instance de l'EntityManager
     */
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SessionFacade() {
        super(Session.class);
    }

    /**
     * Création d'une session dans la base de données
     * @param date - numéro de semaine d'organisation de la session
     * @param nbParticipants - nombre de participants à la session
     * @param duree - durée de la session (3 ou 5 jours)
     * @param capaciteMin - capacité minimale de la session
     * @param capaciteMax - capacité maximale de la session
     * @param idFormation - identifiant de la formation associée
     * @return - identifiant de la session créée
     */
    @Override
    public Long creerSession(int date, int nbParticipants, int duree, int capaciteMin, int capaciteMax, Long idFormation) {
        this.session = new Session(date, nbParticipants, duree, capaciteMin, capaciteMax, idFormation);
        this.create(session);
        return session.getIdSession();
    }

    /**
     * Récupération de la liste des sessions associées à une formation
     * @param codeFormation - identifiant de la formation recherchée
     * @return - liste des sessions associées
     */
    @Override
    public List<Session> findByCodeFormation(long codeFormation) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Session> cq = cb.createQuery(Session.class);
        //Construction de la requête
        Root<Session> root = cq.from(Session.class);
        cq.where(
                cb.equal(root.get("idFormation").as(Long.class), codeFormation)
        );
        //Récupération des résultats
        return getEntityManager().createQuery(cq).getResultList();
    }

}
