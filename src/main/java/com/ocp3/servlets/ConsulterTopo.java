package com.ocp3.servlets;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ocp3.beans.*;
import com.ocp3.dao.*;


@WebServlet("/topo")
public class ConsulterTopo extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TopoDao topoDao;
	private UtilisateurDao utilisateurDao;
	private ReservationDao reservationDao;
	private CommentaireTopoDao commentaireTopoDao;
    
	public void init() throws ServletException {
        this.topoDao = ( (DaoFactory) getServletContext().getAttribute( "daofactory" ) ).getTopoDao();  
        this.utilisateurDao = ( (DaoFactory) getServletContext().getAttribute( "daofactory" ) ).getUtilisateurDao(); 
        this.reservationDao = ( (DaoFactory) getServletContext().getAttribute( "daofactory" ) ).getReservationDao(); 
        this.commentaireTopoDao = ( (DaoFactory) getServletContext().getAttribute( "daofactory" ) ).getCommentaireTopoDao(); 
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();	
		
		long idTopo=Long.parseLong( request.getParameter("id") );	
		Topo topo = topoDao.trouver( utilisateurDao, idTopo );
		List<Reservation> reservations = reservationDao.listerResasPourTopo( utilisateurDao, topoDao, topo.getId() );
		List<CommentaireTopo> commentairesTopo = commentaireTopoDao.lister( utilisateurDao, topoDao, topo.getId() );

		request.setAttribute( "topo", topo );
		request.setAttribute( "reservations", reservations );
		request.setAttribute( "commentairesTopo", commentairesTopo );
	
		
		this.getServletContext().getRequestDispatcher( "/WEB-INF/topo.jsp" ).forward( request, response );
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();	
		Utilisateur utilisateur = (Utilisateur) session.getAttribute("sessionUtilisateur");
		
		
		CommentaireTopo commentaireTopo = new CommentaireTopo();
		Date date = Date.valueOf( LocalDate.now() );
		commentaireTopo.setDateCT( date );
		commentaireTopo.setTexteCT( request.getParameter( "commentaire" ) );
		commentaireTopo.setIdUser( utilisateur.getId() );
		commentaireTopo.setIdTopo( Long.parseLong( request.getParameter( "idTopo" ) ) );
		commentaireTopoDao.ajouter(commentaireTopo);
		
		
		List<Reservation> reservations = reservationDao.listerResasPourTopo( utilisateurDao, topoDao, Long.parseLong( request.getParameter( "idTopo" ) ) );
		List<CommentaireTopo> commentairesTopo = commentaireTopoDao.lister( utilisateurDao, topoDao, Long.parseLong( request.getParameter( "idTopo" ) ) );

		//request.setAttribute( "topo", topo );
		request.setAttribute( "reservations", reservations );
		request.setAttribute( "commentairesTopo", commentairesTopo );
		
		this.getServletContext().getRequestDispatcher( "/WEB-INF/topo.jsp" ).forward( request, response );
		
	}

}
