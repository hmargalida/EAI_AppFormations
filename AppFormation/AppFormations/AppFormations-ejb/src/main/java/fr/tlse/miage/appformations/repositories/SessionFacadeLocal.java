/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.tlse.miage.appformations.repositories;

import fr.tlse.miage.appformations.entities.Session;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author SALLABERRYMarion
 */
@Local
public interface SessionFacadeLocal {

    void create(Session session);

    void edit(Session session);

    void remove(Session session);

    Session find(Object id);

    List<Session> findAll();

    List<Session> findRange(int[] range);

    int count();

    public void creerSession(Long idSession, Date date, int nbParticipants, int duree, int capaciteMin, int capaciteMax, Long idFormation);

    public List<Session> findByCodeFormation(long codeFormation);    
}
