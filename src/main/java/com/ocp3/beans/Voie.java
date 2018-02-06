package com.ocp3.beans;

public class Voie {
	private Integer noVoie;
	private String nom;
	private String cotation;
	private Integer nbPoints;
	private Integer hauteur;
	private String ouvreur;
	private String description;
	private Integer noSecteur;
	private Long idSite;
	
	private Secteur secteur;

	
	public Integer getNoVoie() {
		return noVoie;
	}
	public void setNoVoie(Integer noVoie) {
		this.noVoie = noVoie;
	}

	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getCotation() {
		return cotation;
	}
	public void setCotation(String cotation) {
		this.cotation = cotation;
	}

	public Integer getNbPoints() {
		return nbPoints;
	}
	public void setNbPoints(Integer nbPoints) {
		this.nbPoints = nbPoints;
	}

	public Integer getHauteur() {
		return hauteur;
	}
	public void setHauteur(Integer hauteur) {
		this.hauteur = hauteur;
	}

	public String getOuvreur() {
		return ouvreur;
	}
	public void setOuvreur(String ouvreur) {
		this.ouvreur = ouvreur;
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getNoSecteur() {
		return noSecteur;
	}
	public void setNoSecteur(Integer noSecteur) {
		this.noSecteur = noSecteur;
	}

	public Long getIdSite() {
		return idSite;
	}
	public void setIdSite(Long idSite) {
		this.idSite = idSite;
	}

	
	public Secteur getSecteur() {
		return secteur;
	}
	public void setSecteur(Secteur secteur) {
		this.secteur = secteur;
	}
	

}
