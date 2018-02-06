package com.ocp3.dao;

import com.ocp3.beans.Utilisateur;


public interface UtilisateurDao {
	void ajouter( Utilisateur utilisateur ) throws DaoException;
	Utilisateur trouver( String email) throws DaoException;
	Boolean existe (String email ) throws DaoException;
}