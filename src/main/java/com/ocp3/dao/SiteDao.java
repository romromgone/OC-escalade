package com.ocp3.dao;

import java.util.List;

import com.ocp3.beans.Site;


public interface SiteDao {
	void ajouter( Site site ) throws DaoException;
	Site trouver( Long idSite ) throws DaoException;
	//Site trouver( Long idUser, String titreTopo ) throws DaoException; // trouve le topo lors de l'ajout d'une réservation avec le nom du topo
	List<Site> lister() throws DaoException; // liste tous les sites
}
