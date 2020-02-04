package fr.eni.stagiaire.projet.bll.servlets;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.stagiaire.projet.beans.BeanException;
import fr.eni.stagiaire.projet.beans.Categorie;
import fr.eni.stagiaire.projet.beans.Enchere;
import fr.eni.stagiaire.projet.beans.Utilisateur;
import fr.eni.stagiaire.projet.beans.Vente;
import fr.eni.stagiaire.projet.dal.DALException;
import fr.eni.stagiaire.projet.dal.DAOFactory;
import fr.eni.stagiaire.projet.utils.GestionDate;

/**
 * Servlet implementation class GestionDesVentes
 * 
 * @author arebiere2018
 */
@WebServlet("/GestionDesVentes")
public class GestionDesVentes extends HttpServlet {
	private static final long serialVersionUID = 1L;

	int idUtilisateurConnecte = 0;
	HttpSession session = null;
	String recupChoix = null;
	Utilisateur utilisateur = null;
	String categorie = null;
	String recupNoVente = null;
	String recupArticle = null;
	String recupDescription = null;
	Integer recupCategorie = null;
	Date recupDate = null;
	int recupPrixInitial = 0;
	String recupPrixVente = null;
	String recupImage = null;
	String recupRueRetrait = null;
	String recupVilleRetrait = null;
	String recupCPRetrait = null;

	Boolean recupFiltreVentes;
	Boolean recupFiltreEnCours;
	Boolean recupFiltreAcquis;
	Boolean recupFiltreAutres;

	String recupRecherche = null;

	List<Vente> listeVentes;
	Vente uneVente = null;
	Date yesterday = new Date();
	Date now = new Date();
	String encherisseur = null;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GestionDesVentes() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			yesterday.setTime(System.currentTimeMillis() - 24*60*60*1000);
			now.setTime(System.currentTimeMillis());
			request.setAttribute("now", GestionDate.DateToString(now));
			// Recuperation de l'id de l'utilisateur.
			session = request.getSession();
			idUtilisateurConnecte = (int) session.getAttribute("noUtilisateur");

			// Recuperation de la valeur du bouton submit
			recupChoix = request.getParameter("choix_vente").toLowerCase();

