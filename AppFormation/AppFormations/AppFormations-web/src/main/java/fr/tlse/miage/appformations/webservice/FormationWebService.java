/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.tlse.miage.appformations.webservice;

import fr.tlse.miage.appformations.business.GestionFormationsLocal;
import fr.tlse.miage.appformations.exceptions.FormationNotFoundException;
import fr.tlse.miage.appformations.exceptions.SessionInexistanteException;
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
     * Retrieves representation of an instance of
     * fr.tlse.miage.appformations.webservice.FormationWebService
     *
     * @param content
     * @return an instance of java.lang.String
     */
    @Path("{idSession}/annulerSession")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public String annulerSession(String content) {
        try{
            return this.gestionFormations.annulerSession(content);
        }
        catch(SessionInexistanteException siexc){
            return siexc.toString();
        }   
    }

    /**
     * POST method for updating or creating an instance of FormationWebService
     *
     * @param content representation for the resource
     * @return 
     */
    @Path("{idFormation}/traiterDemandes")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String traiterDemandes(String content) {
        try{
            return this.gestionFormations.traiterDemandes(content);
        }
        catch(FormationNotFoundException fnfexc){
            return fnfexc.toString();
        }
        
    }

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
