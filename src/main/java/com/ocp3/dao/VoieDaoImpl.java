package com.ocp3.dao;

import static com.ocp3.dao.DaoUtilitaire.fermeturesSilencieuses;
import static com.ocp3.dao.DaoUtilitaire.initialisationRequetePreparee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ocp3.beans.Voie;


public class VoieDaoImpl implements VoieDao {
	private static final String SQL_INSERT = "INSERT INTO voie (nomvoie, cotationvoie, nbpoints, hauteur, ouvreur, descriptionvoie, novoie, idsite) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_SELECT_PAR_SITE = "SELECT * FROM voie WHERE idsite = ? ORDER BY novoie";
    private static final String SQL_SELECT_PAR_SECTEUR = "SELECT * FROM voie WHERE nosecteur= ? AND idsite = ? ORDER BY novoie";

    private DaoFactory daoFactory;

    VoieDaoImpl(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
    
    /* Implémentation de la méthode définie dans l'interface VoieDao */
    @Override
    public void ajouter( Voie voie ) throws DaoException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet valeursAutoGenerees = null;

        try {
        	/* Récupération d'une connexion depuis la Factory */
            connexion = daoFactory.getConnection();
            /* Préparation de la requête avec les objets passés en arguments et exécution */
            preparedStatement = initialisationRequetePreparee( connexion, SQL_INSERT, true, voie.getNom(), voie.getCotation(), voie.getNbPoints(), voie.getHauteur(), voie.getOuvreur(), voie.getDescription(), voie.getNoVoie(), voie.getIdSite() );
            int statut = preparedStatement.executeUpdate();
            /* Traitement selon les cas */
            if ( statut == 0 ) {
                throw new DaoException( "Échec de la création de la voie, aucune ligne ajoutée dans la table." );
            }
            valeursAutoGenerees = preparedStatement.getGeneratedKeys();
            if ( valeursAutoGenerees.next() ) {
                voie.setNoVoie( valeursAutoGenerees.getInt( 1 ) );
            } else {
                throw new DaoException( "Échec de la création du voie en base, aucun ID auto-généré retourné." );
            }
        } catch ( SQLException e ) {
            throw new DaoException( e );
        } finally {
            fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
        }
    }
      
    /* Implémentation de la méthode définie dans l'interface VoieDao */
    @Override
    public List<Voie> listerParSite( SecteurDao secteurDao, SiteDao siteDao, Long idSite ) throws DaoException {
    	return lister( secteurDao, siteDao, SQL_SELECT_PAR_SITE, idSite ); 	
    }
    
    /* Implémentation de la méthode définie dans l'interface VoieDao */
    @Override
    public List<Voie> listerParSecteur( SecteurDao secteurDao, SiteDao siteDao, Integer noSecteur, Long idSite ) throws DaoException {
    	return lister( secteurDao, siteDao, SQL_SELECT_PAR_SECTEUR, noSecteur, idSite ); 	
    }
      
    
    /*
     * Méthode générique utilisée pour lister les voies depuis la base
     * de données, correspondant à la requête SQL donnée prenant en paramètres
     * les objets passés en argument.
     */
    private List<Voie> lister( SecteurDao secteurDao, SiteDao siteDao, String sql, Object... objets ) throws DaoException {
    	Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Voie> voies = new ArrayList<Voie>();

        try {
            /* Récupération d'une connexion depuis la Factory */
            connexion = daoFactory.getConnection();
            /* Préparation de la requête avec les objets passés en arguments et exécution */
            preparedStatement = initialisationRequetePreparee( connexion, sql, false, objets );
            resultSet = preparedStatement.executeQuery();
            /* Parcours des lignes de données retournée dans le ResultSet */      
        	while ( resultSet.next() ) {
        		Voie voie =  map( resultSet );
        		voie.setSecteur( secteurDao.trouver( siteDao, voie.getNoSecteur(), voie.getIdSite() ) );
        		voies.add( voie );
        	} 
        } catch ( SQLException e ) {
            throw new DaoException( e );
        } finally {
            fermeturesSilencieuses( resultSet, preparedStatement, connexion );
        }
        return voies;
    }
    
    /*
     * Simple méthode utilitaire permettant de faire la correspondance (le
     * mapping) entre une ligne issue de la table des voies (un
     * ResultSet) et un bean Voie.
     */
    private static Voie map( ResultSet resultSet ) throws SQLException {
        Voie voie = new Voie();
        voie.setNoVoie( resultSet.getInt( "novoie" ) );
        voie.setNom( resultSet.getString( "nomvoie" ) );
        voie.setCotation( resultSet.getString( "cotationvoie" ) );
        voie.setNbPoints( resultSet.getInt( "nbpoints" ) );
        voie.setHauteur( resultSet.getInt( "hauteur" ) );
        voie.setOuvreur( resultSet.getString( "ouvreur" ) );
        voie.setDescription( resultSet.getString( "descriptionvoie" ) );
        voie.setNoSecteur( resultSet.getInt( "nosecteur" ) );
        voie.setIdSite( resultSet.getLong( "idsite" ) );
        return voie;
    }

}
