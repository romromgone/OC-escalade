package com.ocp3.dao;

import java.sql.*;
import java.util.List;

import static com.ocp3.dao.DaoUtilitaire.fermeturesSilencieuses;
import static com.ocp3.dao.DaoUtilitaire.initialisationRequetePreparee;

import com.ocp3.beans.Topo;


public class TopoDaoImpl implements TopoDao {
    private static final String SQL_INSERT = "INSERT INTO topo (titre, auteur, anneeedition, departement, descriptiontopo, iduser) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SQL_SELECT = "SELECT * FROM topo";
    private static final String SQL_SELECT_PAR_USER = "SELECT * FROM topo WHERE iduser = ?";
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
    public List<Topo> lister( Long idUser ) throws DaoException {
    	
    }
    
    /* Implémentation de la méthode définie dans l'interface TopoDao */
    @Override
    public List<Topo> lister() throws DaoException {
    	
    }

}
