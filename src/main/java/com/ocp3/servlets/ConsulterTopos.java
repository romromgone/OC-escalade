package com.ocp3.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ocp3.beans.Topo;
import com.ocp3.dao.*;


@WebServlet("/topos")
public class ConsulterTopos extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TopoDao topoDao;
	private UtilisateurDao utilisateurDao;
    
	public void init() throws ServletException {
		/* Récupération des instances des DAO */
        this.topoDao = ( (DaoFactory) getServletContext().getAttribute( "daofactory" ) ).getTopoDao();  
        this.utilisateurDao = ( (DaoFactory) getServletContext().getAttribute( "daofactory" ) ).getUtilisateurDao(); 
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {			
		List<Topo> topos = topoDao.lister( utilisateurDao );
		
		request.setAttribute( "topos", topos );
		
		this.getServletContext().getRequestDispatcher( "/WEB-INF/topos.jsp" ).forward( request, response );
	}
	
}
