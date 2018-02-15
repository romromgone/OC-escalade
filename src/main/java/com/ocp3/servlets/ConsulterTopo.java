package com.ocp3.servlets;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ocp3.beans.*;
import com.ocp3.dao.*;


@WebServlet("/topo")
public class ConsulterTopo extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TopoDao topoDao;
	private UtilisateurDao utilisateurDao;
	private ReservationDao reservationDao;
	private SiteDao siteDao;
	private CommentaireTopoDao commentaireTopoDao;
    
	public void init() throws ServletException {
		/* Récupération des instances des DAO */
        this.topoDao = ( (DaoFactory) getServletContext().getAttribute( "daofactory" ) ).getTopoDao();  
        this.utilisateurDao = ( (DaoFactory) getServletContext().getAttribute( "daofactory" ) ).getUtilisateurDao(); 
        this.reservationDao = ( (DaoFactory) getServletContext().getAttribute( "daofactory" ) ).getReservationDao(); 
        this.siteDao = ( (DaoFactory) getServletContext().getAttribute( "daofactory" ) ).getSiteDao();
        this.commentaireTopoDao = ( (DaoFactory) getServletContext().getAttribute( "daofactory" ) ).getCommentaireTopoDao(); 
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		afficherDonnees( request, "id" );
		
		this.getServletContext().getRequestDispatcher( "/WEB-INF/topo.jsp" ).forward( request, response );
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/* Récupération des attributs du commentaire */
		Timestamp dateCT = new Timestamp( System.currentTimeMillis() );	
		String texteCT = request.getParameter( "commentaire" );
		Long idUser = ( (Utilisateur) request.getSession().getAttribute("sessionUtilisateur") ).getId();
		Long idTopo = Long.parseLong( request.getParameter( "idTopo" ) );
		
		/* Création et initialisation du commentaire pour ajout */
		CommentaireTopo commentaireTopo = new CommentaireTopo();
		commentaireTopo.setDateCT( dateCT );
		commentaireTopo.setTexteCT( texteCT );
		commentaireTopo.setIdUser( idUser );
		commentaireTopo.setIdTopo( idTopo );
		commentaireTopoDao.ajouter(commentaireTopo);
	
		afficherDonnees( request, "idTopo" );
		
		this.getServletContext().getRequestDispatcher( "/WEB-INF/topo.jsp" ).forward( request, response );		
	}
	
	/* Méthode privée qui récupère les données à afficher et les transmet.
	 * L'id du topo est récupéré grâce à "request.getParameter" mais via 2 paramètres différents pour les méthode POST et GET :
	 * via la variable de l'url "id" lors du premier accès à la page par lien pour GET
	 * via un input caché "idTopo" du formulaire d'envoi de commentaire pour POST 
	 * (dont la valeur est initialisée grâce à l'attribut "topo" de l'objet request récupéré lors du premier accès à la page).
	 * C'est le paramètre "parameter" de cette fonction qui permet de différencier quel paramètre de request récupérer.
	 */
	private void afficherDonnees( HttpServletRequest request, String parameter ) {
		long idTopo = Long.parseLong( request.getParameter( parameter ) );
		
		Topo topo = topoDao.trouver( utilisateurDao, idTopo );
		List<Site> sites = siteDao.lister( topo.getDepartement() );
		List<Reservation> reservations = reservationDao.listerResasPourTopo( utilisateurDao, topoDao, idTopo );
		List<CommentaireTopo> commentairesTopo = commentaireTopoDao.lister( utilisateurDao, topoDao, idTopo );

		request.setAttribute( "topo", topo );
		request.setAttribute( "sites", sites );
		request.setAttribute( "reservations", reservations );
		request.setAttribute( "commentairesTopo", commentairesTopo );
	}

}
