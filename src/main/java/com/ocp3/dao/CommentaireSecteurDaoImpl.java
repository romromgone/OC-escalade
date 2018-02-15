package com.ocp3.dao;

import static com.ocp3.dao.DaoUtilitaire.fermeturesSilencieuses;
import static com.ocp3.dao.DaoUtilitaire.initialisationRequetePreparee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ocp3.beans.CommentaireSecteur;


public class CommentaireSecteurDaoImpl implements CommentaireSecteurDao {
	private static final String SQL_INSERT = "INSERT INTO commentairesecteur (datecse, textecse, iduser, nosecteur, idsite) VALUES (?, ?, ?, ?, ?)";
	private static final String SQL_SELECT_PAR_SECTEUR = "SELECT cast (datecse as timestamp(0)), textecse, iduser, nosecteur, idsite FROM commentairesecteur WHERE nosecteur = ? AND idsite ) ? ORDER BY datecse DESC";

    private DaoFactory daoFactory;

    CommentaireSecteurDaoImpl(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
    
    /* Implémentation de la méthode définie dans l'interface CommentaireSecteurDao */
    @Override
    public void ajouter( CommentaireSecteur commentaireSecteur ) throws DaoException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;

        try {
        	/* Récupération d'une connexion depuis la Factory */
            connexion = daoFactory.getConnection();
            /* Préparation de la requête avec les objets passés en arguments et exécution */
            preparedStatement = initialisationRequetePreparee( connexion, SQL_INSERT, false, commentaireSecteur.getDateCSe(), commentaireSecteur.getTexteCSe(), commentaireSecteur.getIdUser(), commentaireSecteur.getNoSecteur(), commentaireSecteur.getIdSite() );
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
    
    /* Implémentation de la méthode définie dans l'interface CommentaireSecteurDao */
    @Override
    public List<CommentaireSecteur> lister( UtilisateurDao utilisateurDao, SiteDao siteDao, Long idSecteur ) throws DaoException {
    	return lister( utilisateurDao, siteDao, SQL_SELECT_PAR_SECTEUR, idSecteur ); 	
    }
    
    
    /*
     * Méthode générique utilisée pour lister les commentaires depuis la base
     * de données, correspondant à la requête SQL donnée prenant en paramètres
     * les objets passés en argument.
     */
    private List<CommentaireSecteur> lister( UtilisateurDao utilisateurDao, SiteDao siteDao, String sql, Object... objets ) throws DaoException {
    	Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<CommentaireSecteur> commentairesSecteur = new ArrayList<CommentaireSecteur>();

        try {
            /* Récupération d'une connexion depuis la Factory */
            connexion = daoFactory.getConnection();
            /* Préparation de la requête avec les objets passés en arguments et exécution */
            preparedStatement = initialisationRequetePreparee( connexion, sql, false, objets );
            resultSet = preparedStatement.executeQuery();
            /* Parcours des lignes de données retournée dans le ResultSet */      
        	while ( resultSet.next() ) {
        		CommentaireSecteur commentaireSecteur =  map( resultSet );
        		commentaireSecteur.setUtilisateur( utilisateurDao.trouver( commentaireSecteur.getIdUser() ) );
        		commentaireSecteur.setSite( siteDao.trouver( commentaireSecteur.getIdSite() ) );
        		commentairesSecteur.add( commentaireSecteur );
        	}       
        } catch ( SQLException e ) {
            throw new DaoException( e );
        } finally {
            fermeturesSilencieuses( resultSet, preparedStatement, connexion );
        }
        return commentairesSecteur;
    }
    
    /*
     * Simple méthode utilitaire permettant de faire la correspondance (le
     * mapping) entre une ligne issue de la table des commentaires (un
     * ResultSet) et un bean CommmentaireSecteur.
     */
    private static CommentaireSecteur map( ResultSet resultSet ) throws SQLException {
    	CommentaireSecteur commentaireSecteur = new CommentaireSecteur();
    	commentaireSecteur.setDateCSe( resultSet.getTimestamp( "datecse" ) );
    	commentaireSecteur.setTexteCSe( resultSet.getString( "textecse" ) );
    	commentaireSecteur.setIdUser( resultSet.getLong( "iduser" ) );
    	commentaireSecteur.setNoSecteur( resultSet.getInt( "nosecteur" ) );
    	commentaireSecteur.setIdSite( resultSet.getLong( "idsite" ) );
           
        return commentaireSecteur;
    }

}
