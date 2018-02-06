package com.ocp3.dao;

import java.sql.*;

import static com.ocp3.dao.DaoUtilitaire.fermeturesSilencieuses;
import static com.ocp3.dao.DaoUtilitaire.initialisationRequetePreparee;

import com.ocp3.beans.Utilisateur;


public class UtilisateurDaoImpl implements UtilisateurDao {
    private static final String SQL_INSERT = "INSERT INTO utilisateur (nomuser, prenomuser, mail, mdp, codepostaluser) VALUES (?, ?, ?, ?, ?)";
    private static final String SQL_SELECT_PAR_EMAIL = "SELECT iduser, nomuser, prenomuser, mail, mdp, codepostaluser FROM utilisateur WHERE mail = ?";
    private DaoFactory daoFactory;

    UtilisateurDaoImpl(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
    
    /* Implémentation de la méthode définie dans l'interface UtilisateurDao */
    @Override
    public void ajouter( Utilisateur utilisateur ) throws DaoException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet valeursAutoGenerees = null;

        try {
        	/* Récupération d'une connexion depuis la Factory */
            connexion = daoFactory.getConnection();
            /*
             * Préparation de la requête avec les objets passés en arguments et exécution.
             */
            preparedStatement = initialisationRequetePreparee( connexion, SQL_INSERT, true, utilisateur.getNom(), utilisateur.getPrenom(), utilisateur.getMail(), utilisateur.getMdp(), utilisateur.getCodePostal() );
            int statut = preparedStatement.executeUpdate();
            /* Traitement selon les cas */
            if ( statut == 0 ) {
                throw new DaoException( "Échec de la création de l'utilisateur, aucune ligne ajoutée dans la table." );
            }
            valeursAutoGenerees = preparedStatement.getGeneratedKeys();
            if ( valeursAutoGenerees.next() ) {
                utilisateur.setId( valeursAutoGenerees.getLong( 1 ) );
            } else {
                throw new DaoException( "Échec de la création de l'utilisateur en base, aucun ID auto-généré retourné." );
            }
        } catch ( SQLException e ) {
            throw new DaoException( e );
        } finally {
            fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
        }
    }
    
    /* Implémentation de la méthode définie dans l'interface UtilisateurDao */
    @Override
    public Utilisateur trouver( String email ) throws DaoException {
        return trouver( SQL_SELECT_PAR_EMAIL, email );
    }
    
    /* Implémentation de la méthode définie dans l'interface UtilisateurDao */
    @Override
    public Boolean existe( String email ) throws DaoException {
    	Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Boolean result = false;

        try {
            /* Récupération d'une connexion depuis la Factory */
            connexion = daoFactory.getConnection();
            /*
             * Préparation de la requête avec les objets passés en arguments
             * (ici, uniquement une adresse email) et exécution.
             */
            preparedStatement = initialisationRequetePreparee( connexion, SQL_SELECT_PAR_EMAIL, false, email );
            resultSet = preparedStatement.executeQuery();
            /* Traitement si une ligne est trouvée, sinon result reste à false */
            if ( resultSet.next() ) {
                result = true;
            } 
        } catch ( SQLException e ) {
            throw new DaoException( e );
        } finally {
            fermeturesSilencieuses( resultSet, preparedStatement, connexion );
        }
        return result;
    }

    
    /*
     * Méthode générique utilisée pour retourner un utilisateur depuis la base
     * de données, correspondant à la requête SQL donnée prenant en paramètres
     * les objets passés en argument.
     */
    private Utilisateur trouver( String sql, Object... objets ) throws DaoException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Utilisateur utilisateur = null;

        try {
            /* Récupération d'une connexion depuis la Factory */
            connexion = daoFactory.getConnection();
            /*
             * Préparation de la requête avec les objets passés en arguments
             * (ici, uniquement une adresse email) et exécution.
             */
            preparedStatement = initialisationRequetePreparee( connexion, sql, false, objets );
            resultSet = preparedStatement.executeQuery();
            /* Parcours de la ligne de données retournée dans le ResultSet */
            if ( resultSet.next() ) {
                utilisateur = map( resultSet );
            } else {
                throw new DaoException( "Utilisateur inexistant dans la base de donnée" );
            }
        } catch ( SQLException e ) {
            throw new DaoException( e );
        } finally {
            fermeturesSilencieuses( resultSet, preparedStatement, connexion );
        }
        return utilisateur;
    }

  
    /*
     * Simple méthode utilitaire permettant de faire la correspondance (le
     * mapping) entre une ligne issue de la table des utilisateurs (un
     * ResultSet) et un bean Utilisateur.
     */
    private static Utilisateur map( ResultSet resultSet ) throws SQLException {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setId( resultSet.getLong( "iduser" ) );
        utilisateur.setNom( resultSet.getString( "nomuser" ) );
        utilisateur.setPrenom( resultSet.getString( "prenomuser" ) );
        utilisateur.setMail( resultSet.getString( "mail" ) );
        utilisateur.setCodePostal( resultSet.getString( "codepostaluser" ) );
        utilisateur.setMdp( resultSet.getString( "mdp" ) );
             
        return utilisateur;
    }

}
