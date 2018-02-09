package com.ocp3.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.ocp3.beans.Site;
import com.ocp3.dao.*;


@WebServlet("/site")
public class ConsulterSite extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private SiteDao siteDao;
	//private CommentaireTopoDao commentaireTopoDao;
    
	public void init() throws ServletException {
		/* Récupération des instances des DAO */
        this.siteDao = ( (DaoFactory) getServletContext().getAttribute( "daofactory" ) ).getSiteDao();        
        //this.commentaireTopoDao = ( (DaoFactory) getServletContext().getAttribute( "daofactory" ) ).getCommentaireTopoDao(); 
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		afficherDonnees( request, "id" );
		
		this.getServletContext().getRequestDispatcher( "/WEB-INF/site.jsp" ).forward( request, response );
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
	}
	
	/* Méthode privée qui récupère les données à afficher et les transmet.
	 * L'id du site est récupéré grâce à "request.getParameter" mais via 2 paramètres différents pour les méthode POST et GET :
	 * via la variable de l'url "id" lors du premier accès à la page par lien pour GET
	 * via un input caché "idSite" du formulaire d'envoi de commentaire pour POST 
	 * (dont la valeur est initialisée grâce à l'attribut "site" de l'objet request récupéré lors du premier accès à la page).
	 * C'est le paramètre "parameter" de cette fonction qui permet de différencier quel paramètre de request récupérer.
	 */
	private void afficherDonnees( HttpServletRequest request, String parameter ) {
		long idSite = Long.parseLong( request.getParameter( parameter ) );
		
		Site site = siteDao.trouver( idSite );
		//List<CommentaireTopo> commentairesTopo = commentaireTopoDao.lister( utilisateurDao, topoDao, idTopo );

		request.setAttribute( "site", site );
		//request.setAttribute( "commentairesTopo", commentairesTopo );
	}


}
