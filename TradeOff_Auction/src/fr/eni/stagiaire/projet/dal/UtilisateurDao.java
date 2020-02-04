package fr.eni.stagiaire.projet.dal;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import fr.eni.stagiaire.projet.utils.Parametre;
import fr.eni.stagiaire.projet.dal.DALException;
import fr.eni.stagiaire.projet.beans.BeanException;
import fr.eni.stagiaire.projet.beans.Utilisateur;

/**
 * Classe pour le CRUD Utilisateur
 * 
 * @author tjames2018 et Yann Le Douget
 *
 */
public class UtilisateurDao implements IDAL<Utilisateur> {

	private final String PROCEDURE_INSERT = Parametre.lireString("PROCEDURE_INSERT_USER");
	private final String PROCEDURE_SELECT_NO_USER = Parametre.lireString("PROCEDURE_SELECT_NO_USER");
	private final String PROCEDURE_SELECT_ALL = Parametre.lireString("PROCEDURE_SELECT_ALL_USER");
	private final String PROCEDURE_SELECT_PSEUDO = Parametre.lireString("PROCEDURE_SELECT_PSEUDO_USER");
	private final String PROCEDURE_SELECT_EMAIL = Parametre.lireString("PROCEDURE_SELECT_EMAIL_USER");
	private final String PROCEDURE_UPDATE = Parametre.lireString("PROCEDURE_UPDATE_USER");
	private final String PROCEDURE_DELETE = Parametre.lireString("PROCEDURE_DELETE_USER");
	private final String NO_USER = Parametre.lireString("VARIABLE_NO_USER");
	private final String PSEUDO = Parametre.lireString("VARIABLE_PSEUDO");
	private final String NOM = Parametre.lireString("VARIABLE_NOM");
	private final String PRENOM = Parametre.lireString("VARIABLE_PRENOM");
	private final String EMAIL = Parametre.lireString("VARIABLE_EMAIL");
	private final String TELEPHONE = Parametre.lireString("VARIABLE_TELEPHONE");
	private final String RUE = Parametre.lireString("VARIABLE_RUE");
	private final String CODE_POSTAL = Parametre.lireString("VARIABLE_CODE_POSTAL");
	private final String VILLE = Parametre.lireString("VARIABLE_VILLE");
	private final String MOT_DE_PASSE = Parametre.lireString("VARIABLE_MOT_DE_PASSE");
	private final String CREDIT = Parametre.lireString("VARIABLE_CREDIT");
	private final String ADMINISTRATEUR = Parametre.lireString("VARIABLE_ADMINISTRATEUR");
	private final String RETOUR = Parametre.lireString("VARIABLE_RETOUR");
	private final String COL_NO_UTILISATEUR = Parametre.lireString("COLONNE_NO_UTILISATEUR");
	private final String COL_PSEUDO = Parametre.lireString("COLONNE_PSEUDO");
	private final String COL_NOM = Parametre.lireString("COLONNE_NOM");
	private final String COL_PRENOM = Parametre.lireString("COLONNE_PRENOM");
	private final String COL_EMAIL = Parametre.lireString("COLONNE_EMAIL");
	private final String COL_TELEPHONE = Parametre.lireString("COLONNE_TELEPHONE");
	private final String COL_RUE = Parametre.lireString("COLONNE_RUE");
	private final String COL_CODE_POSTAL = Parametre.lireString("COLONNE_CODE_POSTAL");
	private final String COL_VILLE = Parametre.lireString("COLONNE_VILLE");
	private final String COL_MOT_DE_PASSE = Parametre.lireString("COLONNE_MOT_DE_PASSE");
	private final String COL_CREDIT = Parametre.lireString("COLONNE_CREDIT");
	private final String COL_ADMINISTRATEUR = Parametre.lireString("COLONNE_ADMINISTRATEUR");
	private final String COL_ARCHIVE = Parametre.lireString("COLONNE_ARCHIVE");

