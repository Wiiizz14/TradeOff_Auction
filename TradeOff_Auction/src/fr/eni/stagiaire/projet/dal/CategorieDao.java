/**
 * 
 */
package fr.eni.stagiaire.projet.dal;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import fr.eni.stagiaire.projet.beans.Categorie;
import fr.eni.stagiaire.projet.utils.Parametre;

/**
 * @author Yann Le Douget
 *
 */
public class CategorieDao implements IDAL<Categorie> {
	
	private final String PROCEDURE_INSERT = Parametre.lireString("PROCEDURE_INSERT_CAT");
	private final String PROCEDURE_SELECT_ID = Parametre.lireString("PROCEDURE_SELECT_NO_CAT");
	private final String PROCEDURE_SELECT_LIBELLE = Parametre.lireString("PROCEDURE_SELECT_LIBELLE_CAT");
	private final String PROCEDURE_SELECT_ALL = Parametre.lireString("PROCEDURE_SELECT_ALL_CAT");
	private final String PROCEDURE_UPDATE = Parametre.lireString("PROCEDURE_UPDATE_CAT");
	private final String PROCEDURE_DELETE = Parametre.lireString("PROCEDURE_DELETE_CAT");
	private final String ID = Parametre.lireString("VARIABLE_NO_CAT");
	private final String LIBELLE = Parametre.lireString("VARIABLE_LIBELLE_CAT");
	private final String COL_ID = Parametre.lireString("COLONNE_NO_CAT");
	private final String COL_LIBELLE = Parametre.lireString("COLONNE_LIBELLE_CAT");

	@Override
	public boolean insert(Categorie item) throws DALException {
		boolean resultat = false;
		
		try (Connection cnx = AccesBDD.seConnecter()) {
			CallableStatement requete = cnx.prepareCall(
				"EXEC " + PROCEDURE_INSERT + " " + LIBELLE + " = ?, " + ID + " = ?");
			requete.setString(1, item.getLibelle());
			requete.registerOutParameter(2, Types.INTEGER);
			requete.executeUpdate();
			item.setNoCategorie(requete.getInt(2));
			resultat = item.getNoCategorie() != 0;
			requete.close();
			
		} catch (SQLException e) {
			throw new DALException("probleme du CallableStatement  - methode insert(Categorie item) " + this.getClass().getName() + "\r" + e.getMessage());
		}
		return resultat;
	}

	@Override
	public Categorie get(int noCategorie) throws DALException {
		Categorie categorie = null;
		try (Connection cnx = AccesBDD.seConnecter()) {
			CallableStatement requete = cnx.prepareCall(
				"EXEC " + PROCEDURE_SELECT_ID + " " + ID + " = ?");
			requete.setInt(1, noCategorie);
			ResultSet rs = requete.executeQuery();
			if (rs.next()) {
				categorie = itemBuilder(rs);
			}
		} catch (SQLException e) {
			throw new DALException("probleme du CallableStatement  - methode get(int id) " + this.getClass().getName() + "\r" + e.getMessage());
		}
		return categorie;
		
	}
	
	/**
	 * 
	 */
	public Categorie get(String libelle) throws DALException {
		Categorie categorie = null;
		try (Connection cnx = AccesBDD.seConnecter()) {
			System.out.println(libelle);
			CallableStatement requete = cnx.prepareCall(
				"EXEC " + PROCEDURE_SELECT_LIBELLE + " " + LIBELLE + " = ?");
			requete.setString(1, libelle);
			ResultSet rs = requete.executeQuery();
			if (rs.next()) {
				categorie = itemBuilder(rs);
			}
		} catch (SQLException e) {
			throw new DALException("probleme du CallableStatement  - methode get(String libelle) " + this.getClass().getName() + "\r" + e.getMessage());
		}
		return categorie;
		
	}

	@Override
	public List<Categorie> get() throws DALException {
		List<Categorie> listeDesCategories = new ArrayList<Categorie>();

		try (Connection cnx = AccesBDD.seConnecter()) {

			CallableStatement requete = cnx.prepareCall("EXEC " + PROCEDURE_SELECT_ALL);

			ResultSet rs = requete.executeQuery();

			while (rs.next()) {
				Categorie categorie = itemBuilder(rs);
				listeDesCategories.add(categorie);
			}

		} catch (SQLException e) {
			throw new DALException("probleme du CallableStatement - methode get() "+ this.getClass().getName() + "\r" +e.getMessage());
		}
		return listeDesCategories;
	}

	@Override
	public boolean update(Categorie item) throws DALException {
		boolean resultat = false;

		try (Connection cnx = AccesBDD.seConnecter()) {

			CallableStatement requete = cnx.prepareCall(
					"EXEC " + PROCEDURE_UPDATE + " " + ID + " = ?," + LIBELLE + " = ?");

			requete.setInt(1, item.getNoCategorie());
			requete.setString(2, item.getLibelle());
			resultat = requete.executeUpdate() == 1;

		} catch (SQLException e) {
			throw new DALException("probleme du CallableStatement - update(Categorie item) "+ this.getClass().getName() + "\r" +e.getMessage());
		}
		return resultat;
	}

	@Override
	public boolean delete(Categorie item) throws DALException {
		return delete(item.getNoCategorie());
	}

	@Override
	public boolean delete(int noCategorie) throws DALException {
		boolean resultat = false;

		try (Connection cnx = AccesBDD.seConnecter()) {
			CallableStatement requete = cnx.prepareCall("EXEC " + PROCEDURE_DELETE + " " + ID + " = ?");
			requete.setInt(1, noCategorie);
			resultat = requete.execute();

		} catch (SQLException e) {
			throw new DALException("probleme du CallableStatement  - methode delete(int noUtilisateur) "+e.getMessage());
		}
		return resultat;
	}

	@Override
	public Categorie itemBuilder(ResultSet rs) throws DALException {
		Categorie categorie = new Categorie();
		try {
			categorie.setLibelle(rs.getString(COL_LIBELLE));
			categorie.setNoCategorie(rs.getInt(COL_ID));
		} catch (SQLException e) {
			throw new DALException("probleme - methode itemBuilder " + this.getClass().getName() + "\r" + e.getMessage());
		}
		return categorie;
	}

}
