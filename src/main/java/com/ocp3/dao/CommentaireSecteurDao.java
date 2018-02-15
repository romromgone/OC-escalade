package com.ocp3.dao;

import java.util.List;

import com.ocp3.beans.CommentaireSecteur;


public interface CommentaireSecteurDao {
	void ajouter( CommentaireSecteur commentaireSecteur ) throws DaoException;
	List<CommentaireSecteur> lister( UtilisateurDao utilisateurDao, SiteDao siteDao, Long idSecteur ) throws DaoException;
}
