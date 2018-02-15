package com.ocp3.dao;

import java.util.List;

import com.ocp3.beans.Secteur;


public interface SecteurDao {
	void ajouter( Secteur secteur ) throws DaoException;
	Secteur trouver( SiteDao siteDao, Integer noSecteur, Long idSite ) throws DaoException;
	List<Secteur> lister( SiteDao siteDao ) throws DaoException; // liste tous les secteurs
}
