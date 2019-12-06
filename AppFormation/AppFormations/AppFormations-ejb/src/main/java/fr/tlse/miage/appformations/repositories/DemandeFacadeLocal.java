/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.tlse.miage.appformations.repositories;

import fr.tlse.miage.appformations.entities.Demande;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author SALLABERRYMarion
 */
@Local
public interface DemandeFacadeLocal {

    void create(Demande demande);

    void edit(Demande demande);

    void remove(Demande demande);

    Demande find(Object id);

    List<Demande> findAll();

    List<Demande> findRange(int[] range);

    int count();

    /**
     * Création d'une demande dans la base de données
     *
     * @param idFormation - identifiant de la formation associée
     * @param nbParticipants - nombre de participants à la formation
     * @param idClient - identifiant du client associé
     * @return - identifiant de la demande créée
     */
    public Long creerDemande(Long idFormation, int nbParticipants, Long idClient);

}