	@Override
	public boolean insert(Utilisateur item) throws DALException {

		boolean resultat = false;

		try (Connection cnx = AccesBDD.seConnecter()) {

			CallableStatement requete = cnx.prepareCall(
					"EXEC " + PROCEDURE_INSERT + " " + PSEUDO + " = ?, " + NOM + " = ?, " + PRENOM + " = ?, " + EMAIL + " = ?, " + TELEPHONE + " = ?, " + RUE + " = ?, " + CODE_POSTAL + " = ?, " + VILLE + " = ? , " + MOT_DE_PASSE + " = ?, " + CREDIT + " = ?, " + ADMINISTRATEUR + " = ?, " + RETOUR + " = ?");

			requete.setString(1, item.getPseudo());
			requete.setString(2, item.getNom());
			requete.setString(3, item.getPrenom());
			requete.setString(4, item.getEmail());
			requete.setString(5, item.getTelephone());
			requete.setString(6, item.getRue());
			requete.setString(7, item.getCodePostal());
			requete.setString(8, item.getVille());
			requete.setString(9, item.getMotDePasse());
			requete.setInt(10, item.getCredit());
			requete.setBoolean(11, item.isAdministrateur());
			requete.registerOutParameter(12, Types.INTEGER);
			requete.executeUpdate();

			item.setNoUtilisateur(requete.getInt(12));
			resultat = item.getNoUtilisateur() != 0;
			requete.close();

		} catch (SQLException e) {
			throw new DALException("probleme du CallableStatement  - methode insert(Utilisateur item) "+e.getMessage());
		}
		return resultat;
	}

	@Override
	public Utilisateur get(int noUtilisateur) throws DALException, BeanException {

		Utilisateur unUtilisateur = null;

		try (Connection cnx = AccesBDD.seConnecter()) {

			CallableStatement requete = cnx.prepareCall("EXEC " + PROCEDURE_SELECT_NO_USER + " " + NO_USER + " = ?");

			requete.setInt(1, noUtilisateur);
			ResultSet rs = requete.executeQuery();
			if (rs.next()) {
				unUtilisateur = itemBuilder(rs);
			}

		} catch (SQLException e) {
			throw new DALException("probleme du CallableStatement  - methode get(int noUtilisateur) "+e.getMessage());
		}
		return unUtilisateur;
	}
	
	/**
	 * Select un utilisateur par son pseudo
	 * @param pseudo
	 * @return un objet utilisateur
	 * @throws DALException
	 * @throws BeanException 
	 */
	public Utilisateur get(String pseudo) throws DALException, BeanException {

		Utilisateur unUtilisateur = null;

		try (Connection cnx = AccesBDD.seConnecter()) {

			CallableStatement requete = cnx.prepareCall("EXEC " + PROCEDURE_SELECT_PSEUDO + " " + PSEUDO + " = ?");

			requete.setString(1, pseudo);
			ResultSet rs = requete.executeQuery();
			if (rs.next()) {
				unUtilisateur = itemBuilder(rs);
			}

		} catch (SQLException e) {
			throw new DALException("probleme du CallableStatement  - methode get(String pseudo) "+e.getMessage());
		}
		return unUtilisateur;
	}
	
	/**
	 * Select un utilisateur par son pseudo
	 * @param pseudo
	 * @return un objet utilisateur
	 * @throws DALException
	 * @throws BeanException 
	 */
	public Utilisateur getByMail(String mail) throws DALException, BeanException {
		
		Utilisateur unUtilisateur = null;
		
		try (Connection cnx = AccesBDD.seConnecter()) {
			
			CallableStatement requete = cnx.prepareCall("EXEC " + PROCEDURE_SELECT_EMAIL + " " + EMAIL + " = ?");
			
			requete.setString(1, mail);
			ResultSet rs = requete.executeQuery();
			if (rs.next()) {
				unUtilisateur = itemBuilder(rs);
			}
			
		} catch (SQLException e) {
			throw new DALException("probleme du CallableStatement  - methode get(String pseudo) "+e.getMessage());
		}
		return unUtilisateur;
	}

