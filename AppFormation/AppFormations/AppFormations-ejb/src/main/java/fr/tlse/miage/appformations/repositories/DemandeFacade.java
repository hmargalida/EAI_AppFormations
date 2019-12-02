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

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DemandeFacade() {
        super(Demande.class);
    }
    
    public void creerDemande(Long idDemande, Long idFormation, int nbParticipants, Long idClient){
        this.demande = new Demande(idDemande, idFormation, nbParticipants, idClient);
        this.create(demande);
    }
}
