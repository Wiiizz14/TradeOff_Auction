package fr.eni.stagiaire.projet.dal;

import java.sql.ResultSet;
import java.util.List;

import fr.eni.stagiaire.projet.beans.BeanException;

/**
 * Interface pour la BDD
 * 
 * @author tjames2018 et Yann Le Douget
 *
 * @param <T>
 */
public interface IDAL<T> {

	/**
	 * Méthode pour l'insert d'un item.
	 * Mets à jour l'attribut id. 
	 * 
	 * @param item
	 * @throws DALException
	 * @return true si l'insertion c'est bien passée.
	 */
	boolean insert(T item) throws DALException;

	/**
	 * Select par id
	 * 
	 * @param id
	 * @throws DALException
	 * @return un item par son id
	 * @throws BeanException 
	 */
	T get(int id) throws DALException, BeanException;

	/**
	 * Select All
	 * 
	 * @return List d'item
	 * @throws DALException
	 * @throws BeanException 
	 */
	List<T> get() throws DALException, BeanException;

	/**
	 * Update un item
	 * 
	 * @param item
	 * @return true si l'update c'est bien passé
	 * @throws DALException
	 */
	boolean update(T item) throws DALException;

	/**
	 * Delete avec le type
	 * 
	 * @param item
	 * @return true si le delete c'est bien passé
	 * @throws DALException
	 */
	boolean delete(T item) throws DALException;

	/**
	 * Delete avec l'id
	 * 
	 * @param id
	 * @return true si le delete c'est bien passé
	 * @throws DALException
	 */
	boolean delete(int id) throws DALException;

	/**
	 * ItemBuilder
	 * 
	 * @param rs
	 * @return item
	 * @throws DALException
	 * @throws BeanException 
	 */
	T itemBuilder(ResultSet rs) throws DALException, BeanException;

}
