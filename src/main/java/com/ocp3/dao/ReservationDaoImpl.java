package com.ocp3.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.ocp3.dao.DaoUtilitaire.fermeturesSilencieuses;
import static com.ocp3.dao.DaoUtilitaire.initialisationRequetePreparee;

import com.ocp3.beans.Reservation;


public class ReservationDaoImpl implements ReservationDao {
	private static final String SQL_INSERT = "INSERT INTO reservation (datedeb, datefin, iduser, idtopo) VALUES (?, ?, ?, ?)";
	private static final String SQL_DELETE_PAR_ID = "DELETE FROM reservation WHERE datedeb = ? AND iduser = ? AND idtopo = ?";
    private static final String SQL_SELECT_PAR_PRETEUR = "SELECT * FROM reservation WHERE idtopo IN ( SELECT idtopo from topo WHERE iduser = ?) ORDER BY datedeb DESC";
    private static final String SQL_SELECT_PAR_BENEFICIAIRE = "SELECT * FROM reservation WHERE iduser = ? ORDER BY datedeb DESC";
    private static final String SQL_SELECT_PAR_IDTOPO = "SELECT * FROM reservation WHERE idtopo = ? AND datefin >= DATE(NOW()) ORDER BY datedeb DESC";

    private DaoFactory daoFactory;

    ReservationDaoImpl(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
    
    /* Implémentation de la méthode définie dans l'interface ReservationDao */
    @Override
    public void ajouter( Reservation reservation ) throws DaoException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;

        try {
        	/* Récupération d'une connexion depuis la Factory */
            connexion = daoFactory.getConnection();
            /* Préparation de la requête avec les objets passés en arguments et exécution */
            preparedStatement = initialisationRequetePreparee( connexion, SQL_INSERT, false, reservation.getDateDeb(), reservation.getDateFin(), reservation.getIdUser(), reservation.getIdTopo() );
            int statut = preparedStatement.executeUpdate();
            /* Si échec */
            if ( statut == 0 ) {
                throw new DaoException( "Échec de la création de la reservation, aucune ligne ajoutée dans la table." );
            }   
        } catch ( SQLException e ) {
            throw new DaoException( e );
        } finally {
            fermeturesSilencieuses( preparedStatement, connexion );
        }
    }
    
    /* Implémentation de la méthode définie dans l'interface ReservationDao */
    @Override
    public void supprimer( Date dateDeb, Long idUser, Long idTopo ) throws DaoException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;

        try {
        	/* Récupération d'une connexion depuis la Factory */
            connexion = daoFactory.getConnection();
            /* Préparation de la requête avec les objets passés en arguments et exécution */
            preparedStatement = initialisationRequetePreparee( connexion, SQL_DELETE_PAR_ID, false, dateDeb, idUser, idTopo );
            int statut = preparedStatement.executeUpdate();
            /* Si échec */
            if ( statut == 0 ) {
                throw new DaoException( "Échec de la suppression de la reservation, aucune ligne ajoutée dans la table." );
            }   
        } catch ( SQLException e ) {
            throw new DaoException( e );
        } finally {
            fermeturesSilencieuses( preparedStatement, connexion );
        }
    }
    
    /* Implémentation de la méthode définie dans l'interface ReservationDao */
    @Override
    public List<Reservation> listerPrets( UtilisateurDao utilisateurDao, TopoDao topoDao, Long idUserPreteur ) throws DaoException {
    	return lister( utilisateurDao, topoDao, SQL_SELECT_PAR_PRETEUR, idUserPreteur ); 	
    }
    
    /* Implémentation de la méthode définie dans l'interface ReservationDao */
    @Override
    public List<Reservation> listerResasPourUser( UtilisateurDao utilisateurDao, TopoDao topoDao, Long idUser ) throws DaoException {
    	return lister( utilisateurDao, topoDao, SQL_SELECT_PAR_BENEFICIAIRE, idUser ); 	
    }
    
    /* Implémentation de la méthode définie dans l'interface ReservationDao */
    @Override
    public List<Reservation> listerResasPourTopo( UtilisateurDao utilisateurDao, TopoDao topoDao, Long idTopo ) throws DaoException {
    	return lister( utilisateurDao, topoDao, SQL_SELECT_PAR_IDTOPO, idTopo ); 	
    }
    
    
    /*
     * Méthode générique utilisée pour lister les réservations depuis la base
     * de données, correspondant à la requête SQL donnée prenant en paramètres
     * les objets passés en argument.
     */
    private List<Reservation> lister( UtilisateurDao utilisateurDao, TopoDao topoDao, String sql, Object... objets ) throws DaoException {
    	Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Reservation> reservations = new ArrayList<Reservation>();

        try {
            /* Récupération d'une connexion depuis la Factory */
            connexion = daoFactory.getConnection();
            /* Préparation de la requête avec les objets passés en arguments et exécution */
            preparedStatement = initialisationRequetePreparee( connexion, sql, false, objets );
            resultSet = preparedStatement.executeQuery();
            /* Parcours des lignes de données retournée dans le ResultSet */      
        	while ( resultSet.next() ) {
        		Reservation reservation =  map( resultSet );
        		reservation.setUtilisateur( utilisateurDao.trouver( reservation.getIdUser() ) );
        		reservation.setTopo( topoDao.trouver( utilisateurDao, reservation.getIdTopo() ) );
        		reservations.add( reservation );
        	}       
        } catch ( SQLException e ) {
            throw new DaoException( e );
        } finally {
            fermeturesSilencieuses( resultSet, preparedStatement, connexion );
        }
        return reservations;
    }
    
    /*
     * Simple méthode utilitaire permettant de faire la correspondance (le
     * mapping) entre une ligne issue de la table des réservations (un
     * ResultSet) et un bean Reservation.
     */
    private static Reservation map( ResultSet resultSet ) throws SQLException {
    	Reservation reservation = new Reservation();
    	reservation.setDateDeb( resultSet.getDate( "datedeb" ) );
    	reservation.setDateFin( resultSet.getDate( "datefin" ) );
    	reservation.setIdUser( resultSet.getLong( "iduser" ) );
    	reservation.setIdTopo( resultSet.getLong( "idtopo" ) );
           
        return reservation;
    }

}
