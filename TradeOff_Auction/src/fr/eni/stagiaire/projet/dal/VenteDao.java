package fr.eni.stagiaire.projet.dal;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fr.eni.stagiaire.projet.beans.BeanException;
import fr.eni.stagiaire.projet.beans.Categorie;
import fr.eni.stagiaire.projet.beans.Utilisateur;
import fr.eni.stagiaire.projet.beans.Vente;
import fr.eni.stagiaire.projet.utils.Parametre;

/**
 * Classe pour le CRUD Vente
 * 
 * @author Thibault James et Alexis Rebiere
 *
 */
public class VenteDao implements IDAL<Vente> {

	private final String PROCEDURE_INSERT = Parametre.lireString("PROCEDURE_INSERT_AUCTION");
	private final String PROCEDURE_SELECT_NO_AUCTION = Parametre.lireString("PROCEDURE_SELECT_NO_AUCTION");
	private final String PROCEDURE_SELECT_ALL = Parametre.lireString("PROCEDURE_SELECT_ALL_AUCTION");
	
	//Rajouté 24.01
	private final String PROCEDURE_SELECT_ALL_BY_ID = Parametre.lireString("PROCEDURE_SELECT_ALL_AUCTION_BY_ID");
	private final String PROCEDURE_SELECT_ALL_BY_BIDDING = Parametre.lireString("PROCEDURE_SELECT_ALL_AUCTION_BY_BIDDING");
	private final String PROCEDURE_SELECT_ALL_WON_AUCTION = Parametre.lireString("PROCEDURE_SELECT_ALL_WON_AUCTION_BY_ID");
	private final String PROCEDURE_SELECT_ALL_OTHERS = Parametre.lireString("PROCEDURE_SELECT_ALL_OTHERS_BY_ID");
	private final String PROCEDURE_SELECT_ALL_RSRCH_CATE = Parametre.lireString("PROCEDURE_SELECT_ALL_BY_CATE");
	private final String PROCEDURE_SELECT_ALL_RSRCH = Parametre.lireString("PROCEDURE_SELECT_ALL_BY_RSRCH");
	private final String PROCEDURE_UPDATE = Parametre.lireString("PROCEDURE_UPDATE_AUCTION");
	private final String PROCEDURE_DELETE = Parametre.lireString("PROCEDURE_DELETE_AUCTION");
	
	private final String NO_AUCTION = Parametre.lireString("VARIABLE_NO_AUCTION");
	private final String NOM_ARTICLE = Parametre.lireString("VARIABLE_NOM_ARTICLE");
	private final String DESCRIPTION = Parametre.lireString("VARIABLE_DESCRIPTION");
	private final String DATEFIN_ENCHERE = Parametre.lireString("VARIABLE_DATEFIN_ENCHERE");
	private final String PRIX_INIT = Parametre.lireString("VARIABLE_PRIX_INIT");
	private final String PRIX_VENTE = Parametre.lireString("VARIABLE_PRIX_VENTE");
	private final String NO_UTILISATEUR = Parametre.lireString("VARIABLE_NO_UTILISATEUR");
	private final String RUE = Parametre.lireString("VARIABLE_RUE_RET");
	private final String CP = Parametre.lireString("VARIABLE_CODE_POSTAL_RET");
	private final String VILLE = Parametre.lireString("VARIABLE_VILLE_RET");
	private final String LIBELLE = Parametre.lireString("VARIABLE_LIBELLE");
	private final String NO_CATEGORIE = Parametre.lireString("VARIABLE_NO_CATEGORIE");
	private final String RETOUR = Parametre.lireString("VARIABLE_RETOUR");
	private final String COL_ID = Parametre.lireString("COLONNE_NO_VENTE");
	private final String COL_ARTICLE = Parametre.lireString("COLONNE_ARTICLE_VENTE");
	private final String COL_DESC = Parametre.lireString("COLONNE_DESC_VENTE");
	private final String COL_DATE_FIN = Parametre.lireString("COLONNE_DATE_FIN_VENTE");
	private final String COL_PRIX_INI = Parametre.lireString("COLONNE_PRIX_INI_VENTE");
	private final String COL_PRIX_VENTE = Parametre.lireString("COLONNE_PRIX_VENTE_VENTE");
	private final String COL_RUE = Parametre.lireString("COLONNE_RUE_RET");
	private final String COL_CP = Parametre.lireString("COLONNE_CODE_POSTAL_RET");
	private final String COL_VILLE = Parametre.lireString("COLONNE_VILLE_RET");
	private final String COL_NO_VENDEUR = Parametre.lireString("COLONNE_NO_UTILISATEUR_VENTE");
	private final String COL_NO_CAT = Parametre.lireString("COLONNE_NO_CAT_VENTE");
	private final String COL_ARCHIVE = Parametre.lireString("COLONNE_ARCHIVE_VENTE");

