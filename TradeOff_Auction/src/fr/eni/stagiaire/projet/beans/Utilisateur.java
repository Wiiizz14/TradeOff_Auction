package fr.eni.stagiaire.projet.beans;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fr.eni.stagiaire.projet.utils.Parametre;

/**
 * 
 * @author dbondigu2018
 *
 */
public class Utilisateur {

	int noUtilisateur;
	String pseudo;
	String nom;
	String prenom;
	String email;
	String telephone;
	String rue;
	String codePostal;
	String ville;
	String motDePasse;
	int credit;
	boolean administrateur;
	boolean archive = false;

	public Utilisateur() {
	}

	public Utilisateur(String pseudo, String nom, String prenom, String email, String telephone, String rue,
			String codePostal, String ville) throws BeanException {
		setPseudo(pseudo);
		setNom(nom);
		setPrenom(prenom);
		setEmail(email);
		setTelephone(telephone);
		setRue(rue);
		setCodePostal(codePostal);
		setVille(ville);
		setCredit(Parametre.lireInt("CREDITS_INITIAUX"));
	}
	
	public Utilisateur(String pseudo, String nom, String prenom, String email, String telephone,
			String rue, String codePostal, String ville, boolean administrateur) throws BeanException {
		this(pseudo, nom, prenom, email, telephone, rue, codePostal, ville);
		setAdministrateur(administrateur);
}

	public Utilisateur(int noUtilisateur, String pseudo, String nom, String prenom, String email, String telephone,
			String rue, String codePostal, String ville, String motDePasse, boolean administrateur) throws BeanException {
		this(pseudo, nom, prenom, email, telephone, rue, codePostal, ville, administrateur);
		setNoUtilisateur(noUtilisateur);
		setMotDePasse(motDePasse);
	}
		
		

	public int getNoUtilisateur() {
		return noUtilisateur;
	}

	public void setNoUtilisateur(int noUtilisateur) {
		this.noUtilisateur = noUtilisateur;
	}

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) throws BeanException {
		if(email == null || isValidEmail(email)==true) {
		this.email = email;
		}else {
			throw new BeanException();
		}
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) throws BeanException {
		if(telephone==null || isValidTelephone(telephone)==true) {
			this.telephone = telephone;
		}else {
			throw new BeanException();
		}
	}

	public String getRue() {
		return rue;
	}

	public void setRue(String rue) {
		this.rue = rue;
	}

	public String getCodePostal() {
		return codePostal;
	}

	public void setCodePostal(String codePostal) {
		this.codePostal = codePostal;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	public String getMotDePasse() {
		return motDePasse;
	}

	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}

	public int getCredit() {
		return credit;
	}

	public void setCredit(int credit) {
		this.credit = credit;
	}

	public boolean isAdministrateur() {
		return administrateur;
	}

	public void setAdministrateur(boolean administrateur) {
		this.administrateur = administrateur;
	}
	
	/**
	 * @return the archive
	 */
	public boolean isArchive() {
		return archive;
	}

	/**
	 * @param archive the archive to set
	 */
	public void setArchive(boolean archive) {
		this.archive = archive;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Utilisateur other = (Utilisateur) obj;
		if (administrateur != other.administrateur)
			return false;
		if (archive != other.archive)
			return false;
		if (codePostal == null) {
			if (other.codePostal != null)
				return false;
		} else if (!codePostal.equals(other.codePostal))
			return false;
		if (credit != other.credit)
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (motDePasse == null) {
			if (other.motDePasse != null)
				return false;
		} else if (!motDePasse.equals(other.motDePasse))
			return false;
		if (noUtilisateur != other.noUtilisateur)
			return false;
		if (nom == null) {
			if (other.nom != null)
				return false;
		} else if (!nom.equals(other.nom))
			return false;
		if (prenom == null) {
			if (other.prenom != null)
				return false;
		} else if (!prenom.equals(other.prenom))
			return false;
		if (pseudo == null) {
			if (other.pseudo != null)
				return false;
		} else if (!pseudo.equals(other.pseudo))
			return false;
		if (rue == null) {
			if (other.rue != null)
				return false;
		} else if (!rue.equals(other.rue))
			return false;
		if (telephone == null) {
			if (other.telephone != null)
				return false;
		} else if (!telephone.equals(other.telephone))
			return false;
		if (ville == null) {
			if (other.ville != null)
				return false;
		} else if (!ville.equals(other.ville))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Utilisateur [noUtilisateur=" + noUtilisateur + ", pseudo=" + pseudo + ", nom=" + nom + ", prenom="
				+ prenom + ", email=" + email + ", telephone=" + telephone + ", rue=" + rue + ", codePostal="
				+ codePostal + ", ville=" + ville + ", motDePasse=" + motDePasse + ", credit=" + credit
				+ ", administrateur=" + administrateur + ", archivé= " + (archive?"oui":"non") + "]";
	}

	// méthode pour vérifier le format de l'adress mail
	public static boolean isValidEmail(String email) {
        String emailPattern = "^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        Pattern p = Pattern.compile(emailPattern);
        Matcher m = p.matcher(email);
        return m.matches();
    }
	
	//méthode pour vérifier le format du numéro de téléphone
	
	public static boolean isValidTelephone(String telephone) {
		String telPattern= "^(0|\\+33)[1-9]([-. ]?[0-9]{2}){4}$";
		Pattern p = Pattern.compile(telPattern);
        Matcher m = p.matcher(telephone);
        return m.matches();
		
	}
	
	
	
	
	
}
