package com.ocp3.dao;

import static com.ocp3.dao.DaoUtilitaire.fermeturesSilencieuses;
import static com.ocp3.dao.DaoUtilitaire.initialisationRequetePreparee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ocp3.beans.Site;


public class SiteDaoImpl implements SiteDao {
	private static final String SQL_INSERT = "INSERT INTO site (nomsite, commune, codepostalsite, altitude, orientation, rocher, acces, descriptionsite) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_SELECT_PAR_ID = "SELECT * FROM site WHERE idsite = ?";
    private static final String SQL_SELECT = "SELECT * FROM site ORDER BY codepostalsite, nomsite";

    private DaoFactory daoFactory;

    SiteDaoImpl(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
    
    /* Implémentation de la méthode définie dans l'interface TopoDao */
    @Override
    public void ajouter( Site site ) throws DaoException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet valeursAutoGenerees = null;

        try {
        	/* Récupération d'une connexion depuis la Factory */
            connexion = daoFactory.getConnection();
            /* Préparation de la requête avec les objets passés en arguments et exécution */
            preparedStatement = initialisationRequetePreparee( connexion, SQL_INSERT, true, site.getNom(), site.getCommune(), site.getCodePostal(), site.getAltitude(), site.getOrientation(), site.getRocher(), site.getAcces(), site.getDescription() );
            int statut = preparedStatement.executeUpdate();
            /* Traitement selon les cas */
            if ( statut == 0 ) {
                throw new DaoException( "Échec de la création du site, aucune ligne ajoutée dans la table." );
            }
            valeursAutoGenerees = preparedStatement.getGeneratedKeys();
            if ( valeursAutoGenerees.next() ) {
                site.setId( valeursAutoGenerees.getLong( 1 ) );
            } else {
                throw new DaoException( "Échec de la création du site en base, aucun ID auto-généré retourné." );
            }
        } catch ( SQLException e ) {
            throw new DaoException( e );
        } finally {
            fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
        }
    }
    
    /* Implémentation de la méthode définie dans l'interface TopoDao */
    @Override
    public Site trouver( Long idSite ) throws DaoException {
    	return trouver( SQL_SELECT_PAR_ID, idSite ); 	
    }
    
    /* Implémentation de la méthode définie dans l'interface TopoDao */
    @Override
    public List<Site> lister() throws DaoException {
    	return lister( SQL_SELECT ); 	
    }
    
    
    /*
     * Méthode générique utilisée pour trouver un site depuis la base
     * de données, correspondant à la requête SQL donnée prenant en paramètres
     * les objets passés en argument.
     */
    private Site trouver( String sql, Object... objets ) throws DaoException {
    	Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Site site = null;

        try {
            /* Récupération d'une connexion depuis la Factory */
            connexion = daoFactory.getConnection();
            /*
             * Préparation de la requête avec les objets passés en arguments
             * (ici, uniquement l'id du site) et exécution.
             */
            preparedStatement = initialisationRequetePreparee( connexion, sql, false, objets );
            resultSet = preparedStatement.executeQuery();
            /* Parcours de la ligne de données retournée dans le ResultSet */
            if ( resultSet.next() ) {
                site = map( resultSet );
            } else {
                throw new DaoException( "Topo inexistant dans la base de donnée" );
            }
        } catch ( SQLException e ) {
            throw new DaoException( e );    
        } finally {
            fermeturesSilencieuses( resultSet, preparedStatement, connexion );
        }
    	return site;	
    }
    
    /*
     * Méthode générique utilisée pour lister les sites depuis la base
     * de données, correspondant à la requête SQL donnée prenant en paramètres
     * les objets passés en argument.
     */
    private List<Site> lister( String sql, Object... objets ) throws DaoException {
    	Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Site> sites = new ArrayList<Site>();

        try {
            /* Récupération d'une connexion depuis la Factory */
            connexion = daoFactory.getConnection();
            /* Préparation de la requête avec les objets passés en arguments et exécution */
            preparedStatement = initialisationRequetePreparee( connexion, sql, false, objets );
            resultSet = preparedStatement.executeQuery();
            /* Parcours des lignes de données retournée dans le ResultSet */      
        	while ( resultSet.next() ) {
        		Site site =  map( resultSet );
        		sites.add( site );
        	} 
        } catch ( SQLException e ) {
            throw new DaoException( e );
        } finally {
            fermeturesSilencieuses( resultSet, preparedStatement, connexion );
        }
        return sites;
    }
    
    /*
     * Simple méthode utilitaire permettant de faire la correspondance (le
     * mapping) entre une ligne issue de la table des sites (un
     * ResultSet) et un bean Site.
     */
    private static Site map( ResultSet resultSet ) throws SQLException {
        Site site = new Site();
        site.setId( resultSet.getLong( "idsite" ) );
        site.setNom( resultSet.getString( "nomsite" ) );
        site.setCommune( resultSet.getString( "commune" ) );
        site.setCodePostal( resultSet.getString( "codepostalsite" ) );
        site.setAltitude( resultSet.getInt( "altitude" ) );
        site.setOrientation( resultSet.getString( "orientation" ) );
        site.setRocher( resultSet.getString( "rocher" ) );
        site.setAcces( resultSet.getString( "acces" ) );
        site.setDescription( resultSet.getString( "descriptionsite" ) );
             
        return site;
    }

}