	// Rajouté le 24/01
	private final String RECHERCHE = Parametre.lireString("VARIABLE_RECHERCHE");
	/**
	 * Méthode pour créer une vente
	 * 
	 * @author Thibault James
	 * 
	 * @param item
	 * @return boolean resultat
	 * @throws DALException
	 * @Override
	 */
	@Override
	public boolean insert(Vente item) throws DALException {

		boolean resultat = false;

		try (Connection cnx = AccesBDD.seConnecter()) {

			CallableStatement requete = cnx.prepareCall("EXEC " + PROCEDURE_INSERT + " " + NOM_ARTICLE + " = ?, "
					+ DESCRIPTION + " = ?, " + DATEFIN_ENCHERE + " = ?, " + PRIX_INIT + " = ?, " + PRIX_VENTE + " = ?, "
					+ NO_UTILISATEUR + " = ?, " + NO_CATEGORIE + " = ?, " + RUE + " = ?, " + CP + " = ?, " + VILLE + " = ?, " + RETOUR + " = ?");
			// Voir pour l'insert de la photo et adresse de retrait

			requete.setString(1, item.getNomArticle());
			requete.setString(2, item.getDescription());
			requete.setDate(3, new java.sql.Date(item.getDateFinEncheres().getTime()));
			requete.setInt(4, item.getPrixInitial());
			requete.setInt(5, item.getPrixVente());
			requete.setInt(6, item.getUtilisateur().getNoUtilisateur());
			requete.setInt(7, item.getCategorie().getNoCategorie());
			requete.setString(8, item.getRueRetrait());
			requete.setString(9, item.getCodePostalRetrait());
			requete.setString(10, item.getVilleRetrait());
			requete.registerOutParameter(11, Types.INTEGER);
			requete.executeUpdate();

			item.setNoVente(requete.getInt(11));
			resultat = item.getNoVente() != 0;
			requete.close();

		} catch (SQLException e) {
			throw new DALException("probleme du CallableStatement  - methode insert(Vente item) " + e.getMessage());
		}
		return resultat;
	}

	/**
	 * Méthode pour selectionner une vente
	 * 
	 * @author Thibault James
	 * 
	 * @param id
	 * @return un objet vente
	 * @throws DALException
	 * @throws BeanException 
	 * @Override
	 */
	@Override
	public Vente get(int id) throws DALException, BeanException {

		Vente uneVente = null;

		try (Connection cnx = AccesBDD.seConnecter()) {

			CallableStatement requete = cnx.prepareCall("EXEC " + PROCEDURE_SELECT_NO_AUCTION + " " + NO_AUCTION + " = ?");
			requete.setInt(1, id);
			ResultSet rs = requete.executeQuery();
			if (rs.next()) {
				uneVente = itemBuilder(rs);
			}
			requete.close();
		} catch (SQLException e) {
			throw new DALException("probleme du CallableStatement  - methode get(int id) " + e.getMessage());
		}
		return uneVente;
	}

	/**
	 * Méthode pour lister l'intégralité des ventes.
	 * 
	 * @author Thibault James
	 * 
	 * @return listeDesVentes
	 * @throws DALException
	 * @throws BeanException 
	 * @Override
	 */
	@Override
	public List<Vente> get() throws DALException, BeanException {

		List<Vente> listeDesVentes = new ArrayList<Vente>();

		try (Connection cnx = AccesBDD.seConnecter()) {

			CallableStatement requete = cnx.prepareCall("EXEC " + PROCEDURE_SELECT_ALL);

			ResultSet rs = requete.executeQuery();

			while (rs.next()) {
				Vente uneVente = itemBuilder(rs);
				listeDesVentes.add(uneVente);
			}
			requete.close();
		} catch (SQLException e) {
			throw new DALException("probleme du CallableStatement  - methode get() " + e.getMessage());
		}
		return listeDesVentes;
	}
	
