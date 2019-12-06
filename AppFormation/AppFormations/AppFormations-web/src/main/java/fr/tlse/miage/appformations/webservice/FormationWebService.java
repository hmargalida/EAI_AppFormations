/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.tlse.miage.appformations.webservice;

import fr.tlse.miage.appformations.business.GestionFormationsLocal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.enterprise.context.RequestScoped;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author SALLABERRYMarion
 */
@Path("formation")
@RequestScoped
public class FormationWebService {

    GestionFormationsLocal gestionFormations = lookupGestionFormationsLocal();

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of FormationWebService
     */
    public FormationWebService() {
    }

    /**
     * Annule une session de formation
     *
     * @param idSession - identifiant de la session à annuler
     * @return - message de confirmation de l'annulation
     */
    @Path("{idSession}/annulerSession")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public String annulerSession(@PathParam("idSession") long idSession) {
        try {
            return this.gestionFormations.annulerSession(idSession);
        } catch (Exception ex) {
            Logger.getLogger(FormationWebService.class.getName()).log(Level.SEVERE, null, ex);
            return ex.toString();
        }
    }

    /**
     * Traiter les demandes associées à une formation
     *
     * @param idFormation - identifiant de la formation pour laquelle on
     * souhaite traiter les demandes
     * @return - message de confirmation du traitement des demandes
     */
    @Path("{idFormation}/traiterDemandes")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String traiterDemandes(@PathParam("idFormation") long idFormation) {
        try {
            return this.gestionFormations.traiterDemandes(idFormation);
        } catch (Exception ex) {
            Logger.getLogger(FormationWebService.class.getName()).log(Level.SEVERE, null, ex);
            return ex.toString();
        }
    }

    /**
     * Initialise la variable gestionFormations
     *
     * @return - instance de la classe GestionFormationsLocal
     */
    private GestionFormationsLocal lookupGestionFormationsLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (GestionFormationsLocal) c.lookup("java:global/AppFormations-ear/AppFormations-ejb-1.0-SNAPSHOT/GestionFormations!fr.tlse.miage.appformations.business.GestionFormationsLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

}
