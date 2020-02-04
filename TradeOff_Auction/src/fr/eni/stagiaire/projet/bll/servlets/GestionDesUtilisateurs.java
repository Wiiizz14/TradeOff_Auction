package fr.eni.stagiaire.projet.bll.servlets;
/**
 * @author dbondigu2018 & jamest & Yann Le Douget
 */

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
import fr.eni.stagiaire.projet.utils.Parametre;

/**
 * Servlet implementation class GestionDesUtilisateurs
 * 
 */
@WebServlet("/GestionDesUtilisateurs")
public class GestionDesUtilisateurs extends HttpServlet implements Servlet {
	private static final long serialVersionUID = 1L;

	// Variables
	private String recupChoix = null;
	private String recupPseudo = null;
	private String recupNom = null;
	private String recupPrenom = null;
	private String recupEmail = null;
	private String recupTelephone = null;
	private String recupRue = null;
	private String recupCodePostal = null;
	private String recupVille = null;
	private String recupMotDePasse = null;
	private String recupConfirmation = null;
	private String sourceErreur = null;
	private int noUtilisateur = 0;
	private HttpSession session = null;
	// pas prise en compte
	private int credit;
	private boolean administrateur;

	private Utilisateur unUtilisateur = new Utilisateur();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GestionDesUtilisateurs() {
		super();
	}

	/**
	 * @throws IOException 
	 * @throws ServletException 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//Si on a pas de choix (première connexion), on redirige vers la page d'accueil.
			if (request.getParameter("choix_user").isEmpty()) {
				this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/Se_connecter.jsp").forward(request, response);
			} else {
				session = request.getSession();
				if (session.getAttribute("noUtilisateur")!=null) {
					noUtilisateur = (int) session.getAttribute("noUtilisateur");
				}
				// Recuperation de la valeur du bouton submit
				recupChoix = request.getParameter("choix_user").toLowerCase();

				// Recuperation des valeurs saisies en fonction du Choix
				switch (recupChoix) {

				case "creer_user":
					creerUtilisateur(request, response);
					break;

				case "mon_profil":
					recupUserWithoutPwd(request);
					request.setAttribute("compact", false);
					this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/Vue_user.jsp").forward(request, response);
					break;

				case "vers_modifier_user":
					recupUserWithoutPwd(request);
					request.setAttribute("modifier", true);
					this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/Modifier_user.jsp").forward(request, response);
					break;

				case "modifier_user":
					modifierUtilisateur(request, response);
					break;
					
				case "deconnexion":
					this.session.invalidate();
					this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/Se_connecter.jsp").forward(request, response);
					break;

				case "supprimer_user":
					supprimerUtilisateur(request, response);
					break;

				case "annuler":
					this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/Se_connecter.jsp").forward(request, response);
					break;

				case "connexion":
					seConnecter(request, response);
					break;
					
				case "vue_profil":
					request.setAttribute("compact", true);
					Utilisateur client = DAOFactory.getUtilisateurDAO().get(Integer.parseInt(request.getParameter("client")));
					client.setMotDePasse(null);
					client.setAdministrateur(false);
					request.setAttribute("utilisateur", client);
					this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/Vue_user.jsp").forward(request, response);
					break;

				case "lien_creation":
					this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/Creation_user.jsp").forward(request, response);
					break;

				case "qestion":
				case "retour":
				case "retour_liste_encheres":
					fowardListeEncheres(request, response);
					break;

				default:
					break;
				}
			}
		} catch (Exception e1) {
			this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/Se_connecter.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	/**
	 * Requete pour creer un utilisateur
	 * 
	 * @param request
	 * @param response
	 * @throws DALException
	 * @throws IOException 
	 * @throws ServletException 
	 * @throws BeanException 
	 */
	private void creerUtilisateur(HttpServletRequest request, HttpServletResponse response) throws DALException, ServletException, IOException {
		try {
			recupPseudo = request.getParameter("pseudo").trim();
			recupNom = request.getParameter("nom").trim();
			recupPrenom = request.getParameter("prenom").trim();
			recupEmail = request.getParameter("email").trim();
			recupRue = request.getParameter("rue").trim();
			recupCodePostal = request.getParameter("code_postal").trim();
			recupVille = request.getParameter("ville").trim();
			recupMotDePasse = request.getParameter("mot_de_passe").trim();
			recupConfirmation = request.getParameter("confirmation").trim();
			recupTelephone = request.getParameter("telephone").trim();
			if (recupEmail.isEmpty()) {
				recupEmail = null;
			}
			if (recupTelephone.isEmpty()) {
				recupTelephone = null;
			}
			unUtilisateur = new Utilisateur(recupPseudo, recupNom, recupPrenom, recupEmail, recupTelephone, recupRue, recupCodePostal, recupVille);
			request.setAttribute("utilisateur", unUtilisateur);
			//Si tout les champs sont remplis
			if (recupTelephone!=null && !recupPseudo.isEmpty() && !recupNom.isEmpty() && !recupPrenom.isEmpty() && recupEmail!=null && !recupRue.isEmpty() && !recupCodePostal.isEmpty() && !recupVille.isEmpty() && !recupMotDePasse.isEmpty() && !recupConfirmation.isEmpty()) {

				// A voir selon reponse du client
				credit = Parametre.lireInt("CREDITS_INITIAUX");
				administrateur = false;

				unUtilisateur.setMotDePasse(recupMotDePasse);
				// Creation du compte si le mot de passe est identique a la confirmation
				if (recupMotDePasse.equals(recupConfirmation)) {

					try {
						DAOFactory.getUtilisateurDAO().insert(unUtilisateur);
					} catch (DALException e) {
						//auteur : Yann Le Douget
						sourceErreur = recupereSourceErreur(e.getMessage());
						if (sourceErreur.equals(recupPseudo)) {
							request.setAttribute("pseudoExistant", true);
							this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/Creation_user.jsp").forward(request, response);
						} else if (sourceErreur.equals(recupEmail)){
							request.setAttribute("emailExistant", true);
							this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/Creation_user.jsp").forward(request, response);
						} else if (sourceErreur.equals(recupTelephone)){
							request.setAttribute("telephoneExistant", true);
							this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/Creation_user.jsp").forward(request, response);
						} else {
							throw e;
						}
					}
					session.setAttribute("noUtilisateur", unUtilisateur.getNoUtilisateur());
					fowardListeEncheres(request, response);
				} else {
					request.setAttribute("erreurConfirme", true);
					this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/Creation_user.jsp").forward(request, response);
				}
			} else {
				request.setAttribute("champsVides", true);
				this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/Creation_user.jsp").forward(request, response);
			}
		} catch (BeanException e){
			request.setAttribute("erreurFormat", true);
			this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/Creation_user.jsp").forward(request, response);
		}
	}

