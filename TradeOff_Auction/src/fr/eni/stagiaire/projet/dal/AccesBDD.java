package fr.eni.stagiaire.projet.dal;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * Classe pour réaliser la connexion à la BDD
 * @author Yann Le Douget
 *
 */
public class AccesBDD {
	public static Connection seConnecter() throws DALException {
		Connection cnx = null;
		InitialContext context = null;
		DataSource ds = null;
		
		//obtenir une reference sur le context initial
		try {
			context = new InitialContext();
		} catch (NamingException e) {
			throw new DALException("Erreur d'accés au contexte initial " + e.getMessage());
		}
		
		//rechercher la datasource dans l'annuaire du server
		try {
			ds = (DataSource) context.lookup("java:comp/env/ds/Troc");
		} catch (NamingException e) {
			throw new DALException("Objet introuvable dans l'annuaire " + e.getMessage());
		}
		
		//obtenir la connexion
		try {
			cnx = ds.getConnection();
			return cnx;
		} catch (SQLException e) {
			throw new DALException("impossible d'obtenir une connection " + e.getMessage());
		}		
	}
}
