package com.ocp3.forms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.jasypt.util.password.ConfigurablePasswordEncryptor;

import com.ocp3.beans.Utilisateur;
import com.ocp3.dao.DaoException;
import com.ocp3.dao.UtilisateurDao;


public final class InscriptionForm { 
	private static final String CHAMP_NOM = "nom";
    private static final String CHAMP_PRENOM = "prenom";
	private static final String CHAMP_EMAIL = "mail";
	private static final String CHAMP_CODEPOSTAL = "cp";
    private static final String CHAMP_PASS = "mdp";
    private static final String CHAMP_CONF = "mdp2";
    
    private String resultat;
    private Map<String, String> erreurs = new HashMap<String, String>();
    private UtilisateurDao utilisateurDao;

    public InscriptionForm( UtilisateurDao utilisateurDao ) {
        this.utilisateurDao = utilisateurDao;
    }

    public String getResultat() {
        return resultat;
    }

    public Map<String, String> getErreurs() {
        return erreurs;
    }
    
    public Utilisateur inscrireUtilisateur( HttpServletRequest request ) {
    	String nom = getValeurChamp( request, CHAMP_NOM );
    	String prenom = getValeurChamp( request, CHAMP_PRENOM );
        String email = getValeurChamp( request, CHAMP_EMAIL );
        String codePostal = getValeurChamp( request, CHAMP_CODEPOSTAL );
        String motDePasse = getValeurChamp( request, CHAMP_PASS );
        String confirmation = getValeurChamp( request, CHAMP_CONF );
        
        Utilisateur utilisateur = new Utilisateur();

        try {
        	traiterNom( nom, utilisateur );
        	traiterPrenom( prenom, utilisateur );
        	traiterEmail( email, utilisateur );
        	traiterCodePostal ( codePostal, utilisateur );
            traiterMotsDePasse( motDePasse, confirmation, utilisateur );
           
	        if ( erreurs.isEmpty() ) {
	        	utilisateurDao.ajouter( utilisateur );
	            resultat = "Vous êtes inscrit, vous pouvez vous connecter.";
	        } 
	        else resultat = "Échec de l'inscription.";
        } catch ( DaoException e ) {
            resultat = "Échec de l'inscription : une erreur imprévue est survenue.";
            e.printStackTrace();
        }
        return utilisateur;
    }
    
    
    /*
     * Appel à la validation du nom reçu et initialisation de la propriété nom
     * du bean
     */
    private void traiterNom( String nom, Utilisateur utilisateur ) {
        try {
            validationNom( nom );
        } catch ( FormValidationException e ) {
            setErreur( CHAMP_NOM, e.getMessage() );
        }
        utilisateur.setNom( nom );
    }
    
    private void traiterPrenom( String prenom, Utilisateur utilisateur ) {
        try {
            validationPrenom( prenom );
        } catch ( FormValidationException e ) {
            setErreur( CHAMP_PRENOM, e.getMessage() );
        }
        utilisateur.setPrenom( prenom );
    }
    
    private void traiterEmail( String email, Utilisateur utilisateur ) {
        try {
            validationEmail( email );
        } catch ( FormValidationException e ) {
            setErreur( CHAMP_EMAIL, e.getMessage() );
        }
        utilisateur.setMail( email );
    }
    
    private void traiterCodePostal( String codePostal, Utilisateur utilisateur ) {
        try {
            validationCodePostal( codePostal );
        } catch ( FormValidationException e ) {
            setErreur( CHAMP_CODEPOSTAL, e.getMessage() );
        }
        utilisateur.setCodePostal( codePostal );
    }

    private void traiterMotsDePasse( String motDePasse, String confirmation, Utilisateur utilisateur ) {
        try {
            validationMotsDePasse( motDePasse, confirmation );
        } catch ( FormValidationException e ) {
            setErreur( CHAMP_PASS, e.getMessage() );
            setErreur( CHAMP_CONF, null );
        }

        /*
         * Utilisation de la bibliothèque Jasypt pour chiffrer le mot de passe
         * efficacement.
         * 
         * L'algorithme SHA-256 est ici utilisé, avec par défaut un salage
         * aléatoire et un grand nombre d'itérations de la fonction de hashage.
         * 
         * La String retournée est de longueur 56 et contient le hash en Base64.
         */
        ConfigurablePasswordEncryptor passwordEncryptor = new ConfigurablePasswordEncryptor();
        passwordEncryptor.setAlgorithm( "SHA-256" );
        passwordEncryptor.setPlainDigest( false );
        String motDePasseChiffre = passwordEncryptor.encryptPassword( motDePasse );

        utilisateur.setMdp( motDePasseChiffre );
    }

    
     private void validationNom( String nom ) throws FormValidationException {
        if ( nom != null && nom.length() < 2 ) {
            throw new FormValidationException( "Le nom doit contenir au moins 2 caractères." );
        }
        if ( nom == null ) {
        	throw new FormValidationException( "Merci de saisir le nom." );
        }
    }
    
    private void validationPrenom( String prenom ) throws FormValidationException {
        if ( prenom != null && prenom.length() < 2 ) {
            throw new FormValidationException( "Le prenom doit contenir au moins 2 caractères." );
        }
        else if ( prenom == null ) {
        	throw new FormValidationException( "Merci de saisir le prénom." );
        }
    }
    
    private void validationEmail( String email ) throws FormValidationException {
        if ( email != null ) {
            if ( !email.matches( "([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)" ) ) {
                throw new FormValidationException( "Merci de saisir une adresse mail valide." );
            } else if ( utilisateurDao.existe(email) ) {
                throw new FormValidationException( "Cette adresse email est déjà utilisée, merci d'en choisir une autre." );
            }
        } else {
            throw new FormValidationException( "Merci de saisir l'adresse mail." );
        }
    }
    
    private void validationCodePostal( String codePostal ) throws FormValidationException {
        if ( codePostal != null && codePostal.length() != 5 ) {
            throw new FormValidationException( "Le code postal doit contenir 5 chiffres." );
        }
        else if ( codePostal == null ) {
        	throw new FormValidationException( "Merci de saisir le code postal." );
        }
    }

    private void validationMotsDePasse( String motDePasse, String confirmation ) throws FormValidationException {
        if ( motDePasse != null && confirmation != null ) {
            if ( !motDePasse.equals( confirmation ) ) {
                throw new FormValidationException( "Mots de passe différents, merci de les saisir à nouveau." );
            } else if ( motDePasse.length() < 5 ) {
                throw new FormValidationException( "Les mots de passe doivent contenir au moins 5 caractères." );
            }
        } else {
            throw new FormValidationException( "Merci de saisir et confirmer votre mot de passe." );
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