	/**
	 * Cette méthode permet de selectionner dans la base de données toutes les ventes affiliées à
	 * un identifiant utilisateur
	 * 
	 * rajouté 24.01
	 * 
	 * @author Alexis Rebiere
	 * 
	 * @param id
	 * @return listeDesVentes
	 * @throws DALException
	 * @throws BeanException 
	 */
	
	public List<Vente> getListeVentes(int id) throws DALException, BeanException {
		
		List<Vente> listeDesVentes = new ArrayList<Vente>();
		
		try (Connection cnx = AccesBDD.seConnecter()) {

			CallableStatement requete = cnx.prepareCall("EXEC " + PROCEDURE_SELECT_ALL_BY_ID + " " + NO_UTILISATEUR + " = ?");

			requete.setInt(1, id);
			
			ResultSet rs = requete.executeQuery();
			
			
			while (rs.next()) {
				Vente uneVente = itemBuilder(rs);
				listeDesVentes.add(uneVente);
			}
			requete.close();
			return listeDesVentes;
			
		} catch (SQLException e) {
			throw new DALException("Erreur dans la méthode getList(int id) - " + e.getMessage());
		}
		
	}
	
	/**
	 * Cette méthode permet de selectionner dans la base de données toutes les ventes affiliées é
	 * un utilisateur
	 * 
	 * rajouté 24.01
	 * 
	 * @author Alexis Rebiere
	 * 
	 * @param user
	 * @return listeDesVentes
	 * @throws DALException
	 * @throws BeanException 
	 */
	
	public List<Vente> getListeVentes(Utilisateur user) throws DALException, BeanException {
		
		List<Vente> listeDesVentes = new ArrayList<Vente>();
		
		try (Connection cnx = AccesBDD.seConnecter()) {

			CallableStatement requete = cnx.prepareCall("EXEC " + PROCEDURE_SELECT_ALL_BY_BIDDING + " " + NO_UTILISATEUR + " = ?");

			requete.setInt(1, user.getNoUtilisateur());
			
			ResultSet rs = requete.executeQuery();
			
			while (rs.next()) {
				Vente uneVente = itemBuilder(rs);
				listeDesVentes.add(uneVente);
			}
			requete.close();
			return listeDesVentes;
			
		} catch (SQLException e) {
			throw new DALException("Erreur dans la méthode getList(Utilisateur user) - " + e.getMessage());
		}
		
	}
	
	/**
	 * Cette méthode permet de récupérer via son identifiant la liste des ventes 
	 * sur lesquelles l'utilisateur a enchéri  
	 * 
	 * rajouté 24.01
	 * 
	 * @author Alexis Rebiere
	 * 
	 * @param user
	 * @return listeDesVentes
	 * @throws DALException
	 * @throws BeanException 
	 */
	
	public List<Vente> getListeEncheres(int id) throws DALException, BeanException {
		
		List<Vente> listeDesVentes = new ArrayList<Vente>();
		
		try (Connection cnx = AccesBDD.seConnecter()) {

			CallableStatement requete = cnx.prepareCall("EXEC " + PROCEDURE_SELECT_ALL_BY_BIDDING + " " + NO_UTILISATEUR + " = ?");

			requete.setInt(1, id);
			
			ResultSet rs = requete.executeQuery();
			
			while (rs.next()) {
				Vente uneVente = itemBuilder(rs);
				listeDesVentes.add(uneVente);
			}
			requete.close();
			return listeDesVentes;
			
		} catch (SQLException e) {
			throw new DALException("Erreur dans la méthode getListeEncheres(Utilisateur user) - " + e.getMessage());
		}
		
	}
	
	/**
	 * Cette méthode permet de récupérer la liste des ventes sur lesquelles l'utilisateur a 
	 * enchéri
	 * 
	 * rajouté 24.01
	 * 
	 * @author Alexis Rebiere
	 * 
	 * @param user
	 * @return listeDesVentes
	 * @throws DALException
	 * @throws BeanException 
	 */
	
	public List<Vente> getListeEncheres(Utilisateur user) throws DALException, BeanException {
		
		return getListeEncheres(user.getNoUtilisateur());
		
	}

