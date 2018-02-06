package com.ocp3.beans;

import java.util.Date;

public class CommentaireTopo {
	private Date dateCT;
	private String textCT;
	private Long idUser;
	private Long idTopo;
	
	private Utilisateur utilisateur;
	private Topo topo;
	
	
	public Date getDateCT() {
		return dateCT;
	}
	public void setDateCT(Date dateCT) {
		this.dateCT = dateCT;
	}
	
	public String getTextCT() {
		return textCT;
	}
	public void setTextCT(String textCT) {
		this.textCT = textCT;
	}
	
	public Long getIdUser() {
		return idUser;
	}
	public void setIdUser(Long idUser) {
		this.idUser = idUser;
	}
	
	public Long getIdTopo() {
		return idTopo;
	}
	public void setIdTopo(Long idTopo) {
		this.idTopo = idTopo;
	}
	
	
	public Utilisateur getUtilisateur() {
		return utilisateur;
	}
	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}
	
	public Topo getTopo() {
		return topo;
	}
	public void setTopo(Topo topo) {
		this.topo = topo;
	}
	
}
