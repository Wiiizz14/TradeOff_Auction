package fr.eni.stagiaire.projet.beans;

import java.util.Date;

/**
 * 
 * @author dbondigu2018
 *
 */
public class Enchere {
	
	Date dateEnchere;
	int montant;
	Utilisateur utilisateur;
	Vente vente;
	boolean archive = false;
	
	
	public Enchere() {
	}


	public Enchere(Date dateEnchere, Utilisateur utilisateur, Vente vente, int montant) {
		setDateEnchere(dateEnchere);
		setUtilisateur(utilisateur);
		setVente(vente);
		setMontant(montant);
	}


	public Date getDateEnchere() {
		return dateEnchere;
	}


	public void setDateEnchere(Date dateEnchere) {
		this.dateEnchere = dateEnchere;
	}

	/**
	 * @return the montant
	 */
	public int getMontant() {
		return montant;
	}

	/**
	 * @param montant the montant to set
	 */
	public void setMontant(int montant) {
		this.montant = montant;
	}


	public Utilisateur getUtilisateur() {
		return utilisateur;
	}


	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}


	public Vente getVente() {
		return vente;
	}


	public void setVente(Vente vente) {
		this.vente = vente;
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


	@Override
	public String toString() {
		return "Enchere [dateEnchere=" + dateEnchere + ", utilisateur=" + utilisateur + ", vente=" + vente + ", archivé= " + (archive?"oui":"non") + "]";
	}

}
