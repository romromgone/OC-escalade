package com.ocp3.forms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.ocp3.beans.Topo;
import com.ocp3.beans.Utilisateur;
import com.ocp3.dao.DaoException;
import com.ocp3.dao.TopoDao;


public class MesToposForm {
	private static final String CHAMP_TITRE = "titre";
    private static final String CHAMP_AUTEUR = "auteur";
	private static final String CHAMP_ANNEEEDITION = "anneeEdition";
	private static final String CHAMP_DEPARTEMENT = "departement";
    private static final String CHAMP_DESCRIPTION = "description";
    
    private String resultat;
    private Map<String, String> erreurs = new HashMap<String, String>();
    private TopoDao topoDao;

    public MesToposForm( TopoDao topoDao ) {
        this.topoDao = topoDao;
    }

    public String getResultat() {
        return resultat;
    }

    public Map<String, String> getErreurs() {
        return erreurs;
    }
    
    public Topo ajouterTopo( HttpServletRequest request ) {
    	String titre = getValeurChamp( request, CHAMP_TITRE );
    	String auteur = getValeurChamp( request, CHAMP_AUTEUR );
        String anneeEdition = getValeurChamp( request, CHAMP_ANNEEEDITION );
        String departement = getValeurChamp( request, CHAMP_DEPARTEMENT );
        String description = getValeurChamp( request, CHAMP_DESCRIPTION );
        
        Topo topo = new Topo();

        try {
        	traiterTitre( titre, topo );
        	traiterAuteur( auteur, topo );
        	traiterAnneeEdition( anneeEdition, topo );
        	traiterDepartement( departement, topo );
            traiterDescription( description, topo );
            
            /* Récupération de l'utilisateur en session et affectation au topo */
            Utilisateur utilisateur = (Utilisateur) request.getSession().getAttribute("sessionUtilisateur");
            topo.setUtilisateur(utilisateur);
            topo.setIdUser(utilisateur.getId());
            
	        if ( erreurs.isEmpty() ) {
	        	topoDao.ajouter( topo );
	            resultat = "Topo ajouté.";
	        } 
	        else resultat = "Échec de l'ajout.";
        } catch ( DaoException e ) {
            resultat = "Échec : une erreur imprévue est survenue.";
            e.printStackTrace();
        }
        return topo;
    }
    
   
    private void traiterTitre( String titre, Topo topo ) {
        try {
            validationTitre( titre );
        } catch ( FormValidationException e ) {
            setErreur( CHAMP_TITRE, e.getMessage() );
        }
        topo.setTitre( titre );
    }
    
    private void traiterAuteur( String auteur, Topo topo ) {
        try {
            validationAuteur( auteur );
        } catch ( FormValidationException e ) {
            setErreur( CHAMP_AUTEUR, e.getMessage() );
        }
        topo.setAuteur( auteur );
    }
    
    private void traiterAnneeEdition( String anneeEdition, Topo topo ) {
        try {
            validationAnneeEdition( anneeEdition );
        } catch ( FormValidationException e ) {
            setErreur( CHAMP_ANNEEEDITION, e.getMessage() );
        }
        topo.setAnneeEdition( anneeEdition );
    }
    
    private void traiterDepartement( String departement, Topo topo ) {
        try {
            validationDepartement( departement );
        } catch ( FormValidationException e ) {
            setErreur( CHAMP_DEPARTEMENT, e.getMessage() );
        }
        topo.setDepartement( departement );
    }
    
    private void traiterDescription( String description, Topo topo ) {
        try {
            validationDescription( description );
        } catch ( FormValidationException e ) {
            setErreur( CHAMP_DESCRIPTION, e.getMessage() );
        }
        topo.setDescription( description );
    }
    
    
    private void validationTitre( String titre ) throws FormValidationException {
        if ( titre == null ) {
        	throw new FormValidationException( "Merci de saisir le titre." );
        }
    }
    
    private void validationAuteur( String auteur ) throws FormValidationException {
        if ( auteur != null && auteur.length() < 3 ) {
            throw new FormValidationException( "Le nom doit contenir au moins 3 caractères." );
        }
        if ( auteur == null ) {
        	throw new FormValidationException( "Merci de saisir l'auteur." );
        }
    }
    
    private void validationAnneeEdition( String anneeEdition ) throws FormValidationException {
        if ( anneeEdition != null && anneeEdition.length() != 4 ) {
            throw new FormValidationException( "L'année d'édition doit contenir 4 chiffres." );
        }
        if ( anneeEdition == null ) {
        	throw new FormValidationException( "Merci de saisir l'année d'édition." );
        }
    }
    
    private void validationDepartement( String departement ) throws FormValidationException {
        if ( departement != null && ( departement.length() > 3 || departement.length() < 2 ) ) {
            throw new FormValidationException( "Le département doit contenir 2 ou 3 chiffres." );
        }
        if ( departement == null ) {
        	throw new FormValidationException( "Merci de saisir à quel département ce topo fait référence." );
        }
    }
    
    private void validationDescription( String description ) throws FormValidationException {
        if ( description != null && description.length() < 50 ) {
            throw new FormValidationException( "Min 50 caractères." );
        }
        if ( description == null ) {
        	throw new FormValidationException( "Merci de saisir une description de ce topo (min 50 caractères)." );
        }
    }
    
    
    /*
     * Ajoute un message correspondant au champ spécifié à la map des erreurs.
     */
    private void setErreur( String champ, String message ) {
        erreurs.put( champ, message );
    }
    
    /*
     * Méthode utilitaire qui retourne null si un champ est vide, et son contenu
     * sinon.
     */
    private static String getValeurChamp( HttpServletRequest request, String nomChamp ) {
        String valeur = request.getParameter( nomChamp );
        if ( valeur == null || valeur.trim().length() == 0 ) {
            return null;
        } else {
            return valeur.trim();
        }
    }

}
