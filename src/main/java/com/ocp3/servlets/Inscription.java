package com.ocp3.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ocp3.beans.Utilisateur;
import com.ocp3.dao.*;
import com.ocp3.forms.*;


@WebServlet("/inscription")
public class Inscription extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UtilisateurDao utilisateurDao;
    
	public void init() throws ServletException {
		/* Récupération d'une instance de notre DAO Utilisateur */
        this.utilisateurDao = ( (DaoFactory) getServletContext().getAttribute( "daofactory" ) ).getUtilisateurDao();
    }
	
	protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        this.getServletContext().getRequestDispatcher( "/WEB-INF/inscription.jsp" ).forward( request, response );
	}

	protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {		
		/* Préparation de l'objet formulaire */
        InscriptionForm form = new InscriptionForm( utilisateurDao );

        /* Traitement de la requête et récupération du bean en résultat */
        Utilisateur utilisateur = form.inscrireUtilisateur( request );

        /* Stockage du formulaire et du bean dans l'objet request */
        request.setAttribute( "inscriptionForm", form );
        request.setAttribute( "utilisateur", utilisateur );

        this.getServletContext().getRequestDispatcher( "/WEB-INF/inscription.jsp" ).forward( request, response );
	}

}
