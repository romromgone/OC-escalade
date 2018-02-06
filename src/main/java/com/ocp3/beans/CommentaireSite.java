package com.ocp3.beans;

import java.util.Date;

public class CommentaireSite {
	private Date dateCSi;
	private String textCSi;
	private Long idUser;
	private Long idSite;
	
	private Utilisateur utilisateur;
	private Site site;
	
	
	public Date getDateCSi() {
		return dateCSi;
	}
	public void setDateCSi(Date dateCSi) {
		this.dateCSi = dateCSi;
	}
	
	public String getTextCSi() {
		return textCSi;
	}
	public void setTextCSi(String textCSi) {
		this.textCSi = textCSi;
	}
	
	public Long getIdUser() {
		return idUser;
	}
	public void setIdUser(Long idUser) {
		this.idUser = idUser;
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
