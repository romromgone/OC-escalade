package com.ocp3.dao;

import static com.ocp3.dao.DaoUtilitaire.fermeturesSilencieuses;
import static com.ocp3.dao.DaoUtilitaire.initialisationRequetePreparee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ocp3.beans.CommentaireSite;


public class CommentaireSiteDaoImpl implements CommentaireSiteDao {
	private static final String SQL_INSERT = "INSERT INTO commentairesite (datecsi, textecsi, iduser, idsite) VALUES (?, ?, ?, ?)";
	private static final String SQL_SELECT_PAR_SITE = "SELECT cast (datecsi as timestamp(0)), textecsi, iduser, idsite FROM commentairesite WHERE idsite = ? ORDER BY datecsi DESC";

    private DaoFactory daoFactory;

    CommentaireSiteDaoImpl(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
    
    /* Implémentation de la méthode définie dans l'interface CommentaireSiteDao */
    @Override
    public void ajouter( CommentaireSite commentaireSite ) throws DaoException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;

        try {
        	/* Récupération d'une connexion depuis la Factory */
            connexion = daoFactory.getConnection();
            /* Préparation de la requête avec les objets passés en arguments et exécution */
            preparedStatement = initialisationRequetePreparee( connexion, SQL_INSERT, false, commentaireSite.getDateCSi(), commentaireSite.getTexteCSi(), commentaireSite.getIdUser(), commentaireSite.getIdSite() );
            int statut = preparedStatement.executeUpdate();
            /* Si échec */
            if ( statut == 0 ) {
                throw new DaoException( "Échec de la création du commentaire, aucune ligne ajoutée dans la table." );
            }   
        } catch ( SQLException e ) {
            throw new DaoException( e );
        } finally {
            fermeturesSilencieuses( preparedStatement, connexion );
        }
    }
    
    /* Implémentation de la méthode définie dans l'interface CommentaireSiteDao */
    @Override
    public List<CommentaireSite> lister( UtilisateurDao utilisateurDao, SiteDao siteDao, Long idSite ) throws DaoException {
    	return lister( utilisateurDao, siteDao, SQL_SELECT_PAR_SITE, idSite ); 	
    }
    
    
    /*
     * Méthode générique utilisée pour lister les commentaires depuis la base
     * de données, correspondant à la requête SQL donnée prenant en paramètres
     * les objets passés en argument.
     */
    private List<CommentaireSite> lister( UtilisateurDao utilisateurDao, SiteDao siteDao, String sql, Object... objets ) throws DaoException {
    	Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<CommentaireSite> commentairesSite = new ArrayList<CommentaireSite>();

        try {
            /* Récupération d'une connexion depuis la Factory */
            connexion = daoFactory.getConnection();
            /* Préparation de la requête avec les objets passés en arguments et exécution */
            preparedStatement = initialisationRequetePreparee( connexion, sql, false, objets );
            resultSet = preparedStatement.executeQuery();
            /* Parcours des lignes de données retournée dans le ResultSet */      
        	while ( resultSet.next() ) {
        		CommentaireSite commentaireSite =  map( resultSet );
        		commentaireSite.setUtilisateur( utilisateurDao.trouver( commentaireSite.getIdUser() ) );
        		commentaireSite.setSite( siteDao.trouver( commentaireSite.getIdSite() ) );
        		commentairesSite.add( commentaireSite );
        	}       
        } catch ( SQLException e ) {
            throw new DaoException( e );
        } finally {
            fermeturesSilencieuses( resultSet, preparedStatement, connexion );
        }
        return commentairesSite;
    }
    
    /*
     * Simple méthode utilitaire permettant de faire la correspondance (le
     * mapping) entre une ligne issue de la table des commentaires (un
     * ResultSet) et un bean CommmentaireSite.
     */
    private static CommentaireSite map( ResultSet resultSet ) throws SQLException {
    	CommentaireSite commentaireSite = new CommentaireSite();
    	commentaireSite.setDateCSi( resultSet.getTimestamp( "datecsi" ) );
    	commentaireSite.setTexteCSi( resultSet.getString( "textecsi" ) );
    	commentaireSite.setIdUser( resultSet.getLong( "iduser" ) );
    	commentaireSite.setIdSite( resultSet.getLong( "idsite" ) );
           
        return commentaireSite;
    }

}
