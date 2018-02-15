package com.ocp3.dao;

import static com.ocp3.dao.DaoUtilitaire.fermeturesSilencieuses;
import static com.ocp3.dao.DaoUtilitaire.initialisationRequetePreparee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ocp3.beans.Secteur;


public class SecteurDaoImpl implements SecteurDao {
	private static final String SQL_INSERT = "INSERT INTO secteur (nomsecteur, descriptionsecteur, idsite) VALUES (?, ?, ?)";
    private static final String SQL_SELECT_PAR_ID = "SELECT * FROM secteur WHERE nosecteur= ? AND idsite = ?";
    private static final String SQL_SELECT = "SELECT * FROM secteur ORDER BY nosecteur";

    private DaoFactory daoFactory;

    SecteurDaoImpl(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
    
    /* Implémentation de la méthode définie dans l'interface SecteurDao */
    @Override
    public void ajouter( Secteur secteur ) throws DaoException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet valeursAutoGenerees = null;

        try {
        	/* Récupération d'une connexion depuis la Factory */
            connexion = daoFactory.getConnection();
            /* Préparation de la requête avec les objets passés en arguments et exécution */
            preparedStatement = initialisationRequetePreparee( connexion, SQL_INSERT, true, secteur.getNom(), secteur.getDescription(), secteur.getIdSite() );
            int statut = preparedStatement.executeUpdate();
            /* Traitement selon les cas */
            if ( statut == 0 ) {
                throw new DaoException( "Échec de la création du secteur, aucune ligne ajoutée dans la table." );
            }
            valeursAutoGenerees = preparedStatement.getGeneratedKeys();
            if ( valeursAutoGenerees.next() ) {
                secteur.setNoSecteur( valeursAutoGenerees.getInt( 1 ) );
            } else {
                throw new DaoException( "Échec de la création du secteur en base, aucun ID auto-généré retourné." );
            }
        } catch ( SQLException e ) {
            throw new DaoException( e );
        } finally {
            fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
        }
    }
    
    /* Implémentation de la méthode définie dans l'interface SecteurDao */
    @Override
    public Secteur trouver( SiteDao siteDao, Integer noSecteur, Long idSite ) throws DaoException {
    	return trouver( siteDao, SQL_SELECT_PAR_ID, noSecteur, idSite ); 	
    }
    
    /* Implémentation de la méthode définie dans l'interface SecteurDao */
    @Override
    public List<Secteur> lister( SiteDao siteDao ) throws DaoException {
    	return lister( siteDao, SQL_SELECT ); 	
    }
    
    
    /*
     * Méthode générique utilisée pour trouver un secteur depuis la base
     * de données, correspondant à la requête SQL donnée prenant en paramètres
     * les objets passés en argument.
     */
    private Secteur trouver( SiteDao siteDao, String sql, Object... objets ) throws DaoException {
    	Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Secteur secteur = null;

        try {
            /* Récupération d'une connexion depuis la Factory */
            connexion = daoFactory.getConnection();
            /*
             * Préparation de la requête avec les objets passés en arguments
             * (ici, le numéro du secteur et l'id du site) et exécution.
             */
            preparedStatement = initialisationRequetePreparee( connexion, sql, false, objets );
            resultSet = preparedStatement.executeQuery();
            /* Parcours de la ligne de données retournée dans le ResultSet */
            if ( resultSet.next() ) {
                secteur = map( resultSet );
                secteur.setSite( siteDao.trouver( secteur.getIdSite() ) );
            } else {
                throw new DaoException( "Secteur inexistant dans la base de donnée" );
            }
        } catch ( SQLException e ) {
            throw new DaoException( e );    
        } finally {
            fermeturesSilencieuses( resultSet, preparedStatement, connexion );
        }
    	return secteur;	
    }
    
    /*
     * Méthode générique utilisée pour lister les secteurs depuis la base
     * de données, correspondant à la requête SQL donnée prenant en paramètres
     * les objets passés en argument.
     */
    private List<Secteur> lister( SiteDao siteDao, String sql, Object... objets ) throws DaoException {
    	Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Secteur> secteurs = new ArrayList<Secteur>();

        try {
            /* Récupération d'une connexion depuis la Factory */
            connexion = daoFactory.getConnection();
            /* Préparation de la requête avec les objets passés en arguments et exécution */
            preparedStatement = initialisationRequetePreparee( connexion, sql, false, objets );
            resultSet = preparedStatement.executeQuery();
            /* Parcours des lignes de données retournée dans le ResultSet */      
        	while ( resultSet.next() ) {
        		Secteur secteur =  map( resultSet );
        		secteur.setSite( siteDao.trouver( secteur.getIdSite() ) );
        		secteurs.add( secteur );
        	} 
        } catch ( SQLException e ) {
            throw new DaoException( e );
        } finally {
            fermeturesSilencieuses( resultSet, preparedStatement, connexion );
        }
        return secteurs;
    }
    
    /*
     * Simple méthode utilitaire permettant de faire la correspondance (le
     * mapping) entre une ligne issue de la table des secteurs (un
     * ResultSet) et un bean Secteur.
     */
    private static Secteur map( ResultSet resultSet ) throws SQLException {
        Secteur secteur = new Secteur();
        secteur.setNoSecteur( resultSet.getInt( "nosecteur" ) );
        secteur.setNom( resultSet.getString( "nomsecteur" ) );
        secteur.setDescription( resultSet.getString( "descriptionsecteur" ) );
        secteur.setIdSite( resultSet.getLong( "idsite" ) );
        return secteur;
    }

}
