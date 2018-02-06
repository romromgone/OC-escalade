package com.ocp3.dao;

import static com.ocp3.dao.DaoUtilitaire.fermeturesSilencieuses;
import static com.ocp3.dao.DaoUtilitaire.initialisationRequetePreparee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ocp3.beans.CommentaireTopo;

public class CommentaireTopoDaoImpl implements CommentaireTopoDao {
	private static final String SQL_INSERT = "INSERT INTO commentairetopo (datect, textect, iduser, idtopo) VALUES (?, ?, ?, ?)";
	private static final String SQL_SELECT_PAR_TOPO = "SELECT FROM commentairetopo WHERE idtopo = ? ORDER BY datect DESC";

    private DaoFactory daoFactory;

    CommentaireTopoDaoImpl(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
    
    /* Implémentation de la méthode définie dans l'interface TopoDao */
    @Override
    public void ajouter( CommentaireTopo commentaireTopo ) throws DaoException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;

        try {
        	/* Récupération d'une connexion depuis la Factory */
            connexion = daoFactory.getConnection();
            /*
             * Préparation de la requête avec les objets passés en arguments et exécution.
             */
            preparedStatement = initialisationRequetePreparee( connexion, SQL_INSERT, false, commentaireTopo.getDateCT(), commentaireTopo.getTexteCT(), commentaireTopo.getIdUser(), commentaireTopo.getIdTopo() );
            int statut = preparedStatement.executeUpdate();
            /* Traitement selon les cas */
            if ( statut == 0 ) {
                throw new DaoException( "Échec de la création du commentaire, aucune ligne ajoutée dans la table." );
            }   
        } catch ( SQLException e ) {
            throw new DaoException( e );
        } finally {
            fermeturesSilencieuses( preparedStatement, connexion );
        }
    }
    
    /* Implémentation de la méthode définie dans l'interface TopoDao */
    @Override
    public List<CommentaireTopo> lister( UtilisateurDao utilisateurDao, TopoDao topoDao, Long idTopo ) throws DaoException {
    	return lister( utilisateurDao, topoDao, SQL_SELECT_PAR_TOPO, idTopo ); 	
    }
    
    
    /*
     * Méthode générique utilisée pour lister les commentaires depuis la base
     * de données, correspondant à la requête SQL donnée prenant en paramètres
     * les objets passés en argument.
     */
    private List<CommentaireTopo> lister( UtilisateurDao utilisateurDao, TopoDao topoDao, String sql, Object... objets ) throws DaoException {
    	Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<CommentaireTopo> commentairesTopo = new ArrayList<CommentaireTopo>();

        try {
            /* Récupération d'une connexion depuis la Factory */
            connexion = daoFactory.getConnection();
            /* Préparation de la requête avec les objets passés en arguments et exécution */
            preparedStatement = initialisationRequetePreparee( connexion, sql, false, objets );
            resultSet = preparedStatement.executeQuery();
            /* Parcours des lignes de données retournée dans le ResultSet */      
        	while ( resultSet.next() ) {
        		CommentaireTopo commentaireTopo =  map( resultSet );
        		commentaireTopo.setUtilisateur( utilisateurDao.trouver( commentaireTopo.getIdUser() ) );
        		commentaireTopo.setTopo( topoDao.trouver( utilisateurDao, commentaireTopo.getIdTopo() ) );
        		commentairesTopo.add( commentaireTopo );
        	}       
        } catch ( SQLException e ) {
            throw new DaoException( e );
        } finally {
            fermeturesSilencieuses( resultSet, preparedStatement, connexion );
        }
        return commentairesTopo;
    }
    
    /*
     * Simple méthode utilitaire permettant de faire la correspondance (le
     * mapping) entre une ligne issue de la table des commentaires (un
     * ResultSet) et un bean CommmentaireTopo.
     */
    private static CommentaireTopo map( ResultSet resultSet ) throws SQLException {
    	CommentaireTopo commentaireTopo = new CommentaireTopo();
    	commentaireTopo.setDateCT( resultSet.getDate( "datect" ) );
    	commentaireTopo.setTexteCT( resultSet.getString( "textect" ) );
    	commentaireTopo.setIdUser( resultSet.getLong( "iduser" ) );
    	commentaireTopo.setIdTopo( resultSet.getLong( "idtopo" ) );
           
        return commentaireTopo;
    }

}
