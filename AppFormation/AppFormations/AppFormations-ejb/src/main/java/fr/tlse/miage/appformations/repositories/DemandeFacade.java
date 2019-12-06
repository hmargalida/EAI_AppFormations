/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.tlse.miage.appformations.repositories;

import fr.tlse.miage.appformations.entities.Demande;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author SALLABERRYMarion
 */
@Stateless
public class DemandeFacade extends AbstractFacade<Demande> implements DemandeFacadeLocal {

    @PersistenceContext(unitName = "appFormationsPersistenceUnit")
    private EntityManager em;
    private Demande demande;

    /**
     * Récupération d'une instance de l'EntityManager pour agir sur la base de données
     * @return - instance de l'EntityManager
     */
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DemandeFacade() {
        super(Demande.class);
    }
    
    /**
     * Création d'une demande dans la base de données
     * @param idFormation - identifiant de la formation associée
     * @param nbParticipants - nombre de participants à la formation
     * @param idClient - identifiant du client associé
     * @return - identifiant de la demande créée
     */
    public Long creerDemande(Long idFormation, int nbParticipants, Long idClient){
        this.demande = new Demande(idFormation, nbParticipants, idClient);
        this.create(demande);
        return demande.getIdDemande();
    }
}
