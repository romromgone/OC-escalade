package com.ocp3.forms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.jasypt.util.password.ConfigurablePasswordEncryptor;

import com.ocp3.beans.Utilisateur;
import com.ocp3.dao.DaoException;
import com.ocp3.dao.UtilisateurDao;


public final class ConnexionForm {
    private static final String CHAMP_EMAIL = "email";
    private static final String CHAMP_PASS = "password";

    private String resultat;
    private Map<String, String> erreurs = new HashMap<String, String>();
    private UtilisateurDao utilisateurDao;
    
    public ConnexionForm( UtilisateurDao utilisateurDao ) {
        this.utilisateurDao = utilisateurDao;
    }

    public String getResultat() {
        return resultat;
    }

    public Map<String, String> getErreurs() {
        return erreurs;
    }

    public Utilisateur connecterUtilisateur( HttpServletRequest request ) {
        String email = getValeurChamp( request, CHAMP_EMAIL );
        String motDePasse = getValeurChamp( request, CHAMP_PASS );

        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setMail(email);
        try {        
        	traiterEmail( email, utilisateur );
            traiterMotsDePasse( motDePasse, utilisateur ); 

	        if ( erreurs.isEmpty() ) {
	        	utilisateur = utilisateurDao.trouver( email );
	        	
	        	ConfigurablePasswordEncryptor passwordEncryptor = new ConfigurablePasswordEncryptor();
	            passwordEncryptor.setAlgorithm( "SHA-256" );
	            passwordEncryptor.setPlainDigest( false );
	            
	            if ( passwordEncryptor.checkPassword( motDePasse, utilisateur.getMdp() ) ) {	            
	            	resultat = "Succès de la connexion.";
	            }
	            else {
	            	resultat = "Échec de la connexion : mot de passe invalide.";
	            	setErreur( "", "Mot de passe invalide." );
	            }
	        } 
	    } catch ( DaoException e ) {
	        resultat = "Échec de la connexion : utilisateur inexistant.";
	        setErreur( "", "Utilisateur inexistant." );
	        e.printStackTrace();
	    }
        return utilisateur;
    }
    
    
    private void traiterEmail( String email, Utilisateur utilisateur ) {
        try {
            validationEmail( email );
        } catch ( FormValidationException e ) {
            setErreur( CHAMP_EMAIL, e.getMessage() );
        }
    }
    
    private void traiterMotsDePasse( String motDePasse, Utilisateur utilisateur ) {
        try {
            validationMotDePasse( motDePasse );
        } catch ( FormValidationException e ) {
            setErreur( CHAMP_PASS, e.getMessage() );
        }    
    }

    private void validationEmail( String email ) throws FormValidationException {
    	 if ( email != null ) {
    		 if ( !email.matches( "([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)" ) ) {
                 throw new FormValidationException( "Merci de saisir une adresse mail valide." ); 
    		 }
         } else {
             throw new FormValidationException( "Merci de saisir une adresse mail." );
         }
    }

    private void validationMotDePasse( String motDePasse ) throws FormValidationException {
        if ( motDePasse != null ) {
            if ( motDePasse.length() < 5 ) {
                throw new FormValidationException( "Le mot de passe contient au moins 5 caractères." );
            }
        } else {
            throw new FormValidationException( "Merci de saisir votre mot de passe." );
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
            return valeur;
        }
    }
}