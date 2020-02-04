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

import fr.eni.stagiaire.projet.beans.BeanException;
import fr.eni.stagiaire.projet.beans.Enchere;
import fr.eni.stagiaire.projet.beans.Utilisateur;
import fr.eni.stagiaire.projet.beans.Vente;
import fr.eni.stagiaire.projet.utils.Parametre;

/**
 * @author yledouge2018
 *
 */
public class EnchereDao implements IDAL<Enchere> {

	private final String PROCEDURE_INSERT = Parametre.lireString("PROCEDURE_INSERT_ENCH");
	private final String PROCEDURE_SELECT_ID = Parametre.lireString("PROCEDURE_SELECT_NO_ENCH");
	private final String PROCEDURE_SELECT_ALL = Parametre.lireString("PROCEDURE_SELECT_ALL_ENCH");
	private final String PROCEDURE_UPDATE = Parametre.lireString("PROCEDURE_UPDATE_ENCH");
	private final String PROCEDURE_DELETE = Parametre.lireString("PROCEDURE_DELETE_ENCH");
	private final String PROCEDURE_RECH_ENCHERISSEUR = Parametre.lireString("PROCEDURE_RECH_BY_VENTE");
	private final String DATE = Parametre.lireString("VARIABLE_DATE_ENCH");
	private final String MONTANT = Parametre.lireString("VARIABLE_MONTANT_ENCH");
	private final String USER = Parametre.lireString("VARIABLE_USER_ENCH");
	private final String VENTE = Parametre.lireString("VARIABLE_VENTE_ENCH");
	private final String COL_DATE = Parametre.lireString("COL_DATE_ENCH");
	private final String COL_MONTANT = Parametre.lireString("COL_MONTANT_ENCH");
	private final String COL_USER = Parametre.lireString("COL_USER_ENCH");
	private final String COL_VENTE = Parametre.lireString("COL_VENTE_ENCH");
	private final String COL_ARCHIVE = Parametre.lireString("COL_ARCHIVE_ENCH");

	@Override
	public boolean insert(Enchere item) throws DALException {
		boolean resultat = false;
		
		try (Connection cnx = AccesBDD.seConnecter()) {
			CallableStatement requete = cnx.prepareCall(
				"EXEC " + PROCEDURE_INSERT + " " + DATE + " = ?, " + MONTANT + " = ?, " + USER + " = ?, " + VENTE + " = ?");
			requete.setDate(1, new java.sql.Date(item.getDateEnchere().getTime()));
			requete.setInt(2, item.getMontant());
			requete.setInt(3, item.getUtilisateur().getNoUtilisateur());
			requete.setInt(4, item.getVente().getNoVente());
			resultat = requete.executeUpdate() == 1;
			requete.close();
			
		} catch (SQLException e) {
			throw new DALException("probleme du CallableStatement  - methode insert(Enchere item) " + this.getClass().getName() + "\r" + e.getMessage());
		}
		return resultat;
	}

	/**
	 * Select par couple id
	 * 
	 * @param noUtilisateur
	 * @param noVente
	 * @return un item par son id
	 * @throws DALException
	 * @throws BeanException 
	 */
	public Enchere get(int noUtilisateur, int noVente) throws DALException, BeanException {
		Enchere enchere = null;
		try (Connection cnx = AccesBDD.seConnecter()) {
			CallableStatement requete = cnx.prepareCall(
				"EXEC " + PROCEDURE_SELECT_ID + " " + USER + " = ?," + VENTE + " = ?");
			requete.setInt(1, noUtilisateur);
			requete.setInt(2, noVente);
			ResultSet rs = requete.executeQuery();
			if (rs.next()) {
				enchere = itemBuilder(rs);
			}
			requete.close();
		} catch (SQLException e) {
			throw new DALException("probleme du CallableStatement  - methode get(int noUtilisateur, int noVente) " + this.getClass().getName() + "\r" + e.getMessage());
		}
		return enchere;
		
	}

