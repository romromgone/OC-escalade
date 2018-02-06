package com.ocp3.beans;

public class Utilisateur {
	private Long id;
	private String nom;
	private String prenom;
	private String mail;
	private String codePostal;
	private String mdp;
	
	public Long getId() {
		return id;
	}
	public void setId( Long id ) {
		this.id = id;
	}
	
	public String getNom() {
		return nom;
	}
	public void setNom( String nom ) {
		this.nom = nom;
	}
	
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom( String prenom ) {
		this.prenom = prenom;
	}
	
	public String getCodePostal() {
		return codePostal;
	}
	public void setCodePostal( String codePostal ) {
		this.codePostal = codePostal;
	}
	
	public String getMail() {
		return mail;
	}
	public void setMail( String mail ) {
		this.mail = mail;
	}
	
	public String getMdp() {
		return mdp;
	}
	public void setMdp( String mdp ) {
		this.mdp = mdp;
	}
	
}
