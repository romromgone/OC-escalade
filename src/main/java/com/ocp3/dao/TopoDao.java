package com.ocp3.dao;

import java.util.List;
import com.ocp3.beans.Topo;

public interface TopoDao {
	void ajouter( Topo topo ) throws DaoException;
	List<Topo> lister( Long idUser ) throws DaoException;
	List<Topo> lister() throws DaoException;
}
