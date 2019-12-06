/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.tlse.miage.appformations.repositories;

import fr.tlse.miage.appformations.entities.Session;
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

    /**
     * Création d'une session dans la base de données
     *
     * @param date - numéro de semaine d'organisation de la session
     * @param nbParticipants - nombre de participants à la session
     * @param duree - durée de la session (3 ou 5 jours)
     * @param capaciteMin - capacité minimale de la session
     * @param capaciteMax - capacité maximale de la session
     * @param idFormation - identifiant de la formation associée
     * @return - identifiant de la session créée
     */
    public Long creerSession(int date, int nbParticipants, int duree, int capaciteMin, int capaciteMax, Long idFormation);

    /**
     * Récupération de la liste des sessions associées à une formation
     *
     * @param codeFormation - identifiant de la formation recherchée
     * @return - liste des sessions associées
     */
    public List<Session> findByCodeFormation(long codeFormation);
}
