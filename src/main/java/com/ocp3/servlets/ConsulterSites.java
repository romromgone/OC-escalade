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
		/* Si clique sur bouton d'ajout de site */
		if ( request.getParameter( "rechercher" ) == null ) {
			SitesForm form = new SitesForm( siteDao );
	        Site site = form.ajouterSite( request );
	        
	        request.setAttribute( "sitesForm", form );
	        request.setAttribute( "site", site );
	        
	        listerSites( request );	
		/* Si clique sur bouton de recherche */	
		} else {
			String departement = request.getParameter( "inputDep" );
			String rocher = request.getParameter( "inputRocher" );
			String orientation = request.getParameter( "inputOrientation" );
			
			if ( departement == "" && orientation == "" && rocher == "" ) {
				listerSites( request );	
			} else if ( departement != "" && orientation == "" && rocher == "" ) {
				List<Site> sites = siteDao.listerParDep( departement );
				request.setAttribute( "sites", sites );
			} else if ( departement == "" && orientation != "" && rocher == "" ) {
				List<Site> sites = siteDao.listerParOrientation( orientation );
				request.setAttribute( "sites", sites );
			} else if ( departement == "" && orientation == "" && rocher != "" ) {
					List<Site> sites = siteDao.listerParRocher( rocher );
					request.setAttribute( "sites", sites );
			} else if ( departement != "" && orientation != "" && rocher == "" ) {
				List<Site> sites = siteDao.listerParDepEtOrientation( departement, orientation );
				request.setAttribute( "sites", sites );
			} else if ( departement != "" && orientation == "" && rocher != "" ) {
				List<Site> sites = siteDao.listerParDepEtRocher( departement, rocher );
				request.setAttribute( "sites", sites );
			} else if ( departement == "" && orientation != "" && rocher != "" ) {
				List<Site> sites = siteDao.listerParOrientationEtRocher( orientation, rocher );
				request.setAttribute( "sites", sites );
			} else if ( departement == "" && orientation != "" && rocher != "" ) {
				List<Site> sites = siteDao.listerParDOR( departement, orientation, rocher );
				request.setAttribute( "sites", sites );
			} 	
		}

        this.getServletContext().getRequestDispatcher( "/WEB-INF/sites.jsp" ).forward( request, response );
	}
	
	/* Méthode privée qui récupère les données à afficher et les transmet */ 
	private void listerSites( HttpServletRequest request ) {
		List<Site> sites = siteDao.listerTout();
		request.setAttribute( "sites", sites );
	}
	
}
