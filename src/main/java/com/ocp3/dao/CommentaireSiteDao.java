package com.ocp3.dao;

import java.util.List;

import com.ocp3.beans.CommentaireSite;


public interface CommentaireSiteDao {
	void ajouter( CommentaireSite commentaireSite ) throws DaoException;
	List<CommentaireSite> lister( UtilisateurDao utilisateurDao, SiteDao siteDao, Long idSite ) throws DaoException;
}
