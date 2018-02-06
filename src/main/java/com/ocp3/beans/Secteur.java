package com.ocp3.beans;

public class Secteur {
	private Integer noSecteur;
	private String nom;
	private String description;
	private Long idSite;
	
	private Site site;

	
	public Integer getNoSecteur() {
		return noSecteur;
	}
	public void setNoSecteur(Integer noSecteur) {
		this.noSecteur = noSecteur;
	}

	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public Long getIdSite() {
		return idSite;
	}
	public void setIdSite(Long idSite) {
		this.idSite = idSite;
	}

	
	public Site getSite() {
		return site;
	}
	public void setSite(Site site) {
		this.site = site;
	}
	

}