			// Recuperation des valeurs saisies en fonction du Choix
			switch (recupChoix) {
			case "publier" :
				creerVente(request, response);
				break;
			case "rechercher":
				rechercher(request, response);
				break;
			case "vers_encherir":
				uneVente = DAOFactory.getVenteDao().get(Integer.parseInt(request.getParameter("vente")));
				session.setAttribute("vente", uneVente);
				int no_enchere = DAOFactory.getEnchereDao().getEncherisseur(uneVente.getNoVente());
				if (no_enchere == 0) {
					encherisseur = "... (Prix initial)";
				} else {
					encherisseur = DAOFactory.getUtilisateurDAO().get(no_enchere).getPseudo();
				}
				request.setAttribute("nomEncherisseur", encherisseur);
				recupUserWithoutPwd(request);
				System.out.println();
				if (uneVente.getUtilisateur().getNoUtilisateur()!=idUtilisateurConnecte) {
					this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/Encherir.jsp").forward(request, response);
				} else {
					this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/Detail_ma_vente.jsp").forward(request, response);
				}
				break;
			case "encherir":
				encherir(request, response);
				break;
			case "annuler la vente":
				DAOFactory.getVenteDao().delete((Vente) session.getAttribute("vente"));
				fowardListeEncheres(request, response);
				break;
			case "Annuler_ma_derniere_enchere":
				uneVente = DAOFactory.getVenteDao().get(Integer.parseInt(request.getParameter("vente")));
				DAOFactory.getEnchereDao().delete(idUtilisateurConnecte, uneVente.getNoVente());
			case "annuler":
			case "retour":
				fowardListeEncheres(request, response);
				break;
			case "retour_liste_encheres":
				fowardListeEncheres(request, response);
				break;
			case "vendre":
				List<Categorie> listeCategorie = DAOFactory.getCategorieDao().get();
				request.setAttribute("ListeCategorie", listeCategorie);
				recupUserWithoutPwd(request);
				this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/Vendre_un_article.jsp").forward(request, response);
				break;

			default:
				break;
			}
		} catch (DALException | ParseException | BeanException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/Se_connecter.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);

	}

	private void rechercher(HttpServletRequest request, HttpServletResponse response) throws DALException, BeanException, ServletException, IOException {
		listeVentes = new LinkedList<>();
		LinkedList<Vente> listeFiltres = new LinkedList<>();

		// On récupére les différents filtres qui sont cochés ou non par le user
		recupFiltreVentes = request.getParameter("mes_ventes") != null;
		recupFiltreEnCours = request.getParameter("mes_encheres_encours") != null;
		recupFiltreAcquis = request.getParameter("mes_acquisitions") != null;
		recupFiltreAutres = request.getParameter("autres_encheres") != null;

		// On crée la liste correspondante.
		if(recupFiltreVentes) {
			listeFiltres.addAll(DAOFactory.getVenteDao().getListeVentes(idUtilisateurConnecte));
		}
		if(recupFiltreEnCours) {
			listeFiltres.addAll(DAOFactory.getVenteDao().getListeEncheres(idUtilisateurConnecte));
		}
		if(recupFiltreAcquis) {
			listeFiltres.addAll(DAOFactory.getVenteDao().getListeAcquis(idUtilisateurConnecte));
		}
		if(recupFiltreAutres) {
			listeFiltres.addAll(DAOFactory.getVenteDao().getListeAutres(idUtilisateurConnecte));
		}
		// On récupére la catégorie de la liste déroulante <select>
		if (request.getParameter("categorie")!=null) {
			recupCategorie = Integer.parseInt(request.getParameter("categorie"));
			if (recupCategorie == 0) {
				recupCategorie = null;
			}
		} else {
			recupCategorie = null;
		}
		// On récupére le ou les mots recherchés par l'utilisateur
		recupRecherche = request.getParameter("recherche");
		// On crée la liste correspondante aux paramétres de recherche et de categorie
		if (recupCategorie != null) {
			categorie = DAOFactory.getCategorieDao().get(recupCategorie).getLibelle();
			listeVentes = DAOFactory.getVenteDao().getListeRC(recupRecherche, categorie);
		} else {
			listeVentes = DAOFactory.getVenteDao().getListeR(recupRecherche);
		}
		// On ne retient que les éléments communs aux 2 listes.
		if (!listeFiltres.isEmpty()) {
			listeVentes.retainAll(listeFiltres);
		} else {
			request.setAttribute("noAuctionFilter", true);
		}
		fowardListeEncheres(request, response, listeVentes);

	}

	public void creerVente (HttpServletRequest request, HttpServletResponse response) throws DALException, ParseException, BeanException, ServletException, IOException { 
		recupArticle = request.getParameter("article").trim();
		recupDescription = request.getParameter("description").trim();
		//		recupImage = request.getParameter("image");
		recupRueRetrait = request.getParameter("rue").trim(); 
		recupVilleRetrait = request.getParameter("ville").trim(); 
		recupCPRetrait = request.getParameter("cp").trim();
		//Si tout les champs sont remplis
		if (!recupArticle.isEmpty() && !recupDescription.isEmpty() && !request.getParameter("categorie").isEmpty() && !request.getParameter("prix_initial").isEmpty() && !request.getParameter("date").isEmpty() && !recupRueRetrait.isEmpty() && !recupVilleRetrait.isEmpty() && !recupCPRetrait.isEmpty()) {
			recupCategorie = Integer.parseInt(request.getParameter("categorie").trim());
			recupPrixInitial = Integer.parseInt(request.getParameter("prix_initial").trim());
			recupDate = new Date(GestionDate.StringToDate(request.getParameter("date")).getTime());
			if (recupDate.after(now)) {
				uneVente = new Vente(recupArticle, recupDescription, recupDate, recupPrixInitial, recupPrixInitial, DAOFactory.getUtilisateurDAO().get(idUtilisateurConnecte), DAOFactory.getCategorieDao().get(recupCategorie), recupRueRetrait, recupCPRetrait, recupVilleRetrait);
				DAOFactory.getVenteDao().insert(uneVente);
				fowardListeEncheres(request, response);
			} else {
				List<Categorie> listeCategorie = DAOFactory.getCategorieDao().get();
				request.setAttribute("ListeCategorie", listeCategorie);
				request.setAttribute("Article", recupArticle);
				request.setAttribute("Description", recupDescription);
				request.setAttribute("Categorie", recupCategorie);
				request.setAttribute("RueRetrait", recupRueRetrait);
				request.setAttribute("VilleRetrait", recupVilleRetrait);
				request.setAttribute("CPRetrait", recupCPRetrait);
				recupUserWithoutPwd(request);
				request.setAttribute("datePasse", true);
				this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/Vendre_un_article.jsp").forward(request, response);
			}
		}else {
			request.setAttribute("champsVides", true);
			request.setAttribute("Article", recupArticle);
			request.setAttribute("Description", recupDescription);
			request.setAttribute("Categorie", recupCategorie);
			request.setAttribute("RueRetrait", recupRueRetrait);
			request.setAttribute("VilleRetrait", recupVilleRetrait);
			request.setAttribute("CPRetrait", recupCPRetrait);
			List<Categorie> listeCategorie = DAOFactory.getCategorieDao().get();
			request.setAttribute("ListeCategorie", listeCategorie);
			recupUserWithoutPwd(request);
			this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/Vendre_un_article.jsp").forward(request, response);
		}
	}

	private void encherir(HttpServletRequest request, HttpServletResponse response) throws DALException, BeanException, ServletException, IOException {
		int recupMontant = 0;
		try {
			recupMontant = Integer.parseInt(request.getParameter("montant").trim());
		} catch (Exception e) {
			int no_enchere = DAOFactory.getEnchereDao().getEncherisseur(uneVente.getNoVente());
			if (no_enchere == 0) {
				encherisseur = "... (Prix initial)";
			} else {
				encherisseur = DAOFactory.getUtilisateurDAO().get(no_enchere).getPseudo();
			}
			request.setAttribute("nomEncherisseur", encherisseur);
			request.setAttribute("montantIncorrect", true);
			recupUserWithoutPwd(request);
			this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/Encherir.jsp").forward(request, response);
		}
		uneVente = (Vente) session.getAttribute("vente");
		recupUserWithoutPwd(request);
		if (recupMontant > uneVente.getPrixVente() ) {
			Enchere enchere = new Enchere(yesterday, utilisateur, uneVente, recupMontant);
			//On test si l'enchere existe en base.
			if (DAOFactory.getEnchereDao().get(idUtilisateurConnecte, uneVente.getNoVente())!=null) {
				DAOFactory.getEnchereDao().update(enchere);
			} else {
				DAOFactory.getEnchereDao().insert(enchere);
			}
			fowardListeEncheres(request, response);
		} else {
			int no_enchere = DAOFactory.getEnchereDao().getEncherisseur(uneVente.getNoVente());
			if (no_enchere == 0) {
				encherisseur = "... (Prix initial)";
			} else {
				encherisseur = DAOFactory.getUtilisateurDAO().get(no_enchere).getPseudo();
			}
			request.setAttribute("nomEncherisseur", encherisseur);
			request.setAttribute("montantIncorrect", true);
			recupUserWithoutPwd(request);
			this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/Encherir.jsp").forward(request, response);
		}
	}

	/**
	 * Insert l'utilisateur connecté dans la requete. Attribut : "utilisateur"
	 * @auteur : Yann Le Douget
	 * @param request
	 * @throws DALException
	 * @throws BeanException
	 */
	private void recupUserWithoutPwd (HttpServletRequest request) throws DALException, BeanException {
		utilisateur = DAOFactory.getUtilisateurDAO().get(idUtilisateurConnecte);
		//On efface le mot de passe pour éviter de le faire transiter sur le réseau
		utilisateur.setMotDePasse(null);
		request.setAttribute("utilisateur", utilisateur);
	}

	/**
	 * Réalise un forward vers la jsp Liste_enchere en établissant une liste de vente par défault ou en appliquant celle fournit
	 * @auteur : Yann Le Douget
	 * @param request
	 * @param response
	 * @param ListeVentes (facultatif)
	 * @throws DALException
	 * @throws ServletException
	 * @throws IOException
	 * @throws BeanException
	 */
	private void fowardListeEncheres(HttpServletRequest request, HttpServletResponse response) throws DALException, ServletException, IOException, BeanException {
		List<Vente> listeVentes = DAOFactory.getVenteDao().get();
		listeVentes.removeIf(n -> (n.isArchive()));
		for (Vente vente : listeVentes) {
			vente.getUtilisateur().setMotDePasse(null);
		}
		request.setAttribute("ListeCategorie", DAOFactory.getCategorieDao().get());
		request.setAttribute("ListeVentes", listeVentes);
		this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/Liste_enchere.jsp").forward(request, response);
	}

	/**
	 * Réalise un forward vers la jsp Liste_enchere en établissant une liste de vente par défault ou en appliquant celle fournit
	 * @auteur : Yann Le Douget
	 * @param request
	 * @param response
	 * @param ListeVentes (facultatif)
	 * @throws DALException
	 * @throws ServletException
	 * @throws IOException
	 * @throws BeanException
	 */
	private void fowardListeEncheres(HttpServletRequest request, HttpServletResponse response, List<Vente> ListeVentes) throws DALException, ServletException, IOException, BeanException {
		//listeVentes.removeIf(n -> (n.isArchive()));
		for (Vente vente : listeVentes) {
			vente.getUtilisateur().setMotDePasse(null);
		}
		request.setAttribute("ListeCategorie", DAOFactory.getCategorieDao().get());
		request.setAttribute("ListeVentes", ListeVentes);
		this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/Liste_enchere.jsp").forward(request, response);
	}

}