	@Override
	public List<Enchere> get() throws DALException, BeanException {
		List<Enchere> listeDesEncheres = new ArrayList<Enchere>();

		try (Connection cnx = AccesBDD.seConnecter()) {

			CallableStatement requete = cnx.prepareCall("EXEC " + PROCEDURE_SELECT_ALL);

			ResultSet rs = requete.executeQuery();

			while (rs.next()) {
				Enchere Enchere = itemBuilder(rs);
				listeDesEncheres.add(Enchere);
			}
			
			requete.close();

		} catch (SQLException e) {
			throw new DALException("probleme du CallableStatement - methode get() "+ this.getClass().getName() + "\r" +e.getMessage());
		}
		return listeDesEncheres;
	}

	@Override
	public boolean update(Enchere item) throws DALException {
		boolean resultat = false;

		try (Connection cnx = AccesBDD.seConnecter()) {

			CallableStatement requete = cnx.prepareCall(
					"EXEC " + PROCEDURE_UPDATE + " " + DATE + " = ?," + MONTANT + " = ?," + USER + " = ?," + VENTE + " = ?");

			requete.setDate(1, new java.sql.Date(item.getDateEnchere().getTime()));
			requete.setInt(2, item.getMontant());
			requete.setInt(3, item.getUtilisateur().getNoUtilisateur());
			requete.setInt(4, item.getVente().getNoVente());
			resultat = requete.executeUpdate() == 1;
			requete.close();

		} catch (SQLException e) {
			throw new DALException("probleme du CallableStatement - update(Enchere item) "+ this.getClass().getName() + "\r" +e.getMessage());
		}
		return resultat;
	}

	@Override
	public boolean delete(Enchere item) throws DALException {
		return delete(item.getUtilisateur().getNoUtilisateur(), item.getVente().getNoVente());
	}

	/**
	 * Delete par couple id
	 * 
	 * @param noUtilisateur
	 * @param noVente
	 * @return true si le delete c'est bien passé
	 * @throws DALException
	 */
	public boolean delete(int noUtilisateur, int noVente) throws DALException {
		boolean resultat = false;

		try (Connection cnx = AccesBDD.seConnecter()) {
			CallableStatement requete = cnx.prepareCall("EXEC " + PROCEDURE_DELETE + " " + USER + " = ?," + VENTE + " = ?");
			requete.setInt(1, noUtilisateur);
			requete.setInt(2, noVente);
			resultat = requete.execute();
			requete.close();

		} catch (SQLException e) {
			throw new DALException("probleme du CallableStatement  - methode delete(int noUtilisateur, int noVente) "+e.getMessage());
		}
		return resultat;
	}
	
	public int getEncherisseur(int noVente) throws DALException {
		int resultat = 0;
		try (Connection cnx = AccesBDD.seConnecter()) {
			CallableStatement requete = cnx.prepareCall("EXEC " + PROCEDURE_RECH_ENCHERISSEUR + " " + VENTE + " = ?," + USER + " = ?");
			requete.setInt(1, noVente);
			requete.registerOutParameter(2, Types.INTEGER);
			requete.execute();
			resultat = requete.getInt(2);
			requete.close();
			
		} catch (SQLException e) {
			throw new DALException("probleme du CallableStatement  - methode getEncherisseur(int noVente) "+e.getMessage());
		}
		return resultat;
	}

	@Override
	public Enchere itemBuilder(ResultSet rs) throws DALException, BeanException {
		Enchere enchere = new Enchere();
		try {
			int noUser = rs.getInt(COL_USER);
			int noVente = rs.getInt(COL_VENTE);
			Utilisateur user = DAOFactory.getUtilisateurDAO().get(noUser);
			Vente vente = DAOFactory.getVenteDao().get(noVente);
			enchere.setDateEnchere(new java.util.Date(rs.getDate(COL_DATE).getTime()));
			enchere.setArchive(rs.getBoolean(COL_ARCHIVE));
			enchere.setUtilisateur(user);
			enchere.setVente(vente);
			enchere.setMontant(rs.getInt(COL_MONTANT));
		} catch (SQLException e) {
			throw new DALException("probleme - methode itemBuilder " + this.getClass().getName() + "\r" + e.getMessage());
		}
		return enchere;
	}

	/**
	 * @deprecated Inutilisable, une enchere est identifier par un couple utilisateur et vente.
	 */
	@Override
	@Deprecated
	public Enchere get(int id) throws DALException {
		return null;
	}

	/**
	 * @deprecated Inutilisable, une enchere est identifier par un couple utilisateur et vente.
	 */
	@Override
	@Deprecated
	public boolean delete(int id) throws DALException {
		return false;
	}

}
