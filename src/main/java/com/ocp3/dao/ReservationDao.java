package com.ocp3.dao;

import java.sql.Date;
import java.util.List;

import com.ocp3.beans.Reservation;


public interface ReservationDao {
	void ajouter( Reservation reservation ) throws DaoException;
	void supprimer( Date dateDeb, Long idUser, Long idTopo )  throws DaoException;
	List<Reservation> listerPrets( UtilisateurDao utilisateurDao, TopoDao topoDao, Long idUserPreteur ) throws DaoException; // liste les prêts de topos d'un utilisateur
	List<Reservation> listerResasPourUser( UtilisateurDao utilisateurDao, TopoDao topoDao, Long idUser ) throws DaoException; // liste les réservations effectuées d'un utilisateur
	List<Reservation> listerResasPourTopo( UtilisateurDao utilisateurDao, TopoDao topoDao, Long idTopo ) throws DaoException; // liste les réservations en cours ou à venir d'un topo
}
