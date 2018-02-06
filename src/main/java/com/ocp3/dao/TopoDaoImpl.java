package com.ocp3.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.ocp3.dao.DaoUtilitaire.fermeturesSilencieuses;
import static com.ocp3.dao.DaoUtilitaire.initialisationRequetePreparee;

import com.ocp3.beans.Topo;


public class TopoDaoImpl implements TopoDao {
    private static final String SQL_INSERT = "INSERT INTO topo (titre, auteur, anneeedition, departement, descriptiontopo, iduser) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SQL_SELECT_PAR_ID = "SELECT * FROM topo WHERE idtopo = ?";
    private static final String SQL_SELECT_PAR_TITRE_ET_USER = "SELECT * FROM topo WHERE iduser = ? AND titre = ?";  
    private static final String SQL_SELECT_PAR_USER = "SELECT * FROM topo WHERE iduser = ? ORDER BY departement, anneeedition DESC";
    private static final String SQL_SELECT = "SELECT * FROM topo ORDER BY departement, anneeedition DESC";

    private DaoFactory daoFactory;

    TopoDaoImpl(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
    
    /* Implémentation de la méthode définie dans l'interface TopoDao */
    @Override
    public void ajouter( Topo topo ) throws DaoException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet valeursAutoGenerees = null;

        try {
        	/* Récupération d'une connexion depuis la Factory */
            connexion = daoFactory.getConnection();
            /*
             * Préparation de la requête avec les objets passés en arguments et exécution.
             */
            preparedStatement = initialisationRequetePreparee( connexion, SQL_INSERT, true, topo.getTitre(), topo.getAuteur(), topo.getAnneeEdition(), topo.getDepartement(), topo.getDescription(), topo.getIdUser() );
            int statut = preparedStatement.executeUpdate();
            /* Traitement selon les cas */
            if ( statut == 0 ) {
                throw new DaoException( "Échec de la création du topo, aucune ligne ajoutée dans la table." );
            }
            valeursAutoGenerees = preparedStatement.getGeneratedKeys();
            if ( valeursAutoGenerees.next() ) {
                topo.setId( valeursAutoGenerees.getLong( 1 ) );
            } else {
                throw new DaoException( "Échec de la création du topo en base, aucun ID auto-généré retourné." );
            }
        } catch ( SQLException e ) {
            throw new DaoException( e );
        } finally {
            fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
        }
    }
    
    /* Implémentation de la méthode définie dans l'interface TopoDao */
    @Override
    public Topo trouver( UtilisateurDao utilisateurDao, Long idTopo ) throws DaoException {
    	return trouver( utilisateurDao, SQL_SELECT_PAR_ID, idTopo ); 	
    }
    
    /* Implémentation de la méthode définie dans l'interface TopoDao */
    @Override
    public Topo trouver( UtilisateurDao utilisateurDao, Long idUser, String titreTopo ) throws DaoException {
    	return trouver( utilisateurDao, SQL_SELECT_PAR_TITRE_ET_USER, idUser, titreTopo ); 	
    }
    
    /* Implémentation de la méthode définie dans l'interface TopoDao */
    @Override
    public List<Topo> lister( UtilisateurDao utilisateurDao, Long idUser ) throws DaoException {
    	return lister( utilisateurDao, SQL_SELECT_PAR_USER, idUser ); 	
    }
    
    /* Implémentation de la méthode définie dans l'interface TopoDao */
    @Override
    public List<Topo> lister( UtilisateurDao utilisateurDao ) throws DaoException {
    	return lister( utilisateurDao, SQL_SELECT ); 	
    }
    
    
    /*
     * Méthode générique utilisée pour trouver un topo depuis la base
     * de données, correspondant à la requête SQL donnée prenant en paramètres
     * les objets passés en argument.
     */
    private Topo trouver( UtilisateurDao utilisateurDao, String sql, Object... objets ) throws DaoException {
    	Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Topo topo = null;

        try {
            /* Récupération d'une connexion depuis la Factory */
            connexion = daoFactory.getConnection();
            /*
             * Préparation de la requête avec les objets passés en arguments
             * (ici, uniquement l'id du topo) et exécution.
             */
            preparedStatement = initialisationRequetePreparee( connexion, sql, false, objets );
            resultSet = preparedStatement.executeQuery();
            /* Parcours de la ligne de données retournée dans le ResultSet */
            if ( resultSet.next() ) {
                topo = map( resultSet );
                topo.setUtilisateur( utilisateurDao.trouver( topo.getIdUser() ) );
            } else {
                throw new DaoException( "Topo inexistant dans la base de donnée" );
            }
        } catch ( SQLException e ) {
            throw new DaoException( e );    
        } finally {
            fermeturesSilencieuses( resultSet, preparedStatement, connexion );
        }
    	return topo;	
    }
    
    /*
     * Méthode générique utilisée pour lister les topos depuis la base
     * de données, correspondant à la requête SQL donnée prenant en paramètres
     * les objets passés en argument.
     */
    private List<Topo> lister( UtilisateurDao utilisateurDao, String sql, Object... objets ) throws DaoException {
    	Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Topo> topos = new ArrayList<Topo>();

        try {
            /* Récupération d'une connexion depuis la Factory */
            connexion = daoFactory.getConnection();
            /* Préparation de la requête avec les objets passés en arguments et exécution */
            preparedStatement = initialisationRequetePreparee( connexion, sql, false, objets );
            resultSet = preparedStatement.executeQuery();
            /* Parcours des lignes de données retournée dans le ResultSet */      
        	while ( resultSet.next() ) {
        		Topo topo =  map( resultSet );
        		topo.setUtilisateur( utilisateurDao.trouver( topo.getIdUser() ) );
        		topos.add( topo );
        	} 
        } catch ( SQLException e ) {
            throw new DaoException( e );
        } finally {
            fermeturesSilencieuses( resultSet, preparedStatement, connexion );
        }
        return topos;
    }
    
    /*
     * Simple méthode utilitaire permettant de faire la correspondance (le
     * mapping) entre une ligne issue de la table des topos (un
     * ResultSet) et un bean Topo.
     */
    private static Topo map( ResultSet resultSet ) throws SQLException {
        Topo topo = new Topo();
        topo.setId( resultSet.getLong( "idtopo" ) );
        topo.setTitre( resultSet.getString( "titre" ) );
        topo.setAuteur( resultSet.getString( "auteur" ) );
        topo.setAnneeEdition( resultSet.getString( "anneeedition" ) );
        topo.setDepartement( resultSet.getString( "departement" ) );
        topo.setDescription( resultSet.getString( "descriptiontopo" ) );
        topo.setIdUser( resultSet.getLong( "iduser" ) );
             
        return topo;
    }

}
