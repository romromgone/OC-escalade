package com.ocp3.dao;

import java.util.List;

import com.ocp3.beans.Site;


public interface SiteDao {
	void ajouter( Site site ) throws DaoException;
	Site trouver( Long idSite ) throws DaoException;
	List<Site> listerTout() throws DaoException; // liste tous les sites
	List<Site> listerParDep( String departement ) throws DaoException; // liste tous les sites du département donnée
	List<Site> listerParOrientation( String orientation ) throws DaoException; // liste tous les sites de l'orientation donnée
	List<Site> listerParRocher( String rocher ) throws DaoException;
	List<Site> listerParDepEtOrientation( String departement, String orientation ) throws DaoException;
	List<Site> listerParDepEtRocher( String departement, String rocher ) throws DaoException;
	List<Site> listerParOrientationEtRocher( String orientation, String rocher ) throws DaoException;
	List<Site> listerParDOR( String departement, String orientation, String rocher ) throws DaoException;
}
