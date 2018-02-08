package com.ocp3.beans;

import java.sql.Timestamp;


public class CommentaireTopo {
	private Timestamp dateCT;
	private String texteCT;
	private Long idUser;
	private Long idTopo;
	
	private Utilisateur utilisateur;
	private Topo topo;
	
	
	public Timestamp getDateCT() {
		return dateCT;
	}
	public void setDateCT(Timestamp dateCT) {
		this.dateCT = dateCT;
	}
	
	public String getTexteCT() {
		return texteCT;
	}
	public void setTexteCT(String textCT) {
		this.texteCT = textCT;
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
