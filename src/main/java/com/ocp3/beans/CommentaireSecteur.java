package com.ocp3.beans;

import java.util.Date;

public class CommentaireSecteur {
	private Date dateCSe;
	private String textCSe;
	private Long idUser;
	private Integer noSecteur;
	private Long idSite;
	
	private Utilisateur utilisateur;
	private Site site;
	
	
	public Date getDateCSe() {
		return dateCSe;
	}
	public void setDateCSe(Date dateCSe) {
		this.dateCSe = dateCSe;
	}
	
	public String getTextCSe() {
		return textCSe;
	}
	public void setTextCSe(String textCSe) {
		this.textCSe = textCSe;
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
