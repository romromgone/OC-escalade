package com.ocp3.dao;

import java.util.List;

import com.ocp3.beans.CommentaireTopo;


public interface CommentaireTopoDao {
	void ajouter( CommentaireTopo commentaireTopo ) throws DaoException;
	List<CommentaireTopo> lister( UtilisateurDao utilisateurDao, TopoDao topoDao, Long idTopo ) throws DaoException;
}
