package com.ocp3.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ocp3.beans.Site;
import com.ocp3.dao.*;
import com.ocp3.forms.SitesForm;


@WebServlet("/sites")
public class ConsulterSites extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private SiteDao siteDao;
    
	public void init() throws ServletException {
		/* Récupération de l'instances du DAO */
        this.siteDao = ( (DaoFactory) getServletContext().getAttribute( "daofactory" ) ).getSiteDao();  
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {					
		listerSites( request );
		
		this.getServletContext().getRequestDispatcher( "/WEB-INF/sites.jsp" ).forward( request, response );
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {			
		/* Préparation de l'objet formulaire */
		SitesForm form = new SitesForm( siteDao );
		
		/* Traitement de la requête et récupération du bean en résultat */
        Site site = form.ajouterSite( request );
        
        /* Stockage du formulaire et du bean dans l'objet request */
        request.setAttribute( "sitesForm", form );
        request.setAttribute( "site", site );
        
        listerSites( request );
        
        this.getServletContext().getRequestDispatcher( "/WEB-INF/sites.jsp" ).forward( request, response );
	}
	
	/* Méthode privée qui récupère les données à afficher et les transmet */ 
	private void listerSites( HttpServletRequest request ) {
		List<Site> sites = siteDao.lister();
		request.setAttribute( "sites", sites );
	}
	
}