	/**
	 * Cette méthode permet de lister toutes les acquisitions d'un utilisateur en fonction de son id
	 * 
	 * @author Alexis Rebiere
	 * 
	 * @param id
	 * @return listeAcquis
	 * @throws DALException
	 * @throws BeanException 
	 */
	public List<Vente> getListeAcquis(int id) throws DALException, BeanException {
		
		List<Vente> listeAcquis = new ArrayList<Vente>();
		Vente venteTampon;
		
		try (Connection cnx = AccesBDD.seConnecter()) {
			
			CallableStatement requete = cnx.prepareCall("EXEC " + PROCEDURE_SELECT_ALL_WON_AUCTION 
																+ " " + NO_UTILISATEUR + " = ?" );
			
			requete.setInt(1, id);
			
			ResultSet rs = requete.executeQuery();
			if (rs.next()) {
				venteTampon = itemBuilder(rs);
				listeAcquis.add(venteTampon);
			}
			requete.close();
		} catch (SQLException e) {
			throw new DALException("Erreur dans la methode getListeAcquis - " + e.getMessage());
		}

		return listeAcquis;
		
	}
	
	/**
	 * Cette méthode permet de récupérer la liste des ventes remportées par un utilisateur
	 * 
	 * @author Alexis Rebiere
	 * 
	 * @param user
	 * @return listeAcquis
	 * @throws DALException
	 * @throws BeanException 
	 */
	public List<Vente> getListeAcquis(Utilisateur user) throws DALException, BeanException {
		
		return getListeAcquis(user.getNoUtilisateur());
		
	}
	
	/**
	 * Cette méthode permet de récupérer la liste des ventes qui n'ont pas été publiées par
	 * l'utilisateur et sur lesquelles l'utilisateur n'a pas enchéri.
	 * 
	 * @author Alexis Rebiere
	 * 
	 * @param id
	 * @return listeAutres
	 * @throws DALException
	 * @throws BeanException 
	 */
	
	public List<Vente> getListeAutres(int id) throws DALException, BeanException {
		
		List<Vente> listeAutres = new ArrayList<Vente>();
		Vente venteTampon;
		
		try (Connection cnx = AccesBDD.seConnecter()) {

			CallableStatement requete = cnx.prepareCall("EXEC " + PROCEDURE_SELECT_ALL_OTHERS + " " + NO_UTILISATEUR + " = ?");

			requete.setInt(1, id);
			ResultSet rs = requete.executeQuery();
			while (rs.next()) {
				venteTampon = itemBuilder(rs);
				listeAutres.add(venteTampon);
			}
			requete.close();
		} catch (SQLException e) {
			throw new DALException("probleme du CallableStatement  - methode get(String pseudo) "+e.getMessage());
		}
		return listeAutres;
	}
		
	/**
	 * Cette méthode permet de récupérer la liste des ventes qui n'ont pas été publiées par
	 * l'utilisateur et sur lesquelles l'utilisateur n'a pas enchéri.
	 * 
	 * @author Alexis Rebiere
	 * 
	 * @param id
	 * @return listeAutres
	 * @throws DALException
	 * @throws BeanException 
	 */
	
	public List<Vente> getListeAutres(Utilisateur user) throws DALException, BeanException {
		
		return getListeAutres(user.getNoUtilisateur());
	}
	
	public List<Vente> getListeRC(String recherche, String categorie) throws DALException, BeanException {
		
		List<Vente> listeRC = new ArrayList<Vente>();
		Vente venteTampon;
		try (Connection cnx = AccesBDD.seConnecter()){
			
			CallableStatement requete = cnx.prepareCall("EXEC " + PROCEDURE_SELECT_ALL_RSRCH_CATE
																+ " " + LIBELLE + " = ?,"
																+ " " + RECHERCHE + " = ?");
			
			requete.setString(1, categorie);
			requete.setString(2, recherche);
			
			ResultSet rs = requete.executeQuery();
			while (rs.next()) {
				venteTampon = itemBuilder(rs);
				listeRC.add(venteTampon);
			}
			requete.close();
		} catch (SQLException e) {
			throw new DALException("Probleme dans la méthode getListeRC - " + e.getMessage());
		}
		return listeRC;
	}
	
	public List<Vente> getListeR(String recherche) throws DALException, BeanException {
		
		List<Vente> listeRC = new ArrayList<Vente>();
		Vente venteTampon;
		try (Connection cnx = AccesBDD.seConnecter()){
			
			CallableStatement requete = cnx.prepareCall("EXEC " + PROCEDURE_SELECT_ALL_RSRCH
					+ " " + RECHERCHE + " = ?");
			
			requete.setString(1, recherche);
			
			ResultSet rs = requete.executeQuery();
			while (rs.next()) {
				venteTampon = itemBuilder(rs);
				listeRC.add(venteTampon);
			}
			requete.close();
		} catch (SQLException e) {
			throw new DALException("Probleme dans la méthode getListeRC - " + e.getMessage());
		}
		return listeRC;
	}
	
