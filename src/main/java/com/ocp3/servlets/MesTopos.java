package com.ocp3.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ocp3.beans.Topo;
import com.ocp3.beans.Utilisateur;
import com.ocp3.dao.*;
import com.ocp3.forms.MesToposForm;


@WebServlet("/mestopos")
public class MesTopos extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TopoDao topoDao;
	private UtilisateurDao utilisateurDao;
    
	public void init() throws ServletException {
		/* Récupération des instances des DAO */
        this.topoDao = ( (DaoFactory) getServletContext().getAttribute( "daofactory" ) ).getTopoDao();
        this.utilisateurDao = ( (DaoFactory) getServletContext().getAttribute( "daofactory" ) ).getUtilisateurDao(); 
    }
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		listerTopos(request);
		
		this.getServletContext().getRequestDispatcher( "/WEB-INF/mestopos.jsp" ).forward( request, response );
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		/* Préparation de l'objet formulaire */
		MesToposForm form = new MesToposForm( topoDao );
		
		/* Traitement de la requête et récupération du bean en résultat */
        Topo topo = form.ajouterTopo( request );
        
        /* Stockage du formulaire et du bean dans l'objet request */
        request.setAttribute( "mesToposForm", form );
        request.setAttribute( "topo", topo );
        
        listerTopos( request );
        
        this.getServletContext().getRequestDispatcher( "/WEB-INF/mestopos.jsp" ).forward( request, response );
	}
	
	/* Méthode privée qui récupère les données à afficher et les transmet */ 
	private void listerTopos( HttpServletRequest request ) {
		HttpSession session = request.getSession();	
		Utilisateur utilisateur = (Utilisateur) session.getAttribute("sessionUtilisateur");
		
		List<Topo> topos = topoDao.lister( utilisateurDao, utilisateur.getId() );
		
		request.setAttribute( "topos", topos );	
	}

}
