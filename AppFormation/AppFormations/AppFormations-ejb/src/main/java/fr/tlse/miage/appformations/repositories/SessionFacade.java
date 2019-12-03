/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.tlse.miage.appformations.repositories;

import fr.tlse.miage.appformations.entities.Session;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author SALLABERRYMarion
 */
@Stateless
public class SessionFacade extends AbstractFacade<Session> implements SessionFacadeLocal {

    @PersistenceContext(unitName = "appFormationsPersistenceUnit")
    private EntityManager em;
    private Session session;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SessionFacade() {
        super(Session.class);
    }
    
    public void creerSession(Long idSession, int date, int nbParticipants, int duree, int capaciteMin, int capaciteMax, Long idFormation){
        this.session = new Session(idSession, date, nbParticipants, duree, capaciteMin, capaciteMax, idFormation);
        this.create(session);
    }
    
    public List<Session> findByCodeFormation(long codeFormation){
        List<Session> sessionsFormation = new ArrayList<Session>();
        return sessionsFormation;
    }
    
}