	/**
	 * @auteur : Yann Le Douget
	 */
	@Override
	public boolean update(Vente item) throws DALException {
		boolean resultat = false;

		try (Connection cnx = AccesBDD.seConnecter()) {

			CallableStatement requete = cnx.prepareCall(
					"EXEC " + PROCEDURE_UPDATE + " " + NOM_ARTICLE + " = ?, "
							+ DESCRIPTION + " = ?, " + DATEFIN_ENCHERE + " = ?, " + PRIX_INIT + " = ?, " + PRIX_VENTE + " = ?, "
							+ NO_UTILISATEUR + " = ?, " + NO_CATEGORIE + " = ?, " + NO_AUCTION + " = ?");

					requete.setString(1, item.getNomArticle());
					requete.setString(2, item.getDescription());
					requete.setDate(3, new java.sql.Date(item.getDateFinEncheres().getTime()));
					requete.setInt(4, item.getPrixInitial());
					requete.setInt(5, item.getPrixVente());
					requete.setInt(6, item.getUtilisateur().getNoUtilisateur());
					requete.setInt(7, item.getCategorie().getNoCategorie());
					requete.setInt(8, item.getNoVente());
					resultat=requete.executeUpdate()==1;
					requete.close();
			
		} catch (SQLException e) {
			throw new DALException("probleme du CallableStatement - update(Vente item) "+ this.getClass().getName() + "\r" +e.getMessage());
		}
		return resultat;
	}

	/**
	 * Méthode supprimant en base de données la vente passée en paramètre de méthode
	 * 
	 * @author Thibault James
	 * 
	 * @param objet
	 *            Vente
	 * @return item.getNoVente
	 * @throws DALException
	 * @Override
	 */
	@Override
	public boolean delete(Vente item) throws DALException {
		return delete(item.getNoVente());
	}

	/**
	 * Méthode supprimant en base de données la vente en fonction de son numéro (noVente)
	 * 
	 * @author Thibault James
	 * 
	 * @param noVente
	 * @return boolean resultat
	 * @throws DALException
	 * @Override
	 */
	@Override
	public boolean delete(int noVente) throws DALException {

		boolean resultat = false;

		try (Connection cnx = AccesBDD.seConnecter()) {

			CallableStatement requete = cnx.prepareCall("EXEC " + PROCEDURE_DELETE + " " + NO_AUCTION + " = ?");
			requete.setInt(1, noVente);
			resultat = requete.execute();
			requete.close();
		} catch (SQLException e) {
			throw new DALException("probleme du CallableStatement  - methode delete(int noVente) " + e.getMessage());
		}
		return resultat;
	}

	/**
	 * Itembuilder de l'objet Vente
	 * 
	 * @param rs
	 * @return une vente
	 * @throws DALException
	 * @throws BeanException 
	 * @Override
	 */
	@Override
	public Vente itemBuilder(ResultSet rs) throws DALException, BeanException {

		Vente uneVente = new Vente();

		try {
			int noUser = rs.getInt(COL_NO_VENDEUR);
			int noCat = rs.getInt(COL_NO_CAT);
			Utilisateur unUtilisateur = DAOFactory.getUtilisateurDAO().get(noUser);
			Categorie uneCategorie = DAOFactory.getCategorieDao().get(noCat);

			uneVente.setNoVente(rs.getInt(COL_ID));
			uneVente.setNomArticle(rs.getString(COL_ARTICLE));
			uneVente.setDescription(rs.getString(COL_DESC));
			uneVente.setDateFinEncheres(new Date(rs.getDate(COL_DATE_FIN).getTime()));
			uneVente.setPrixInitial(rs.getInt(COL_PRIX_INI));
			uneVente.setPrixVente(rs.getInt(COL_PRIX_VENTE));
			uneVente.setRueRetrait(rs.getString(COL_RUE));
			uneVente.setCodePostalRetrait(rs.getString(COL_CP));
			uneVente.setVilleRetrait(rs.getString(COL_VILLE));
			uneVente.setArchive(rs.getBoolean((COL_ARCHIVE)));
			uneVente.setUtilisateur(unUtilisateur);
			uneVente.setCategorie(uneCategorie);
		} catch (SQLException e) {
			throw new DALException("probleme - méthode itemBuilder " + e.getMessage());
		}
		return uneVente;
	}
}
