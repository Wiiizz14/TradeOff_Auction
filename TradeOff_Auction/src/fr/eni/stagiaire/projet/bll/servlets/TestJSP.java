package fr.eni.stagiaire.projet.bll.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.stagiaire.projet.beans.BeanException;
import fr.eni.stagiaire.projet.beans.Utilisateur;
import fr.eni.stagiaire.projet.beans.Vente;
import fr.eni.stagiaire.projet.dal.DALException;
import fr.eni.stagiaire.projet.dal.DAOFactory;

/**
 * Servlet implementation class TestJSP
 */
@WebServlet("/TestJSP")
public class TestJSP extends HttpServlet implements Servlet {
	private static final long serialVersionUID = 1L;
	
	//Renseigner ici le fichier jsp à tester.
	private static final String fichier = "Vendre_un_article.jsp";
       
    public TestJSP() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
		HttpSession session = request.getSession();
		Utilisateur unUtilisateur = DAOFactory.getUtilisateurDAO().get(1);
		//On efface le mot de passe pour éviter de le faire transiter sur le réseau
		unUtilisateur.setMotDePasse(null);
		session.setAttribute("noUtilisateur", unUtilisateur.getNoUtilisateur());
		List<Vente> ListeVentes = DAOFactory.getVenteDao().get();
		Vente vente = DAOFactory.getVenteDao().get(1);
		request.setAttribute("ListeCategorie", DAOFactory.getCategorieDao().get());
		request.setAttribute("ListeVentes", ListeVentes);
		request.setAttribute("utilisateur", unUtilisateur);
		request.setAttribute("vente", vente);
		response.setIntHeader("Refresh", 1);
		this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/" + fichier).forward(request, response);
		} catch (DALException | BeanException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