	/**
	 * Requete pour modifier un utilisateur
	 * 
	 * @param request
	 * @param response
	 * @throws DALException 
	 * @throws IOException 
	 * @throws ServletException 
	 * @throws BeanException 
	 */
	private void modifierUtilisateur(HttpServletRequest request, HttpServletResponse response) throws DALException, ServletException, IOException, BeanException{
		request.setAttribute("modifier", true);
		unUtilisateur = DAOFactory.getUtilisateurDAO().get(noUtilisateur);
		String mdp = unUtilisateur.getMotDePasse();
		//On efface le mot de passe pour éviter de le faire transiter sur le réseau
		unUtilisateur.setMotDePasse(null);
		request.setAttribute("utilisateur", unUtilisateur);
		recupPseudo = request.getParameter("pseudo").trim();
		recupNom = request.getParameter("nom").trim();
		recupPrenom = request.getParameter("prenom").trim();
		recupEmail = request.getParameter("email").trim();
		recupTelephone = request.getParameter("telephone").trim();
		recupRue = request.getParameter("rue").trim();
		recupCodePostal = request.getParameter("code_postal").trim();
		recupVille = request.getParameter("ville").trim();
		String recupAcienMotDePasse = request.getParameter("ancien_mot_de_passe").trim();
		recupMotDePasse = request.getParameter("mot_de_passe").trim();
		recupConfirmation = request.getParameter("confirmation").trim();

		try {
			unUtilisateur = new Utilisateur(recupPseudo, recupNom, recupPrenom, recupEmail, recupTelephone, recupRue, recupCodePostal, recupVille);
			request.setAttribute("utilisateur", unUtilisateur);
			unUtilisateur.setNoUtilisateur(noUtilisateur);
			//Si tout les champs sont remplis
			if (!recupTelephone.isEmpty() && !recupPseudo.isEmpty() && !recupNom.isEmpty() && !recupPrenom.isEmpty() && !recupEmail.isEmpty() && !recupRue.isEmpty() && !recupCodePostal.isEmpty() && !recupVille.isEmpty()) {
				if (mdp.equals(recupAcienMotDePasse)) {
					if (recupMotDePasse.equals(recupConfirmation)) {
						unUtilisateur.setPseudo(recupPseudo);
						unUtilisateur.setNom(recupNom);
						unUtilisateur.setPrenom(recupPrenom);
						unUtilisateur.setEmail(recupEmail);
						unUtilisateur.setTelephone(recupTelephone);
						unUtilisateur.setRue(recupRue);
						unUtilisateur.setCodePostal(recupCodePostal);
						unUtilisateur.setVille(recupVille);
						if (recupMotDePasse.isEmpty()) {
							recupMotDePasse = mdp;
						}
						unUtilisateur.setMotDePasse(recupMotDePasse);
						try {
							DAOFactory.getUtilisateurDAO().update(unUtilisateur);
							fowardListeEncheres(request, response);
						} catch (DALException e) {
							//auteur : Yann Le Douget
							sourceErreur = recupereSourceErreur(e.getMessage());
							if (sourceErreur.equals(recupPseudo)) {
								request.setAttribute("pseudoExistant", true);
								this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/Modifier_user.jsp").forward(request, response);
							} else if (sourceErreur.equals(recupEmail)){
								request.setAttribute("emailExistant", true);
								this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/Modifier_user.jsp").forward(request, response);
							} else if (sourceErreur.equals(recupTelephone)){
								request.setAttribute("telephoneExistant", true);
								this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/Modifier_user.jsp").forward(request, response);
							} else {
								throw e;
							}
						}
					} else {
						request.setAttribute("erreurConfirme", true);
						this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/Modifier_user.jsp").forward(request, response);
					}
				} else {
					request.setAttribute("erreurAncienMdp", true);
					this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/Modifier_user.jsp").forward(request, response);
				}
			} else {
				request.setAttribute("champsVides", true);
				this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/Modifier_user.jsp").forward(request, response);
			}
		} catch (BeanException e){
			request.setAttribute("erreurFormat", true);
			this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/Modifier_user.jsp").forward(request, response);
		}
	}

