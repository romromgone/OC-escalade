package com.ocp3.dao;

import java.util.List;

import com.ocp3.beans.Voie;


public interface VoieDao {
	void ajouter( Voie voie ) throws DaoException;
	List<Voie> listerParSite( SecteurDao secteurDao, SiteDao siteDao, Long idSite ) throws DaoException; // liste toutes les voies d'un site
	List<Voie> listerParSecteur( SecteurDao secteurDao, SiteDao siteDao, Integer noSecteur, Long idSite ) throws DaoException; // liste toutes les voies d'un secteur
}
