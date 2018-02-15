package com.ocp3.beans;

import java.sql.Timestamp;


public class CommentaireSecteur {
	private Timestamp dateCSe;
	private String texteCSe;
	private Long idUser;
	private Integer noSecteur;
	private Long idSite;
	
	private Utilisateur utilisateur;
	private Site site;
	
	
	public Timestamp getDateCSe() {
		return dateCSe;
	}
	public void setDateCSe(Timestamp dateCSe) {
		this.dateCSe = dateCSe;
	}
	
	public String getTexteCSe() {
		return texteCSe;
	}
	public void setTexteCSe(String texteCSe) {
		this.texteCSe = texteCSe;
	}
	
	public Long getIdUser() {
		return idUser;
	}
	public void setIdUser(Long idUser) {
		this.idUser = idUser;
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
	
	
	public Utilisateur getUtilisateur() {
		return utilisateur;
	}
	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}
	
	public Site getSite() {
		return site;
	}
	public void setSite(Site site) {
		this.site = site;
	}
	

}