	/**
	 * Requete pour supprimer un utilisateur
	 * 
	 * @param request
	 * @param response
	 * @throws DALException 
	 * @throws IOException 
	 * @throws ServletException 
	 * @throws BeanException 
	 */
	private void supprimerUtilisateur(HttpServletRequest request, HttpServletResponse response) throws DALException, ServletException, IOException, BeanException {
		request.setAttribute("modifier", true);
		unUtilisateur = DAOFactory.getUtilisateurDAO().get(noUtilisateur);
		System.out.println();
		if (request.getParameter("ancien_mot_de_passe").equals(unUtilisateur.getMotDePasse())) {
				DAOFactory.getUtilisateurDAO().delete(unUtilisateur);
				this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/Se_connecter.jsp").forward(request,response);
		} else {
			request.setAttribute("mdp", true);
			recupUserWithoutPwd(request);
			this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/Modifier_user.jsp").forward(request, response);
		}
	}

	/**
	 * Requete pour se connecter
	 * 
	 * @param request
	 * @param response
	 * @throws DALException 
	 */
	private void seConnecter(HttpServletRequest request, HttpServletResponse response) throws Exception {

		recupPseudo = request.getParameter("identifiant").trim();
		recupMotDePasse = request.getParameter("mot_de_passe").trim();
		session = request.getSession();

		if (!recupPseudo.isEmpty() && !recupMotDePasse.isEmpty()) {
			if (recupPseudo.contains("@")) {
				// Recuperation de l'utilisateur par le mail
				unUtilisateur = DAOFactory.getUtilisateurDAO().getByMail(recupPseudo);
			} else {
				// Recuperation de l'utilisateur par le pseudo
				unUtilisateur = DAOFactory.getUtilisateurDAO().get(recupPseudo);
			}
			// Test du pseudo
			if(unUtilisateur != null) {
				// On vérifie que le compte n'a pas été supprimé.
				if (!unUtilisateur.isArchive()) {
					if (recupMotDePasse.equals(unUtilisateur.getMotDePasse())) {
						session.setAttribute("noUtilisateur", unUtilisateur.getNoUtilisateur());
						fowardListeEncheres(request, response);
					} else {
						request.setAttribute("erreurMotDePasse", true);
						this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/Se_connecter.jsp").forward(request, response);
					}
				} else {
					request.setAttribute("compteSupprimer", true);
					this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/Se_connecter.jsp").forward(request, response);
				}
			}else {
				request.setAttribute("erreurPseudo", true);
				this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/Se_connecter.jsp").forward(request, response);
			}
		} else {
			request.setAttribute("champsVides", true);
			this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/Se_connecter.jsp").forward(request, response);
		}
	}

	/**
	 * Recupere la valeur dupliquee dans le message d'erreur de la DAL
	 * @param message
	 * @return la valeur dupliquee
	 */
	private String recupereSourceErreur (String message) {
		String sourceErreur = null;
		int index = 0;
		index = message.indexOf("Valeur de clé dupliquée : (") + 27;
		if (index != -1) {
			sourceErreur = message.substring(index, message.length()-2);
		}
		return sourceErreur;

	}

	/**
	 * Insert l'utilisateur connecté dans la requete. Attribut : "utilisateur"
	 * @auteur : Yann Le Douget
	 * @param request
	 * @throws DALException
	 * @throws BeanException
	 */
	private void recupUserWithoutPwd (HttpServletRequest request) throws DALException, BeanException {
		unUtilisateur = DAOFactory.getUtilisateurDAO().get(noUtilisateur);
		//On efface le mot de passe pour éviter de le faire transiter sur le réseau
		unUtilisateur.setMotDePasse(null);
		request.setAttribute("utilisateur", unUtilisateur);
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
		List<Vente> ListeVentes = DAOFactory.getVenteDao().get();
		for (Vente vente : ListeVentes) {
			//On supprime les mots de passe des vendeurs.
			vente.getUtilisateur().setMotDePasse(null);
		}
		request.setAttribute("ListeCategorie", DAOFactory.getCategorieDao().get());
		request.setAttribute("ListeVentes", ListeVentes);
		this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/Liste_enchere.jsp").forward(request, response);
	}


}
