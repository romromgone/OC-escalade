package com.ocp3.beans;

public class Longueur {
	private Integer noLongueur;
	private Integer hauteurRelais;
	private String cotationLongueur;
	private Integer noVoie;
	private Integer noSecteur;
	private Long idSite;
	
	private Voie voie;

	public Integer getNoLongueur() {
		return noLongueur;
	}
	public void setNoLongueur(Integer noLongueur) {
		this.noLongueur = noLongueur;
	}

	public Integer getHauteurRelais() {
		return hauteurRelais;
	}
	public void setHauteurRelais(Integer hauteurRelais) {
		this.hauteurRelais = hauteurRelais;
	}

	public String getCotationLongueur() {
		return cotationLongueur;
	}
	public void setCotationLongueur(String cotationLongueur) {
		this.cotationLongueur = cotationLongueur;
	}

	public Integer getNoVoie() {
		return noVoie;
	}
	public void setNoVoie(Integer noVoie) {
		this.noVoie = noVoie;
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

	
	public Voie getVoie() {
		return voie;
	}
	public void setVoie(Voie voie) {
		this.voie = voie;
	}

}
