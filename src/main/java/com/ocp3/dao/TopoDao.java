package com.ocp3.dao;

import java.util.List;
import com.ocp3.beans.Topo;


public interface TopoDao {
	void ajouter( Topo topo ) throws DaoException;
	Topo trouver( UtilisateurDao utilisateurDao, Long idTopo ) throws DaoException;
	Topo trouver( UtilisateurDao utilisateurDao, Long idUser, String titreTopo ) throws DaoException; // trouve le topo lors de l'ajout d'une réservation avec le nom du topo
	List<Topo> lister( UtilisateurDao utilisateurDao, Long idUser ) throws DaoException; // liste les topos d'un utilisateur en fonction de l'id
	List<Topo> lister( UtilisateurDao utilisateurDao ) throws DaoException; // liste tous les topos avec leur propriétaire
}
