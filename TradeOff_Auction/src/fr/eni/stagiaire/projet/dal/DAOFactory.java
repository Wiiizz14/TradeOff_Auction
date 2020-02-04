package fr.eni.stagiaire.projet.dal;

/**
 * Factory pour choix de la DAO correspondante (Utilisateur/Administrateur)
 * 
 * @author tjames2018
 *
 */
public class DAOFactory {
	
	/**
	 * 
	 * @return Une instance de la DAO Utilisateur
	 */
	public static UtilisateurDao getUtilisateurDAO(){
		
		return new UtilisateurDao();
		
	}
	
	/**
	 * 
	 * @return Une instance de la DAO Vente
	 */
	public static VenteDao getVenteDao() {
		return new VenteDao();
	}
	
	/**
	 * 
	 * @return Une instance de la DAO Categorie
	 */
	public static CategorieDao getCategorieDao() {
		return new CategorieDao();
	}
	
	/**
	 * 
	 * @return Une instance de la DAO Enchere
	 */
	public static EnchereDao getEnchereDao() {
		return new EnchereDao();
	}
	
}
