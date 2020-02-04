package fr.eni.stagiaire.projet.beans;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * @author dbondigu2018
 *
 */
public class Vente {
	
	int noVente;
	String nomArticle;
	String description;
	Date dateFinEncheres;
	String dateFinEncheresFormate;
	int prixInitial;
	int prixVente;
	Utilisateur utilisateur;
	Categorie categorie;
	String rueRetrait;
	String codePostalRetrait;
	String villeRetrait;
	boolean termine = false;
	boolean archive = false;
	
	public Vente() {
	}


	public Vente(String nomArticle, String description, Date dateFinEncheres, int prixInitial,
			int prixVente, Utilisateur utilisateur, Categorie categorie) {
		setNomArticle(nomArticle);
		setDescription(description);
		setDateFinEncheres(dateFinEncheres);
		setPrixInitial(prixInitial);
		setPrixVente(prixVente);
		setUtilisateur(utilisateur);
		setCategorie(categorie);
	}


	public Vente(String nomArticle, String description, Date dateFinEncheres, int prixInitial, int prixVente,
			Utilisateur utilisateur, Categorie categorie, String rueRetrait, String codePostalRetrait,
			String villeRetrait) {
		this(nomArticle, description, dateFinEncheres, prixInitial, prixVente, utilisateur, categorie);
		setRueRetrait(rueRetrait);
		setCodePostalRetrait(codePostalRetrait);
		setVilleRetrait(villeRetrait);
	}


	public int getNoVente() {
		return noVente;
	}


	public void setNoVente(int noVente) {
		this.noVente = noVente;
	}


	public String getNomArticle() {
		return nomArticle;
	}


	public void setNomArticle(String nomArticle) {
		this.nomArticle = nomArticle;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public Date getDateFinEncheres() {
		return dateFinEncheres;
	}


	public void setDateFinEncheres(Date dateFinEncheres) {
		this.dateFinEncheres = dateFinEncheres;
		this.setDateFinEncheresFormate();
	}


	public int getPrixInitial() {
		return prixInitial;
	}


	public void setPrixInitial(int prixInitial) {
		this.prixInitial = prixInitial;
	}


	public int getPrixVente() {
		return prixVente;
	}


	public void setPrixVente(int prixVente) {
		this.prixVente = prixVente;
	}


	public Utilisateur getUtilisateur() {
		return utilisateur;
	}


	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}


	public Categorie getCategorie() {
		return categorie;
	}


	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}

	/**
	 * @return the rueRetrait
	 */
	public String getRueRetrait() {
		return rueRetrait;
	}


	/**
	 * @param rueRetrait the rueRetrait to set
	 */
	public void setRueRetrait(String rueRetrait) {
		this.rueRetrait = rueRetrait;
	}


	/**
	 * @return the codePostalRetrait
	 */
	public String getCodePostalRetrait() {
		return codePostalRetrait;
	}


	/**
	 * @param codePostalRetrait the codePostalRetrait to set
	 */
	public void setCodePostalRetrait(String codePostalRetrait) {
		this.codePostalRetrait = codePostalRetrait;
	}


	/**
	 * @return the villeRetrait
	 */
	public String getVilleRetrait() {
		return villeRetrait;
	}


	/**
	 * @param villeRetrait the villeRetrait to set
	 */
	public void setVilleRetrait(String villeRetrait) {
		this.villeRetrait = villeRetrait;
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

	/**
	 * @return the dateFinEncheresFormate
	 */
	public String getDateFinEncheresFormate() {
		return dateFinEncheresFormate;
	}


	/**
	 * @param dateFinEncheresFormate the dateFinEncheresFormate to set
	 */
	private void setDateFinEncheresFormate() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		this.dateFinEncheresFormate = sdf.format(this.getDateFinEncheres());
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
		Vente other = (Vente) obj;
		if (archive != other.archive)
			return false;
		if (categorie == null) {
			if (other.categorie != null)
				return false;
		} else if (!categorie.equals(other.categorie))
			return false;
		if (codePostalRetrait == null) {
			if (other.codePostalRetrait != null)
				return false;
		} else if (!codePostalRetrait.equals(other.codePostalRetrait))
			return false;
		if (dateFinEncheres == null) {
			if (other.dateFinEncheres != null)
				return false;
		} else if (!dateFinEncheres.equals(other.dateFinEncheres))
			return false;
		if (dateFinEncheresFormate == null) {
			if (other.dateFinEncheresFormate != null)
				return false;
		} else if (!dateFinEncheresFormate.equals(other.dateFinEncheresFormate))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (noVente != other.noVente)
			return false;
		if (nomArticle == null) {
			if (other.nomArticle != null)
				return false;
		} else if (!nomArticle.equals(other.nomArticle))
			return false;
		if (prixInitial != other.prixInitial)
			return false;
		if (prixVente != other.prixVente)
			return false;
		if (rueRetrait == null) {
			if (other.rueRetrait != null)
				return false;
		} else if (!rueRetrait.equals(other.rueRetrait))
			return false;
		if (utilisateur == null) {
			if (other.utilisateur != null)
				return false;
		} else if (!utilisateur.equals(other.utilisateur))
			return false;
		if (villeRetrait == null) {
			if (other.villeRetrait != null)
				return false;
		} else if (!villeRetrait.equals(other.villeRetrait))
			return false;
		return true;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Vente [noVente=" + noVente + ", nomArticle=" + nomArticle + ", description=" + description
				+ ", dateFinEncheres=" + dateFinEncheres + ", prixInitial=" + prixInitial + ", prixVente=" + prixVente
				+ ", utilisateur=" + utilisateur + ", categorie=" + categorie + ", rueRetrait=" + rueRetrait
				+ ", codePostalRetrait=" + codePostalRetrait + ", villeRetrait=" + villeRetrait + ", archive=" + archive
				+ "]";
	}

}
