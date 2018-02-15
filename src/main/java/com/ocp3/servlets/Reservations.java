package com.ocp3.servlets;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ocp3.beans.*;
import com.ocp3.dao.*;
import com.ocp3.forms.ReservationsForm;


@WebServlet("/reservations")
public class Reservations extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ReservationDao reservationDao;
	private TopoDao topoDao;
	private UtilisateurDao utilisateurDao;   
    
	public void init() throws ServletException {
		/* Récupération des instances des DAO */
		this.reservationDao = ( (DaoFactory) getServletContext().getAttribute( "daofactory" ) ).getReservationDao();  
        this.topoDao = ( (DaoFactory) getServletContext().getAttribute( "daofactory" ) ).getTopoDao();  
        this.utilisateurDao = ( (DaoFactory) getServletContext().getAttribute( "daofactory" ) ).getUtilisateurDao(); 
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		afficherDonnees( request );
		
		this.getServletContext().getRequestDispatcher( "/WEB-INF/reservations.jsp" ).forward( request, response );
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/* Si clique sur bouton d'ajout de réservation */
		if ( request.getParameter( "suppr" ) == null ) {
			ReservationsForm form = new ReservationsForm( reservationDao, utilisateurDao, topoDao );
	        Reservation reservation = form.ajouterReservation(request);
	        
	        request.setAttribute( "reservationsForm", form );
	        request.setAttribute( "reservation", reservation ); 
        /* Si clique sur bouton de suppression d'une réservation */
		} else {
			Date dateDeb = Date.valueOf( request.getParameter( "resaDateDeb" ) );
			Long idUser = Long.parseLong( request.getParameter( "resaIdUser" ) );
			Long idTopo = Long.parseLong( request.getParameter( "resaIdTopo" ) );
			
			reservationDao.supprimer( dateDeb, idUser, idTopo );	
		}
		
		afficherDonnees( request );
        
		this.getServletContext().getRequestDispatcher( "/WEB-INF/reservations.jsp" ).forward( request, response );
	}
	
	/* Méthode privée qui récupère les données à afficher et les transmet */ 
	private void afficherDonnees( HttpServletRequest request ) {
		HttpSession session = request.getSession();	
		Utilisateur utilisateur = (Utilisateur) session.getAttribute("sessionUtilisateur");
		
		List<Topo> topos = topoDao.lister( utilisateurDao, utilisateur.getId() );
        List<Reservation> prets = reservationDao.listerPrets( utilisateurDao, topoDao, utilisateur.getId() );
		List<Reservation> reservations = reservationDao.listerResasPourUser( utilisateurDao, topoDao, utilisateur.getId() );

        request.setAttribute( "topos", topos );	
        request.setAttribute( "prets", prets );
        request.setAttribute( "reservations", reservations );			
	}
	

}