	@Override
	public List<Utilisateur> get() throws DALException, BeanException {

		List<Utilisateur> listeDesUtilisateurs = new ArrayList<Utilisateur>();

		try (Connection cnx = AccesBDD.seConnecter()) {

			CallableStatement requete = cnx.prepareCall("EXEC " + PROCEDURE_SELECT_ALL);

			ResultSet rs = requete.executeQuery();

			while (rs.next()) {
				Utilisateur unUtilisateur = itemBuilder(rs);
				listeDesUtilisateurs.add(unUtilisateur);
			}

		} catch (SQLException e) {
			throw new DALException("probleme du CallableStatement  - methode get() "+e.getMessage());
		}
		return listeDesUtilisateurs;
	}

	@Override
	public boolean update(Utilisateur item) throws DALException {

		boolean resultat = false;

		try (Connection cnx = AccesBDD.seConnecter()) {

			CallableStatement requete = cnx.prepareCall(
					"EXEC " + PROCEDURE_UPDATE + " " + PSEUDO + " = ?, " + NOM + " = ?, " + PRENOM + " = ?, " + EMAIL + " = ?, " + TELEPHONE + " = ?, " + RUE + " = ?, " + CODE_POSTAL + " = ?, " + VILLE + " = ?, " + MOT_DE_PASSE + " = ?, " + CREDIT + " = ?, " + ADMINISTRATEUR + " = ?, " + NO_USER + " = ?");

			requete.setString(1, item.getPseudo());
			requete.setString(2, item.getNom());
			requete.setString(3, item.getPrenom());
			requete.setString(4, item.getEmail());
			requete.setString(5, item.getTelephone());
			requete.setString(6, item.getRue());
			requete.setString(7, item.getCodePostal());
			requete.setString(8, item.getVille());
			requete.setString(9, item.getMotDePasse());
			requete.setInt(10, item.getCredit());
			requete.setBoolean(11, item.isAdministrateur());
			requete.setInt(12, item.getNoUtilisateur());
			resultat = requete.executeUpdate() == 1;

		} catch (SQLException e) {
			throw new DALException("probleme du CallableStatement  - methode update(Utilisateur item) "+e.getMessage());
		}
		return resultat;
	}

	@Override
	public boolean delete(Utilisateur item) throws DALException {
		return delete(item.getNoUtilisateur());
	}

	@Override
	public boolean delete(int noUtilisateur) throws DALException {
		boolean resultat = false;

		try (Connection cnx = AccesBDD.seConnecter()) {
			CallableStatement requete = cnx.prepareCall("EXEC " + PROCEDURE_DELETE + " " + NO_USER + " = ?");
			requete.setInt(1, noUtilisateur);
			resultat = requete.execute();

		} catch (SQLException e) {
			throw new DALException("probleme du CallableStatement  - methode delete(int noUtilisateur) "+e.getMessage());
		}
		return resultat;
	}

	@Override
	public Utilisateur itemBuilder(ResultSet rs) throws DALException, BeanException {

		Utilisateur unUtilisateur = new Utilisateur();

		try {
			unUtilisateur.setNoUtilisateur(rs.getInt(COL_NO_UTILISATEUR));
			unUtilisateur.setPseudo(rs.getString(COL_PSEUDO));
			unUtilisateur.setNom(rs.getString(COL_NOM));
			unUtilisateur.setPrenom(rs.getString(COL_PRENOM));
			unUtilisateur.setEmail(rs.getString(COL_EMAIL));
			unUtilisateur.setTelephone(rs.getString(COL_TELEPHONE));
			unUtilisateur.setRue(rs.getString(COL_RUE));
			unUtilisateur.setCodePostal(rs.getString(COL_CODE_POSTAL));
			unUtilisateur.setVille(rs.getString(COL_VILLE));
			unUtilisateur.setMotDePasse(rs.getString(COL_MOT_DE_PASSE));
			unUtilisateur.setCredit(rs.getInt(COL_CREDIT));
			unUtilisateur.setAdministrateur(rs.getBoolean(COL_ADMINISTRATEUR));
			unUtilisateur.setArchive(rs.getBoolean(COL_ARCHIVE));
		} catch (SQLException e) {
			throw new DALException("probleme - methode itemBuilder " + this.getClass().getName() + "\r" + e.getMessage());
		}

		return unUtilisateur;
	}
}
