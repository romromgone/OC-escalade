package com.ocp3.dao;

import static com.ocp3.dao.DaoUtilitaire.fermeturesSilencieuses;
import static com.ocp3.dao.DaoUtilitaire.initialisationRequetePreparee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.ocp3.beans.Couvre;


public class CouvreDaoImpl implements CouvreDao {
	private static final String SQL_INSERT = "INSERT INTO couvre (idtopo, idsite) VALUES (?, ?)";

    private DaoFactory daoFactory;

    CouvreDaoImpl(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
    
    /* Implémentation de la méthode définie dans l'interface TopoDao */
    @Override
    public void ajouter( Couvre couvre ) throws DaoException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;

        try {
        	/* Récupération d'une connexion depuis la Factory */
            connexion = daoFactory.getConnection();
            /* Préparation de la requête avec les objets passés en arguments et exécution */
            preparedStatement = initialisationRequetePreparee( connexion, SQL_INSERT, false, couvre.getIdTopo(), couvre.getIdSite() );
            int statut = preparedStatement.executeUpdate();
            /* Traitement si échec */
            if ( statut == 0 ) {
                throw new DaoException( "Échec de la création, aucune ligne ajoutée dans la table." );
            }
        } catch ( SQLException e ) {
            throw new DaoException( e );
        } finally {
            fermeturesSilencieuses( preparedStatement, connexion );
        }
    }

}
