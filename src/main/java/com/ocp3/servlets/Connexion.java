package com.ocp3.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ocp3.beans.Utilisateur;
import com.ocp3.dao.*;
import com.ocp3.forms.*;


@WebServlet("/connexion")
public class Connexion extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UtilisateurDao utilisateurDao;

	public void init() throws ServletException {
		/* Récupération d'une instance de notre DAO Utilisateur */
        this.utilisateurDao = ( (DaoFactory) getServletContext().getAttribute( "daofactory" ) ).getUtilisateurDao();
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher( "/index.jsp" ).forward( request, response );
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/* Préparation de l'objet formulaire */
        ConnexionForm form = new ConnexionForm( utilisateurDao );

        /* Traitement de la requête et récupération du bean en résultat */
        Utilisateur utilisateur = form.connecterUtilisateur( request );
        
        /* Récupération de la session depuis la requête */
        HttpSession session = request.getSession();

        /**
         * Si aucune erreur de validation n'a eu lieu, alors ajout du bean
         * Utilisateur à la session, sinon suppression du bean de la session.
         */
        if ( form.getErreurs().isEmpty() ) {
            session.setAttribute( "sessionUtilisateur", utilisateur );
        } else {
            session.setAttribute( "sessionUtilisateur", null );
        }

        /* Stockage du formulaire et du bean dans l'objet request */
        request.setAttribute( "connexionForm", form );
        request.setAttribute( "utilisateur", utilisateur );

        this.getServletContext().getRequestDispatcher( "/index.jsp" ).forward( request, response );
	}

}
