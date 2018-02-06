package com.ocp3.forms;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.ocp3.beans.*;
import com.ocp3.dao.*;


public class ReservationsForm {
	private static final String CHAMP_TOPO = "inputTopo";
    private static final String CHAMP_MAIL = "inputMail";
	private static final String CHAMP_DATEDEB = "inputDateDeb";
	private static final String CHAMP_DATEFIN = "inputDateFin";
    
    private String resultat;
    private Map<String, String> erreurs = new HashMap<String, String>();
    private ReservationDao reservationDao;
    private UtilisateurDao utilisateurDao;
    private TopoDao topoDao;

    public ReservationsForm( ReservationDao reservationDao, UtilisateurDao utilisateurDao, TopoDao topoDao ) {
        this.reservationDao = reservationDao;
        this.utilisateurDao = utilisateurDao;
        this.topoDao = topoDao;
    }

    public String getResultat() {
        return resultat;
    }

    public Map<String, String> getErreurs() {
        return erreurs;
    }
    
    public Reservation ajouterReservation( HttpServletRequest request ) {
    	String titreTopo = getValeurChamp( request, CHAMP_TOPO );
    	String mailUser = getValeurChamp( request, CHAMP_MAIL );
        String dateDeb = getValeurChamp( request, CHAMP_DATEDEB );
        String dateFin = getValeurChamp( request, CHAMP_DATEFIN );
        
        Reservation reservation = new Reservation();

        try {
        	traiterTopo( titreTopo, reservation, request );
        	traiterMail( mailUser, reservation );
        	traiterDateDeb( dateDeb, reservation );
        	traiterDateFin( dateDeb, dateFin, reservation );

	        if ( erreurs.isEmpty() ) {
	        	reservationDao.ajouter( reservation );
	            resultat = "Réservation ajoutée.";
	        } 
	        else resultat = "Échec de l'ajout.";
        } catch ( DaoException e ) {
            resultat = "Échec : une erreur imprévue est survenue.";
            e.printStackTrace();
        }
        return reservation;
    }
    
    
    private void traiterTopo( String titreTopo, Reservation reservation, HttpServletRequest request ) {
        try {
            validationTitre( titreTopo );
            HttpSession session = request.getSession();	
    		Utilisateur utilisateur = (Utilisateur) session.getAttribute("sessionUtilisateur");
            Topo topo = topoDao.trouver( utilisateurDao, utilisateur.getId(), titreTopo );
            reservation.setIdTopo( topo.getId() );
            reservation.setTopo( topo );
        } catch ( FormValidationException e ) {
            setErreur( CHAMP_TOPO, e.getMessage() );
        }       
    }
    
    private void traiterMail( String mailUser, Reservation reservation ) {
        try {
            validationMail( mailUser );
            Utilisateur utilisateur = utilisateurDao.trouver( mailUser );
            reservation.setIdUser( utilisateur.getId() );
            reservation.setUtilisateur( utilisateur );
        } catch ( FormValidationException e ) {
            setErreur( CHAMP_MAIL, e.getMessage() );
        }    
    }
    
    private void traiterDateDeb( String dateDeb, Reservation reservation ) {    	
    	try {
            validationDateDeb( dateDeb );       
            Date dateD = Date.valueOf(dateDeb);
            reservation.setDateDeb( dateD );
        } catch ( FormValidationException e ) {
            setErreur( CHAMP_DATEDEB, e.getMessage() );
        }
        
    }
    
    private void traiterDateFin( String dateDeb, String dateFin, Reservation reservation ) {
    	try {
            validationDateFin( dateDeb, dateFin );
            Date dateF = Date.valueOf(dateFin);       
            reservation.setDateFin( dateF);
        } catch ( FormValidationException e ) {
            setErreur( CHAMP_DATEFIN, e.getMessage() );
        }
    }
    
    
    private void validationTitre( String titreTopo ) throws FormValidationException {
        if ( titreTopo == null ) {
        	throw new FormValidationException( "Merci de sélectionner le topo." );
        }
    }
    
    private void validationMail( String mailUser ) throws FormValidationException {
    	if ( mailUser != null ) {
            if ( !mailUser.matches( "([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)" ) ) {
                throw new FormValidationException( "Merci de saisir une adresse mail valide." );
            } else if ( utilisateurDao.existe( mailUser ) == false ) {
                throw new FormValidationException( "Cette adresse email n'appartient à aucun utilisateur" );
            }
        } else {
            throw new FormValidationException( "Merci de saisir l'adresse mail." );
        }    
    }
    
    private void validationDateDeb( String dateDeb ) throws FormValidationException {    
    	if ( dateDeb == null ) {
        	throw new FormValidationException( "Merci d'entrer une date." );
        } else {
            LocalDate dateDebut = LocalDate.parse( dateDeb );
            if ( dateDebut.compareTo( LocalDate.now() ) < 0 ) {
            	throw new FormValidationException( "Date antérieur à la date du jour." );     	
            }
        }       	
    }
    
    private void validationDateFin( String dateDeb, String dateFin ) throws FormValidationException {
    	if ( dateFin == null ) {
        	throw new FormValidationException( "Merci d'entrer une date." );       	 
        } else {
            LocalDate dateDebut = LocalDate.parse( dateDeb );
            LocalDate dateF = LocalDate.parse( dateFin );
            if ( dateF.compareTo( dateDebut ) < 0 ) {
            	throw new FormValidationException( "Date de fin antérieur à la date de début." ); 
            }    
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
