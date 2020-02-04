/**
 * 
 */
package fr.eni.stagiaire.projet.utils;

import java.util.ResourceBundle;

/**
 * Classe permettant de recuperer des paramétres stockés dans le fichier parametres.properties.
 * Son but est de prévenir un changement de paramétres constant du code dans l'avenir.
 * @author yledouge2018
 *
 */
public class Parametre {

	public static String lireString(String cle) {
		
		ResourceBundle bundle = ResourceBundle.getBundle("fr.eni.stagiaire.projet.utils.parametres");
		if (bundle != null) {
			return bundle.getString(cle);
		} else {
			return null;
		}
	}
	
public static Integer lireInt(String cle) {
		
		ResourceBundle bundle = ResourceBundle.getBundle("fr.eni.stagiaire.projet.utils.parametres");
		if (bundle != null) {
			return Integer.valueOf(bundle.getString(cle));
		} else {
			return null;
		}
	}
	
}
