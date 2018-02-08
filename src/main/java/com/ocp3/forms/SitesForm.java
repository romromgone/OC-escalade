package com.ocp3.forms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.ocp3.beans.Site;
import com.ocp3.dao.DaoException;
import com.ocp3.dao.SiteDao;


public class SitesForm {
	private static final String CHAMP_NOM = "nom";
    private static final String CHAMP_COMMUNE = "commune";
	private static final String CHAMP_CODEPOSTAL = "codePostal";
	private static final String CHAMP_ALTITUDE = "altitude";
	private static final String CHAMP_ORIENTATION = "orientation";
	private static final String CHAMP_ROCHER = "rocher";
	private static final String CHAMP_ACCES = "acces";
	private static final String CHAMP_DESCRIPTION = "description";
    
    private String resultat;
    private Map<String, String> erreurs = new HashMap<String, String>();
    private SiteDao siteDao;

    public SitesForm( SiteDao siteDao ) {
        this.siteDao = siteDao;
    }

    public String getResultat() {
        return resultat;
    }

    public Map<String, String> getErreurs() {
        return erreurs;
    }
    
    public Site ajouterSite( HttpServletRequest request ) {
    	String nom = getValeurChamp( request, CHAMP_NOM );
    	String commune = getValeurChamp( request, CHAMP_COMMUNE );
        String codePostal = getValeurChamp( request, CHAMP_CODEPOSTAL );
        String altitude = getValeurChamp( request, CHAMP_ALTITUDE );
        String orientation = getValeurChamp( request, CHAMP_ORIENTATION );
        String rocher = getValeurChamp( request, CHAMP_ROCHER );
        String acces = getValeurChamp( request, CHAMP_ACCES );
        String description = getValeurChamp( request, CHAMP_DESCRIPTION );
        
        Site site = new Site();

        try {
        	traiterNom( nom, site );
        	traiterCommune( commune, site );
        	traiterCodePostal( codePostal, site );
        	traiterAltitude( altitude, site );
        	site.setOrientation( orientation );
        	site.setRocher( rocher );
        	site.setAcces( acces );
        	site.setDescription( description );
                  
	        if ( erreurs.isEmpty() ) {
	        	siteDao.ajouter( site );
	            resultat = "Site ajouté.";
	        } 
	        else resultat = "Échec de l'ajout.";
        } catch ( DaoException e ) {
            resultat = "Échec : une erreur imprévue est survenue.";
            e.printStackTrace();
        }
        return site;
    }
    
    private void traiterNom( String nom, Site site ) {
        try {
            validationNom( nom );
        } catch ( FormValidationException e ) {
            setErreur( CHAMP_NOM, e.getMessage() );
        }
        site.setNom( nom );
    }
    
    private void traiterCommune( String commune, Site site ) {
        try {
            validationCommune( commune );
        } catch ( FormValidationException e ) {
            setErreur( CHAMP_COMMUNE, e.getMessage() );
        }
        site.setCommune( commune );
    }
    
    private void traiterCodePostal( String codePostal, Site site ) {
        try {
            validationCodePostal( codePostal );
        } catch ( FormValidationException e ) {
            setErreur( CHAMP_CODEPOSTAL, e.getMessage() );
        }
        site.setCodePostal( codePostal );
    }
    
    private void traiterAltitude( String altitude, Site site ) {
        try {
            validationAltitude( altitude );
        } catch ( FormValidationException e ) {
            setErreur( CHAMP_ALTITUDE, e.getMessage() );
        }
        site.setAltitude( Integer.parseInt( altitude ) );
    }
    
    private void validationNom( String nom ) throws FormValidationException {
        if ( nom == null ) {
        	throw new FormValidationException( "Merci de saisir le nom du site." );
        }
    }
    
    private void validationCommune( String commune ) throws FormValidationException {
        if ( commune == null ) {
        	throw new FormValidationException( "Merci de saisir la commune." );
        }
    }
    
    private void validationCodePostal( String codePostal ) throws FormValidationException {
        if ( codePostal != null && codePostal.length() != 5 ) {
            throw new FormValidationException( "Le code postal doit contenir 5 chiffres." );
        }
        else if ( codePostal == null ) {
        	throw new FormValidationException( "Merci de saisir le code postal du site." );
        }
    }
    
    private void validationAltitude( String altitude ) throws FormValidationException {
        if ( altitude != null && altitude.length() > 4 ) {
            throw new FormValidationException( "L'altitude est limitée à 4 chiffres." );
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
